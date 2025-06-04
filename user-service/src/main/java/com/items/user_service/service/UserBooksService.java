package com.items.user_service.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.items.user_service.DTO.Book;
import com.items.user_service.config.BooksClient;
import com.items.user_service.model.UserBooks;
import com.items.user_service.repository.UserBooksRepository;
import java.util.*;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

@Service
public class UserBooksService {
    @Autowired
    private UserBooksRepository userBooksRepository;
    @Autowired
    private BooksClient booksClient;

    public ResponseEntity<?> addBookToUser(String userId, List<Long> bookIds) {
        userBooksRepository.addBooksForUser(userId, bookIds);
        return ResponseEntity.ok("Book added!");
    }

    public List<Long> getUserBooks(String userId) {
        return userBooksRepository.getBooksForUser(userId);
    }

    public Page<Book> getUserFullBooks(String userId, Pageable pageable, @Nullable String name) {
        Optional<UserBooks> userBooksOptional = userBooksRepository.findById(userId);

        if (userBooksOptional.isPresent()) {
            List<Long> bookIds = userBooksOptional.get().getBookIds();

            if (bookIds.isEmpty()) {
                return Page.empty(pageable);
            }

            List<Book> allBooks = bookIds.stream()
                    .map(id -> {
                        try {
                            return booksClient.getBookById(id);
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (name != null && !name.isEmpty()) {
                allBooks = allBooks.stream()
                        .filter(book -> book.getName() != null &&
                                book.getName().toLowerCase().contains(name.toLowerCase()))
                        .collect(Collectors.toList());
            }

            if (pageable.getSort().isSorted()) {
                Comparator<Book> comparator = null;

                for (Sort.Order order : pageable.getSort()) {
                    Comparator<Book> orderComparator;

                    switch (order.getProperty()) {
                        case "name":
                            orderComparator = Comparator.comparing(
                                    (Book book) -> Optional.ofNullable(book.getName()).orElse(""),
                                    Comparator.naturalOrder());
                            break;
                        case "price":
                            orderComparator = Comparator.comparing(
                                    (Book book) -> Optional.ofNullable(book.getPrice()).orElse(0),
                                    Comparator.naturalOrder());
                            break;
                        case "pd":
                            orderComparator = Comparator.comparing(
                                    (Book book) -> Optional.ofNullable(book.getPd()).orElse(null),
                                    Comparator.nullsLast(Comparator.naturalOrder()));
                            break;
                        default:
                            continue;
                    }

                    if (order.getDirection() == Sort.Direction.DESC) {
                        orderComparator = orderComparator.reversed();
                    }

                    comparator = comparator == null ? orderComparator : comparator.thenComparing(orderComparator);
                }

                if (comparator != null) {
                    allBooks.sort(comparator);
                }
            }

            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), allBooks.size());

            if (start > end) {
                return Page.empty(pageable);
            }

            List<Book> pagedBooks = allBooks.subList(start, end);
            return new PageImpl<>(pagedBooks, pageable, allBooks.size());
        }

        return Page.empty(pageable);
    }

    public void deleteBookForUser(String userId, Long bookId) {
        UserBooks userBooks = userBooksRepository.findById(userId).get();
        userBooks.getBookIds().removeIf(id -> id.equals(bookId));
        userBooksRepository.save(userBooks);
    }
}

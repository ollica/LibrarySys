package com.items.books.service;

import java.util.List;
import java.util.Map;
import java.lang.IllegalStateException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.items.books.exception.BookNotFoundException;
import com.items.books.model.Book;
import com.items.books.repository.BookRepository;
import jakarta.transaction.Transactional;

@Service
public class BookService {

    @Autowired // can be omitted
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Page<Book> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public List<Book> getBooksByIds(List<Long> bookIds) {
        return bookRepository.findByIdIn(bookIds);
    }

    public List<Book> findAllOrderByPriceDesc() {
        return bookRepository.findAllOrderByPriceDesc();
    }

    public List<Map<String, Object>> countBooks() {
        return bookRepository.countBooks();
    }

    public void addBook(Book book) {
        Optional<Book> bookOptional = bookRepository.findByIsbn(book.getIsbn());
        if (bookOptional.isPresent()) {
            throw new IllegalStateException("isbn已被使用");
        }
        bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        boolean exists = bookRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("书 " + id + " 不存在");
        }
        bookRepository.deleteById(id);
    }

    @Transactional
    public void updateBook(Long id, Book book) {
        bookRepository.findById(id)
                .map(updatedBook -> {
                    updatedBook.setName(book.getName());
                    updatedBook.setPrice(book.getPrice());
                    updatedBook.setPd(book.getPd());
                    updatedBook.setIsbn(book.getIsbn());
                    return bookRepository.save(updatedBook);
                })
                .orElseThrow(() -> new IllegalStateException(
                        "书 " + id + " 不存在"));
    }

    public Page<Book> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        return bookRepository.findByNameContainingIgnoreCase(name, pageable);
    }
}

package com.items.user_service.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.items.user_service.model.UserBooks;

public interface UserBooksRepository extends MongoRepository<UserBooks, String> {

    default void addBooksForUser(String userId, List<Long> bookId) {
        Optional<UserBooks> userBooksOptional = findById(userId);

        if (userBooksOptional.isPresent()) {
            UserBooks userBooks = userBooksOptional.get();
            Set<Long> existingIds = new HashSet<>(userBooks.getBookIds());

            for (Long id : bookId) {
                if (!existingIds.contains(id)) {
                    userBooks.getBookIds().add(id);
                }
            }
            save(userBooks);
        } else {
            UserBooks userBooks = new UserBooks();
            userBooks.setUsername(userId);
            // 删除bookId列表中重复项
            List<Long> uniqueBookIds = new ArrayList<>(new LinkedHashSet<>(bookId));
            userBooks.setBookIds(uniqueBookIds);
            save(userBooks);
        }
    }

    default List<Long> getBooksForUser(String userId) {
        return findById(userId)
                .map(UserBooks::getBookIds)
                .orElse(Collections.emptyList());
    }
}

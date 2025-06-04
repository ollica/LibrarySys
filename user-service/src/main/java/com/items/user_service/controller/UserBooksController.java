package com.items.user_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.items.user_service.DTO.Book;
import com.items.user_service.service.UserBooksService;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/user-books")
public class UserBooksController {
    @Autowired
    private UserBooksService userBooksService;

    @PostMapping("/{userId}/add")
    public void addBookToUser(@PathVariable String userId, @RequestBody List<Long> bookIds) {
        userBooksService.addBookToUser(userId, bookIds);
    }

    @DeleteMapping("/{userId}/book/{bookId}")
    public void deleteBookForUser(@PathVariable String userId, @PathVariable Long bookId) {
        userBooksService.deleteBookForUser(userId, bookId);
    }

    @GetMapping("/{userId}")
    public List<Long> getUserBooks(@PathVariable String userId) {
        return userBooksService.getUserBooks(userId);
    }

    @GetMapping("/{userId}/full-books")
    public Page<Book> getBooks(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) List<String> sort,
            @RequestParam(required = false) String name) {
        List<Sort.Order> orders = sort != null ? sort.stream()
                .map(s -> s.split(","))
                .filter(arr -> arr.length >= 1)
                .map(arr -> {
                    String property = arr[0];
                    String direction = arr.length > 1 ? arr[1] : "asc";
                    return new Sort.Order(Sort.Direction.fromString(direction), property);
                })
                .collect(Collectors.toList()) : null;

        Pageable pageable = PageRequest.of(page, size, orders != null ? Sort.by(orders) : Sort.unsorted());
        System.out.println("Sorting by: " + orders);

        return userBooksService.getUserFullBooks(userId, pageable, name);
    }
}

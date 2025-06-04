package com.items.user_service.config;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.items.user_service.DTO.Book;

@FeignClient(name = "book")
public interface BooksClient {
    @GetMapping("/books/{Id}")
    Book getBookById(@PathVariable("Id") Long bookId);

    // 批量提取
    @PostMapping("/books/batch")
    List<Book> findByIds(@RequestBody List<Long> bookIds);

}

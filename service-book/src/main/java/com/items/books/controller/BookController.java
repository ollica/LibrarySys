package com.items.books.controller;

import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.items.books.model.Book;
import com.items.books.service.BookService;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping(path = "/books")
public class BookController {

    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/allbooks")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping
    public Page<Book> getBooks(
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
        if (name != null && !name.isEmpty()) {
            return bookService.findByNameContainingIgnoreCase(name, pageable);
        }

        return bookService.getBooks(pageable);
    }

    @GetMapping("/group-by-price")
    public List<Book> findAllOrderByPriceDesc() {
        return bookService.findAllOrderByPriceDesc();
    }

    @GetMapping("/amount-of-books")
    public List<Map<String, Object>> countBooks() {
        return bookService.countBooks();
    }

    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable("bookId") Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/batch")
    public List<Book> getBooksByIds(@RequestBody List<Long> bookIds) {
        return bookService.getBooksByIds(bookIds);
    }

    @PostMapping
    public void addNewBook(@RequestBody Book book) {
        bookService.addBook(book);
    }

    @DeleteMapping(path = "{bookId}")
    public void deleteBook(@PathVariable("bookId") Long id) {
        bookService.deleteBook(id);
    }

    @PutMapping(path = "{bookId}")
    public void updateBook(
            @RequestBody Book book,
            @PathVariable("bookId") Long id) {
        bookService.updateBook(id, book);
    }
}
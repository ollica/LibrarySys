package com.items.books.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.items.books.model.Book;
import com.items.books.service.BookService;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping(path = "api/items/books")
public class BookController {

    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable("bookId") Long id) {
        return bookService.getBookById(id);
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
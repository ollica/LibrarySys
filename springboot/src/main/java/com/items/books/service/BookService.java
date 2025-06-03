package com.items.books.service;

import java.util.List;
import java.lang.IllegalStateException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public void addBook(Book book) {
        Optional<Book> bookOptional = bookRepository.findByIsbn(book.getIsbn());
        if (bookOptional.isPresent()) {
            throw new IllegalStateException("isbn taken");
        }
        bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        boolean exists = bookRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("book with id " + id + " dees not exists");
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
                        "book with id " + id + " dees not exists"));
    }
}

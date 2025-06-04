package com.items.books.exception;

//Custom
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("无法找到 " + id);
    }
}

package com.items.user_service.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Book {
    private Long id;
    private String name;
    private Integer price;
    private LocalDate pd;
    private String isbn;
    private Integer year;
}

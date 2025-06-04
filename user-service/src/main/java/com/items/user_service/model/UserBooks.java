package com.items.user_service.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_books")
public class UserBooks {
    @Id
    private String username;
    private List<Long> bookIds;

    public UserBooks(String username) {
        this.username = username;
    }

}
package com.items.books.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.items.books.model.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.isbn = ?1")
    Optional<Book> findByIsbn(String isbn);

    @Query("SELECT b FROM Book b ORDER BY b.price DESC")
    List<Book> findAllOrderByPriceDesc();

    @Query(value = "SELECT pd, COUNT(*) AS totalBooks FROM library.book GROUP BY pd ORDER BY pd DESC", nativeQuery = true)
    List<Map<String, Object>> countBooks();

    Page<Book> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Book> findByIdIn(List<Long> bookIds);
}

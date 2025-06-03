package com.items.books.model;

import java.time.LocalDate;
import java.time.Period;

import jakarta.persistence.*;

@Entity
@Table
public class Book {
	@Id
	@SequenceGenerator(name = "book_sequence", sequenceName = "book_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
	private Long id;
	private String name;
	private Integer price;
	private LocalDate pd;
	private String isbn;
	@Transient
	private Integer year;

	public Integer getYear() {
		if (pd == null)
			return 0;
		return Period.between(this.pd, LocalDate.now()).getYears();
	}

	public Book(String name,
			Integer price,
			LocalDate pd,
			String isbn) {
		this.name = name;
		this.price = price;
		this.pd = pd;
		this.isbn = isbn;
	}

	public Book(Long id,
			String name,
			Integer price,
			LocalDate pd,
			String isbn) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.pd = pd;
		this.isbn = isbn;
	}

	public Book() {
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public LocalDate getPd() {
		return pd;
	}

	public void setPd(LocalDate pd) {
		this.pd = pd;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", price=" + price + ", pd=" + pd + ", isbn=" + isbn + ", year="
				+ year + ", getYear()=" + getYear() + ", getIsbn()=" + getIsbn() + ", getClass()=" + getClass()
				+ ", getId()=" + getId() + ", getName()=" + getName() + ", getPrice()=" + getPrice() + ", getPd()="
				+ getPd() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}

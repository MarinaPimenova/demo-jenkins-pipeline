package com.mp.model.book;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Book extends RepresentationModel<Book> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String author;
    private String ISBN;
    private String title;
    @Temporal(TemporalType.DATE)
    private Date addedDate;
    private int amount;

    public Book(String isbn, String title, String author) {
        this.ISBN = isbn;
        this.title = title;
        this.author = author;
        this.addedDate = new Date();
        this.amount = 1;
    }

    public Book() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

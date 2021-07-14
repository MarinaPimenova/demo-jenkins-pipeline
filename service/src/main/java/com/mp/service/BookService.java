package com.mp.service;

import com.mp.model.book.Book;

import java.util.List;
import java.util.Map;

public interface BookService {
    Book addBook(String isbn);

    List<Book> getStoredBooks(Map<String, List<String>> matrixVars);

    Book getBook(String isbn);

    void updateNumberOfAvailableBooksForOrder(String isbn, Integer amount);
}

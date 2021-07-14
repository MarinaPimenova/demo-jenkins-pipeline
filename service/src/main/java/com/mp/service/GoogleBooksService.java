package com.mp.service;

import com.mp.model.book.Book;

public interface GoogleBooksService {
    Book findByIsbn(String isbn);
}

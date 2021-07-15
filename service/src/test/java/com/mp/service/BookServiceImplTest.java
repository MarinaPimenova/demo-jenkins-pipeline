package com.mp.service;

import com.mp.model.book.Book;
import com.mp.model.book.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;

public class BookServiceImplTest {
    private BookServiceImpl bookService;
    @Mock
    private GoogleBooksService googleBooksService;
    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        openMocks(this);
        bookService = new BookServiceImpl(googleBooksService, bookRepository);
    }

    @Test
    public void shouldReturnBook() {
        //given
        Book book = new Book("isbn", "title", "Author");
        //Book book = googleBooksService.findByIsbn(isbn);
        given(googleBooksService.findByIsbn(anyString())).willReturn(book);
        //bookRepository.save(book);
        given(bookRepository.save(any(Book.class))).willReturn(book);
        //when
        Book result = bookService.addBook("isbn");
        //then
        assertEquals(book.getAuthor(), result.getAuthor());
    }
}

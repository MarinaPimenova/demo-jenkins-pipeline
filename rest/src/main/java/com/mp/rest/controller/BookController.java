package com.mp.rest.controller;

import com.mp.model.book.Book;
import com.mp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/book/{isbn}")
    public ResponseEntity<Book> addBookByIsbn(@PathVariable(name = "isbn") String isbn) {
        Book book = bookService.addBook(isbn);
        book.add(linkTo(methodOn(BookController.class).addBookByIsbn(isbn)).withSelfRel());
        return ResponseEntity.ok(book);
    }

    @GetMapping("/books/{book}")
    public ResponseEntity<List<Book>> getBooks(@MatrixVariable Map<String, List<String>> matrixVars) {
        return ResponseEntity.ok(bookService.getStoredBooks(matrixVars));
    }

    @GetMapping("/book/info/{isbn}")
    public ResponseEntity<Book> getBook(@PathVariable(name = "isbn") String isbn) {
        return ResponseEntity.ok(bookService.getBook(isbn));
    }

    @GetMapping("/book/upd/{isbn}")
    public ResponseEntity<?> updateBook(@PathVariable(name = "isbn") String isbn,
                                        @RequestParam(defaultValue = "1") Integer amount) {
        bookService.updateNumberOfAvailableBooksForOrder(isbn, amount);
        return ResponseEntity.noContent().build();
    }
}

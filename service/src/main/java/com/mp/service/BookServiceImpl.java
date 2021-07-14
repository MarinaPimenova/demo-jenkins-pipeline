package com.mp.service;

import com.mp.exception.BookException;
import com.mp.model.book.Book;
import com.mp.model.book.BookRepository;
import com.mp.model.book.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private GoogleBooksService googleBooksService;
    @Autowired
    private BookRepository bookRepository;

    @Transactional
    @Override
    public Book addBook(String isbn) {
        Book book = googleBooksService.findByIsbn(isbn);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getStoredBooks(Map<String, List<String>> matrixVars) {
        if(CollectionUtils.isEmpty(matrixVars)) {
            return (List<Book>) bookRepository.findAll();
        }
        Filter filter = new Filter(matrixVars);
        return (List<Book>) bookRepository.findAll(filter, prepareSortExpression(matrixVars));
    }

    private Sort prepareSortExpression(Map<String, List<String>> matrixVars) {
        String[] properties = matrixVars.keySet().toArray(new String[0]);
        return Sort.by(Sort.Direction.ASC, properties);
    }

    @Override
    public Book getBook(String isbn) {
        return bookRepository.findByISBN(isbn).orElseThrow(() -> new BookException("Failed to find book by ISBN: " + isbn));
    }

    @Transactional
    @Override
    public void updateNumberOfAvailableBooksForOrder(String isbn, Integer amount) {
        if(bookRepository.updateBook(amount, isbn) <= 0) {
            throw new BookException("Failed to update book with ISBN: " + isbn);
        }
    }
}

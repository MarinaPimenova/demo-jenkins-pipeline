package com.mp.model.book;

import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class BookSpec {
    public static Specification<Book> hasAddedDate(Date date) {
        return (root, query, builder) -> builder.equal(root.get("addedDate"), date);
    }

    public static Specification<Book> hasIsbn(String isbn) {
        return (root, query, builder) -> builder.equal(root.get("ISBN"), isbn);
    }

    public static Specification<Book> hasTitle(String title) {
        return (root, query, builder) -> builder.equal(root.get("title"), title);
    }

    public static Specification<Book> hasAuthor(String author) {
        return (Specification<Book>) (root, query, builder) -> builder.equal(root.get("author"), author);
    }
}

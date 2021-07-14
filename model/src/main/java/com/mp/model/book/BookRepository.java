package com.mp.model.book;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    Optional<Book> findByISBN(String isbn);

    @Modifying
    @Query("update Book b set b.amount = :amount where b.ISBN = :isbn")
    int updateBook(@Param("amount") Integer amount,
                   @Param("isbn") String isbn);
}

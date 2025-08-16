package com.bookworm.bookworm_backend.service;

import com.bookworm.bookworm_backend.model.Book;

import java.util.List;
import java.util.Optional;

/**
 * Interface for the Book Service layer.
 * This defines the contract for all operations related to the Book entity.
 * Separating this from the controller makes the code cleaner and easier to manage.
 */
public interface BookService {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Book save(Book book);
    void deleteById(Long id);
}

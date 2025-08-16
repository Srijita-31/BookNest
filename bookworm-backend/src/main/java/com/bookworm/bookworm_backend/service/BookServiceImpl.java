package com.bookworm.bookworm_backend.service;

import com.bookworm.bookworm_backend.model.Book;
import com.bookworm.bookworm_backend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the BookService interface.
 * This class contains the business logic for managing books.
 * It acts as a middleman between the controller and the repository.
 */
@Service // Marks this class as a service component.
public class BookServiceImpl implements BookService {

    // Automatically injects the BookRepository bean.
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book save(Book book) {
        // You could add business logic here before saving, like validation.
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}

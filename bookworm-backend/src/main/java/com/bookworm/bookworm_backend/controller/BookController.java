package com.bookworm.bookworm_backend.controller;

import com.bookworm.bookworm_backend.model.Book;
import com.bookworm.bookworm_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing books.
 * This class exposes a series of endpoints for CRUD operations on books.
 * It now uses the BookService layer to handle business logic.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    // Automatically injects the BookService bean.
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Endpoint to get all books.
     * Accessible at GET http://localhost:8080/api/books
     * @return A list of all books in the database.
     */
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    /**
     * Endpoint to get a single book by its ID.
     * Accessible at GET http://localhost:8080/api/books/{id}
     * @param id The ID of the book to retrieve.
     * @return A ResponseEntity with the book if found, or a 404 Not Found status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to create a new book.
     * Accessible at POST http://localhost:8080/api/books
     * @param book The book object sent in the request body.
     * @return The newly created book with its assigned ID.
     */
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    /**
     * Endpoint to update an existing book.
     * Accessible at PUT http://localhost:8080/api/books/{id}
     * @param id The ID of the book to update.
     * @param updatedBook The updated book object.
     * @return The updated book, or a 404 Not Found status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        return bookService.findById(id)
                .map(existingBook -> {
                    existingBook.setTitle(updatedBook.getTitle());
                    existingBook.setAuthor(updatedBook.getAuthor());
                    existingBook.setPublicationYear(updatedBook.getPublicationYear());
                    existingBook.setDescription(updatedBook.getDescription());
                    return ResponseEntity.ok(bookService.save(existingBook));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to delete a book by its ID.
     * Accessible at DELETE http://localhost:8080/api/books/{id}
     * @param id The ID of the book to delete.
     * @return A ResponseEntity with no content and a 200 OK status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

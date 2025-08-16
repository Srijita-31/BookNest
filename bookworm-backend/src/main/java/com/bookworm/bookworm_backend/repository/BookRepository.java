package com.bookworm.bookworm_backend.repository;

import com.bookworm.bookworm_backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository interface for the Book entity.
 * Spring Data JPA will automatically provide all the necessary CRUD (Create, Read, Update, Delete) methods.
 * We simply extend JpaRepository, which takes the Entity class (Book) and the type of its primary key (Long) as generic arguments.
 */
@Repository // Indicates that this interface is a repository component.
public interface BookRepository extends JpaRepository<Book, Long> {
    // You can add custom query methods here if needed, e.g.:
    // List<Book> findByAuthor(String author);
    // List<Book> findByTitleContaining(String title);
}

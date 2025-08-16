package com.bookworm.bookworm_backend.config;

import com.bookworm.bookworm_backend.model.Book;
import com.bookworm.bookworm_backend.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * A component to load initial data into the database on application startup.
 * Implements CommandLineRunner, which means the run() method will execute
 * automatically once the application context is loaded.
 */
//@Component
public class DataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;

     //Use constructor injection for the repository. This is the recommended practice.
    public DataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if the repository is empty to avoid adding duplicate data on every startup.
        if (bookRepository.count() == 0) {
            System.out.println("No books found. Loading initial data...");

            // Create and save a few sample books.
            Book book1 = new Book();
            book1.setTitle("The Hobbit");
            book1.setAuthor("J.R.R. Tolkien");
            book1.setPublicationYear(1937);
            book1.setDescription("A fantasy novel and a children's book.");
            bookRepository.save(book1);

            Book book2 = new Book();
            book2.setTitle("1984");
            book2.setAuthor("George Orwell");
            book2.setPublicationYear(1949);
            book2.setDescription("A dystopian social science fiction novel.");
            bookRepository.save(book2);

            Book book3 = new Book();
            book3.setTitle("To Kill a Mockingbird");
            book3.setAuthor("Harper Lee");
            book3.setPublicationYear(1960);
            book3.setDescription("A novel about the injustice of racism in the American South.");
            bookRepository.save(book3);

            System.out.println("Initial data loaded. Number of books: " + bookRepository.count());
        } else {
            System.out.println("Books already exist in the database. Skipping data load.");
        }
    }
}

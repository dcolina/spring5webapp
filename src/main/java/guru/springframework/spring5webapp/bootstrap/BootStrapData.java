package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.domain.Author;
import guru.springframework.spring5webapp.domain.Book;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
@Slf4j
public class BootStrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    public void run(String... args) {
        Author eric = Author.builder()
                .firstName("Eric")
                .lastName("Evans")
                .build();

        Book ddd = Book.builder()
                .title("Domain Driven Design")
                .isbn("123123")
                .build();

        setAuthorBookRelationship(eric, ddd);

        authorRepository.save(eric);
        bookRepository.save(ddd);

        Author rod = Author.builder()
                .firstName("Rod")
                .lastName("Johnson")
                .build();

        Book noEJB = Book.builder()
                .title("J2EE Development without EJB")
                .isbn("3939459459")
                .build();

        setAuthorBookRelationship(rod, noEJB);

        authorRepository.save(rod);
        bookRepository.save(noEJB);

        log.info("Started in Bootstrap");
        log.info(String.format("Number of Books: %s%n", bookRepository.count()));
    }

    private void setAuthorBookRelationship(Author author, Book book) {
        Set<Book> books = new HashSet<>();
        books.add(book);
        author.setBooks(books);

        Set<Author> authors = new HashSet<>();
        authors.add(author);
        book.setAuthors(authors);
    }

}

package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.domain.Author;
import guru.springframework.spring5webapp.domain.Book;
import guru.springframework.spring5webapp.domain.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
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

    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    public void run(String... args) {

        log.info(String.format("Started in Bootstrap %n"));

        Publisher publisher = Publisher.builder()
                .name("SFG Publishing")
                .city("St Petersburg")
                .state("FL")
                .build();

        publisherRepository.save(publisher);

        log.info(String.format("Publisher count: %s%n", publisherRepository.count()));

        Author eric = Author.builder()
                .firstName("Eric")
                .lastName("Evans")
                .build();

        Book ddd = Book.builder()
                .title("Domain Driven Design")
                .isbn("123123")
                .build();

        setAuthorBookRelationship(eric, ddd);
//        setPublisherBookRelationship(publisher, ddd);

        authorRepository.save(eric);
        bookRepository.save(ddd);
        publisherRepository.save(publisher);

        Author rod = Author.builder()
                .firstName("Rod")
                .lastName("Johnson")
                .build();

        Book noEJB = Book.builder()
                .title("J2EE Development without EJB")
                .isbn("3939459459")
                .build();

        setAuthorBookRelationship(rod, noEJB);
        setPublisherBookRelationship(publisher, noEJB);

        authorRepository.save(rod);
        bookRepository.save(noEJB);
        publisherRepository.save(publisher);

        log.info(String.format("Number of Books: %s%n", bookRepository.count()));
        log.info(String.format("Publisher Number of Books: %s%n", publisher.getBooks().size()));
    }

    private void setPublisherBookRelationship(Publisher publisher, Book book) {
        Set<Book> books = new HashSet<>();
        books.add(book);
        publisher.setBooks(books);

        book.setPublisher(publisher);
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

package bg.mvr.the.kiss.rest.services.serviceImplementation;

import bg.mvr.the.kiss.rest.entities.Book;
import bg.mvr.the.kiss.rest.repositories.BookRepository;
import bg.mvr.the.kiss.rest.services.BookService;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 08.01.2021.
 * Time: 11:04.
 * Organization: DKIS MOIA.
 */
@Service
public class BookServiceImplementation implements BookService {

    private BookRepository bookRepository;

    public BookServiceImplementation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book with id: " + id + " doesn't exist"));
    }

    @Override
    public List<Book> findBookByName(String name) {
        return bookRepository.findBookByName(name);
    }

    @Override
    public Book findBookByNameAndAuthor(String name, String author) {
        return bookRepository.findBookByNameAndAuthor(name,author).orElseThrow(() -> new IllegalArgumentException("Book with name " + name + " and author "+author+" doesn't exist"));
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book insertBook(Book book) {
        bookRepository.findBookByNameAndAuthor(book.getName(), book.getAuthor()).ifPresent(bookFromDB -> {
            throw new IllegalArgumentException("That book already exist");
        });
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        Book bookFromDB = bookRepository.findById(book.getId()).orElseThrow(() -> new IllegalArgumentException("That book doesn't exist"));
        bookFromDB.setAuthor(book.getAuthor());
        bookFromDB.setName(book.getName());
        bookFromDB.setActive(book.isActive());
        bookRepository.save(bookFromDB);
        return bookFromDB;
    }

    @Override
    public Book deleteById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book with id: " + id + " doesn't exist"));
        bookRepository.deleteById(id);
        return book;
    }
}

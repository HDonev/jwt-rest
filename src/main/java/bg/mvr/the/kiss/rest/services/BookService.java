package bg.mvr.the.kiss.rest.services;

import bg.mvr.the.kiss.rest.entities.Book;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 08.10.2020.
 * Time: 11:02.
 * Organization: DKIS MOIA.
 */
public interface BookService {
    Book findById(Long id);

    Book findBookByNameAndAuthor(String name, String authors);

    List<Book> findBookByName(String name);

    List<Book> findAllBooks();

    Book insertBook(Book book);

    Book updateBook(Book book);

    Book deleteById(Long id);
}

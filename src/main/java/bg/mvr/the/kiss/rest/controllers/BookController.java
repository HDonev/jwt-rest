package bg.mvr.the.kiss.rest.controllers;

import bg.mvr.the.kiss.rest.entities.Book;
import bg.mvr.the.kiss.rest.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 21.09.2020.
 * Time: 09:13.
 * Organization: DKIS MOIA.
 */
@RestController
@CrossOrigin(maxAge = 3600)
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping(path = "/book/{id}")
    public ResponseEntity<Book> getBookByID(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));

    }

    @GetMapping(path = "/books/{name}")
    public ResponseEntity<List<Book>> getBookByName(@PathVariable String name) {
        List<Book> booksByName = bookService.findBookByName(name);
        if (booksByName.size() > 0) {
            return ResponseEntity.ok(booksByName);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(path = "/book")
    public ResponseEntity<Book> getBookByNameAndAuthor(@RequestParam String name, @RequestParam String author) {
        return ResponseEntity.ok(bookService.findBookByNameAndAuthor(name, author));
    }

    @GetMapping(path = "/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @PostMapping("/book")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        Book bookFromDb = bookService.insertBook(book);
        return ResponseEntity.ok(bookFromDb);
    }

    @PutMapping(path = "/book")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        Book bookFromDb = bookService.updateBook(book);
        return ResponseEntity.ok(bookFromDb);
    }

    @DeleteMapping(path = "/book/{id}")
    public ResponseEntity<Book> deleteBook(Long id) {
        Book book = bookService.deleteById(id);
        return ResponseEntity.ok(book);
    }
}

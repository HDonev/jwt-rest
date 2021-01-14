package bg.mvr.the.kiss.rest.repositories;

import bg.mvr.the.kiss.rest.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 11.01.2021.
 * Time: 09:14.
 * Organization: DKIS MOIA.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBookByName(String name);

    Optional<Book> findBookByNameAndAuthor(String name, String authors);
}

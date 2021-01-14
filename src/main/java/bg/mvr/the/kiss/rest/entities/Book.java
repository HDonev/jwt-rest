package bg.mvr.the.kiss.rest.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 21.09.2020.
 * Time: 09:07.
 * Organization: DKIS MOIA.
 */
@Entity
@Table(name = "books", uniqueConstraints = @UniqueConstraint(columnNames = {"author", "name"}))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String author;

    private String name;

    private boolean isActive;

}

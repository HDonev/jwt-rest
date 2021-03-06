package bg.mvr.the.kiss.rest.entities;

import lombok.*;
import javax.persistence.*;


/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 10.01.2021.
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

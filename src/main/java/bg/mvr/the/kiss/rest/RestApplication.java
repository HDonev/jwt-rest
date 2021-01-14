package bg.mvr.the.kiss.rest;

import bg.mvr.the.kiss.rest.dto.UserSignUp;
import bg.mvr.the.kiss.rest.entities.Book;
import bg.mvr.the.kiss.rest.entities.Role;
import bg.mvr.the.kiss.rest.entities.User;
import bg.mvr.the.kiss.rest.services.BookService;
import bg.mvr.the.kiss.rest.services.RoleService;
import bg.mvr.the.kiss.rest.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApplication implements CommandLineRunner {

    private RoleService roleService;
    private UserService userService;
    private BookService bookService;
    private ModelMapper modelMapper;

    @Autowired
    public RestApplication(RoleService roleService, UserService userService, ModelMapper modelMapper, BookService bookService) {
        this.roleService = roleService;
        this.userService = userService;
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleService.findAllRoles().size() == 0) {
            roleService.insertRole(new Role("USER"));
            roleService.insertRole(new Role("ADMIN"));
            userService.insertUser(modelMapper.map(new UserSignUp("admin@abv.bg", "admin", "P@ssw0rd"), User.class));
        }
        if (bookService.findAllBooks().size()==0){
            Book firstBook=new Book(0l,"Иван Вазов","Под Игото",true);
            bookService.insertBook(firstBook);
        }
    }
}

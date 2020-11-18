package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.jws.soap.SOAPBinding;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

        User userWithACar1 = new User("User999", "who", "cares");
        userWithACar1.setCar(new Car("ведро", 99));
        userWithACar1.setCar(new Car("ведро", 98));
        User userWithACar2 = new User("User666", "lol", "кек");
        userWithACar2.setCar(new Car("boat", 666));
        User userWithACar3 = new User("User777", "%", "от 0");
        userWithACar3.setCar(new Car("собачья упряжка на пекинесах", 99));

        userService.add(userWithACar1);
        userService.add(userWithACar2);
        userService.add(userWithACar3);

        List<User> users = userService.listUsers();
        for (User user : users) {
            doSomePrinting(user);
        }

        User userFromDB = userService.getUserWithCar("собачья упряжка на пекинесах", 99);
        doSomePrinting(userFromDB);

        context.close();
    }

    private static void doSomePrinting(User user) {
        System.out.println("Id = " + user.getId());
        System.out.println("First Name = " + user.getFirstName());
        System.out.println("Last Name = " + user.getLastName());
        System.out.println("Email = " + user.getEmail());
        if (user.getCar().size() != 0) {
            System.out.println("Model = " + user.getCar().get(0).getModel());
            System.out.println("Series = " + user.getCar().get(0).getSeries());
        }
        System.out.println();
    }
}

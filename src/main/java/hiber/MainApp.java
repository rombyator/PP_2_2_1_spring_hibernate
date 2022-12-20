package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(AppConfig.class);

        // Create & save some cars
        Car car1 = new Car("Honda", 250);
        Car car2 = new Car("Opel", 120);
        Car car3 = new Car("Mercedes", 600);

        CarService carService = context.getBean(CarService.class);
        carService.add(car1);
        carService.add(car2);
        carService.add(car3);

        // Create some users
        User user1 = new User("User1", "Lastname1", "user1@mail.ru");
        User user2 = new User("User2", "Lastname2", "user2@mail.ru");
        User user3 = new User("User3", "Lastname3", "user3@mail.ru");
        User user4 = new User("User4", "Lastname4", "user4@mail.ru");

        // Assign cars to users
        user1.setCar(car1);
        user2.setCar(car2);
        user3.setCar(car3);

        // Save users
        UserService userService = context.getBean(UserService.class);
        userService.add(user1);
        userService.add(user2);
        userService.add(user3);
        userService.add(user4);

        // Retrieve users & cars
        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car = " + user.getCar());
            System.out.println("=================================");
        }

        // Get user by car
        System.out.println("Get user with car");
        System.out.println(userService.getUserByCar(car1));
        System.out.println("=================================");

        // Try to get nonexistent user
        System.out.println("If nothing is found");
        try {
            User nobody = userService.getUserByCar(new Car("Some car", 123));
        } catch (NoResultException e) {
            System.out.println("User not found");
        }

        context.close();
    }
}

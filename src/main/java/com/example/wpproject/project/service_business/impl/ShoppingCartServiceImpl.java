package com.example.wpproject.project.service_business.impl;

import com.example.wpproject.project.model.Course;
import com.example.wpproject.project.model.ShoppingCart;
import com.example.wpproject.project.model.User;
import com.example.wpproject.project.model.enumerations.CartStatus;
import com.example.wpproject.project.model.exceptions.CardIsAlreadyCreatedForThisUser;
import com.example.wpproject.project.model.exceptions.ShoppingCardIsNotActiveException;
import com.example.wpproject.project.repository_persistence.ShoppingCartRepository;
import com.example.wpproject.project.service_business.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final CourseService courseService;
    private final PaymentService paymentService;
    private final PaypalService paypalService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserService userService, CourseService courseService, PaymentService paymentService, PaypalService paypalService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userService = userService;
        this.courseService = courseService;
        this.paymentService = paymentService;
        this.paypalService = paypalService;
    }


    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        return this.shoppingCartRepository
                .findByUserUsernameAndStatus(username, CartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    User user = this.userService.findById(username);
                    shoppingCart.setUser(user);
                    return this.shoppingCartRepository.save(shoppingCart);
                });
    }

    @Override
    public List<ShoppingCart> findAllByUsername(String username) {
        return this.shoppingCartRepository.findAllByUserUsername(username);
    }

    @Override
    public ShoppingCart createNewShoppingCart(String username) {
        User user = this.userService.findById(username);
        if(this.shoppingCartRepository
                .existsByUserUsernameAndStatus(user.getUsername(), CartStatus.CREATED))
        {
            throw new CardIsAlreadyCreatedForThisUser(username);
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setStatus(CartStatus.CREATED);
        shoppingCart.setUser(user);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart addCourseToShoppingCart(String username, Long courseId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Course course = this.courseService.findById(courseId);

//        for (Course course1 : shoppingCart.getCourses()) {
//            if(course == course1){
//                continue;
//            }
//            shoppingCart.getCourses().add(course);
//        }

        shoppingCart.getCourses().add(course);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart removeCourseFromShoppingCart(String username, Long courseId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        shoppingCart.setCourses(
                shoppingCart.getCourses()
                        .stream()
                        .filter(course -> !course.getId().equals(courseId))
                        .collect(Collectors.toList())
        );
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart cancelShoppingCart(String username) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserUsernameAndStatus(username, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCardIsNotActiveException(username));

        shoppingCart.setStatus(CartStatus.CANCELLED);
        shoppingCart.setFinished(LocalDateTime.now());
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart checkoutShoppingCart(String username) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserUsernameAndStatus(username, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCardIsNotActiveException(username));

        List<Course> courses = shoppingCart.getCourses();
        float price=0;

        for(Course course : courses){
            price += course.getPrice().getPrice();
        }

        this.paymentService.pay(price);

        shoppingCart.setCourses(courses);
        shoppingCart.setStatus(CartStatus.FINISHED);
        shoppingCart.setFinished(LocalDateTime.now());
        return this.shoppingCartRepository.save(shoppingCart);
    }
}

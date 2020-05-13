package com.example.wpproject.project.service_business.impl;

import com.example.wpproject.project.model.dto.ChargeRequest;
import com.example.wpproject.project.model.Course;
import com.example.wpproject.project.model.ShoppingCart;
import com.example.wpproject.project.model.User;
import com.example.wpproject.project.model.enumerations.CartStatus;
import com.example.wpproject.project.model.exceptions.CardIsAlreadyCreatedForThisUser;
import com.example.wpproject.project.model.exceptions.CourseIsAlreadyInShoppingCard;
import com.example.wpproject.project.model.exceptions.ShoppingCardIsNotActiveException;
import com.example.wpproject.project.model.exceptions.TransactionFailedException;
import com.example.wpproject.project.repository_persistence.ShoppingCartRepository;
import com.example.wpproject.project.service_business.*;
import com.stripe.exception.*;
import com.stripe.model.Charge;
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
    public ShoppingCart findActiveShoppingCartByUserId(Integer userId) {
        return this.shoppingCartRepository.findByUserIdAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCardIsNotActiveException(userId));

    }

    @Override
    public ShoppingCart getActiveShoppingCart(Integer userId) {
        return this.shoppingCartRepository
                .findByUserIdAndStatus(userId, CartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    User user = this.userService.findById(userId);
                    shoppingCart.setUser(user);
                    return this.shoppingCartRepository.save(shoppingCart);
                });
    }

    @Override
    public List<ShoppingCart> findAllByUsername(Integer userId) {
        return this.shoppingCartRepository.findAllByUserId(userId);
    }

    @Override
    public ShoppingCart createNewShoppingCart(Integer userId) {
        User user = this.userService.findById(userId);
        if(this.shoppingCartRepository
                .existsByUserIdAndStatus(user.getId(), CartStatus.CREATED))
        {
            throw new CardIsAlreadyCreatedForThisUser(userId);
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setStatus(CartStatus.CREATED);
        shoppingCart.setUser(user);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart addCourseToShoppingCart(Integer userId, Long courseId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        Course course = this.courseService.findById(courseId);

        for (Course c : shoppingCart.getCourses()) {
            if(c.getId().equals(courseId)){
                throw new CourseIsAlreadyInShoppingCard(course.getName());
            }
        }
        shoppingCart.getCourses().add(course);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart removeCourseFromShoppingCart(Integer userId, Long courseId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        shoppingCart.setCourses(
                shoppingCart.getCourses()
                        .stream()
                        .filter(course -> !course.getId().equals(courseId))
                        .collect(Collectors.toList())
        );
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart cancelShoppingCart(Integer userId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserIdAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCardIsNotActiveException(userId));

        shoppingCart.setStatus(CartStatus.CANCELLED);
        shoppingCart.setFinished(LocalDateTime.now());
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart checkoutShoppingCart(Integer userId, ChargeRequest chargeRequest) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserIdAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCardIsNotActiveException(userId));

        List<Course> courses = shoppingCart.getCourses();
        float price=0;

        for(Course course : courses){
            price += course.getPrice().getPrice();
        }

        Charge charge = null;
        try {
            charge = this.paymentService.charge(chargeRequest);
        } catch (CardException | APIException | AuthenticationException | APIConnectionException | InvalidRequestException e) {
            throw new TransactionFailedException(userId, e.getMessage());
        }


        shoppingCart.setCourses(courses);
        shoppingCart.setStatus(CartStatus.FINISHED);
        shoppingCart.setFinished(LocalDateTime.now());
        return this.shoppingCartRepository.save(shoppingCart);
    }
}

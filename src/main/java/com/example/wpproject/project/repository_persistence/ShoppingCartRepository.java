package com.example.wpproject.project.repository_persistence;

import com.example.wpproject.project.model.ShoppingCart;
import com.example.wpproject.project.model.enumerations.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    List<ShoppingCart> findAllByUserId(Integer userId);

    boolean existsByUserIdAndStatus(Integer userId, CartStatus status);

    Optional<ShoppingCart> findByUserIdAndStatus(Integer userId, CartStatus status);
}

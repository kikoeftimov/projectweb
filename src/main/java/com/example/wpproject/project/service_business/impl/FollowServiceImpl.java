package com.example.wpproject.project.service_business.impl;

import com.example.wpproject.project.model.Author;
import com.example.wpproject.project.model.User;
import com.example.wpproject.project.model.exceptions.AuthorNotFoundException;
import com.example.wpproject.project.model.exceptions.UserNotFoundException;
import com.example.wpproject.project.repository_persistence.AuthorRepository;
import com.example.wpproject.project.repository_persistence.UserRepository;
import com.example.wpproject.project.service_business.FollowService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {

    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;

    public FollowServiceImpl(UserRepository userRepository, AuthorRepository authorRepository) {
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
    }


    @Override
    public void follow(Long userId, Long authorId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Author author = this.authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException(authorId));
        user.follow(author);
        this.userRepository.save(user);
        this.authorRepository.save(author);
    }

    @Override
    public void unFollow(Long userId, Long authorId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Author author = this.authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException(authorId));
        user.unFollow(author);
        this.userRepository.save(user);
        this.authorRepository.save(author);
    }

    @Override
    public Page<User> getFollowers(Long authorId, int page, int pageSize) {
        return null;
    }
}

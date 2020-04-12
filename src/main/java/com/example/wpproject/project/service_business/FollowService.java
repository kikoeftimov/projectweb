package com.example.wpproject.project.service_business;

import com.example.wpproject.project.model.User;
import org.springframework.data.domain.Page;

public interface FollowService {
    void follow(Long userId, Long authorId);

    void unFollow(Long userId, Long authorId);

    Page<User> getFollowers(Long authorId, int page, int pageSize);
}

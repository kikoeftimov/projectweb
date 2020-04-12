package com.example.wpproject.project.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @ManyToMany
    private List<Author> authorList;

    public User() {
    }

    public User(Long id, String username, List<Author> authorList) {
        this.id = id;
        this.username = username;
        this.authorList = authorList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    public void follow(Author author) {
        this.authorList.add(author);
        author.getUserList().add(this);
    }

    public void unFollow(Author author) {
        this.authorList.remove(author);
        author.getUserList().remove(this);
    }
}

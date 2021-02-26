package org.udg.pds.springtodo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity (name = "usergroup")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private User userG;

    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<User> members = new ArrayList<>();

    public Group() {
    }

    public Group(String name, String description) {
        this.name = name;
        this.description = description;

    }

    public void setUser(User user) {
        this.userG = user;
    }

    public Long getId() {
        return id;
    }

    public User getOwner() { return userG;}

    public void addMember(User member) {
        members.add(member);
    }
}

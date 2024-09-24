package com.waffiyyi.fashion.blog.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "blog_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private String firstName;
    private String lastName;

    private String email;
    private String gender;
    private String password;
    private String profilePicture;
    @ManyToMany
    @JoinTable(
       name = "user_followers",
       joinColumns = @JoinColumn(name = "user_id"),
       inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> followers = new HashSet<>();

    @ManyToMany(mappedBy = "followers")
    private Set<User> following = new HashSet<>();
    @ManyToMany
    @JoinTable(
       name = "user_likes",
       joinColumns = @JoinColumn(name = "user_id"),
       inverseJoinColumns = @JoinColumn(name = "design_id")
    )
    private Set<Design> likedDesigns = new HashSet<>();
}

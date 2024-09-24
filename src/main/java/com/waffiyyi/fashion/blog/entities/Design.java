package com.waffiyyi.fashion.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "designs")
public class Design {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Column
    private String name;

    @Column
    private String description;
    @Column
    private Long likesCount;
   @ManyToOne
   @JoinColumn(name = "category_id")
   private Category category;
   @JsonIgnore
   @ManyToOne
   private User designOwner;
  @JsonIgnore
  @ManyToMany
  @JoinTable(
     name = "design_likes",
     joinColumns = @JoinColumn(name = "design_id"),
     inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private Set<User> likedByUsers = new HashSet<>();
  @Column
  private LocalDateTime lastActivity;
}

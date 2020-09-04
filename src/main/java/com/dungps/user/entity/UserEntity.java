package com.dungps.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(unique = true)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    private void setTime() {
        Date currentTime = new Date();
        this.createdAt = currentTime;
        this.updatedAt = currentTime;
    }

    @PreUpdate
    private void updateTime() {
        this.updatedAt = new Date();
    }
}

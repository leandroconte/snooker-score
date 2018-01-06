package org.lera.snooker.domain;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by lnd on 06/02/17.
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column
    private String username;

    @NotNull
    @Column
    @JsonIgnore
    private String password;

    @Column
    @NotNull
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_user_to_score"), nullable = false, unique = true)
    private Score score = new Score();

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

    @JsonIgnore
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}

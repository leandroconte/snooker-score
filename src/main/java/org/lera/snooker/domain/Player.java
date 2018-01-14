package org.lera.snooker.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lnd on 06/02/17.
 */
@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotNull
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Score> scores = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }
}

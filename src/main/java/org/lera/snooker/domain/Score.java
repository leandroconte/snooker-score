package org.lera.snooker.domain;

import javax.persistence.*;

/**
 * Created by lnd on 06/02/17.
 */
@Entity
public class Score {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int points = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

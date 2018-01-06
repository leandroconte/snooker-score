package org.lera.snooker.domain;

import javax.persistence.*;
import java.util.Date;

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

    @Column
    private Date date = new Date();

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

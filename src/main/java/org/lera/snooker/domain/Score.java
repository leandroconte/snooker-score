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
    private Date date = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

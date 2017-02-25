package org.lera.snooker.services;

import org.lera.snooker.domain.Score;
import org.lera.snooker.domain.User;
import org.lera.snooker.repository.ScoreRepository;
import org.lera.snooker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lnd on 10/02/17.
 */
@Service
public class ScoreService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void setScoreboard(Long userId, boolean plus) {
        User user = userRepository.findOne(userId);
        Score score = user.getScore();
        if (plus) {
            score.setPoints(score.getPoints() + 1);
        } else {
            score.setPoints(score.getPoints() - 1);
        }
    }

}


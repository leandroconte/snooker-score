package org.lera.snooker.services;

import org.lera.snooker.domain.Player;
import org.lera.snooker.domain.Score;
import org.lera.snooker.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lnd on 10/02/17.
 */
@Service
public class ScoreService {

    private final PlayerRepository playerRepository;

    @Autowired
    public ScoreService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Transactional
    public void setScoreboard(Long playerId, boolean plus) {
        Player player = playerRepository.findOne(playerId);
        Score score = player.getScores();
        if (plus) {
            score.setPoints(score.getPoints() + 1);
        } else {
            score.setPoints(score.getPoints() - 1);
        }
    }

}


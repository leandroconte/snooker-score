package org.lera.snooker.controller;

import org.lera.snooker.domain.Score;
import org.lera.snooker.repository.PlayerRepository;
import org.lera.snooker.services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private PlayerRepository playerRepository;

    /**
     * Adds 1 point to the player's scoreboard.
     *
     * @param playerId the player's ID.
     * @return 404, if player not found; 200 if one point was added.
     */
    @PostMapping("/{playerId}")
    private HttpStatus createScore(@PathVariable("playerId") Long playerId, @RequestBody Score score) {
        if (!playerRepository.exists(playerId)) {
            return HttpStatus.NOT_FOUND;
        }
        if (score.getDate() == null) {
            return HttpStatus.BAD_REQUEST;
        }
        scoreService.addScore(playerId, score);
        return HttpStatus.OK;
    }

}

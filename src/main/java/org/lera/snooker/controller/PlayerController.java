package org.lera.snooker.controller;

import org.lera.snooker.domain.Player;
import org.lera.snooker.repository.PlayerRepository;
import org.lera.snooker.services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lnd on 09/02/17.
 */
@RestController("/player")
public class PlayerController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private PlayerRepository playerRepository;

    /**
     * Creates a new player.
     *
     * @param player Attributes of a new player.
     * @return 400 (bad request), if there are any incorrect attribute;
     *          409 (conflict), if a player name already exists;
     *          201, if a new player was created.
     */
    @PostMapping
    private ResponseEntity<Player> createPlayer(@RequestBody Player player) {

        if (player.getName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (playerRepository.countByName(player.getName()) > 0) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        playerRepository.save(player);
        return new ResponseEntity<>(player, HttpStatus.CREATED);
    }

    @PutMapping
    private ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
        if (player.getId() == null || player.getName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Player playerMerged = playerRepository.findOne(player.getId());
        if (playerMerged == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        playerRepository.save(player);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }


    /**
     * Adds 1 point to the player's scoreboard.
     *
     * @param playerId the player's ID.
     * @return 404, if player not found; 200 if one point was added.
     */
    @PatchMapping("/{playerId}/plus")
    private HttpStatus plusScore(@PathVariable("playerId") Long playerId) {
        if (!playerRepository.exists(playerId)) {
            return HttpStatus.NOT_FOUND;
        }
        // Add 1 point
        scoreService.setScoreboard(playerId, true);
        return HttpStatus.OK;
    }

    /**
     * Subtracts 1 point of the player's scoreboard.
     *
     * @param playerId the player's ID.
     * @return 404, if player not found; 200 if one point was subtracted.
     */
    @PatchMapping("/{playerId}/minus")
    private HttpStatus minusScore(@PathVariable("playerId") Long playerId) {
        if (!playerRepository.exists(playerId)) {
            return HttpStatus.NOT_FOUND;
        }
        // Remove 1 point
        scoreService.setScoreboard(playerId, false);
        return HttpStatus.OK;
    }

    /**
     * Returns all players.
     *
     * @return A list of all players.
     */
    @GetMapping
    private ResponseEntity<Iterable<Player>> findAll() {
        Iterable<Player> players = playerRepository.findAll();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    /**
     * Deletes a player.
     * @param playerId the player's id.
     * @return 404, if player not found; 200 if player was deleted.
     */
    @DeleteMapping("/{playerId}")
    private HttpStatus deletePlayer(@PathVariable("playerId") Long playerId) {
        if (!playerRepository.exists(playerId)) {
            return HttpStatus.NOT_FOUND;
        }
        Player player = playerRepository.findOne(playerId);
        playerRepository.delete(player);
        return HttpStatus.OK;
    }

}

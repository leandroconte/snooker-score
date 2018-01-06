package org.lera.snooker.controller;

import org.lera.snooker.domain.User;
import org.lera.snooker.repository.UserRepository;
import org.lera.snooker.services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lnd on 09/02/17.
 */
@RestController
public class UserController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new user.
     *
     * @param user Attributes of a new user.
     * @return 400 (bad request), if there are any incorrect attribute;
     *          409 (conflict), if a username or name already exists;
     *          201, if a new user was created.
     *
     */
    @PostMapping("/user") //TODO: must return the user object.
    private ResponseEntity<Long> createUser(@RequestBody User user) {

        if (user.getName().isEmpty() || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (userRepository.countByUsernameOrName(user.getUsername(), user.getName()) > 0) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Long id = userRepository.save(user).getId();
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    /**
     * Adds 1 point to the user's scoreboard.
     *
     * @param userId the user's ID.
     * @return 404, if user not found; 200 if one point was added.
     */
    @PatchMapping("/user/{userId}/plus")
    private HttpStatus plusScore(@PathVariable("userId") Long userId) {
        if (!userRepository.exists(userId)) {
            return HttpStatus.NOT_FOUND;
        }
        // Add 1 point
        scoreService.setScoreboard(userId, true);
        return HttpStatus.OK;
    }

    /**
     * Subtracts 1 point of the user's scoreboard.
     *
     * @param userId the user's ID.
     * @return 404, if user not found; 200 if one point was subtracted.
     */
    @PatchMapping("/user/{userId}/minus")
    private HttpStatus minusScore(@PathVariable("userId") Long userId) {
        if (!userRepository.exists(userId)) {
            return HttpStatus.NOT_FOUND;
        }
        // Remove 1 point
        scoreService.setScoreboard(userId, false);
        return HttpStatus.OK;
    }

    /**
     * Returns all users.
     *
     * @return A list of all users.
     */
    @GetMapping("/user")
    private ResponseEntity<Iterable<User>> findAll() {
        Iterable<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Sets a new score to the user.
     *
     * @param userId the user's ID.
     * @param newScore the new score.
     * @return 404, if user not found;
     *          400, if the new score is less than 0;
     *          200 if the score was updated.
     */
    @PatchMapping("/user/{userId}/scoreboard")
    private HttpStatus setScore(@PathVariable("userId") Long userId, @RequestParam("score") int newScore) {

        if (!userRepository.exists(userId)) {
            return HttpStatus.NOT_FOUND;
        }

        if (newScore < 0) {
            return HttpStatus.BAD_REQUEST;
        }

        User user = userRepository.findOne(userId);
        user.getScore().setPoints(newScore);
        userRepository.save(user);

        return HttpStatus.OK;
    }

    @DeleteMapping("/user/{userId}")
    private HttpStatus deleteUser(@PathVariable("userId") Long userId) {
        if (!userRepository.exists(userId)) {
            return HttpStatus.NOT_FOUND;
        }
        User user = userRepository.findOne(userId);
        userRepository.delete(user);
        return HttpStatus.OK;
    }

}

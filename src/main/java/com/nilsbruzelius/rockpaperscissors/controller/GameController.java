package com.nilsbruzelius.rockpaperscissors.controller;

import com.nilsbruzelius.rockpaperscissors.model.Game;
import com.nilsbruzelius.rockpaperscissors.model.Move;
import com.nilsbruzelius.rockpaperscissors.model.Player;
import com.nilsbruzelius.rockpaperscissors.model.State;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
public class GameController {

    private final Map<UUID, Game> gameMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "/api/games", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> newGame(@RequestBody Map<String, String> body) {
        Game newGame = new Game();
        newGame.setPlayerOne(new Player(body.get("name")));
        gameMap.put(newGame.getGameID(), newGame);

        return ResponseEntity.status(HttpStatus.CREATED).body("Player 1 joined: " + newGame.getPlayerOne().getName() + "\nGame ID: " + newGame.getGameID() + "\n");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/games/{id}/join", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> joinGame(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        Game game = gameMap.get(id);

        if(game != null) {
            if (game.getState() != State.Ended && game.getPlayerTwo() == null) {
                game.setPlayerTwo(new Player(body.get("name")));
                return ResponseEntity.status(HttpStatus.OK).body("Player 2 joined: " + game.getPlayerTwo().getName() + "\n");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The requested game is full"+ "\n");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Game ID was not found"+ "\n");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/games/{id}/move", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> makeMove(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        Game game = gameMap.get(id);
        Player playerOne;
        Player playerTwo;

        if(game != null) {
            playerOne = game.getPlayerOne();
            playerTwo = game.getPlayerTwo();

            if (game.getState() == State.Ended) {
                return ResponseEntity.status(HttpStatus.OK).body("This game has ended");
            }

            if (body.get("name").equals(playerOne.getName())) {
                playerOne.setMove(Move.valueOf(body.get("move")));

                return ResponseEntity.status(HttpStatus.OK).body("Player 1: " + playerOne.getName() + " chose " + playerOne.getMove().toString()+ "\n");

            } else if (body.get("name").equals(playerTwo.getName())) {
                playerTwo.setMove(Move.valueOf(body.get("move")));

                return ResponseEntity.status(HttpStatus.OK).body("Player 2: " + playerTwo.getName() + " chose " + playerTwo.getMove().toString()+ "\n");

            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Player is not part of this game"+ "\n");
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Game ID was not found"+ "\n");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/games/{id}")
    public ResponseEntity<String> checkState(@PathVariable UUID id) {
        Game game = gameMap.get(id);

        if(game != null) {
            game.evaluateMoves(game.getPlayerOne(), game.getPlayerTwo());
            return ResponseEntity.status(HttpStatus.OK).body(game.toString());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Game ID was not found"+ "\n");

    }
}
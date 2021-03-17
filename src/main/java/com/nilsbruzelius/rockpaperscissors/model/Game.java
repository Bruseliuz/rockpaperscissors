package com.nilsbruzelius.rockpaperscissors.model;

import java.util.UUID;

public class Game {

    private Player playerOne;
    private Player playerTwo;
    private final UUID gameID;
    private State state;


    public Game() {
        this.gameID = generateUUID();
        this.state = State.Started;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public UUID generateUUID() {
        return UUID.randomUUID();
    }

    public UUID getGameID() {
        return gameID;
    }

    public State getState() {
        return state;
    }

    @Override
    public String toString() {


        if (state == State.Ended) {
            return "Game ID: " + gameID +
                    "\n\nGame state: " + state +
                    "\n\nPlayer 1: " + playerOne.getName() +
                    "\nMove: " + playerOne.getMove() +
                    "\n\nPlayer 2: " + playerTwo.getName() +
                    "\nMove: " + playerTwo.getMove() +
                    "\n\nRESULT: \n" + ((playerOne.getResult() == Result.Draw) ? "The game ended in a draw" : "Player 1 " + ((playerOne.getResult() == Result.Lose) ? "lost. \nPlayer 2 won." : "won. \nPlayer 2 lost.\n"));
        } else {
            return "Game ID: " + gameID +
                    "\n\nGame state: " + state +
                    "\n\nPlayer 1: " + playerOne.getName() +
                    "\nMove: " + ((playerOne.getMove() != null) ? playerOne.getMove() : "No move made") +
                    "\n\nPlayer 2: " + playerTwo.getName() +
                    "\nMove: " + ((playerTwo.getMove() != null) ? playerTwo.getMove() : "No move made") + "\n";
        }
    }

    public void evaluateMoves(Player playerOne, Player playerTwo) {

        if (playerOne.getMove() != null && playerTwo.getMove() != null) {
            if (playerOne.getMove().equals(playerTwo.getMove())) {

                playerOne.setResult(Result.Draw);
                playerTwo.setResult(Result.Draw);
            }

            else if (playerOne.getMove().beats(playerTwo.getMove())) {
                playerOne.setResult(Result.Win);
                playerTwo.setResult(Result.Lose);
            }
            else {
                playerOne.setResult(Result.Lose);
                playerTwo.setResult(Result.Win);
            }
            state = State.Ended;
        }
    }
}
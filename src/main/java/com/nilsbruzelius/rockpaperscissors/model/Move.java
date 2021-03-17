package com.nilsbruzelius.rockpaperscissors.model;

public enum Move {

    Rock {
        public boolean beats(Move move) {
            return(move == Scissors);
        }
    },

    Paper {
        public boolean beats(Move move) {
            return(move == Rock);
        }
    },

    Scissors {
        public boolean beats(Move move) {
            return(move == Paper);
        }
    };

    public abstract boolean beats(Move move);
}
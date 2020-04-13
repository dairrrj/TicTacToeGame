package com.example.tictactoegame;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TicTacToeGame {
    // Characters used to represent the human, computer, and open spots
    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';
    public static final char OPEN_SPOT = ' ';
    public static final int BOARD_SIZE = 9;
    private char mBoard[] = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private Random mRand;

    /**
     * The constructor of the TicTacToeGame have to be remove some code as follows
     */
    public TicTacToeGame() {
        // Seed the random number generator
        mRand = new Random();
        char turn = HUMAN_PLAYER; // Human starts first
        int win = 0; // Set to 1, 2, or 3 when game is over
    }

    /**
     * Clear the board of all X's and O's.
     */
    public void clearBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            mBoard[i] = OPEN_SPOT;
        }
    }

    /**
     * Set the given player at the given location on the game board *
     */
    public void setMove(char player, int location) {
        mBoard[location] = player;
    }

    /**
     * Check for a winner and return a status value indicating who has won.
     *
     * @return Return 0 if no winner or tie yet, 1 if it's a tie, 2 if X won,
     * or 3 if O won.
     */
    public int checkForWinner() {
        // Check horizontal wins
        for (int i = 0; i <= 6; i += 3) {
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i + 1] == HUMAN_PLAYER &&
                    mBoard[i + 2] == HUMAN_PLAYER)
                return 2;
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i + 1] == COMPUTER_PLAYER &&
                    mBoard[i + 2] == COMPUTER_PLAYER)
                return 3;
        }

        // Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i + 3] == HUMAN_PLAYER &&
                    mBoard[i + 6] == HUMAN_PLAYER)
                return 2;
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i + 3] == COMPUTER_PLAYER &&
                    mBoard[i + 6] == COMPUTER_PLAYER)
                return 3;
        }

        // Check for diagonal wins
        if ((mBoard[0] == HUMAN_PLAYER &&
                mBoard[4] == HUMAN_PLAYER &&
                mBoard[8] == HUMAN_PLAYER) ||
                (mBoard[2] == HUMAN_PLAYER &&
                        mBoard[4] == HUMAN_PLAYER &&
                        mBoard[6] == HUMAN_PLAYER))
            return 2;
        if ((mBoard[0] == COMPUTER_PLAYER &&
                mBoard[4] == COMPUTER_PLAYER &&
                mBoard[8] == COMPUTER_PLAYER) ||
                (mBoard[2] == COMPUTER_PLAYER &&
                        mBoard[4] == COMPUTER_PLAYER &&
                        mBoard[6] == COMPUTER_PLAYER))
            return 3;

        // Check for tie
        for (int i = 0; i < BOARD_SIZE; i++) {
            // If we find a number, then no one has won yet
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER)
                return 0;
        }

        // If we make it through the previous loop, all places are taken, so it's a tie
        return 1;
    }

    /**
     * Return the best move for the computer to make. You must call setMove()
     * to actually make the computer move to that location.
     *
     * @return The best move for the computer to make (0-8).
     */
    public int getComputerMove() {
        int move;
// First see if there's a move O can make to win
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                char curr = mBoard[i];
                mBoard[i] = COMPUTER_PLAYER;
                if (checkForWinner() == 3) {
                    System.out.println("Computer is moving to " + (i + 1));
                    return i;
                } else
                    mBoard[i] = curr;
            }
        }
// See if there's a move O can make to block X from winning
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                char curr = mBoard[i]; // Save the current number
                mBoard[i] = HUMAN_PLAYER;
                if (checkForWinner() == 2) {
                    mBoard[i] = COMPUTER_PLAYER;
                    System.out.println("Computer is moving to " + (i + 1));
                    return i;
                } else
                    mBoard[i] = curr;
            }
        }
// Generate random move
        do {
            move = mRand.nextInt(BOARD_SIZE);
        } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == COMPUTER_PLAYER);
        mBoard[move] = COMPUTER_PLAYER;
        return move;
    }

    public Map<String, Integer> getComputerMoveExpert(int location, int Round1, int Round2, int Round3) {
        int move;
        int numofHP;
        int round1;
        int round2;
        int round3;
        numofHP = 0;
        Map<String, Integer> map = new HashMap<String, Integer>();
        round1 = Round1;
        round2 = Round2;
        round3 = Round3;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] == HUMAN_PLAYER) {
                numofHP += 1;
            }
        }
        switch (numofHP) {
            case 0:
                move = 0;
                mBoard[move] = COMPUTER_PLAYER;
                round1 = 9;
                round2 = 9;
                round3 = 9;
                map.put("move", move);
                map.put("round1", round1);
                map.put("round2", round2);
                map.put("round3", round3);
                return map;
            case 1:
                round1 = location;
                round2 = 9;
                round3 = 9;
                switch (location) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                        move = 4;
                        mBoard[move] = COMPUTER_PLAYER;
                        map.put("move", move);
                        map.put("round1", round1);
                        map.put("round2", round2);
                        map.put("round3", round3);
                        return map;
                    case 2:
                        move = 6;
                        mBoard[move] = COMPUTER_PLAYER;
                        map.put("move", move);
                        map.put("round1", round1);
                        map.put("round2", round2);
                        map.put("round3", round3);
                        return map;
                    case 4:
                        move = 8;
                        mBoard[move] = COMPUTER_PLAYER;
                        map.put("move", move);
                        map.put("round1", round1);
                        map.put("round2", round2);
                        map.put("round3", round3);
                        return map;
                    case 6:
                    case 8:
                        move = 2;
                        mBoard[move] = COMPUTER_PLAYER;
                        map.put("move", move);
                        map.put("round1", round1);
                        map.put("round2", round2);
                        map.put("round3", round3);
                        return map;
                }
            case 2:
                round2 = location;
                round3 = 9;
                switch (round1) {
                    case 1:
                        switch (location) {
                            case 2:
                            case 3:
                            case 5:
                            case 6:
                            case 7:
                                move = 8;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                            case 8:
                                move = 6;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                        }
                    case 2:
                        switch (location) {
                            case 1:
                            case 4:
                            case 5:
                            case 7:
                            case 8:
                                move = 3;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                            case 3:
                                move = 8;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                        }
                    case 3:
                        switch (location) {
                            case 1:
                            case 2:
                            case 5:
                            case 6:
                            case 7:
                                move = 8;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                            case 8:
                                move = 2;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                        }
                    case 4:
                        switch (location) {
                            case 1:
                                move = 7;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                            case 2:
                                move = 6;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                            case 3:
                                move = 5;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                            case 5:
                                move = 3;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                            case 6:
                                move = 2;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                            case 7:
                                move = 1;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                        }
                    case 5:
                        switch (location) {
                            case 1:
                            case 2:
                            case 3:
                            case 6:
                            case 7:
                                move = 8;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                            case 8:
                                move = 2;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                        }
                    case 6:
                        switch (location) {
                            case 1:
                                move = 8;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                            case 3:
                            case 4:
                            case 5:
                            case 7:
                            case 8:
                                move = 1;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                        }
                    case 7:
                        switch (location) {
                            case 1:
                            case 2:
                            case 3:
                            case 5:
                            case 6:
                                move = 8;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                            case 8:
                                move = 6;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                        }
                    case 8:
                        switch (location) {
                            case 1:
                                move = 6;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                                move = 1;
                                mBoard[move] = COMPUTER_PLAYER;
                                map.put("move", move);
                                map.put("round1", round1);
                                map.put("round2", round2);
                                map.put("round3", round3);
                                return map;
                        }
                }
            case 3:
                round3 = location;
                switch (round1) {
                    case 1:
                        switch (round2) {
                            case 8:
                                switch (location) {
                                    case 2:
                                    case 5:
                                    case 7:
                                        move = 3;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                    case 3:
                                        move = 2;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                }
                        }
                    case 2:
                        switch (round2) {
                            case 3:
                                switch (location) {
                                    case 1:
                                    case 4:
                                    case 5:
                                        move = 7;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                    case 7:
                                        move = 4;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                }
                        }
                    case 3:
                        switch (round2) {
                            case 8:
                                switch (location) {
                                    case 1:
                                        move = 6;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                    case 5:
                                    case 6:
                                    case 7:
                                        move = 1;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                }
                        }
                    case 4:
                        switch (round2) {
                            case 1:
                                switch (location) {
                                    case 2:
                                    case 3:
                                    case 5:
                                        move = 7;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                    case 6:
                                        move = 2;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                }
                            case 2:
                                switch (location) {
                                    case 1:
                                    case 7:
                                        move = 3;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                    case 3:
                                    case 5:
                                        move = 7;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                }
                            case 3:
                                switch (location) {
                                    case 1:
                                    case 6:
                                    case 7:
                                        move = 5;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                    case 2:
                                        move = 6;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                }
                            case 5:
                                switch (location) {
                                    case 1:
                                    case 2:
                                    case 7:
                                        move = 6;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                    case 6:
                                        move = 2;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                }
                            case 6:
                                switch (location) {
                                    case 1:
                                    case 7:
                                        move = 5;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                    case 3:
                                    case 5:
                                        move = 1;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                }
                            case 7:
                                switch (location) {
                                    case 2:
                                        move = 6;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                    case 3:
                                    case 5:
                                    case 6:
                                        move = 2;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                }
                        }
                    case 5:
                        switch (round2) {
                            case 8:
                                switch (location) {
                                    case 1:
                                        move = 6;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                    case 3:
                                    case 6:
                                    case 7:
                                        move = 1;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                }
                        }
                    case 6:
                        switch (round2) {
                            case 1:
                                switch (location) {
                                    case 3:
                                    case 4:
                                    case 7:
                                        move = 5;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                    case 5:
                                        move = 4;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                }
                        }
                    case 7:
                        switch (round2) {
                            case 8:
                                switch (location) {
                                    case 1:
                                    case 2:
                                    case 5:
                                        move = 3;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                    case 3:
                                        move = 2;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                }
                        }
                    case 8:
                        switch (round2) {
                            case 1:
                                switch (location) {
                                    case 3:
                                        move = 4;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                    case 4:
                                    case 5:
                                    case 7:
                                        move = 3;
                                        mBoard[move] = COMPUTER_PLAYER;
                                        map.put("move", move);
                                        map.put("round1", round1);
                                        map.put("round2", round2);
                                        map.put("round3", round3);
                                        return map;
                                }
                        }
                }
            case 4:
                switch (round1) {
                    case 4:
                        switch (round2) {
                            case 2:
                                switch (round3) {
                                    case 6:
                                        switch (location) {
                                            case 3:
                                                move = 5;
                                                mBoard[move] = COMPUTER_PLAYER;
                                                map.put("move", move);
                                                map.put("round1", round1);
                                                map.put("round2", round2);
                                                map.put("round3", round3);
                                                return map;
                                            case 5:
                                                move = 3;
                                                mBoard[move] = COMPUTER_PLAYER;
                                                map.put("move", move);
                                                map.put("round1", round1);
                                                map.put("round2", round2);
                                                map.put("round3", round3);
                                                return map;
                                        }
                                }
                            case 3:
                                switch (round3) {
                                    case 2:
                                        switch (location) {
                                            case 1:
                                                move = 7;
                                                mBoard[move] = COMPUTER_PLAYER;
                                                map.put("move", move);
                                                map.put("round1", round1);
                                                map.put("round2", round2);
                                                map.put("round3", round3);
                                                return map;
                                            case 7:
                                                move = 1;
                                                mBoard[move] = COMPUTER_PLAYER;
                                                map.put("move", move);
                                                map.put("round1", round1);
                                                map.put("round2", round2);
                                                map.put("round3", round3);
                                                return map;
                                        }
                                }
                            case 5:
                                switch (round3) {
                                    case 6:
                                        switch (location) {
                                            case 1:
                                                move = 7;
                                                mBoard[move] = COMPUTER_PLAYER;
                                                map.put("move", move);
                                                map.put("round1", round1);
                                                map.put("round2", round2);
                                                map.put("round3", round3);
                                                return map;
                                            case 7:
                                                move = 1;
                                                mBoard[move] = COMPUTER_PLAYER;
                                                map.put("move", move);
                                                map.put("round1", round1);
                                                map.put("round2", round2);
                                                map.put("round3", round3);
                                                return map;
                                        }
                                }
                            case 7:
                                switch (round3) {
                                    case 2:
                                        switch (location) {
                                            case 3:
                                                move = 5;
                                                mBoard[move] = COMPUTER_PLAYER;
                                                map.put("move", move);
                                                map.put("round1", round1);
                                                map.put("round2", round2);
                                                map.put("round3", round3);
                                                return map;
                                            case 5:
                                                move = 3;
                                                mBoard[move] = COMPUTER_PLAYER;
                                                map.put("move", move);
                                                map.put("round1", round1);
                                                map.put("round2", round2);
                                                map.put("round3", round3);
                                                return map;
                                        }
                                }
                        }
                }
        }
        return map;
    }
}

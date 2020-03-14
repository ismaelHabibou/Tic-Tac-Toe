package sample;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * This class models the tic-tac toe game..
 */
public class TicTacToe extends BorderPane {
    /**
     * Data field: the board of the game
     */
    private Board board;

    /**
     * Data field: character of the player.
     */
    private char whoseTurn;

    /**
     * Data field: the rule of the game
     */
    private Rule rule;

    /**
     * Data field: message
     */
    private Label status;

    /**
     * Create a default TicTac Game
     */
    public TicTacToe() {
        this('X', "It is X's turn.");
    }

    /**
     * Create a tic-tac with specific parameters: whoseTurn, message
     *
     * @param whoseTurn The turn character of player.
     * @param message   Display the status of the game.
     */
    private TicTacToe(char whoseTurn, String message) {
        this.whoseTurn = whoseTurn; // set it to the turn of the player with character whoseturn.
        this.status = new Label(message);
        this.board = new Board();
        this.rule = new Rule();

        this.setCenter(board); // add the board to the game
        this.setBottom(status); // add the status to the game

        this.board.setOnMouseClicked(this::addPlayerCharacterToBoard); // add the character of the player to the board when mouse is clicked
    }

    /**
     * Set the turn of the player
     *
     * @param whoseTurn The character of the player.
     */
    public void setWhoseTurn(char whoseTurn) {
        this.whoseTurn = whoseTurn;
    }

    /**
     * Get whose turn
     *
     * @return the character of the current player.
     */
    public char getWhoseTurn() {
        return whoseTurn;
    }

    /**
     * Set the message
     *
     * @param status status of the game.
     */
    public void setStatus(String status) {
        this.status.setText(status);
    }

    /**
     * Add player's character to the grid
     *
     * @param mouseEvent Event fired by the players clicked.
     */
    public void addPlayerCharacterToBoard(MouseEvent mouseEvent) {

        //Before adding the characters of the player on the board, verify that the cell is empty and the game is not yet won .
        if (board.isCellClickedEmpty(mouseEvent) && !rule.isWon(getWhoseTurn())) {

            board.updateCellAt(getWhoseTurn(), mouseEvent.getX(), mouseEvent.getY());

            // check the status of the game
            rule.statusOfTheGame();
        }
    }

    /**
     * Reset the game only if the game is over
     */
    public void reset() {
        // Reset the game when it is full or won by one of the player.
        if (rule.isBoardFull() || rule.isWon('X') || rule.isWon('O')) {
            board.rePaint();
            setWhoseTurn('X');
            setStatus("It is " + getWhoseTurn() + " turn.");
        }
    }


    /**
     * This class represents the rule that governs the tic-tac toe game.
     * The rule is as follows:
     * If one player has three consecutive symbols on the board he wins.
     * If the grid of the game is full then it is a draw.
     */
    private class Rule {

        /**
         * Check if the board of the game is full.
         *
         * @return true if the board is full.
         */
        public boolean isBoardFull() {
            return board.isGridFull();
        }

        /**
         * Check if the game is won
         *
         * @param playerCharacter Current player's character.
         * @return true if the player with the following character won.
         */
        public boolean isWon(char playerCharacter) {

            return checkRow(playerCharacter) || checkColumn(playerCharacter) || checkDiagonals(playerCharacter);
        }

        /**
         * Check the rows
         *
         * @param playerCharacter Current player's character
         * @return true if there are three consecutive symbols for the same player on the diagonal.
         */
        private boolean checkRow(char playerCharacter) {
            // check the rows
            for (int i = 0; i < Board.NUMBER_OF_ROWS; i++)
                if (board.getCellTokenAt(i, 0) == playerCharacter && board.getCellTokenAt(i, 1) == playerCharacter &&
                        board.getCellTokenAt(i, 1) == playerCharacter && board.getCellTokenAt(i, 2) == playerCharacter)
                    return true;

            return false;
        }

        /**
         * Check the columns
         *
         * @param playerCharacter Current player character
         * @return true if there are three consecutive symbols for the same player on the diagonal.
         */
        private boolean checkColumn(char playerCharacter) {
            // Check the column
            for (int j = 0; j < Board.NUMBER_OF_COLUMNS; j++)
                if (board.getCellTokenAt(0, j) == playerCharacter && board.getCellTokenAt(1, j) == playerCharacter &&
                        board.getCellTokenAt(1, j) == playerCharacter && board.getCellTokenAt(2, j) == playerCharacter)
                    return true;
            return false;
        }

        /**
         * Check the two diagonals
         *
         * @param playerCharacter Current player character
         * @return true if there are three consecutive symbols for the same player on the diagonal.
         */
        private boolean checkDiagonals(char playerCharacter) {
            // Check the diagonals

            if (board.getCellTokenAt(Board.NUMBER_OF_ROWS - 3, Board.NUMBER_OF_COLUMNS - 3) == playerCharacter &&
                    board.getCellTokenAt(Board.NUMBER_OF_ROWS - 2, Board.NUMBER_OF_COLUMNS - 2) == playerCharacter &&
                    board.getCellTokenAt(Board.NUMBER_OF_ROWS - 2, Board.NUMBER_OF_COLUMNS - 2) == playerCharacter &&
                    board.getCellTokenAt(Board.NUMBER_OF_ROWS - 1, Board.NUMBER_OF_COLUMNS - 1) == playerCharacter)
                return true;

            if (board.getCellTokenAt(Board.NUMBER_OF_ROWS - 3, Board.NUMBER_OF_COLUMNS - 1) == playerCharacter &&
                    board.getCellTokenAt(Board.NUMBER_OF_ROWS - 2, Board.NUMBER_OF_COLUMNS - 2) == playerCharacter &&
                    board.getCellTokenAt(Board.NUMBER_OF_ROWS - 2, Board.NUMBER_OF_COLUMNS - 2) == playerCharacter &&
                    board.getCellTokenAt(Board.NUMBER_OF_ROWS - 1, Board.NUMBER_OF_COLUMNS - 3) == playerCharacter)
                return true;

            return false;
        }

        /**
         * Print the state of the game
         */
        private void statusOfTheGame() {
            if (rule.isWon(getWhoseTurn())) {
                setStatus(getWhoseTurn() + " won! The game is over. Press R to restart.");

            } else if (rule.isBoardFull()) {
                setStatus("Draw! The game is over. Press R to restart.");
            } else {
                setWhoseTurn((getWhoseTurn() == 'X') ? 'O' : 'X');
                status.setText("It is " + whoseTurn + " turn.");
            }
        }
    }
}

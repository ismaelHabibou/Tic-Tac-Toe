package sample;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

/**
 * This class models the board of the tic-tac toe game.
 * It is made up of 9 cells in a 3 * 3 matrix.
 */
public class Board extends GridPane {
    /**
     * Data field: number of rows on the board
     */
    static final int NUMBER_OF_ROWS = 3;

    /**
     * Data field: number of columns on the board
     */
    static final int NUMBER_OF_COLUMNS = 3;

    /**
     * Data field: row index of the cells on the board.
     */
    private int rowIndex = 0;

    /**
     * Data field: column index of the cells on the board.
     */
    private int columnIndex = 0;

    /**
     * Data field: height of the game board
     */
    private double height = 600;

    /**
     * Data field: width of the game board
     */
    private double width = 600;

    /**
     * Data field: tic-tac grid
     */
    private Cell[][] grid;

    /**
     * Create a board for the tic-tac game
     */
    Board() {
        // Initialize the board of the game
        grid = new Cell[NUMBER_OF_ROWS][NUMBER_OF_ROWS];

        // draw the grid
        drawGrid();

        createCells(); // fill the grid with cells

        // Set the width and height of the grid
        setPrefSize(width, height);
    }

    /**
     * Draw the cells the of the board
     */
    private void drawGrid() {
        // make the lines of the grid pane visible
        this.setGridLinesVisible(true);

        // Draw the columns
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / NUMBER_OF_COLUMNS);
            this.getColumnConstraints().add(columnConstraints);
        }

        // Draw the rows
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / NUMBER_OF_ROWS);
            this.getRowConstraints().add(rowConstraints);
        }
    }

    /**
     * Create cells and add them to the board.
     */
    private void createCells() {
        for (int i = 0; i < NUMBER_OF_ROWS; i++)
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                grid[i][j] = new Cell();
                this.add(grid[i][j], j, i);// add the cell the to the grid.
            }
    }

    /**
     * Set the height of the board to the prefer height
     *
     * @param prefHeight The prefer height
     * @param prefWidth  The prefer width
     */
    @Override
    public void setPrefSize(double prefWidth, double prefHeight) {
        super.setPrefSize(prefWidth, prefHeight);
    }

    /**
     * Repaint the grid : delete everything from the grid and restore the grid to its initial state.
     */
    void rePaint() {
        // Remove all the stackPane elements from the board.
        for (Node child : this.getChildren()) {
            if (child instanceof Cell)
                ((Cell) child).clear();
        }

    }

    /**
     * Get the rowIndex of the clicked cell on the grid
     *
     * @param yCoordinate y-Coordinate of the mouse on the grid
     * @return the rowindex of the cells which has the y-coordinate.
     */
    private int computeRowIndex(double yCoordinate) {
        return (int) (yCoordinate / (height / NUMBER_OF_ROWS));
    }

    /**
     * Get the columnIndex of the clicked cell on grid
     *
     * @param xCoordinate x-coordinate of the mouse on the grid
     * @return the column index of the cells associated with following x-coordinate.
     */
    private int computeColumnIndex(double xCoordinate) {
        return (int) (xCoordinate / (width / NUMBER_OF_COLUMNS));
    }

    /**
     * Check if the grid is full
     *
     * @return true if the grid is full.
     */
    boolean isGridFull() {
        for (int i = 0; i < NUMBER_OF_ROWS; i++)
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++)
                if (grid[i][j].isEmpty())
                    return false;

        return true;
    }

    /**
     * Update entry with new value
     *
     * @param whoseTurn   The current player
     * @param xCoordinate x-Coordinate of the cell on  the board
     * @param yCoordinate y-Coordinate of the cell on the board.
     */
    void updateCellAt(char whoseTurn, double xCoordinate, double yCoordinate) {
        // Update the rowIndex and columnIndex before adding character to the board.
        rowIndex = computeRowIndex(yCoordinate);
        columnIndex = computeColumnIndex(xCoordinate);

        grid[rowIndex][columnIndex].setToken(whoseTurn);
    }

    /**
     * Check if the cell click is empty
     *
     * @param event The event fired by the players click.
     * @return true if the cell clicked is empty.
     */
    boolean isCellClickedEmpty(MouseEvent event) {
        // Update row index and column index
        this.rowIndex = computeRowIndex(event.getY());
        this.columnIndex = computeColumnIndex(event.getX());

        return grid[rowIndex][columnIndex].isEmpty();
    }

    /**
     * Check if the cell at specific row index and column index is empty
     *
     * @return token of the cell.
     * @param rowIndex The row index of the cell in the grid.
     * @param columnIndex The column index of the cell in the grid.
     */
    public char getCellTokenAt(int rowIndex, int columnIndex) {
        return grid[rowIndex][columnIndex].getToken();
    }

    /**
     * This class represents the individual cells.
     */
    static class Cell extends StackPane {
        /**
         * Data field: the token of the cell.
         * the value of the tokens can be either O or X
         */
        private char token;

        /**
         * Create a construct default tokens
         */
        Cell() {
            this(' ');
        }

        /**
         * Create a cell with specific token
         *
         * @param token token passed by the player of the game.
         */
        Cell(char token) {
            this.token = token;
        }

        /**
         * Get the the token
         *
         * @return token
         */
        char getToken() {
            return token;
        }

        /**
         * Set the token of the cell.
         *
         * @param token set the token of the cell.
         */
        void setToken(char token) {
            this.token = token;

            if (token == 'X') {
                this.getChildren().add(getSymbolForPlayer1());
            } else if (token == 'O')
                this.getChildren().add(getSymbolForPlayer2());

        }

        /**
         * Check if the cell is empty
         *
         * @return true if the token of the cell is empty.
         */
        boolean isEmpty() {
            return this.token == ' ';
        }

        /**
         * Clear a cell
         */
        public void clear() {
            this.token = ' ';
            this.getChildren().clear();
        }

        /**
         * Create The symbol for the second person
         *
         * @return Symbol for player1
         */
        private Ellipse getSymbolForPlayer2() {
            Ellipse ellipse = new Ellipse(75, 50);
            ellipse.setFill(null);
            ellipse.setStroke(Color.BLUE);
            ellipse.setStrokeWidth(5);
            return ellipse;
        }

        /**
         * Create the symbol of the second player
         *
         * @return Symbol for player2
         */
        private Group getSymbolForPlayer1() {
            Line line1 = new Line(10, 10, 180, 180);
            line1.setStroke(Color.RED);
            line1.setStrokeWidth(5);
            Line line2 = new Line(180, 10, 10, 180);
            line2.setStroke(Color.RED);
            line2.setStrokeWidth(5);

            Group group = new Group();
            group.getChildren().addAll(line1, line2);
            return group;
        }
    }
}

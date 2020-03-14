package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is used to control the tic-tac toe game.
 * @author Ismael Habibou Issaka
 * @version 12-01-2020
 */
public class TicTacToeController extends Application {
    /**
     * Data field: The game tic tac
     */
    private TicTacToe ticTac = new TicTacToe();

    /**
     * Create a scene and places the scene on the stage.
     * Reset the Tic-tac toe game if one of the player pressed the key R (The game will reset only when the game is over.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create a scene
        Scene scene = new Scene(ticTac);
        primaryStage.setTitle("Tic-Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);

        // Reset the board only if the game is over or won.
        scene.setOnKeyPressed(keyEvent -> ticTac.reset());
    }

    public static void main(String[] args) {
        launch(args);
    }
}

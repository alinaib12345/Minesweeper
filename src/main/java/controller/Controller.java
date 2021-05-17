package controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Board;
import model.Tile;
import view.*;

import java.io.IOException;

public class Controller {
    private Board board;
    private View view;
    @FXML
    TextField columns;
    @FXML
    TextField rows;
    @FXML
    TextField bombs;
    @FXML
    Label wrong;

    public Controller(Board board, View view) {
        this.board = board;
        this.view = view;
    }

    public Controller(View view) {
        this.view = view;
    }

    public void playNewGame() {
        int x;;
        int y;
        int bombs;
            try {
                x = Integer.parseInt(columns.getText());
                y = Integer.parseInt(rows.getText());
                bombs = Integer.parseInt(this.bombs.getText());
                if (x <= 0 || y <= 0 || bombs <= 0 || x * y <= bombs || x > 40 || y > 25
                ) {
                    wrong.setVisible(true);
                    return;
                }
                view.setConfiguration(y, x, bombs);
            } catch (Exception i) {
                wrong.setVisible(true);
                return;
            }

        if (!view.isConfigured())
            return;

        Minesweeper.mainStage.setScene(new Scene(view.createGameBoard()));
    }

    public void returnToMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        loader.setController(this);

        Parent root = loader.load();
        Minesweeper.mainStage.setScene(new Scene(root));
    }

    public void reset() {
        Minesweeper.mainStage.getScene().setRoot(view.createGameBoard());
    }

    public void exit() {
        System.exit(0);
    }

    public void mouseClickLeft(Tile tile) {
        board.openTile(tile);
        view.update();
    }

    public void mouseClickRight(Tile tile) {
        board.flagTile(tile);
        view.update();
    }

}
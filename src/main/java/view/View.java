package view;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import controller.Controller;
import model.Board;
import model.Tile;

import java.io.IOException;

public class View {
    private Board field;
    private Controller controller;
    private AnchorPane root;
    private Label flagsLeft;
    private StackPane designations;
    private Group grid;

    private final double r = 17;
    private final double h = r * Math.sqrt(0.75);
    private final double tileHeight = 2 * r;
    private final double tileWidth = 2 * h;

    private final int xOffset = 40;
    private final int yOffset = 60;

    private boolean isConfigured;

    private int rows, tiles, bombs;


    public void setConfigure(int rows, int tiles, int bombs) {
        this.rows = rows;
        this.tiles = tiles;
        this.bombs = bombs;

        isConfigured = true;
    }

    public boolean isConfigured() {
        return isConfigured;
    }

    public Parent createGameBoard() {
        root = new AnchorPane();

        field = new Board(rows, tiles, bombs);
        controller = new Controller(field, this);

        double windowHeight = 2.0 * yOffset + field.getRows() / 2.0 * (tileHeight + r);
        double windowWidth = 2.5 * xOffset + field.getColumns() * tileWidth;
        root.setPrefHeight(windowHeight);
        root.setPrefWidth(windowWidth);


        designations = new StackPane();
        Rectangle rectangle = new Rectangle(windowWidth / 2, 50, 100, 25);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.LIGHTYELLOW);
        rectangle.setStrokeWidth(0.5);
        Text text = new Text("PLAYING...");
        text.setFont(Font.font(18));
        designations.getChildren().addAll(rectangle, text);

        AnchorPane.setTopAnchor(designations, 15d);
        designations.setTranslateX(root.getPrefWidth() / 2 - h);
        root.getChildren().add(designations);

        flagsLeft = new Label("Flags: " + field.getFlagsLeft());
        flagsLeft.setFont(Font.font(24));
        AnchorPane.setTopAnchor(flagsLeft, 15d);
        AnchorPane.setLeftAnchor(flagsLeft, 30d);
        root.getChildren().add(flagsLeft);

        Button exit = new Button("Exit");
        exit.setPrefSize(80, 20);
        AnchorPane.setBottomAnchor(exit, 15d);
        AnchorPane.setRightAnchor(exit, 15d);
        exit.setOnAction(actionEvent -> controller.exit());
        root.getChildren().add(exit);

        Button reset = new Button("Reset");
        reset.setPrefSize(80, 20);
        AnchorPane.setBottomAnchor(reset, 15d);
        reset.setTranslateX(root.getPrefWidth() / 2 - 40);
        reset.setOnAction(actionEvent -> controller.reset());
        root.getChildren().add(reset);

        Button returnToMenu = new Button("Menu");
        returnToMenu.setPrefSize(80, 20);
        AnchorPane.setBottomAnchor(returnToMenu, 15d);
        AnchorPane.setLeftAnchor(returnToMenu, 15d);
        returnToMenu.setOnAction(actionEvent -> {
            try {
                controller.returnToMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        root.getChildren().add(returnToMenu);

        grid = new Group();
        for (Tile[] rows : field.getGrid()) {
            for (Tile tile : rows) {
                StackPane newTile = createClosedTile(tile);
                grid.getChildren().add(newTile);
            }
        }
        root.getChildren().add(grid);

        return root;
    }

    public void update() {
        flagsLeft.setText("Flags: " + field.getFlagsLeft());

        if (field.isGameOver()) {
            designations.getChildren().remove(1);
            Text text = new Text(field.isLost()
                    ? "YOU LOSE :("
                    : "YOU WON! :)");
            text.setFont(Font.font(14));

            designations.getChildren().add(text);
        }

        grid.getChildren().clear();
        for (Tile[] rows : field.getGrid()) {
            for (Tile tile : rows) {
                StackPane newTile = tile.isOpened()
                        ? createOpenedTile(tile)
                        : createClosedTile(tile);
                grid.getChildren().add(newTile);
            }
        }
    }

    public StackPane createClosedTile(Tile tile) {
        StackPane node = new StackPane();

        Hexagon cover = new Hexagon(r, h);
        cover.setFill(Color.YELLOW);
        cover.setStrokeWidth(1);
        cover.setStroke(Color.WHITE);

        Text flag = new Text("F");
        flag.setFill(field.isLost()
                ? tile.hasBomb()
                ? Color.GREEN
                : Color.RED
                : Color.BLACK);
        flag.setFont(Font.font(16));
        if (!tile.isFlagged())
            flag.setVisible(false);

        node.getChildren().addAll(cover, flag);
        if (field.isLost() && tile.hasBomb() && !tile.isFlagged()) {
            Image bomb = new Image(String.valueOf(getClass().getResource("/bee1.png")));
            ImageView iv = new ImageView();
            iv.setImage(bomb);
            iv.setFitHeight(25);
            iv.setFitWidth(20);
            node.getChildren().add(iv);
        }

        node.setTranslateY(tile.getY() * tileHeight * 0.75 + yOffset);
        node.setTranslateX(tile.getX() * tileWidth + (tile.getY() % 2) * h + xOffset);

        node.setOnMouseClicked(event -> {
            switch (event.getButton()) {
                case PRIMARY:
                    controller.mouseClickLeft(tile);
                    break;
                case SECONDARY:
                    controller.mouseClickRight(tile);
                    break;
            }
        });

        return node;
    }

    public StackPane createOpenedTile(Tile tile) {
        StackPane node = new StackPane();

        Hexagon hex = new Hexagon(r, h);
        hex.setFill(Color.WHITE);
        hex.setStrokeWidth(0.4);
        hex.setStroke(Color.BLACK);

        Text bombs = new Text(tile.getBombsAround() > 0 ? String.valueOf(tile.getBombsAround()) : "");
        bombs.setFont(Font.font(16));

        node.getChildren().addAll(hex, bombs);
        if (tile.hasBomb()){
            Image bomb = new Image(String.valueOf(getClass().getResource("/bee1.png")));
            ImageView iv = new ImageView();
            iv.setImage(bomb);
            iv.setFitHeight(25);
            iv.setFitWidth(20);
            node.getChildren().add(iv);
        }
        node.setTranslateY(tile.getY() * tileHeight * 0.75 + yOffset);
        node.setTranslateX(tile.getX() * tileWidth + (tile.getY() % 2) * h + xOffset);

        return node;
    }

    private static class Hexagon extends Polygon {
        Hexagon(double r, double n) {
            getPoints().addAll(
                    0.0, r,
                    n, r * 0.5,
                    n, -r * 0.5,
                    0.0, -r,
                    -n, -r * 0.5,
                    -n, r * 0.5
            );
        }
    }
}

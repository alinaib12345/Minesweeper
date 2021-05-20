import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobotException;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import view.Minesweeper;
import view.View;

import static org.testfx.api.FxAssert.verifyThat;

public class TestGUI extends ApplicationTest {

    final String PLAY = "PLAY";
    final String WRONG = "PLEASE, ENTER CORRECT VALUES";
    final String ROWS = "#rows";
    final String COLUMNS = "#columns";
    final String BOMBS = "#bombs";

    @BeforeEach
    public void setUp() throws Exception{
        ApplicationTest.launch(Minesweeper.class);
    }

    public Stage mainStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        mainStage.setTitle("Minesweeper");
        View view = new View(mainStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        loader.setController(new Controller(view, mainStage));
        Parent root = loader.load();
        mainStage.setScene(new Scene(root));
        mainStage.setResizable(false);
        mainStage.show();
    }

    @AfterEach
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void emptyFields() {
        clickOn(PLAY);
        verifyThat("#wrong", (Label label) -> {
            String text = label.getText();
            return text.contains(WRONG);
        });
    }

    @Test
    public void incorrectValues1() {
        clickOn(ROWS).write("30");
        clickOn(COLUMNS).write("15");
        clickOn(BOMBS).write("10");
        clickOn(PLAY);
        verifyThat("#wrong", (Label label) -> {
            String text = label.getText();
            return text.contains(WRONG);
        });
    }

    @Test
    public void incorrectValues2() {
        clickOn(ROWS).write("10");
        clickOn(COLUMNS).write("50");
        clickOn(BOMBS).write("10");
        clickOn(PLAY);
        verifyThat("#wrong", (Label label) -> {
            String text = label.getText();
            return text.contains(WRONG);
        });
    }

    @Test
    public void incorrectValues3() {
        clickOn(ROWS).write("10");
        clickOn(COLUMNS).write("15");
        clickOn(BOMBS).write("jgihg");
        clickOn(PLAY);
        verifyThat("#wrong", (Label label) -> {
            String text = label.getText();
            return text.contains(WRONG);
        });
    }

    @Test
    public void nonExistentButton() {
        Assertions.assertThrows(FxRobotException.class, () -> clickOn("nonExistentButton"));
    }


}

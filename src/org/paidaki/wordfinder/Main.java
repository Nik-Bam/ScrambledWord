package org.paidaki.wordfinder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.paidaki.wordfinder.app.WordFinder;
import org.paidaki.wordfinder.gui.WindowController;

import java.io.IOException;

import static org.paidaki.wordfinder.app.Constants.HEIGHT;
import static org.paidaki.wordfinder.app.Constants.TITLE;
import static org.paidaki.wordfinder.app.Constants.WIDTH;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/window.fxml"));
        Parent root = loader.load();
        WindowController controller = loader.getController();

        controller.setWordFinder(new WordFinder());
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }
}

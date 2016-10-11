package org.paidaki.wordfinder.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.paidaki.wordfinder.app.WordFinder;
import org.paidaki.wordfinder.gui.dialogs.ErrorDialog;
import org.paidaki.wordfinder.gui.dialogs.OpenFileDialog;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.paidaki.wordfinder.app.Constants.GITHUB_URL;

public class WindowController {

    private OpenFileDialog openFileDialog;
    private ErrorDialog errorDialog;
    private WordFinder wordFinder;
    private ObservableList<String> listMatches;

    @FXML
    public Button btnSearch;
    @FXML
    public HBox container;
    @FXML
    private TextField tfFile;
    @FXML
    private TextField tfWord;
    @FXML
    private ListView<String> lvMatches;

    @FXML
    private void initialize() {
        openFileDialog = new OpenFileDialog();
        errorDialog = new ErrorDialog();
        listMatches = FXCollections.observableArrayList();
        lvMatches.setItems(listMatches);

        tfWord.textProperty().addListener((observable, oldValue, newValue)
                -> btnSearch.setDisable(!newValue.matches(".*\\p{L}.*")));
    }

    @FXML
    private void searchForMatches(ActionEvent event) {
        Control source = (Control) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        String input = tfWord.getText();
        List<String> matches = wordFinder.searchWord(input);

        tfWord.setText(wordFinder.fixWord(input));

        if (matches.isEmpty()) {
            listMatches.clear();
            errorDialog.show(stage, "No matches found.");
        } else {
            listMatches.setAll(matches);
        }
    }

    @FXML
    private void openFile(ActionEvent event) {
        Button source = (Button) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        File file = openFileDialog.show(stage);

        if (file != null) {
            if (wordFinder.loadDictionary(file)) {
                tfFile.setText(file.getAbsolutePath());
                container.setDisable(false);
            } else {
                errorDialog.show(stage, "Invalid Dictionary File.");
            }
        }
    }

    @FXML
    private void creatorUrl() {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(GITHUB_URL));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public WordFinder getWordFinder() {
        return wordFinder;
    }

    public void setWordFinder(WordFinder wordFinder) {
        this.wordFinder = wordFinder;
    }
}

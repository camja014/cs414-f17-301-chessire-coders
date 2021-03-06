package edu.colostate.cs.cs414.chesshireCoders.jungleClient.view;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import static javafx.scene.control.Alert.AlertType.ERROR;

public abstract class BaseView implements View {

    @Override
    public void showError(final String message) {
        System.err.println("[ERROR]: " + message);
        Platform.runLater(() -> {
            Alert alert = new Alert(ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(message);

            alert.showAndWait();
        });
    }

    @Override
    public void showWarning(String message) {

    }

    @Override
    public void showInfo(String message) {
    }
}

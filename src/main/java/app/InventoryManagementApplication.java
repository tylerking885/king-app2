/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Tyler King
 */

package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class InventoryManagementApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Gui.fxml")));

            assert root != null;
            Scene scene = new Scene(root, 1150, 700);
            stage.setScene(scene);
            stage.setTitle("Inventory Management Application");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.example.asu_bookstore;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class ConfirmationScreen {
    private String details;
    private boolean isPurchase;

    public ConfirmationScreen(String details, boolean isPurchase) {
        this.details = details;
        this.isPurchase = isPurchase;
    }

    public void start(Stage stage) {
        VBox layout = new VBox(10);
        Label confirmationLabel = new Label(details);
        Button confirmButton = new Button("Confirm");
        Button cancelButton = new Button("Cancel");

        // Action for the Confirm button
        confirmButton.setOnAction(e -> {
            updateDatabase();
            stage.close();
        });

        // Action for the Cancel button
        cancelButton.setOnAction(e -> stage.close());

        layout.getChildren().addAll(confirmationLabel, confirmButton, cancelButton);
        Scene scene = new Scene(layout, 400, 200);
        stage.setScene(scene);
        stage.setTitle("Confirmation");
        stage.show();
    }

    private void updateDatabase() {
        try (FileWriter writer = new FileWriter("booksAvailable.txt", true)) {
            if (isPurchase) {
                // Decrement the book stock (logic can be expanded based on the database structure)
                writer.write("Book Purchased: " + details + "\n");
            } else {
                // Increment the book stock
                writer.write("Book Sold: " + details + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error updating database: " + e.getMessage());
        }
    }
}

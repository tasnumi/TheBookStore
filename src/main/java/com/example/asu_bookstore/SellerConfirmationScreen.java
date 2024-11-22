package com.example.asu_bookstore;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SellerConfirmationScreen extends Application {
    private String confirmationDetails;

    public SellerConfirmationScreen(String confirmationDetails) {
        this.confirmationDetails = confirmationDetails;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox layout = new VBox(15);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label confirmationMessage = new Label("Book Listed Successfully!");
        confirmationMessage.setStyle("-fx-font-size: 18px; -fx-text-fill: green;");

        Label details = new Label(confirmationDetails);
        details.setStyle("-fx-font-size: 14px;");

        Button returnButton = new Button("Return to Seller Screen");
        returnButton.setOnAction(e -> primaryStage.close());

        layout.getChildren().addAll(confirmationMessage, details, returnButton);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Seller Confirmation");
        primaryStage.show();
    }
}

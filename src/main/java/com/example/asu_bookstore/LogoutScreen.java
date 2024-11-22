package com.example.asu_bookstore;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.io.InputStream;

// This com.example.asu_bookstore.LogoutScreen class holds the layout and functionality of the program's logout screen.
// It provides an option for the user to log out and navigate back to the login screen.
public class LogoutScreen extends BorderPane {
    private Button logoutButton;
    private Button cancelButton;

    public LogoutScreen(final int WIDTH, final int HEIGHT, ASU_Bookstore control) {
        // These Rectangles create the background for the Logout Screen
        Rectangle maroonBackground = new Rectangle(WIDTH, WIDTH);
        maroonBackground.setFill(Color.web("#6e020f"));
        this.getChildren().add(maroonBackground);

        Rectangle goldBackgroundLeft = new Rectangle(0, 0, WIDTH / 4, HEIGHT);
        goldBackgroundLeft.setFill(Color.web("#ae7219"));
        this.getChildren().add(goldBackgroundLeft);

        Rectangle goldBackgroundRight = new Rectangle(WIDTH - (WIDTH / 4), 0, WIDTH / 4, HEIGHT);
        goldBackgroundRight.setFill(Color.web("#ae7219"));
        this.getChildren().add(goldBackgroundRight);

        // The sun image is loaded from the program's resources folder and is placed into the scene
        InputStream sunLogoStream = getClass().getResourceAsStream("/loginSun.png");
        Image sun = new Image(sunLogoStream);
        ImageView displaySun = new ImageView();
        displaySun.setImage(sun);
        displaySun.setFitWidth(125);
        displaySun.setPreserveRatio(true); // sun image is expanded but aspect ratio should be preserved
        displaySun.setX((WIDTH/2.5) + 7);
        displaySun.setY(HEIGHT/13);
        this.getChildren().add(displaySun);

        // Main container to hold the logout screen's interface elements
        VBox logoutContainer = new VBox();
        logoutContainer.setMinWidth(WIDTH);
        logoutContainer.setMinHeight(HEIGHT);
        logoutContainer.setAlignment(Pos.CENTER);
        logoutContainer.setPadding(new Insets(HEIGHT / 3, 0, 0, 0));
        logoutContainer.setSpacing(20);

        // Label to inform the user about the logout action
        Label logoutLabel = new Label("Are you sure you want to log out?");
        logoutLabel.setTextFill(Color.WHITE);
        logoutLabel.setStyle("-fx-font-size: 16px;");

        // Buttons for user actions
        logoutButton = new Button("Log Out");
        cancelButton = new Button("Cancel");
        logoutButton.setMaxWidth(200);
        cancelButton.setMaxWidth(200);

        logoutContainer.getChildren().addAll(logoutLabel, logoutButton, cancelButton);
        this.setCenter(logoutContainer);

        // Event handler for the logout button
        logoutButton.setOnAction(e -> {
            Alert logoutSuccess = new Alert(AlertType.INFORMATION);
            logoutSuccess.setTitle("Logout Successful");
            logoutSuccess.setHeaderText("You have been logged out.");
            logoutSuccess.setContentText("Thank you for using ASU Bookstore. Have a great day!");
            logoutSuccess.showAndWait();

            // Navigate back to the login screen
            control.switchScreen("login");
        });

        // Event handler for the cancel button
        cancelButton.setOnAction(e -> {
            // Navigate back to the previous screen
            control.switchScreen("previous");
        });
    }
}

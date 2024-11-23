package com.example.asu_bookstore;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SellerMainScreen extends BorderPane {
    private BookDatabase bookDatabase;

    public SellerMainScreen(final int WIDTH, final int HEIGHT, ASU_Bookstore control) {
        bookDatabase = new BookDatabase();

        Rectangle background = new Rectangle(WIDTH, WIDTH);
        background.setFill(Color.web("#E4CDE8"));
        this.getChildren().add(background);

        HBox mainLayout = new HBox();
        mainLayout.setSpacing(15);
        mainLayout.setPadding(new Insets(15));
        mainLayout.setAlignment(Pos.CENTER);

        VBox leftPanel = new VBox(20);
        leftPanel.setStyle("-fx-background-color: #be96c4; -fx-border-color: #8273da; -fx-border-width: 0.5; -fx-padding: 20");
        leftPanel.setMinWidth(WIDTH / 3.0);
        leftPanel.setAlignment(Pos.CENTER);

        TextField titleField = new TextField();
        titleField.setPromptText("Book Title");

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        ComboBox<String> genreComboBox = new ComboBox<>();
        genreComboBox.getItems().addAll("Natural Science", "Computer", "Math", "English", "Language", "Others");
        genreComboBox.setPromptText("Genre");

        ToggleGroup conditionGroup = new ToggleGroup();
        RadioButton used = new RadioButton("Used");
        used.setToggleGroup(conditionGroup);
        RadioButton likeNew = new RadioButton("Like New");
        likeNew.setToggleGroup(conditionGroup);
        RadioButton moderatelyUsed = new RadioButton("Moderately Used");
        moderatelyUsed.setToggleGroup(conditionGroup);
        RadioButton heavilyUsed = new RadioButton("Heavily Used");
        heavilyUsed.setToggleGroup(conditionGroup);

        leftPanel.getChildren().addAll(new Label("Add a Book for Sale:"), titleField, priceField, genreComboBox, used, likeNew, moderatelyUsed, heavilyUsed);

        Button sellButton = new Button("Sell");
        sellButton.setOnAction(e -> handleSell(titleField, priceField, genreComboBox, conditionGroup));

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> control.switchScreen("Login"));

        HBox actionButtons = new HBox(10, logoutButton, sellButton);
        actionButtons.setAlignment(Pos.CENTER);

        mainLayout.getChildren().add(leftPanel);
        this.setCenter(mainLayout);
        this.setBottom(actionButtons);
    }

    private void handleSell(TextField titleField, TextField priceField, ComboBox<String> genreComboBox, ToggleGroup conditionGroup) {
        String title = titleField.getText();
        String price = priceField.getText();
        String genre = genreComboBox.getValue();
        RadioButton selectedCondition = (RadioButton) conditionGroup.getSelectedToggle();

        if (title.isEmpty() || price.isEmpty() || genre == null || selectedCondition == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "All fields must be filled out!");
            alert.showAndWait();
            return;
        }

        String bookDetails = String.format("Title: %s | Price: %s | Genre: %s | Condition: %s", title, price, genre, selectedCondition.getText());
        ConfirmationScreen confirmationScreen = new ConfirmationScreen(bookDetails, false);
        confirmationScreen.start(new Stage());
    }
}



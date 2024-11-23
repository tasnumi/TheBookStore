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

import java.util.ArrayList;

public class BuyerMainScreen extends BorderPane {
    private VBox filteredItems = new VBox(15);
    private BookDatabase bookDatabase;

    public BuyerMainScreen(final int WIDTH, final int HEIGHT, ASU_Bookstore control) {
        bookDatabase = new BookDatabase();

        Rectangle background = new Rectangle(WIDTH, WIDTH);
        background.setFill(Color.web("#CDE8E5"));
        this.getChildren().add(background);

        HBox mainLayout = new HBox();
        mainLayout.setSpacing(15);
        mainLayout.setPadding(new Insets(15));
        mainLayout.setAlignment(Pos.CENTER);

        // Left Panel
        VBox leftPanel = new VBox(20);
        leftPanel.setStyle("-fx-background-color: #83b7b8; -fx-border-color: #1212f8; -fx-border-width: 0.5; -fx-padding: 20");
        leftPanel.setMinWidth(WIDTH / 3.0);
        leftPanel.setAlignment(Pos.CENTER);

        ComboBox<String> genreComboBox = new ComboBox<>();
        genreComboBox.getItems().addAll("Natural Science", "Computer", "Math", "English", "Language", "Others");
        genreComboBox.setPromptText("Select Genre");

        CheckBox usedCheckBox = new CheckBox("Used");
        CheckBox likeNewCheckBox = new CheckBox("Like New");
        CheckBox moderatelyUsedCheckBox = new CheckBox("Moderately Used");
        CheckBox heavilyUsedCheckBox = new CheckBox("Heavily Used");

        leftPanel.getChildren().addAll(new Label("Filter by Genre and Condition"), genreComboBox, usedCheckBox, likeNewCheckBox, moderatelyUsedCheckBox, heavilyUsedCheckBox);

        // Right Panel
        VBox rightPanel = new VBox(20);
        rightPanel.setStyle("-fx-background-color: #83b7b8; -fx-border-color: #1212f8; -fx-border-width: 0.5; -fx-padding: 20");
        rightPanel.setMinWidth(WIDTH / 3.0);
        rightPanel.getChildren().add(filteredItems);

        ScrollPane scrollPane = new ScrollPane(rightPanel);

        Button purchaseButton = new Button("Purchase");
        purchaseButton.setOnAction(e -> handlePurchase());

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> control.switchScreen("Login"));

        HBox actionButtons = new HBox(10, logoutButton, purchaseButton);
        actionButtons.setAlignment(Pos.CENTER);

        mainLayout.getChildren().addAll(leftPanel, scrollPane);
        this.setCenter(mainLayout);
        this.setBottom(actionButtons);

        // Event Handling
        genreComboBox.setOnAction(e -> updateFilteredItems(genreComboBox, usedCheckBox, likeNewCheckBox, moderatelyUsedCheckBox, heavilyUsedCheckBox));
        usedCheckBox.setOnAction(e -> updateFilteredItems(genreComboBox, usedCheckBox, likeNewCheckBox, moderatelyUsedCheckBox, heavilyUsedCheckBox));
        likeNewCheckBox.setOnAction(e -> updateFilteredItems(genreComboBox, usedCheckBox, likeNewCheckBox, moderatelyUsedCheckBox, heavilyUsedCheckBox));
        moderatelyUsedCheckBox.setOnAction(e -> updateFilteredItems(genreComboBox, usedCheckBox, likeNewCheckBox, moderatelyUsedCheckBox, heavilyUsedCheckBox));
        heavilyUsedCheckBox.setOnAction(e -> updateFilteredItems(genreComboBox, usedCheckBox, likeNewCheckBox, moderatelyUsedCheckBox, heavilyUsedCheckBox));
    }

    private void updateFilteredItems(ComboBox<String> genreComboBox, CheckBox... conditions) {
        filteredItems.getChildren().clear();
        String genre = genreComboBox.getValue();
        ArrayList<String> books = bookDatabase.getBooks();

        for (String book : books) {
            if ((genre == null || book.contains("Genre: " + genre)) &&
                    matchesCondition(book, conditions)) {
                filteredItems.getChildren().add(new Label(book));
            }
        }
    }

    private boolean matchesCondition(String book, CheckBox... conditions) {
        for (CheckBox condition : conditions) {
            if (condition.isSelected() && book.contains("Condition: " + condition.getText())) {
                return true;
            }
        }
        return false;
    }

    private void handlePurchase() {
        StringBuilder transactionDetails = new StringBuilder("Purchased Books:\n");
        for (var node : filteredItems.getChildren()) {
            if (node instanceof Label) {
                transactionDetails.append(((Label) node).getText()).append("\n");
            }
        }
        ConfirmationScreen confirmationScreen = new ConfirmationScreen(transactionDetails.toString(), true);
        confirmationScreen.start(new Stage());
    }
}

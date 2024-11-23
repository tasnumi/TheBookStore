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

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

// This com.example.asu_bookstore.SellerMainScreen class will eventually host the seller's screen in the program.
public class SellerMainScreen extends BorderPane {
    ScrollPane r = new ScrollPane();

    public SellerMainScreen(final int WIDTH, final int HEIGHT, ASU_Bookstore control) {
        ASU_Bookstore bookstore = new ASU_Bookstore();
        BuyerMainScreen buyer = new BuyerMainScreen(WIDTH, HEIGHT, bookstore);
        ArrayList<String> bookDatabase = buyer.getBookData();

        VBox vbox = new VBox();

        for (String book : bookDatabase) {
            Label bookLabel = new Label(book);
            vbox.getChildren().add(bookLabel);
        }
        r.setContent(vbox);

        Rectangle lightPurpBackground = new Rectangle(WIDTH, WIDTH); // apricot background
        lightPurpBackground.setFill(Color.web("#E4CDE8"));
        this.getChildren().add(lightPurpBackground);

        // This HBox contains everything that will be placed inside the main seller screen
        HBox entireContainer = new HBox();
        entireContainer.setSpacing(15);
        entireContainer.setPadding(new Insets(15, 15, 0, 15));
        entireContainer.setAlignment(Pos.CENTER);

        // This VBox contains everything on the left (condition + genre)
        VBox leftContainer = new VBox(20);
        leftContainer.setStyle("-fx-background-color: #be96c4; -fx-border-color: #8273da; -fx-border-width: 0.5; -fx-padding: 20");
        leftContainer.setMinWidth((WIDTH / 2.5) - 30);
        leftContainer.setMaxHeight(HEIGHT - 60);
        leftContainer.setPadding(new Insets(0, 15, 0, 0));

        // Add book condition and genre options
        Label label1 = new Label("Please select the condition of the book for sale:");
        label1.setStyle(" -fx-font: 9 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");

        ToggleGroup t = new ToggleGroup();
        RadioButton checkbox1 = new RadioButton("Used");
        checkbox1.setStyle(" -fx-text-fill: #FFFFFF");
        checkbox1.setToggleGroup(t);

        RadioButton checkbox2 = new RadioButton("Like New");
        checkbox2.setStyle(" -fx-text-fill: #FFFFFF");
        checkbox2.setToggleGroup(t);

        RadioButton checkbox3 = new RadioButton("Moderately Used");
        checkbox3.setStyle(" -fx-text-fill: #FFFFFF");
        checkbox3.setToggleGroup(t);

        RadioButton checkbox4 = new RadioButton("Heavily Used");
        checkbox4.setStyle(" -fx-text-fill: #FFFFFF");
        checkbox4.setToggleGroup(t);

        Label label2 = new Label("Please select a book genre");
        label2.setStyle(" -fx-font: 9 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");

        ComboBox<String> combobox = new ComboBox<>();
        combobox.getItems().addAll("Natural Science", "Computer", "Math", "English", "Language", "Others");

        Label label3 = new Label("Please Enter the Original Book Price:");
        label3.setStyle(" -fx-font: 9 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");

        TextField text = new TextField();

        Label label4 = new Label("Please enter the book title");
        label4.setStyle(" -fx-font: 9 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");
        TextField title = new TextField();

        leftContainer.getChildren().addAll(label1, checkbox1, checkbox2, checkbox3, checkbox4, label2, combobox, label3, text, label4, title);

        Button listBook = new Button("List Book");

        VBox rightContainer = new VBox(10);
        rightContainer.setStyle("-fx-background-color: #be96c4; -fx-border-color: #8273da; -fx-border-width: 0.5; -fx-padding: 20");
        rightContainer.setMinWidth((WIDTH / 2.5) - 30);
        rightContainer.setMaxHeight(HEIGHT - 60);
        rightContainer.setPadding(new Insets(0, 0, 0, 15));

        // Handle listing books
        listBook.setOnAction(e -> {
            String confirmationDetails = getSellerConfirmationDetails(t, combobox, text, title);
            if (confirmationDetails != null) {
                // Show the Seller Confirmation Screen
                SellerConfirmationScreen confirmationScreen = new SellerConfirmationScreen(confirmationDetails);
                Stage confirmationStage = new Stage();
                confirmationScreen.start(confirmationStage);

                // Save the listing to a file
                saveListingToFile(confirmationDetails);
            }
        });

        rightContainer.getChildren().addAll(listBook, r);
        entireContainer.getChildren().addAll(leftContainer, rightContainer);
        this.setCenter(entireContainer);
    }

    private String getSellerConfirmationDetails(ToggleGroup t, ComboBox<String> combobox, TextField text, TextField title) {
        try {
            RadioButton selectedCondition = (RadioButton) t.getSelectedToggle();
            String condition = selectedCondition.getText();
            String genre = combobox.getValue();
            String price = text.getText();
            String bookTitle = title.getText();

            if (condition == null || genre == null || genre.isEmpty() || price.isEmpty() || bookTitle.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please complete all fields before listing the book.");
                alert.showAndWait();
                return null;
            }

            return String.format("Title: %s\nCondition: %s\nGenre: %s\nPrice: $%s", bookTitle, condition, genre, price);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred: " + ex.getMessage());
            alert.showAndWait();
            return null;
        }
    }

    private void saveListingToFile(String listingDetails) {
        try (FileWriter writer = new FileWriter("listings.txt", true)) {
            writer.write(listingDetails + "\n");
        } catch (IOException e) {
            System.out.println("Error saving listing: " + e.getMessage());
        }
    }
}

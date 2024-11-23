package com.example.asu_bookstore;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

// This com.example.asu_bookstore.BuyerMainScreen class will eventually host the buyer's screen in the program.
public class BuyerMainScreen extends BorderPane{
    private ArrayList<String> addBooksToFile = new ArrayList<String>();
    VBox filteredItems = new VBox(15);

    public BuyerMainScreen(final int WIDTH, final int HEIGHT, ASU_Bookstore control) {
        Rectangle turquoiseBackground = new Rectangle(WIDTH, WIDTH); // apricot background
        turquoiseBackground.setFill(Color.web("#CDE8E5"));
        this.getChildren().add(turquoiseBackground);

        getDataFromTextDB();

        // This HBox contains everything that will be placed inside the main buyer screen
        HBox entireContainer = new HBox();
        entireContainer.setSpacing(15);
        entireContainer.setPadding(new Insets(15, 15, 0, 15));
        entireContainer.setAlignment(Pos.CENTER);

        // This VBox contains everything on the left (condition + genre)
        VBox leftContainer = new VBox();
        leftContainer.setStyle("-fx-background-color: #83b7b8; -fx-border-color: #1212f8; -fx-border-width: 0.5; -fx-padding: 20");
        leftContainer.setMinWidth((WIDTH/2.5) - 30);
        leftContainer.setMaxHeight(HEIGHT - 60);
        leftContainer.setAlignment(Pos.CENTER);
        leftContainer.setPadding(new Insets(0, 15, 0, 0));

        VBox bookConditionsGenre = new VBox(25); //VBox for book conditions
        Label selectCondition = new Label("Please select the condition of the book for purchase");
        Label selectGenre = new Label("Please select a book genre");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Natural Science", "Computer", "Math", "English", "Language", "Other");
        CheckBox used = new CheckBox("Used");
        CheckBox likeNew = new CheckBox("Like New");
        CheckBox moderatelyUsed = new CheckBox("Moderately Used");
        CheckBox heavilyUsed = new CheckBox("Heavily Used");
        bookConditionsGenre.getChildren().addAll(selectCondition, used, likeNew, moderatelyUsed, heavilyUsed, selectGenre, comboBox);

        //sets the style for all the text in the left container to white
        bookConditionsGenre.setStyle("-fx-font-size: 12px;");
        selectCondition.setStyle("-fx-text-fill: white;");
        selectGenre.setStyle("-fx-text-fill: white;");
        comboBox.setStyle("-fx-text-fill: white;");
        used.setStyle("-fx-text-fill: white;");
        likeNew.setStyle("-fx-text-fill: white;");
        moderatelyUsed.setStyle("-fx-text-fill: white;");
        heavilyUsed.setStyle("-fx-text-fill: white;");

        leftContainer.getChildren().add(bookConditionsGenre);

        //Event handler for when the user selects the certain checkboxes and ComboBox for genres.
        used.setOnAction(e -> handleSelection(used, likeNew, moderatelyUsed, heavilyUsed, comboBox, filteredItems));
        likeNew.setOnAction(e -> handleSelection(used, likeNew, moderatelyUsed, heavilyUsed, comboBox, filteredItems));
        moderatelyUsed.setOnAction(e -> handleSelection(used, likeNew, moderatelyUsed, heavilyUsed, comboBox, filteredItems));
        heavilyUsed.setOnAction(e -> handleSelection(used, likeNew, moderatelyUsed, heavilyUsed, comboBox, filteredItems));
        comboBox.setOnAction(e -> handleSelection(used, likeNew, moderatelyUsed, heavilyUsed, comboBox, filteredItems));


        // This VBox contains everything on the right (list of selectable books selected by user)
        VBox rightContainer = new VBox();
        rightContainer.setStyle("-fx-background-color: #83b7b8; -fx-border-color: #1212f8; -fx-border-width: 0.5; -fx-padding: 20");
        rightContainer.setMinWidth((WIDTH/2.5) - 30);
        rightContainer.setMaxHeight(HEIGHT - 60);
        rightContainer.setAlignment(Pos.CENTER);
        rightContainer.setPadding(new Insets(0, 0, 0, 15));

        ScrollPane rightPane = new ScrollPane();
        rightPane.setContent(filteredItems);
        rightContainer.getChildren().add(rightPane);


        // The buyer sun image is loaded from the program's resources folder and is placed into the scene
        InputStream sunLogoStream = getClass().getResourceAsStream("/buyerSun.png");
        Image sun = new Image(sunLogoStream);
        ImageView displaySun = new ImageView();
        displaySun.setImage(sun);
        displaySun.setFitWidth(125);
        displaySun.setPreserveRatio(true); // sun image is expanded but aspect ratio should be preserved
        displaySun.setX((WIDTH/2.5) + 7);
        displaySun.setY(HEIGHT/5);

        // These buttons allow the user to move though the program: Back to the login screen and onto the
        // Buyer finalization screen if the user sets up a proper purchase.
        HBox logAndPurchaseButtons = new HBox();
        logAndPurchaseButtons.setSpacing(WIDTH/2);
        logAndPurchaseButtons.setPadding(new Insets(0, 0, 5, 140));
        Button logOut = new Button("Log Out");

        logOut.setOnAction(e -> control.switchScreen(""));

        Button purchase = new Button("Purchase");
        // The behavior for these buttons can be defined here, which might be easier that creating
        // a separate ButtonHandler<ActionEvent>() class so that the button behavior code can use the
        // constructor parameters that are accessible here. See the LoginScreen buttons for an example.
        logAndPurchaseButtons.getChildren().addAll(logOut, purchase);

        // This adds the main components to the screen
        entireContainer.getChildren().addAll(leftContainer, displaySun, rightContainer);
        this.setCenter(entireContainer);
        this.setBottom(logAndPurchaseButtons);

    }

    public void handleSelection(CheckBox used, CheckBox likeNew, CheckBox moderatelyUsed, CheckBox heavilyUsed, ComboBox<String> genres, VBox filteredItems) {
        filteredItems.getChildren().clear();
        ArrayList<String> filteredBooks = new ArrayList<>();
        String selectedGenre = (String) genres.getValue();

        if(selectedGenre == null || selectedGenre.isEmpty()) {
            filteredItems.getChildren().add(new Label("Please select a genre."));
            return;
        }
            for(int i = 0; i < addBooksToFile.size(); i++) {
                String book = addBooksToFile.get(i);
                boolean matches = false;
                if(used.isSelected() && book.contains("Condition: Used")) {
                    matches = true;
                }
                if(likeNew.isSelected() && book.contains("Condition: Like New")) {
                    matches = true;
                }
                if(moderatelyUsed.isSelected() && book.contains("Condition: Moderately Used")) {
                    matches = true;
                }
                if(heavilyUsed.isSelected() && book.contains("Condition: Heavily Used")) {
                    matches = true;
                }
                boolean genreMatch = book.contains("Genre: " + selectedGenre);

                if(matches && genreMatch) {
                    filteredBooks.add(book);
                }
            }
        for(int i = 0; i < filteredBooks.size(); i++) {
            String book = filteredBooks.get(i);
            String title = book.split("Title: ")[1].split(" \\| ")[0];
            filteredItems.getChildren().add(new Label(title));
            filteredItems.getChildren().add(new CheckBox());
        }
    }

    private void getDataFromTextDB() {
        String currLine = "";
        try {
            InputStream is = getClass().getResourceAsStream("/booksAvailable.txt");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader buffer = new BufferedReader(reader);

            while((currLine = buffer.readLine()) != null) {
                addBooksToFile.add(currLine);
            }
            buffer.close();
            reader.close();
        } catch (Exception e) {
            System.out.println("Error reading in the database");
        }

    }

    public ArrayList<String> getBookData() {
        return addBooksToFile;
    }

}

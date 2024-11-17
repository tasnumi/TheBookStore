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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

// This com.example.asu_bookstore.BuyerMainScreen class will eventually host the buyer's screen in the program.
public class BuyerMainScreen extends BorderPane{
    VBox filteredItems = new VBox();

    public BuyerMainScreen(final int WIDTH, final int HEIGHT, ASU_Bookstore control) {
        Rectangle turquoiseBackground = new Rectangle(WIDTH, WIDTH); // apricot background
        turquoiseBackground.setFill(Color.web("#CDE8E5"));
        this.getChildren().add(turquoiseBackground);

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

        // My recommendation - use CheckBoxes to list the genres and condition. This way the user
        // will be able to view books of multiple genres/conditions at once.
        // delete these comments once completed please!
        VBox bookConditionsGenre = new VBox(25); //VBox for book conditions
        Label selectCondition = new Label("Please select the condition of the book for purchase");
        Label selectGenre = new Label("Please select a book genre");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Natural Science", "Computer", "Math", "English", "Language", "Others");
        CheckBox used = new CheckBox("Used");
        CheckBox likeNew = new CheckBox("Like New");
        CheckBox moderatelyUsed = new CheckBox("Moderately Used");
        CheckBox heavilyUsed = new CheckBox("Heavily Used");
        bookConditionsGenre.getChildren().addAll(selectCondition, used, likeNew, moderatelyUsed, heavilyUsed, selectGenre, comboBox);
        bookConditionsGenre.setStyle("-fx-font-size: 12px;");
        leftContainer.getChildren().add(bookConditionsGenre);

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

        // my recommendation - place a VBox into a ScrollPane to display the list of book checkboxes
        // and not the dropdown list of books from the document (then only one book at a time can be
        // selected. See how the VBox of checkboxes refreshes in the AdminScreen for inspiration.
        // delete these comments once completed please!
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
        logAndPurchaseButtons.setPadding(new Insets(0, 0, 5, 115));
        Button logOut = new Button("Log Out");
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
        public static String[] getBooks() {
            String [] booksAvailable = {
                    "Title: The Secret Lives of Plants | Author: Emily Hawthorne | Published Year: 1999 | Condition: Heavily Used | Genre: Natural Science\n",
                    "Title: The Age of Dinosaurs | Author: James Winters | Published Year: 2005 | Condition: Like New | Genre: Natural Science\n",
                    "Title: The Biome Chronicles | Author: Ava Blackwood | Published Year: 2010 | Condition: Used | Genre: Natural Science\n",
                    "Title: The Physics of Natural Disasters | Author: Ethan Prescott | Published Year: 2017 | Condition: Moderately Used | Genre: Natural Science\n",
                    "Title: The Art of Coding | Author: Olivia Sterling | Published Year: 2012 | Condition: Moderately Heavily Used | Genre: Computer\n",
                    "Title: The Secret Life of Databases | Author: Liam Montgomery | Published Year: 2015 | Condition: Like New | Genre: Computer\n",
                    "Title: Mastering C++ | Author: Sophia Devereux | Published Year: 2008 | Condition: Used | Genre: Computer\n",
                    "Title: Understanding Operating Systems | Author: Lucas Carver | Published Year: 2011 | Condition: Moderately Used | Genre: Computer\n",
                    "Title: The Geometry of Nature | Author: Isabella Greyson | Published Year: 2013 | Condition: Heavily Used | Genre: Math\n",
                    "Title: The Physics of Natural Disasters | Author: Mason Wright | Published Year: 2016 | Condition: Like New | Genre: Math\n",
                    "Title: The Art of Proof | Author: Amelia Stone | Published Year: 2007 | Condition: Used | Genre: Math\n",
                    "Title: The Calculus of Variations | Author: Noah Bennett | Published Year: 2019 | Condition: Moderately Used | Genre: Math\n",
                    "Title: Whispers in the Wind | Author: Chloe Rivers | Published Year: 2003 | Condition: Heavily Used | Genre: English\n",
                    "Title: Echoes of the Past | Author: Benjamin Cross | Published Year: 2011 | Condition: Like New | Genre: English\n",
                    "Title: The Last Letter | Author: Grace Montgomery | Published Year: 2007 | Condition: Used | Genre: English\n",
                    "Title: A Symphony of Words | Author: Victoria Lancaster | Published Year: 2014 | Condition: Moderately Used | Genre: English\n",
                    "Title: The Art of Linguistic Expression | Author: Samuel Rothschild | Published Year: 2010 | Condition: Heavily Used | Genre: Language\n",
                    "Title: Words in Motion | Author: Charlotte Mitchell | Published Year: 1989 | Condition: Like New | Genre: Language\n",
                    "Title: Breaking Down Grammar | Author: Oliver Donovan | Published Year: 2017 | Condition: Used | Genre: Language\n",
                    "Title: A World of Words | Author: Harrison Langley | Published Year: 2006 | Condition: Moderately Used | Genre: Language\n",
                    "Title: Into the Unknown | Author: Samuel Rothschild | Published Year: 2010 | Condition: Heavily Used | Genre: Other\n",
                    "Title: Wandering Souls | Author: Clara Winthrop | Published Year: 1989 | Condition: Like New | Genre: Other\n",
                    "Title: The Eternal Puzzle | Author: Marcus Fields | Published Year: 2017 | Condition: Used | Genre: Other\n",
                    "Title: Whispers from the Void | Author: Evelyn Waters | Published Year: 2006 | Condition: Moderately Used | Genre: Other\n"


            };
            return booksAvailable;
        }

    public void handleSelection(CheckBox used, CheckBox likeNew, CheckBox moderatelyUsed, CheckBox heavilyUsed, ComboBox<String> genres, VBox filteredItems) {
        filteredItems.getChildren().clear();
        ArrayList<String> filteredBooks = new ArrayList<>();
        String[] books = getBooks();
        String selectedGenre = (String) genres.getValue();
        System.out.println(Arrays.toString(books));

        if(selectedGenre == null || selectedGenre.isEmpty()) {
            filteredItems.getChildren().add(new Label("Please select a genre."));
            return;
        }
            for(int i = 0; i < books.length; i++) {
                String book = books[i];
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

    public static void writeToFile(String[] books)  {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("booksAvailable.txt"))) {
            for(int i = 0; i < books.length; i++) {
                writer.write(books[i]);
                writer.newLine();
            }
            System.out.println("Books written to file successfully");
        }
        catch (IOException e){
            System.out.println("Error writing to file.");
        }
    }

}

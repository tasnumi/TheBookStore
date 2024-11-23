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

// This com.example.asu_bookstore.BuyerMainScreen class hosts the buyer's screen in the program. This class is
//also used by the BuyerConfirmationScreen object to gather information on what books are being bought by the user.
public class BuyerMainScreen extends BorderPane{
    private ArrayList<String> addBooksToFile = new ArrayList<String>(); //ArrayList of books in database
    VBox filteredItems = new VBox(15); //adds the books available to the right side of the screen
    ArrayList<String> selectedBooks;
    ArrayList<String> filteredBooks;

    public BuyerMainScreen(final int WIDTH, final int HEIGHT, ASU_Bookstore control) {
        Rectangle turquoiseBackground = new Rectangle(WIDTH, WIDTH); // apricot background
        turquoiseBackground.setFill(Color.web("#CDE8E5"));
        this.getChildren().add(turquoiseBackground);

        getDataFromTextDB(); //calls the method to read through the entire database and save it to an ArrayList
        selectedBooks = new ArrayList<String>();

        // This HBox contains everything that will be placed inside the main buyer screen
        HBox entireContainer = new HBox();
        entireContainer.setSpacing(15);
        entireContainer.setPadding(new Insets(15, 15, 0, 15));
        entireContainer.setAlignment(Pos.CENTER);

        // This VBox contains everything on the left side of the screen (condition + genre)
        VBox leftContainer = new VBox();
        leftContainer.setStyle("-fx-background-color: #83b7b8; -fx-border-color: #1212f8; -fx-border-width: 0.5; -fx-padding: 20");
        leftContainer.setMinWidth((WIDTH/2.5) - 30);
        leftContainer.setMaxHeight(HEIGHT - 60);
        leftContainer.setAlignment(Pos.CENTER);
        leftContainer.setPadding(new Insets(0, 15, 0, 0));

        //contains error check labels, the categories, and conditions
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

        //adds all items to the left container VBox to that stores the conditions and genres
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

        //ScrollPane which contains the available books for users to scroll through and be displayed
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

        //sets the logout button to return to the main home screen and also empties all previous selected items in the buyer screen
        logOut.setOnAction(e -> {
            used.setSelected(false);
            likeNew.setSelected(false);
            moderatelyUsed.setSelected(false);
            heavilyUsed.setSelected(false);
            comboBox.getSelectionModel().clearSelection();

            control.switchScreen("");
        });

        //creates button for purchase and sets the action for the purchase to go through the list of available books from the database
        //and decrements the book that was bought. The purchase button also gets switched to the buyer confirmation screen.
        Button purchase = new Button("Purchase");
        purchase.setOnAction(e -> {
            boolean canPurchase = false;

            for (int i = 0; i < filteredItems.getChildren().size(); i++) {
                if(filteredItems.getChildren().get(i) instanceof CheckBox) {
                    if (((CheckBox) filteredItems.getChildren().get(i)).isSelected()) {
                        selectedBooks.add(((Label) filteredItems.getChildren().get(i - 1)).getText());
                    }
                }
            }

            control.getBuyerMainConfirmationScreen().constructPurchaseInfo(this);

            if (!selectedBooks.isEmpty()) {
                used.setSelected(false);
                likeNew.setSelected(false);
                moderatelyUsed.setSelected(false);
                heavilyUsed.setSelected(false);
                comboBox.getSelectionModel().clearSelection();

                control.switchScreen("buyerConfirm");
            }
        });

        //displays the logout and purchase buttons at the bottom of the screen.
        logAndPurchaseButtons.getChildren().addAll(logOut, purchase);

        // This adds the main components to the screen
        entireContainer.getChildren().addAll(leftContainer, displaySun, rightContainer);
        this.setCenter(entireContainer);
        this.setBottom(logAndPurchaseButtons);

    }

    //This method handles all the user functionality and back-end portion of this class. Includes error checks if certain fields aren't selected and
    //checks what genre or condition the user selected. Once all the data is collected from the user, the program will parse through the database
    //to split the text to grab tbe title the user selects and creates a new label and CheckBox for it.
    public void handleSelection(CheckBox used, CheckBox likeNew, CheckBox moderatelyUsed, CheckBox heavilyUsed, ComboBox<String> genres, VBox filteredItems) {
        filteredItems.getChildren().clear();
        selectedBooks.clear();
        filteredBooks = new ArrayList<String>();
        String selectedGenre = (String) genres.getValue();

        if(!used.isSelected() && !likeNew.isSelected()  && !moderatelyUsed.isSelected() && !heavilyUsed.isSelected()) {
            filteredItems.getChildren().add(new Label("Please select the condition of the book."));
        }

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

    //This method is private to ensure security and reads through the database of books available and then adds every item to an
    //ArrayList of Strings to allow for dynamic changes.
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

    //A getter method to call the books available from the database from other classes.
    public ArrayList<String> getBookData() {
        return addBooksToFile;
    }

}
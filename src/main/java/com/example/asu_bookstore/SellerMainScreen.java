package com.example.asu_bookstore;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ComboBox;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

// This com.example.asu_bookstore.SellerMainScreen class hosts the seller's screen in the program. This class is
// also used by the SellerConfirmationScreen object to gather information on what book is being sold by the user.
public class SellerMainScreen extends BorderPane {
    // global variables primarily used to easily give the SellerConfirmationScreen the information it requires
    ScrollPane r = new ScrollPane();
    TextField title;
    TextField text;
    ComboBox<String> combobox;

    // This is the constructor for the SellerMainScreen class that defines the nodes and backend behaviors used
    // for the selling function of the program.
    public SellerMainScreen(final int WIDTH, final int HEIGHT, ASU_Bookstore control) {
        ASU_Bookstore bookstore = new ASU_Bookstore();
        BuyerMainScreen buyer = new BuyerMainScreen(WIDTH, HEIGHT, bookstore);
        ArrayList<String> bookDatabase = buyer.getBookData();
        VBox vbox = new VBox();

        // importing the books from the book database textfile into the program.
        for(int i=0; i<bookDatabase.size(); i++) {
            Label book = new Label(bookDatabase.get(i));
            vbox.getChildren().add(book);
        }
        r.setContent(vbox); // display all the books from the database to the scene

        // setting the visual background of the scene
        Rectangle lightPurpBackground = new Rectangle(WIDTH, WIDTH); // purple background
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

        // labels are used to denote sections of the screen and inform the user what to do.
        Label label1 = new Label("Please select the condition of the book for sale:");
        label1.setStyle(" -fx-font: 9 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");

        Label label2 = new Label("Please select a book genre");
        label2.setStyle(" -fx-font: 9 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");

        Label label3 = new Label("Please Enter the Original Book Price:");
        label3.setStyle(" -fx-font: 9 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");

        Label label4 = new Label("Please enter the book title");
        label4.setStyle(" -fx-font: 9 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");

        // This user will put thier book title inside this TextField
        title = new TextField();
        title.setPrefWidth(15);
        title.setPrefHeight(15);

        // This toggle group will allow one RadioButtion below to be selected at a time to
        // ensure that the book can only be assigned one condition.
        ToggleGroup t = new ToggleGroup();

        // These Radiobuttons correspond to the quality of the book being sold. It should match with the quality
        // of the books being presented to the user (this list is taken from the booksAvailable.txt file)
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

        // this ComboBox holds all the selectable genres of books that the Bookstore sells. The user will select one
        // of them to sell.
        combobox = new ComboBox<>();
        combobox.getItems().addAll("Natural Science", "Computer", "Math", "English", "Language", "Others");

        // This textfield will have the user put in the matching price of the book
        text = new TextField();
        text.setPrefWidth(15);
        text.setPrefHeight(15);

        // This VBox holds and displays all the books in the database on the right side of the screen
        VBox printData = new VBox(10);

        // This button displays the price the user is trying to sell based on the condition and genre of the book
        Button listBook = new Button("List Book");
        VBox rightContainer = new VBox(10);
        rightContainer.setStyle("-fx-background-color: #be96c4; -fx-border-color: #8273da; -fx-border-width: 0.5; -fx-padding: 20");
        rightContainer.setMinWidth((WIDTH / 2.5) - 30);
        rightContainer.setMaxHeight(HEIGHT - 60);
        rightContainer.setPadding(new Insets(0, 0, 0, 15));

        // calculation for the price of the books to be sold depending on which book condiiton is selected.
        checkbox1.setOnAction(e -> HandleOptions(checkbox1, checkbox2, checkbox3, checkbox4, text, combobox, printData, title, bookDatabase));
        checkbox2.setOnAction(e -> HandleOptions(checkbox1, checkbox2, checkbox3, checkbox4, text, combobox, printData,title, bookDatabase));
        checkbox3.setOnAction(e -> HandleOptions(checkbox1, checkbox2, checkbox3, checkbox4, text, combobox, printData,title, bookDatabase));
        checkbox4.setOnAction(e -> HandleOptions(checkbox1, checkbox2, checkbox3, checkbox4, text, combobox, printData,title, bookDatabase));
        combobox.setOnAction(e -> HandleOptions(checkbox1, checkbox2, checkbox3, checkbox4, text, combobox, printData,title, bookDatabase));

        // set the behavior of the combobox
        listBook.setOnAction(e -> {
            HandleOptions(checkbox1, checkbox2, checkbox3, checkbox4, text, combobox, printData,title, bookDatabase);
        });

        // add the right side of the screen into the scene
        rightContainer.getChildren().addAll(listBook, printData,r);

        // The buyer sun image is loaded from the program's resources folder and is placed into the scene
        InputStream sunLogoStream = getClass().getResourceAsStream("/sellerSun.png");
        Image sun = new Image(sunLogoStream);
        ImageView displaySun = new ImageView();
        displaySun.setImage(sun);
        displaySun.setFitWidth(125);
        displaySun.setPreserveRatio(true); // sun image is expanded but aspect ratio should be preserved
        displaySun.setX((WIDTH / 2.5) + 7);
        displaySun.setY(HEIGHT / 5);

        // These buttons allow the user to move though the program: Back to the login screen and onto the
        // Seller finalization screen if the user sets up a proper book listing for sale.
        HBox logAndSellButtons = new HBox();
        logAndSellButtons.setSpacing(WIDTH / 1.8);
        logAndSellButtons.setAlignment(Pos.CENTER);
        logAndSellButtons.setPadding(new Insets(0, 0, 5, 0));
        Button logOut = new Button("Log Out");
        Button purchase = new Button("Sell");

        // this defines the logout button's behavior.
        logOut.setOnAction(e -> {
            // "refreshing" the screen for the next user to have a blank slate
            t.selectToggle(null);
            combobox.getSelectionModel().clearSelection();
            text.clear();
            title.clear();

            // switch the screen
            control.switchScreen("");
        });

        // this defines the purchase button's behavior
        purchase.setOnAction(e -> {
            // if any of the required data feilds that the user must enter are not given, then the program will NOT enter
            // the final seller confirmation screen.
            if (!(text.getText().trim().isEmpty() || title.getText().trim().isEmpty() || combobox.getSelectionModel() == null || t.getSelectedToggle() == null)) {
                // send the information to the SellerConfirmationScreen and write to seller history textfile
                control.getSellerConfimScreen().constructSaleInfo(this);

                // "refreshing" the screen for the next user to have a blank slate
                t.selectToggle(null);
                combobox.getSelectionModel().clearSelection();
                text.clear();
                title.clear();

                control.switchScreen("sellerConfirm"); // switch the screen to the seller finalization/confrim screen
            }

        });

        // all the nodes are added to the screen
        leftContainer.getChildren().addAll(label1, checkbox1, checkbox2, checkbox3, checkbox4, label2, combobox, label3, text,label4, title);

        // The placement of this screen's buttons are defined here
        logAndSellButtons.getChildren().addAll(logOut, purchase);

        // This adds the main components to the screen
        entireContainer.getChildren().addAll(leftContainer, displaySun, rightContainer);
        this.setCenter(entireContainer);
        this.setBottom(logAndSellButtons);
    }

    // This HandleOptions method processes all the information given by the user in the input TextFeilds, RadioButtons,
    // and ComboBoxes. This method ensures that all parameters are given and calculates a system-generated price for the
    // user's book. This price is shown when the user clicks the "List Book" button. The price generated here also is sent
    // to the SellerConfirmationScreen object that is displayed when the user finalizes their sale.
    public void HandleOptions(RadioButton checkbox1, RadioButton checkbox2, RadioButton checkbox3, RadioButton checkbox4, TextField text, ComboBox<String> combobox, VBox printData, TextField title,  ArrayList<String> bookDatabase) {
        printData.getChildren().clear();
        ArrayList<String> f = new ArrayList<>();

        // checks if the user provided a genre to the system.
        String selectedGenre = (String) combobox.getValue();
        if (selectedGenre == null || selectedGenre.isEmpty()) {
            Label error = new Label("Please enter a genre");
            error.setStyle(" -fx-font: 15 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");

            printData.getChildren().add(error);
            return;
        }
        // checks if the user provided a price to the system.
        if (text.getText() == null || text.getText().isEmpty()) {
            Label error = new Label("Please input a price");
            error.setStyle(" -fx-font: 15 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");

            printData.getChildren().add(error);
            return;
        }

        // Check to make sure that the user matches the book condition that the system has in its database.
        for (int i = 0; i < bookDatabase.size(); i++) {
            String book = bookDatabase.get(i);
            boolean matches = false;
            if (checkbox1.isSelected() && book.contains("Condition: Used")) {
                matches = true;
            }
            if (checkbox2.isSelected() && book.contains("Condition: Like New")) {
                matches = true;
            }
            if (checkbox3.isSelected() && book.contains("Condition: Moderately Used")) {
                matches = true;
            }
            if (checkbox4.isSelected() && book.contains("Condition: Heavily Used")) {
                matches = true;
            }
            boolean genreCheck = book.contains("Genre: " + selectedGenre);

            if (matches && genreCheck) { // if there is a match, then add it to arrayList
                f.add(book);
            }
        }

        // collect the title of each book in the database for comparisons with the user's given title (in the next code block)
        String t = null;
        for (int i = 0; i < f.size(); i++) {
            String book = f.get(i);
            t = book.split("Title: ")[1].split(" \\| ")[0];
        }

        // compares the database title to the user's title
        if (!Objects.equals(t, title.getText())) {
        Label label = new Label("This Book is not in the Database");
            label.setStyle(" -fx-font: 15 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");
            printData.getChildren().add(label);
        return;
        }

        try { // if the input value is not an integer, the program an exception and the user can try again
            int inputVal = Integer.parseInt(text.getText());
            // each book condition has a different rate. The better the condition, the more expensive the price will be.
            // this statement checks for and calculates the price for "Used" books
            if (checkbox1.isSelected() && (selectedGenre.equals("Natural Science") || selectedGenre.equals("Math") || selectedGenre.equals("Computer") || selectedGenre.equals("English") || selectedGenre.equals("Language") || selectedGenre.equals("Others")) && inputVal > 0) {
                double price = inputVal * 0.70;
                String p = String.format("This is your price $%.2f", price);
                Label label = new Label(p);
                label.setStyle(" -fx-font: 15 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");
                printData.getChildren().add(label);
            }
            // this statement checks for and calculates the price for "Like New" books
            else if (checkbox2.isSelected() && (selectedGenre.equals("Natural Science") || selectedGenre.equals("Math") || selectedGenre.equals("Computer") || selectedGenre.equals("English") || selectedGenre.equals("Language") || selectedGenre.equals("Others")) && inputVal > 0) {
                double price = inputVal * 0.90;
                String p = String.format("This is your price $%.2f", price);
                Label label = new Label(p);
                label.setStyle(" -fx-font: 15 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");
                printData.getChildren().add(label);
            }
            // this statement checks for and calculates the price for "Moderately Used" books
            else if (checkbox3.isSelected() && (selectedGenre.equals("Natural Science") || selectedGenre.equals("Math") || selectedGenre.equals("Computer") || selectedGenre.equals("English") || selectedGenre.equals("Language") || selectedGenre.equals("Others")) & inputVal > 0) {
                double price = inputVal * 0.85;
                String p = String.format("This is your price $%.2f", price);
                Label label = new Label(p);
                label.setStyle(" -fx-font: 15 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");
                printData.getChildren().add(label);
            }
            // this statement checks for and calculates the price for "Heavily Used" books
            else if (checkbox4.isSelected() && (selectedGenre.equals("Natural Science") || selectedGenre.equals("Math") || selectedGenre.equals("Computer") || selectedGenre.equals("English") || selectedGenre.equals("Language") || selectedGenre.equals("Others")) & inputVal > 0) {
                double price = inputVal * 0.50;
                String p = String.format("This is your price $%.2f", price);
                Label label = new Label(p);
                label.setStyle(" -fx-font: 15 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");
                printData.getChildren().add(label);
            }
        }
        catch (Exception e) { // if an error occurs with the integer price parsing, throw an exception to the user.
            throw new RuntimeException(e);
        }
    }
}
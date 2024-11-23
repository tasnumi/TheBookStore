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
import javafx.scene.control.ComboBox;

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
        //System.out.println(bookDatabase);
        VBox vbox = new VBox();

        for(int i=0; i<bookDatabase.size(); i++) {
            Label book = new Label(bookDatabase.get(i));
            vbox.getChildren().add(book);
           // System.out.println();
        }
        r.setContent(vbox);
       // System.out.println(bookDatabase);
      //  r.setContent(bookDatabase);


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
        //leftContainer.setAlignment(Pos.CENTER);
        leftContainer.setPadding(new Insets(0, 15, 0, 0));

        // My recommendation - use radiobuttons to list the genres and condition. This way the user
        // will be able to view books of multiple genres/conditions at once.
        // delete these comments once completed please!
        Label label1 = new Label("Please select the condition of the book for sale:");
        label1.setStyle(" -fx-font: 9 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");


        ToggleGroup t = new ToggleGroup();

        RadioButton checkbox1 = new RadioButton("Used");
        // checkbox1.setPadding(new Insets(15,0,0,0));
        checkbox1.setStyle(" -fx-text-fill: #FFFFFF");
        checkbox1.setToggleGroup(t);


        RadioButton checkbox2 = new RadioButton("Like New");
        // checkbox2.setPadding(new Insets(8,0,0,0));
        checkbox2.setStyle(" -fx-text-fill: #FFFFFF");
        checkbox2.setToggleGroup(t);

        RadioButton checkbox3 = new RadioButton("Moderately Used");
        //  checkbox3.setPadding(new Insets(8,0,0,0));
        checkbox3.setStyle(" -fx-text-fill: #FFFFFF");
        checkbox3.setToggleGroup(t);

        RadioButton checkbox4 = new RadioButton("Heavily Used");
        //  checkbox4.setPadding(new Insets(8,0,0,0));
        checkbox4.setStyle(" -fx-text-fill: #FFFFFF");
        checkbox4.setToggleGroup(t);



        Label label2 = new Label("Please select a book genre");
        //   label2.setPadding(new Insets(20,0,15,0));
        label2.setStyle(" -fx-font: 9 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");

        ComboBox<String> combobox = new ComboBox<>();
        combobox.getItems().addAll("Natural Science", "Computer", "Math", "English", "Language", "Others");

        Label label3 = new Label("Please Enter the Original Book Price:");
        label3.setStyle(" -fx-font: 9 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");
        //  label3.setPadding(new Insets(11,0,11,0));

        TextField text = new TextField();
        text.setPrefWidth(15);
        text.setPrefHeight(15);


        Label label4 = new Label("Please enter the book title");
        label4.setStyle(" -fx-font: 9 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");
        TextField title = new TextField();
        title.setPrefWidth(15);
        title.setPrefHeight(15);



        //calculation for the price of the books to be sold
        //double price=0.00;
        VBox printData = new VBox(10);



        leftContainer.getChildren().addAll(label1, checkbox1, checkbox2, checkbox3, checkbox4, label2, combobox, label3, text,label4, title);

        Button listBook = new Button("List Book");
        // This VBox contains everything on the right (list of selectable books selected by user)
        VBox rightContainer = new VBox(10);
        rightContainer.setStyle("-fx-background-color: #be96c4; -fx-border-color: #8273da; -fx-border-width: 0.5; -fx-padding: 20");
        rightContainer.setMinWidth((WIDTH / 2.5) - 30);
        rightContainer.setMaxHeight(HEIGHT - 60);
        // rightContainer.setAlignment(Pos.CENTER);
        rightContainer.setPadding(new Insets(0, 0, 0, 15));

        //HandleOptions(checkbox1, checkbox2, checkbox3, checkbox4, text, combobox, printData);
        checkbox1.setOnAction(e -> HandleOptions(checkbox1, checkbox2, checkbox3, checkbox4, text, combobox, printData, title, bookDatabase));
        checkbox2.setOnAction(e -> HandleOptions(checkbox1, checkbox2, checkbox3, checkbox4, text, combobox, printData,title, bookDatabase));
        checkbox3.setOnAction(e -> HandleOptions(checkbox1, checkbox2, checkbox3, checkbox4, text, combobox, printData,title, bookDatabase));
        checkbox4.setOnAction(e -> HandleOptions(checkbox1, checkbox2, checkbox3, checkbox4, text, combobox, printData,title, bookDatabase));
        combobox.setOnAction(e -> HandleOptions(checkbox1, checkbox2, checkbox3, checkbox4, text, combobox, printData,title, bookDatabase));
        //text.setOnMouseClicked();
       // text.textProperty().addListener((observable, oldValue, newValue) -> HandleOptions(checkbox1, checkbox2, checkbox3, checkbox4, text, combobox, printData));

        listBook.setOnAction(e -> {
            HandleOptions(checkbox1, checkbox2, checkbox3, checkbox4, text, combobox, printData,title, bookDatabase);

        });


        rightContainer.getChildren().addAll(listBook, printData,r);

        // my recommendation - place a VBox into a ScrollPane to display the list of book checkboxes
        // and not the dropdown list of books from the document (then only one book at a time can be
        // selected. See how the VBox of checkboxes refreshes in the AdminScreen for inspiration.
        // delete these two comments once completed please!


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

        logOut.setOnAction(e -> {
            // "refreshing" the screen for the next user to have a blank slate
            t.selectToggle(null);
            combobox.getSelectionModel().clearSelection();
            text.clear();
            title.clear();

            // switch the screen
            control.switchScreen("");
        });

        // The behavior for these buttons can be defined here, which might be easier that creating
        // a separate ButtonHandler<ActionEvent>() class so that the button behavior code can use the
        // constructor parameters that are accessible here. See the LoginScreen buttons for an example.
        logAndSellButtons.getChildren().addAll(logOut, purchase);

        // This adds the main components to the screen
        entireContainer.getChildren().addAll(leftContainer, displaySun, rightContainer);
        this.setCenter(entireContainer);
        this.setBottom(logAndSellButtons);


    }
    public void HandleOptions(RadioButton checkbox1, RadioButton checkbox2, RadioButton checkbox3, RadioButton checkbox4, TextField text, ComboBox<String> combobox, VBox printData, TextField title,  ArrayList<String> bookDatabase) {
        printData.getChildren().clear();
        ArrayList<String> f = new ArrayList<>();
       


        String selectedGenre = (String) combobox.getValue();

        // if (checkbox1.isSelected() || checkbox2.isSelected() || checkbox3.isSelected() || checkbox4.isSelected() && !selectedGenre.isEmpty() && !text.getText().isEmpty()) {
        if (selectedGenre == null || selectedGenre.isEmpty()) {
            Label error = new Label("Please enter a genre");
            error.setStyle(" -fx-font: 15 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");

            printData.getChildren().add(error);
            return;
        }
        if (text.getText() == null || text.getText().isEmpty()) {
            Label error = new Label("Please input a price");
            error.setStyle(" -fx-font: 15 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");

            printData.getChildren().add(error);
            return;
        }

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

            if (matches && genreCheck) {
                f.add(book);
            }
        }

        String t = null;
        for (int i = 0; i < f.size(); i++) {
            String book = f.get(i);
            t = book.split("Title: ")[1].split(" \\| ")[0];

        }
        if (!Objects.equals(t, title.getText())) {
        Label label = new Label("This Book is not in the Database");
            label.setStyle(" -fx-font: 15 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");
            printData.getChildren().add(label);
        return;
        }





        try {

            int inputVal = Integer.parseInt(text.getText());
            if (checkbox1.isSelected() && (selectedGenre.equals("Natural Science") || selectedGenre.equals("Math") || selectedGenre.equals("Computer") || selectedGenre.equals("English") || selectedGenre.equals("Language") || selectedGenre.equals("Others")) && inputVal > 0) {
                double price = inputVal * 0.70;
                String p = String.format("This is your price $%.2f", price);
                Label label = new Label(p);
                label.setStyle(" -fx-font: 15 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");
                printData.getChildren().add(label);
            } else if (checkbox2.isSelected() && (selectedGenre.equals("Natural Science") || selectedGenre.equals("Math") || selectedGenre.equals("Computer") || selectedGenre.equals("English") || selectedGenre.equals("Language") || selectedGenre.equals("Others")) && inputVal > 0) {
                double price = inputVal * 0.90;
                String p = String.format("This is your price $%.2f", price);
                Label label = new Label(p);
                label.setStyle(" -fx-font: 15 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");
                printData.getChildren().add(label);
            } else if (checkbox3.isSelected() && (selectedGenre.equals("Natural Science") || selectedGenre.equals("Math") || selectedGenre.equals("Computer") || selectedGenre.equals("English") || selectedGenre.equals("Language") || selectedGenre.equals("Others")) & inputVal > 0) {
                double price = inputVal * 0.85;
                String p = String.format("This is your price $%.2f", price);
                Label label = new Label(p);
                label.setStyle(" -fx-font: 15 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");
                printData.getChildren().add(label);
            } else if (checkbox4.isSelected() && (selectedGenre.equals("Natural Science") || selectedGenre.equals("Math") || selectedGenre.equals("Computer") || selectedGenre.equals("English") || selectedGenre.equals("Language") || selectedGenre.equals("Others")) & inputVal > 0) {
                double price = inputVal * 0.50;
                String p = String.format("This is your price $%.2f", price);
                Label label = new Label(p);
                label.setStyle(" -fx-font: 15 Arial;-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-underline: true");
                printData.getChildren().add(label);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



}



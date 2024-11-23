package com.example.asu_bookstore;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.*;

// This com.example.asu_bookstore.SellerConfirmationScreen class hosts the seller's transactions in the program. This class is
// also used by the SellerMainScreen for event handling whenever a user wants to sell a book.
public class SellerConfirmationScreen extends BorderPane {
    private String bookSoldTitle; //represents the title of the book that is sold along with the price
    private Label soldBook; //represents the book that is sold
    private double totalPrice = 0.00; //contains the price that changes dynamically
    private Label totalPriceLabel; //contains the label for the total price that changes dynamically

    public SellerConfirmationScreen(final int WIDTH, final int HEIGHT, ASU_Bookstore control) {
        Rectangle lightRedBackground = new Rectangle(WIDTH, WIDTH); //light red background
        lightRedBackground.setFill(Color.web("#e9cdd6"));
        this.getChildren().add(lightRedBackground);

        Rectangle redBackground = new Rectangle(200, 0, (WIDTH - 400), HEIGHT);
        redBackground.setFill(Color.web("#c596a4"));
        this.getChildren().add(redBackground);

        // The sun image is loaded from the program's resources folder and is placed into the scene
        InputStream sunLogoStream = getClass().getResourceAsStream("/SellingConfirmSun.png");
        Image sun = new Image(sunLogoStream);
        ImageView displaySun = new ImageView();
        displaySun.setImage(sun);
        displaySun.setFitWidth(125);
        displaySun.setPreserveRatio(true); // sun image is expanded but aspect ratio should be preserved
        displaySun.setX((WIDTH/2.5) + 7);
        displaySun.setY(HEIGHT/13);

        //labels for front-end portion of the code.
        Label thankYou = new Label("Thank you for selling your book to the ASU Bookstore!");
        Label sellTotal = new Label("Here are your sale details:");

        soldBook = new Label();

        totalPriceLabel = new Label();

        //sets the button backToHome to go back to the login screen by switching screens.
        Button backToHome = new Button("Back to Login");
        backToHome.setOnAction(e -> {

            control.switchScreen("");
        });


        //this VBox sets all the labels, buttons, image, and pricing.
        VBox allNodes = new VBox(20);

        //styling for labels for font size and color.
        thankYou.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-underline: true;");
        sellTotal.setStyle("-fx-text-fill: white;");
        allNodes.setStyle("-fx-font-size: 15px;");
        soldBook.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-underline: true;");
        backToHome.setStyle("-fx-background-color:#e9cdd6;");
        allNodes.setStyle("-fx-font-size: 15px;");
        allNodes.getChildren().addAll(thankYou, sellTotal, soldBook, displaySun, backToHome, totalPriceLabel);

        allNodes.setAlignment(Pos.CENTER);

        //centers every item in the VBox towards the center.
        this.setCenter(allNodes);

    }

    //This method contains the functionality for grabbing the title, price, and genre of the book that the user is selling. The labels
    //display the title, price, and genre on the main screen. The method also calls the text writing method to display the dashes and book information
    //in the database.
    public void constructSaleInfo(SellerMainScreen seller) {
        String title = seller.title.getText();
        String price = seller.text.getText();
        String genre = seller.combobox.getValue();

        bookSoldTitle = title + " - $" + price;
        soldBook.setText(bookSoldTitle);
        writeHistory(title, price, genre);
        writeBottomDashes();
    }

    //This method overwrites the database textfile sellingHistory which represents the database of sold books transactions. Everytime a user sells a book
    //it's formatted the same way as the book available database but only contains the title, price, and category instead.

    private void writeHistory(String title, String price, String category) {
        try {
            FileOutputStream outS = new FileOutputStream("src/main/resources/sellingHistory.txt", true); // true parameter makes it write at the file's end
            OutputStreamWriter writer = new OutputStreamWriter(outS);
            BufferedWriter buffW = new BufferedWriter(writer);

            buffW.write("Title: " + title + " | Price: " + price + " | Category: " + category);  // written in a way for future easy token extraction
            buffW.newLine();
            buffW.close(); // done with the writer for now.
            writer.close();
        }
        catch (IOException e) { // most likely file does not exist or there was a problem in the array content writing
            System.out.println("CRITICAL ERROR IN CREATING THE FILE WRITER!!!");
        }
    }

    //This method is to make the database for seller confirmation screen easily readable for developers by adding dashed lines to split everytime
    //another transaction is made.
    private void writeBottomDashes() {
        try {
            FileOutputStream outS = new FileOutputStream("src/main/resources/sellingHistory.txt", true); // true parameter makes it write at the file's end
            OutputStreamWriter writer = new OutputStreamWriter(outS);
            BufferedWriter buffW = new BufferedWriter(writer);

            buffW.write("----------------------------------------------------------------------------------");
            buffW.newLine();
            buffW.close(); // done with the writer for now.
            writer.close();
        }
        catch (IOException e) { // most likely file does not exist or there was a problem in the array content writing
            System.out.println("CRITICAL ERROR IN CREATING THE FILE WRITER!!!");
        }
    }

}
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
import java.util.ArrayList;

public class BuyerConfirmationScreen extends BorderPane {
    private String booksPurchased;
    private Label allBooks;
    private double totalPrice = 0.00;
    private Label totalPriceLabel;
    private boolean written = false;

    //setup for the screen itself, includes measurements and color
    public BuyerConfirmationScreen(final int WIDTH, final int HEIGHT, BuyerMainScreen buyer, ASU_Bookstore control) {
        Rectangle lightGreenBackground = new Rectangle(WIDTH, WIDTH);
        lightGreenBackground.setFill(Color.web("#d2e7da"));
        this.getChildren().add(lightGreenBackground);

        Rectangle greenBackground = new Rectangle(100, 0, (WIDTH - 200), HEIGHT);
        greenBackground.setFill(Color.web("#a0c3b1"));
        this.getChildren().add(greenBackground);

        // The sun image is loaded from the program's resources folder and is placed into the scene
        InputStream sunLogoStream = getClass().getResourceAsStream("/BuyingConfirmSun.png");
        Image sun = new Image(sunLogoStream);
        ImageView displaySun = new ImageView();
        displaySun.setImage(sun);
        displaySun.setFitWidth(125);
        displaySun.setPreserveRatio(true); // sun image is expanded but aspect ratio should be preserved
        displaySun.setX((WIDTH/2.5) + 7);
        displaySun.setY(HEIGHT/13);

        //labels for the thank you message that appears on screen
        Label thankYou = new Label("Thank you for buying your new book from the ASU Bookstore!");
        thankYou.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-underline: true;");
        Label purchaseTotal = new Label("Here are your purchase details:");
        purchaseTotal.setStyle("-fx-text-fill: white;");

        allBooks = new Label();
        allBooks.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-underline: true;");
        allBooks.setText(booksPurchased);

        totalPriceLabel = new Label();
        totalPriceLabel.setStyle("-fx-text-fill: white;");

        //this button sends the user back to the login
        Button backToHome = new Button("Back to Login");
        backToHome.setStyle("-fx-background-color: #d2e7da;");
        backToHome.setOnAction(e -> {
            written = false;
            booksPurchased = "";
            totalPrice = 0.0;
            control.switchScreen("");
        });
// vbox to display messages, logo, buttons
        VBox allNodes = new VBox(20);
        allNodes.setStyle("-fx-font-size: 15px;");
        allNodes.getChildren().addAll(thankYou, purchaseTotal, allBooks, totalPriceLabel, displaySun, backToHome);
        allNodes.setAlignment(Pos.CENTER);

        this.setCenter(allNodes);
    }
// filtration for the books in order to get the titles specifically
    public void constructPurchaseInfo(BuyerMainScreen buyer) {
        Label booksBought = new Label();
        double runningTotal = 0.00;
        String boughtList = "";
        //written = false;

        ArrayList<String> allSelectedBooks = new ArrayList<String>();
        writeBUYER();
        for (int i = 0; i < buyer.selectedBooks.size(); i++) {
            //written = false;
            String price = buyer.filteredBooks.get(i).split("Price: ")[1].split(" \\| ")[0];
            String category = buyer.filteredBooks.get(i).split("Genre: ")[1].split(" \\| ")[0];
            runningTotal += Double.parseDouble(price);
            boughtList += buyer.selectedBooks.get(i) + " - $" + price + "\n";

            if (!written) {
                writeHistory(buyer.selectedBooks.get(i), price, category);
            }
        }
        writeBottomDashes();
        totalPrice = runningTotal;
        booksPurchased = boughtList;
        allBooks.setText(booksPurchased);
        String price = "Total Price: $" + (int)totalPrice + ".00";
        totalPriceLabel.setText(price);
        written = true;
    }
// writes the title, price, and category
    private void writeHistory(String title, String price, String category) {
        try {
            FileOutputStream outS = new FileOutputStream("src/main/resources/buyingHistory.txt", true); // true parameter makes it write at the file's end
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
        // writes to the buyers to the text file 
    private void writeBUYER() {
        try {
            FileOutputStream outS = new FileOutputStream("src/main/resources/buyingHistory.txt", true); // true parameter makes it write at the file's end
            OutputStreamWriter writer = new OutputStreamWriter(outS);
            BufferedWriter buffW = new BufferedWriter(writer);

            buffW.write("BUYER");
            buffW.newLine();
            buffW.close(); // done with the writer for now.
            writer.close();
        }
        catch (IOException e) { // most likely file does not exist or there was a problem in the array content writing
            System.out.println("CRITICAL ERROR IN CREATING THE FILE WRITER!!!");
        }
    }
    // writes the bottom dashes to the file
    private void writeBottomDashes() {
        try {
            FileOutputStream outS = new FileOutputStream("src/main/resources/buyingHistory.txt", true); // true parameter makes it write at the file's end
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


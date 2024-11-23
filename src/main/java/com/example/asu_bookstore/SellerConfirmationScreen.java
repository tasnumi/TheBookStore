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

public class SellerConfirmationScreen extends BorderPane {
    private String bookSoldTitle;
    private Label soldBook;
    private double totalPrice = 0.00;
    private Label totalPriceLabel;

    public SellerConfirmationScreen(final int WIDTH, final int HEIGHT, ASU_Bookstore control) {
        Rectangle lightRedBackground = new Rectangle(WIDTH, WIDTH);
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
        //this.getChildren().add(displaySun);

        Label thankYou = new Label("Thank you for selling your book to the ASU Bookstore!");
        Label sellTotal = new Label("Here are your sale details:");

        soldBook = new Label();

        totalPriceLabel = new Label();

        Button backToHome = new Button("Back to Login");
        backToHome.setOnAction(e -> {

            control.switchScreen("");
        });

        VBox allNodes = new VBox();
        allNodes.setStyle("-fx-font-size: 15px;");
        allNodes.getChildren().addAll(thankYou, sellTotal, soldBook, displaySun, backToHome, totalPriceLabel);
        allNodes.setAlignment(Pos.CENTER);

        this.setCenter(allNodes);

    }

    public void constructSaleInfo(SellerMainScreen seller) {
        String title = seller.title.getText();
        String price = seller.text.getText();
        String genre = seller.combobox.getValue();

        bookSoldTitle = title + " - $" + price;
        soldBook.setText(bookSoldTitle);
        writeHistory(title, price, genre);
        writeBottomDashes();
    }

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
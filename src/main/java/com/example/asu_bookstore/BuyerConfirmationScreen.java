package com.example.asu_bookstore;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;

public class BuyerConfirmationScreen extends BorderPane {
    String booksPurchased;
    Label allBooks;

    public BuyerConfirmationScreen(final int WIDTH, final int HEIGHT, BuyerMainScreen buyer, ASU_Bookstore control) {
        Rectangle lightGreenBackground = new Rectangle(WIDTH, WIDTH);
        lightGreenBackground.setFill(Color.web("#d2e7da"));
        this.getChildren().add(lightGreenBackground);

        Rectangle greenBackgroundLeft = new Rectangle(200, 0, (WIDTH - 400), HEIGHT);
        greenBackgroundLeft.setFill(Color.web("#a0c3b1"));
        this.getChildren().add(greenBackgroundLeft);

        // The sun image is loaded from the program's resources folder and is placed into the scene
        InputStream sunLogoStream = getClass().getResourceAsStream("/BuyingConfirmSun.png");
        Image sun = new Image(sunLogoStream);
        ImageView displaySun = new ImageView();
        displaySun.setImage(sun);
        displaySun.setFitWidth(125);
        displaySun.setPreserveRatio(true); // sun image is expanded but aspect ratio should be preserved
        displaySun.setX((WIDTH/2.5) + 7);
        displaySun.setY(HEIGHT/13);
        //this.getChildren().add(displaySun);

        Label thankYou = new Label("Thank you for Purchasing with the ASU Bookstore!");
        Label purchaseTotal = new Label("Here are your purchase details:");

        //String booksPurchased = constructPurchaseInfo(buyer);
        allBooks = new Label();
        allBooks.setText(booksPurchased);

        Button backToHome = new Button("Back to Login");
        backToHome.setOnAction(e -> {
            booksPurchased = "";
            control.switchScreen("");
        });

        VBox allNodes = new VBox();
        allNodes.getChildren().addAll(thankYou, purchaseTotal, allBooks, displaySun, backToHome);
        allNodes.setAlignment(Pos.CENTER);

        this.setCenter(allNodes);

    }

    public void constructPurchaseInfo(BuyerMainScreen buyer) {
        Label booksBought = new Label();
        String boughtList = "";
        ArrayList<String> allSelectedBooks = new ArrayList<String>();
        for (int i = 0; i < buyer.selectedBooks.size(); i++) {
            boughtList += buyer.selectedBooks.get(i) + "\n";
        }
        System.out.println("boughtList = " + boughtList);
        booksPurchased = boughtList;
        allBooks.setText(booksPurchased);

    }
}


package com.example.asu_bookstore;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// This com.example.asu_bookstore.SellerMainScreen class will eventually host the seller's screen in the program.
// Currently, it is an empty screen with only a light purple Background.
public class SellerMainScreen extends BorderPane{
    public SellerMainScreen(final int WIDTH, final int HEIGHT, ASU_Bookstore control) {
        Rectangle lightPurpBackground = new Rectangle(WIDTH, WIDTH); // apricot background
        lightPurpBackground.setFill(Color.web("#E4CDE8"));
        this.getChildren().add(lightPurpBackground);
    }
}

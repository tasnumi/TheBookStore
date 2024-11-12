package com.example.asu_bookstore;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// This com.example.asu_bookstore.BuyerMainScreen class will eventually host the buyer's screen in the program.
// Currently, it is an empty screen with only a turquoise Background.
public class BuyerMainScreen extends BorderPane{
    public BuyerMainScreen(final int WIDTH, final int HEIGHT, ASU_Bookstore control) {
        Rectangle turquoiseBackground = new Rectangle(WIDTH, WIDTH); // apricot background
        turquoiseBackground.setFill(Color.web("#CDE8E5"));
        this.getChildren().add(turquoiseBackground);
    }
}

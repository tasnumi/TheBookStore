package com.example.asu_bookstore;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;

// This com.example.asu_bookstore.BuyerMainScreen class will eventually host the buyer's screen in the program.
public class BuyerMainScreen extends BorderPane{
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

        // My reccommendation - use CheckBoxes to list the genres and condition. This way the user
        // will be able to view books of multiple genres/conditions at once.
        // delete these comments once completed please!

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
}

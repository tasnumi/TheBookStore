package com.example.asu_bookstore;

import javafx.application.Application; // these three packages are needed for the scene's set up and launch
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

// This com.example.asu_bookstore.ASU_Bookstore class is the main driver for the entire JavaFX program. It is responsible for creating
// all the screens used in the program and switching them when requested by these different screen objects.
public class ASU_Bookstore extends Application {
    private BorderPane root;
    private AdminScreen admin;
    private BuyerMainScreen buyer;
    private SellerMainScreen seller;
    private LoginScreen login;
    private BuyerConfirmationScreen buyerConfimScreen;
    private SellerConfirmationScreen sellerConfimScreen;

    public void start(Stage primaryStage) {
        try {
            final int WIDTH = 800;  // size of window is not meant to be changed anywhere else in the program
            final int HEIGHT = 500;
            root = new BorderPane();
            admin = new AdminScreen(WIDTH, HEIGHT, this); // an instance of main is sent for screen switching purposes
            buyer = new BuyerMainScreen(WIDTH, HEIGHT, this);
            seller = new SellerMainScreen(WIDTH, HEIGHT, this);
            login = new LoginScreen(WIDTH, HEIGHT, admin, this);
            buyerConfimScreen = new BuyerConfirmationScreen(WIDTH, HEIGHT, buyer, this);
            sellerConfimScreen = new SellerConfirmationScreen(WIDTH, HEIGHT, seller, this);
            root.setCenter(login); // displaying the initial screen
            Scene scene = new Scene(root,WIDTH,HEIGHT); // set the entire scene with the root
            primaryStage.setScene(scene);
            primaryStage.show(); // display the window that will initially contain the login screen only
        }
        catch(Exception e) { // if anything unexpected occurs during the scene setup, the exception will be caught
            e.printStackTrace();
        }
    }

    // this main() method launches the program.
    public static void main(String[] args) {
        launch(args);
    }

    // authentication of user already done in com.example.asu_bookstore.LoginScreen object
    public void switchScreen(String screen) {
        if (screen.equals("admin")) {
            root.setCenter(admin);
        }
        else if (screen.equals("buyer")) {
            root.setCenter(buyer);
        }
        else if (screen.equals("seller")) {
            root.setCenter(seller);
        }
        else if (screen.equals("buyerConfirm")) {
            root.setCenter(buyerConfimScreen);
        }
        else if (screen.equals("sellerConfirm")) {
            root.setCenter(sellerConfimScreen);
        }
        else { //going back to login page
            root.setCenter(login);
        }
    }

    public BuyerConfirmationScreen getBuyerMainConfirmationScreen() {
        return buyerConfimScreen;
    }
}


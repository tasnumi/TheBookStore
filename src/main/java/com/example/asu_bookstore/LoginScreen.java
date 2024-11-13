package com.example.asu_bookstore;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.FileNotFoundException;
import java.io.InputStream;

// This com.example.asu_bookstore.LoginScreen class holds the layout and functionality of the program's login screen.
// This class works closely with the com.example.asu_bookstore.AdminScreen class to perform database operations to
// create new accounts and verify existing accounts that are attempting to log in. 
public class LoginScreen extends BorderPane{
    private Button login;
    private Button createAccount;
    private PasswordField inputPassword;
    private TextField inputUserName;
    private ToggleGroup userChoice;
    private RadioButton adminUser;
    private RadioButton buyerUser;
    private RadioButton sellerUser;

    public LoginScreen (final int WIDTH, final int HEIGHT, AdminScreen admin, ASU_Bookstore control) throws FileNotFoundException {
        // These Rectangles create the background for the Login Screen
        Rectangle maroonBackground = new Rectangle(WIDTH, WIDTH);
        maroonBackground.setFill(Color.web("#6e020f"));
        this.getChildren().add(maroonBackground);

        Rectangle goldBackgroundLeft = new Rectangle(0, 0, WIDTH / 4, HEIGHT);
        goldBackgroundLeft.setFill(Color.web("#ae7219"));
        this.getChildren().add(goldBackgroundLeft);

        Rectangle goldBackgroundRight = new Rectangle(WIDTH - (WIDTH/4), 0, WIDTH / 4, HEIGHT);
        goldBackgroundRight.setFill(Color.web("#ae7219"));
        this.getChildren().add(goldBackgroundRight);

        // The sun image is loaded from the program's resources folder and is placed into the scene
        InputStream sunLogoStream = getClass().getResourceAsStream("/loginSun.png");
        Image sun = new Image(sunLogoStream);
        ImageView displaySun = new ImageView();
        displaySun.setImage(sun);
        displaySun.setFitWidth(125);
        displaySun.setPreserveRatio(true); // sun image is expanded but aspect ratio should be preserved
        displaySun.setX((WIDTH/2.5) + 7);
        displaySun.setY(HEIGHT/13);
        this.getChildren().add(displaySun);

        Label select = new Label("Select which user you are:");
        select.setTextFill(Color.WHITE);

        // Setting the userType buttons and placing labels next to them
        GridPane userContainer = new GridPane();
        userContainer.setAlignment(Pos.CENTER);
        userChoice = new ToggleGroup();
        adminUser = new RadioButton();
        buyerUser = new RadioButton();
        sellerUser = new RadioButton();
        Label adminLabel = new Label("Admin User");
        Label buyerLabel = new Label("Buyer");
        Label sellerLabel = new Label("Seller");
        adminLabel.setTextFill(Color.WHITE);
        buyerLabel.setTextFill(Color.WHITE);
        sellerLabel.setTextFill(Color.WHITE);
        adminUser.setToggleGroup(userChoice);
        buyerUser.setToggleGroup(userChoice);
        sellerUser.setToggleGroup(userChoice);
        userContainer.add(adminUser, 0, 1);
        userContainer.add(buyerUser, 0, 2);
        userContainer.add(sellerUser, 0, 3);
        userContainer.add(adminLabel, 3, 1);
        userContainer.add(buyerLabel, 3, 2);
        userContainer.add(sellerLabel, 3, 3);

        // creating the main container that will hold most of the login screen's interface buttons.
        VBox txtAndButtons = new VBox();
        txtAndButtons.setMinWidth(WIDTH);
        txtAndButtons.setMinHeight(HEIGHT);
        txtAndButtons.setAlignment(Pos.CENTER);
        txtAndButtons.setPadding(new Insets(HEIGHT/2.5, 0, 0, 0));
        txtAndButtons.setSpacing(5);

        // TextFeilds for the user to input their username and password
        inputUserName = new TextField();
        inputUserName.setMaxWidth(200);
        inputUserName.setPromptText("ASU ID");
        inputPassword = new PasswordField();
        inputPassword.setPromptText("ASU Password");
        inputPassword.setMaxWidth(200);

        login = new Button("Login");
        createAccount = new Button("Create Account");
        login.setMaxSize(250, 250);
        createAccount.setMaxSize(125, 125);

        txtAndButtons.getChildren().addAll(select, userContainer, inputUserName, inputPassword, login, createAccount);
        this.setCenter(txtAndButtons);

        // This section of code defines the functionality of the login button. When clicked, the inputted user
        // credentials are sent to the com.example.asu_bookstore.AdminScreen object for proper checking. If successful, the user logs in
        // if unsuccessful an alert is given to the user informing them of the results.
        login.setOnAction(e -> {
            String selectedToggle = checkToggle(userChoice);
            if (admin.checkUserCredentials(inputUserName.getText(), inputPassword.getText(), selectedToggle) == true) {
                inputUserName.clear(); // clear all choices if and when user logs out and returns to login page.
                inputPassword.clear();
                userChoice.selectToggle(null);
                if (selectedToggle.equals("admin")) {  // screen is switched in the com.example.asu_bookstore.ASU_Bookstore instance that was passed in
                    control.switchScreen("admin");
                }
                else if (selectedToggle.equals("buyer")) {
                    control.switchScreen("buyer");
                }
                else if (selectedToggle.equals("seller")) {
                    control.switchScreen("seller");
                }
            }
            else { // User log in was unsuccessful.
                Alert loginFail = new Alert(AlertType.WARNING);
                loginFail.setTitle("Login Failed");
                loginFail.setHeaderText("Login Failed");
                loginFail.setContentText("Unable to log in. Please ensure the username and password is correct," +
                        " all feild have been filled, or create an account if needed.");
                loginFail.showAndWait();
            }
        });

        // This section of code defines the functionality of the create account button. When clicked, the inputed user
        // credentials are sent to the com.example.asu_bookstore.AdminScreen object for proper checking. If successful, the user logs in
        // if unsuccessful an alert is given to the user informing them of the results.
        createAccount.setOnAction(e -> {
            String selectedToggle = checkToggle(userChoice);
            // Must first check to make sure all feilds have information in them and that the user is not attempting
            // to make another admin account.
            if ((selectedToggle.equals("")) || (selectedToggle.equals("admin")) || (inputUserName.getText().equals("")) || (inputPassword.getText().equals(""))) {
                Alert createFail = new Alert(AlertType.WARNING);
                createFail.setTitle("Account Creation Failed");
                createFail.setHeaderText("Unable to create an account.");
                createFail.setContentText("Please ensure all feilds are properly entered/checked. Also, "
                        + "no new admin accounts can be created without special permission.");
                createFail.showAndWait();
            }
            else {
                // if the account does not exist, send information to com.example.asu_bookstore.AdminScreen object to create an account
                if (admin.checkUserCredentials(inputUserName.getText(), inputPassword.getText(), selectedToggle) == false) {
                    admin.createUser(inputUserName.getText(), inputPassword.getText(), selectedToggle);

                    // Alert to inform the user that an account was successfully created.
                    Alert createSuccess = new Alert(AlertType.INFORMATION);
                    createSuccess.setTitle("Account Creation Success!");
                    createSuccess.setHeaderText("Account Creation Success");
                    createSuccess.setContentText("You new account has been created and is ready to be logged into!");
                    createSuccess.showAndWait();
                }
                else { // Account creation failed because it already exists. Alerts the user
                    Alert createFail = new Alert(AlertType.WARNING);
                    createFail.setTitle("Account Creation Failed");
                    createFail.setHeaderText("Account Creation Failed");
                    createFail.setContentText("This account already exists.");
                    createFail.showAndWait();
                }
            }

        });
    }

    // This simple checkToggle method simply checks which user type radiobutton is selected
    // from the login page and returns the result as a string.
    private String checkToggle(ToggleGroup userChoice) {
        if (userChoice.getSelectedToggle() == adminUser) {
            return "admin";
        }
        else if (userChoice.getSelectedToggle() == buyerUser) {
            return "buyer";
        }
        else if (userChoice.getSelectedToggle() == sellerUser) {
            return "seller";
        }
        return "";
    }
}
package com.example.asu_bookstore;

import java.io.*;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// This com.example.asu_bookstore.AdminScreen class holds the functionality and layout of the admin's screen on the program.
// Additionally, the user database is held in this class and works closely with the other classes
// for database related operations.
public class AdminScreen extends BorderPane {
    private ArrayList<String[]> userDatabase; // holds all the users

    public AdminScreen(final int WIDTH, final int HEIGHT, ASU_Bookstore control) {
        Rectangle apricotBackground = new Rectangle(WIDTH, WIDTH); // adding the apricot background to the scence
        apricotBackground.setFill(Color.web("#E8DCCD"));
        this.getChildren().add(apricotBackground);

        userDatabase = new ArrayList<String[]>(); // creating the database and filling it with the past runs
        getDataFromTextDB();

        HBox infoContainer = new HBox(); // This HBox will hold the three main VBoxes that will hold the rest of the screen
        infoContainer.setAlignment(Pos.CENTER);
        infoContainer.setSpacing(20);
        infoContainer.setPadding(new Insets(0, 15, 0, 15));

        VBox transactionContainer = new VBox(); // This Vbox will primarily be used for the transaction information
        transactionContainer.setStyle("-fx-background-color: #C4AA97; -fx-border-color: black; -fx-border-width: 0.5; -fx-padding: 20");
        transactionContainer.setMinWidth((WIDTH/3) - 30);
        transactionContainer.setMaxHeight(HEIGHT - 50);
        transactionContainer.setAlignment(Pos.CENTER);

        VBox graphContainer = new VBox(); // This Vbox will primarily be used for the graph information
        graphContainer.setStyle("-fx-background-color: #C4AA97; -fx-border-color: black; -fx-border-width: 0.5; -fx-padding: 20");
        graphContainer.setMinWidth((WIDTH/3) - 30);
        graphContainer.setMaxHeight(HEIGHT - 50);
        graphContainer.setAlignment(Pos.CENTER);

        // The admin sun image is loaded from the program's resources folder and is placed into the scene
        InputStream sunLogoStream = getClass().getResourceAsStream("/adminSun.png");
        Image sun = new Image(sunLogoStream);
        ImageView displaySun = new ImageView();
        displaySun.setImage(sun);
        displaySun.setFitWidth(90);
        displaySun.setPreserveRatio(true); // sun image is expanded but aspect ratio should be preserved
        displaySun.setX((WIDTH/2.5) + 7);
        displaySun.setY(HEIGHT/5);

        graphContainer.getChildren().addAll(displaySun); //add future nodes to the left of the sun

        VBox databaseContainer = new VBox(); // This Vbox will primarily be used for the database information
        databaseContainer.setStyle("-fx-background-color: #C4AA97; -fx-border-color: black; -fx-border-width: 0.5; -fx-padding: 20");
        databaseContainer.setMinWidth((WIDTH/3) - 30);
        databaseContainer.setMaxHeight(HEIGHT - 50);

        VBox displayDatabase = new VBox(); // This Vbox will be used to see the actual listings in each database
        displayDatabase.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 0.5; -fx-padding: 20");
        displayDatabase.setMinWidth((WIDTH/4));
        displayDatabase.setMinHeight(HEIGHT/2);
        displayDatabase.setPadding(new Insets(20,20,20,20));

        Label dataBaseTitle = new Label("Manage Databases:");
        Button userLoader = new Button("Users");
        Button bookLoader = new Button("Books");
        bookLoader.setAlignment(Pos.CENTER);

        // this userLoader button will fill/show the displayDatabase VBox with all the users as checkboxes
        userLoader.setOnAction(e -> {
            if (displayDatabase.getChildren().size() != 0) { // remove anything that is currently inside displayDatabase
                displayDatabase.getChildren().clear();
            }
            for (int i = 0; i < userDatabase.size(); i++) { // go through each entry and add them to displayDatabase
                CheckBox listUser = createCheckBoxUser(userDatabase.get(i));
                displayDatabase.getChildren().add(listUser);
            }
        });

        // this bookLoader button will eventually fill/show the displayDatabase VBox with all the books listed for purchase/sale
        bookLoader.setOnAction(e -> {
            if (displayDatabase.getChildren().size() != 0) { // remove anything that is currently inside displayDatabase
                displayDatabase.getChildren().clear();
            }
            Label comingSoon = new Label("Coming Soon!");
            displayDatabase.getChildren().add(comingSoon);
        });

        // Add above components to the HBox/VBoxes and then to the pane
        databaseContainer.getChildren().addAll(dataBaseTitle, userLoader, bookLoader, displayDatabase);

        infoContainer.getChildren().addAll(transactionContainer, graphContainer, databaseContainer);
        this.setCenter(infoContainer);
    }

    // This createUser function fills a String array with the given username, password,
    // and userType. A check to ensure the username doesnt exist is also made to ensure no
    // duplicate accounts are made.
    public boolean createUser(String username, String password, String userType) {
        if (usernameExists(username) == false) { // check to make sure a profile with the given username does not exist
            String[] newUser = new String[3];
            newUser[0] = username;
            newUser[1] = password;
            newUser[2] = userType;
            userDatabase.add(newUser); // add user to the arraylist database
            addUserToTextFile(newUser); // add user to the textfile to be saved for future program runs.
            return true;
        }
        return false; // a new user account was not able to be created
    }

    // This method is used to ensure no duplicate usernames can exist in the database.
    public boolean usernameExists(String username) {
        for (int i = 0; i < userDatabase.size(); i++) { // check each entry in the database
            if (userDatabase.get(i)[0] == username) {
                return true;  // username found
            }
        }
        return false; // username not in the database
    }

    // This method is a simple login feature that checks each account in the database for a matching
    // username, password, and user type that was entered in the log in screen. If all these parameters
    // match, then the user exists and can log in.
    public boolean checkUserCredentials (String username, String password, String userType) {
        for (int i = 0; i < userDatabase.size(); i++) {
            if ((userDatabase.get(i)[0].equals(username)) && (userDatabase.get(i)[1].equals(password)) && (userDatabase.get(i)[2].equals(userType))) {
                return true; // user exists, let them log in.
            }
        }
        return false; // user does NOT exist, do not let them log in
    }

    // This is a simply getter method that returns the entire userDatabase for other classes
    // to use.
    public ArrayList<String[]> getDatabase() {
        return userDatabase;
    }

    // This method writes to the userDatabase.txt file that holds all the accounts that were
    // created in previous runs of the program. With each new user account creation, the program
    // writes it to the textfile. Duplicate users was already checked for in the main account
    // creation method.
    private void addUserToTextFile(String[] newUser) {
        try {
            FileOutputStream outS = new FileOutputStream("src/main/resources/userDatabase.txt", true); // true parameter makes it write at the file's end
            OutputStreamWriter writer = new OutputStreamWriter(outS);
            BufferedWriter buffW = new BufferedWriter(writer);

            buffW.write(newUser[0] + " " + newUser[1] + " " + newUser[2]);  // written in a way for future easy token extraction
            buffW.newLine();
            buffW.close(); // done with the writer for now.
            writer.close();
        }
        catch (IOException e) { // most likely file does not exist or there was a problem in the array content writing
            System.out.println("CRITICAL ERROR IN CREATING THE FILE WRITER!!!");
        }
    }

    // This method reads the userDatabase.txt file that holds all the accounts that were
    // created in previous runs of the program and adds them to the userDatabase arraylist.
    private void getDataFromTextDB() {
        String currentLine = "";
        try {
            InputStream is = getClass().getResourceAsStream("/userDatabase.txt"); // loading up the userDatabase.txt file
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader buffR = new BufferedReader(reader);

            // reading line inside the while loop in case file is initially empty
            while ((currentLine = buffR.readLine()) != null) {
                String[] newUser = currentLine.split(" "); // split will create an array in the form of a user
                userDatabase.add(newUser);
            }

            buffR.close();
            reader.close();
        } catch (IOException  e) { // most likely file does not exist or was moved if this occurs.
            System.out.println("CRITICAL ERROR IN READING THE DATABASE TEXTFILE!!!");
        }
    }

    // This method is used to create a checkbox for each user in the textfile database
    // to be displayed in the admin screen.
    public CheckBox createCheckBoxUser(String[] userInfo) {
        CheckBox user = new CheckBox();
        String info = "Username: " + userInfo[0] + "\n" +
                "Password: " + userInfo[1] + "\n" +
                "User Type: " + userInfo[2] + "\n";
        user.setText(info);

        return user;
    }
}


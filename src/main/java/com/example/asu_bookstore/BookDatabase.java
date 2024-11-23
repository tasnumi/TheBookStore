package com.example.asu_bookstore;

import java.io.*;
import java.util.ArrayList;

public class BookDatabase {
    private ArrayList<String> books;

    public BookDatabase() {
        books = new ArrayList<>();
        loadDatabase();
    }

    private void loadDatabase() {
        try (BufferedReader reader = new BufferedReader(new FileReader("booksAvailable.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                books.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading database: " + e.getMessage());
        }
    }

    public void updateDatabase(String bookDetails, boolean isPurchase) {
        if (isPurchase) {
            decrementBook(bookDetails);
        } else {
            incrementBook(bookDetails);
        }
        saveDatabase();
    }

    private void decrementBook(String bookDetails) {
        books.removeIf(book -> book.contains(bookDetails));
    }

    private void incrementBook(String bookDetails) {
        books.add(bookDetails);
    }

    private void saveDatabase() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("booksAvailable.txt"))) {
            for (String book : books) {
                writer.write(book);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving database: " + e.getMessage());
        }
    }

    public ArrayList<String> getBooks() {
        return books;
    }
}

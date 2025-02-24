import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


public class LibrarySystem {

    private static class Book implements Serializable {
        private static final long serialVersionUID = 1L; // Add a serialVersionUID for serialization safety

        private String title;
        private String author;
        private String ISBN;
        private boolean isAvailable;
        private boolean borrower;

        public Book(String title, String author, String ISBN) {
            this.title = title;
            this.author = author;
            this.ISBN = ISBN;
            this.isAvailable = true;
            this.borrower = false;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getISBN() {
            return ISBN;
        }

        public boolean isAvailable() {
            return isAvailable;
        }

        public void setAvailable(boolean available) {
            isAvailable = available;
        }
        public boolean isBorrower() {
            return borrower;
        }
        public void setBorrower(boolean borrower) {
            this.borrower = borrower;
        }

        @Override
        public String toString() {
            return String.format("%s by %s (%s) - %s", title, author, ISBN, isAvailable ? "Available" : "Not Available");
        }
    }

        private static int bookAvailableCount = 0;
        private static int bookBorrowerCount = 0;
        private static ArrayList<Book> Library = new ArrayList<>();
        private static final String FILE_NAME = "Library.dat";

        public static void main(String[] args) {

            Library.add(new Book("The Catcher in the Rye", "J.D. Salinger", "9780316769488"));

            Library.add(new Book("To Kill a Mockingbird", "Harper Lee", "9780060935467"));

            Library.add(new Book("1984", "George Orwell", "9780451524935"));

            Library.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565"));

            Library.add(new Book("Moby Dick", "Herman Melville", "9781503280786"));

            Library.add(new Book("Pride and Prejudice", "Jane Austen", "9780141439518"));

            Library.add(new Book("The Hobbit", "J.R.R. Tolkien", "9780547928227"));

            Library.add(new Book("The Lord of the Rings", "J.R.R. Tolkien", "9780618640157"));

            Library.add(new Book("Fahrenheit 451", "Ray Bradbury", "9781451673319"));

            Library.add(new Book("Jane Eyre", "Charlotte Bronte", "9780141441146"));

            Library.add(new Book("Brave New World", "Aldous Huxley", "9780060850524"));

            Library.add(new Book("Wuthering Heights", "Emily Bronte", "9780141439556"));

            Library.add(new Book("The Odyssey", "Homer", "9780140268867"));

            Library.add(new Book("Dracula", "Bram Stoker", "9780141439846"));

            Library.add(new Book("War and Peace", "Leo Tolstoy", "9780199232765"));

            Library.add(new Book("The Brothers Karamazov", "Fyodor Dostoevsky", "9780374528379"));

            Library.add(new Book("Les Miserables", "Victor Hugo", "9780451419439"));

            Scanner scanner = new Scanner(System.in);

            // Display a welcome screen
            System.out.println("-------------------------------------------------");
            System.out.println("         Welcome to the Library System!");
            System.out.println("-------------------------------------------------");
            System.out.println("This system allows you to manage books simply.");

            // Attempt to load data from file
            loadLibraryFromFile();

            // Main program loop
            while (true) {
                // Display menu
                System.out.println("\nMain Menu:");
                System.out.println("1. Add Book");
                System.out.println("2. List All Books");
                System.out.println("3. List Available Books");
                System.out.println("4. List Borrowed Books");
                System.out.println("5. Search Books");
                System.out.println("6. Borrow Book");
                System.out.println("7. Buy Book");
                System.out.println("8. Return Book");
                System.out.println("9. Sort Books");
                System.out.println("10. Save Library");
                System.out.println("11. Exit");
                System.out.print("Choose an option: ");

                int choice = getIntInput(scanner); // Handle input with validation
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addBook(scanner);
                        break;
                    case 2:
                        listBooks("all");
                        break;
                    case 3:
                        listBooks("available");
                        break;
                    case 4:
                        listBooks("borrowed");
                        break;
                    case 5:
                        searchBooks(scanner);
                        break;
                    case 6:
                        borrowBook(scanner);
                        break;
                    case 7:
                        buyBook(scanner);
                        break;
                    case 8:
                        returnBook(scanner);
                        break;
                    case 9:
                        sortBooks(scanner);
                        break;
                    case 10:
                        saveLibraryToFile();
                        break;
                    case 11:
                        saveLibraryToFile(); // Save before exiting
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        }




    private static void addBook(Scanner scanner) {
        System.out.println("Enter the title of the book: ");
        String title = scanner.nextLine();
        System.out.println("Enter the author of the book: ");
        String author = scanner.nextLine();
        System.out.println("Enter the ISBN of the book: ");
        String ISBN = scanner.nextLine();

        for (Book book : Library ) {
            if (book.getISBN().equals(ISBN)) {
                System.out.println("That ISBN is already in the library!");
                return;
            }
        }

        Book newBook = new Book(title, author, ISBN);
        Library.add(newBook);
        System.out.println("Book added successfully!");
    }

    private static void listBooks(String type) {

            if (Library.isEmpty()) {
                System.out.println("The library is empty!");
                return;
            }

        switch (type) {
            case "all" -> {
                System.out.println("All books:");
                for (Book book : Library) {
                    System.out.println(book);
                }
            }

            case "available" -> {
                if (bookAvailableCount <= 0) {
                    System.out.println("No available books found!");
                    return;
                }
                System.out.println("Available books:");
                for (Book book : Library) {
                    if (book.isAvailable()) {
                        System.out.println(book);
                    }
                }
            }

            case "borrowed" -> {
                if (bookBorrowerCount <= 0) {
                    System.out.println("No borrowed books found!");
                    return;
                }
                System.out.println("Borrowed books:");
                for (Book book : Library) {
                    if (book.isBorrower()) {
                        System.out.println(book);
                    }
                }
            }
        }
    }

    private static void searchBooks(Scanner scanner) {
        System.out.print("Enter keyword to search (title/author): ");
        String keyword = scanner.nextLine().toLowerCase();

        System.out.println("Search Results:");
        boolean found = false;

        for (Book book : Library) {
            if (book.getTitle().toLowerCase().contains(keyword) || book.getAuthor().toLowerCase().contains(keyword)) {
                System.out.println(book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No results found!");
        }
    }

    private static void borrowBook(Scanner scanner) {
        System.out.print("Enter ISBN of book to borrow: ");
        String ISBN = scanner.nextLine();

        for (Book book : Library) {
            if (book.getISBN().equals(ISBN)) {
                if (book.isAvailable()) {
                    book.setBorrower(true);
                    book.setAvailable(false);
                    bookBorrowerCount++;
                    System.out.println("You borrowed " + book);
                } else {
                    System.out.println("That book is already borrowed!");
                }
                return;
            }
        }

        System.out.println("No book found with the given ISBN.");
    }

    private static void buyBook(Scanner scanner) {
        System.out.print("Enter ISBN of the book: ");
        String ISBN = scanner.nextLine();

        for (int i = 0; i < Library.size(); i++) {
            Book book = Library.get(i);
            if (book.getISBN().equals(ISBN)) {
                if (!book.isAvailable()) {
                    System.out.println("This book is unavailable.");
                } else {
                    System.out.println("You bought the book.");
                    Library.remove(i);
                }
                return;
            }
        }


        System.out.println("No book found with the given ISBN.");
    }

    private static void returnBook(Scanner scanner) {
        System.out.print("Enter ISBN of the book to return: ");
        String ISBN = scanner.nextLine();

        for (Book book : Library) {
            if (book.getISBN().equals(ISBN)) {
                if (!book.isAvailable()) {
                    book.setAvailable(true);
                    System.out.println("You returned: " + book);
                } else {
                    System.out.println("This book was not borrowed.");
                }
                return;
            }
        }

        System.out.println("No book found with the given ISBN.");
    }

   private static void sortBooks(Scanner scanner) {
       System.out.println("\nSort by:");
       System.out.println("1. Title");
       System.out.println("2. Author");
       System.out.print("Choose an option: ");
       int choice = getIntInput(scanner);
       switch (choice) {
           case 1:
               Collections.sort(Library, Comparator.comparing(Book::getTitle));
               System.out.println("Books sorted by title.");
               break;
           case 2:
               Collections.sort(Library, Comparator.comparing(Book::getAuthor));
               System.out.println("Books sorted by author.");
               break;
           default:
               System.out.println("Invalid choice!");
       }
   }

   private static void saveLibraryToFile() {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
                oos.writeObject(Library);
                System.out.println("Library saved successfully!");
            } catch (IOException e) {
                e.printStackTrace(); // Print stack trace for detailed debugging in console
                System.out.println("Error saving library to file! - " + e.getMessage());
            }
   }

   private static void loadLibraryFromFile() {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                System.out.println("No saved library data found. Starting fresh.");
                return;
            }
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                Library = (ArrayList<Book>) ois.readObject();
                System.out.println("Library loaded successfully!");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading library from file! - " + e.getMessage());
            }
   }


    private static int getIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input! Please enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
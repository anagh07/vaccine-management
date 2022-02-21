package com.tintinpharma;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PharmacyDriver {

    private static final String PASSWORD = "password";
    private static Scanner sc = new Scanner(System.in);
    private int attempts;
    private int choice;
    // Manage inventory and vaccines
    private int maxVaccines;
    private Vaccine[] inventory;
    private int currentInventoryIndex;

    public PharmacyDriver() {
        this.attempts = 0;
        this.maxVaccines = 0;
        this.currentInventoryIndex = 0;
    }

    public void run() {
        // Display welcome message
        System.out.println("Welcome to TinTin Pharma!\n");

        // Set inventory size and create inventory
        setupInventory();

        while (this.choice != 5) {
            // Check password attempts
            if (this.attempts >= 12 && this.choice == 1) {
                System.out.println("Program detected suspicious activities and will terminate immediately!");
                System.exit(-1);
            }

            // Reset choice
            this.choice = 0;

            // Display main menu until user enters 1-5
            displayMainMenu();
            while (this.choice < 1 || this.choice > 5) {
                try {
                    this.choice = sc.nextInt();
                } catch (InputMismatchException error) {
                    System.out.print("Please enter a valid choice: ");
                    this.choice = 0;
                }
                sc.nextLine();
            }

            // Based on entered choice run required process
            switch (this.choice) {
                case 1:
                    if (verifyPassword()) {
                        enterNewVaccines();
                    }
                    break;
                case 2:
                    if (verifyPassword()) {
                        updateVaccine();
                    }
                    break;
                case 3:
                    findVaccinesByBrand();
                    break;
                case 4:
                    findCheaperThan();
                    break;
                case 5:
                    System.out.println("Logging off!");
                    System.exit(0);
                    break;
            }
        }
    }

    /**
     * Method handles main menu option-4, that displays vaccines in inventory that are cheaper than a certain price.
     */
    private void findCheaperThan() {
        double price = 0;
        System.out.print("Enter a price value: $");
        while (true) {
            try {
                price = sc.nextDouble();
                sc.nextLine();
                break;
            } catch (InputMismatchException error) {
                System.out.println("Invalid price!");
                sc.nextLine();
            }
        }
        boolean empty = true;
        for (int i = 0; i < currentInventoryIndex; i++) {
            if (inventory[i].getPriceTag() < price) {
                System.out.println(inventory[i].toString());
                System.out.println();
                empty = false;
            }
        }
        if (empty) System.out.println("No vaccines under $" + price);
    }

    /**
     * Method handles main menu option-3, that displays all vaccines from a specific brand in inventory.
     */
    private void findVaccinesByBrand() {
        Vaccine.Brands brand = this.brandSelector();
        boolean empty = true;
        for (int i = 0; i < currentInventoryIndex; i++) {
            if (inventory[i].getBrand().equals(brand)) {
                System.out.println(inventory[i].toString());
                System.out.println();
                empty = false;
            }
        }
        if (empty) System.out.println("No vaccines from " + brand);
    }

    /**
     * Method handles option 2, that allows user to update an existing vaccine.
     */
    private void updateVaccine() {
        // Prompt vaccine number
        int vacNum = -1;
        while (vacNum == -1) {
            System.out.print("Enter vaccine number (0-" + (maxVaccines - 1) + "): ");
            while (true) {
                try {
                    vacNum = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (InputMismatchException error) {
                    System.out.println("Invalid input");
                    sc.nextLine();
                }
            }

            if (inventory[vacNum] == null) {
                // If no vaccine at position, prompt for another vaccine number
                System.out.print(
                        "No vaccine at that position. \nWhat would you like to do?\n" +
                                "\t1. Re-enter another vaccine number\n" +
                                "\t2. Back to main menu ↩\n" +
                                "Your choice > "
                );
                int choice;
                while (true) {
                    try {
                        choice = sc.nextInt();
                        sc.nextLine();
                        if (choice != 1 && choice != 2) {
                            throw new InputMismatchException("not in range");
                        }
                        break;
                    } catch (InputMismatchException error) {
                        System.out.println("Invalid input");
//                        sc.nextLine();
                    }
                }
                if (choice == 2) {
                    return;
                } else if (choice == 1) {
                    vacNum = -1;
                }
            }
        }

        // Display chosen vaccine info
        System.out.println("Vaccine: " + vacNum);
        System.out.println(inventory[vacNum].toString());

        // Change vaccine information
        int choice = 0;
        while (choice != 5) {
            System.out.println("What information would you like to change?");
            System.out.print(
                    "\t1. Brand\n" +
                            "\t2. Dose\n" +
                            "\t3. Expiry\n" +
                            "\t4. Price\n" +
                            "\t5. Quit\n" +
                            "Your choice >"
            );

            choice = 0;
            while (choice < 1 || choice > 5) {
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                } catch (InputMismatchException error) {
                    System.out.println("Invalid input");
                    sc.nextLine();
                }
            }
            switch (choice) {
                case 1:
                    System.out.print("Current brand " + inventory[vacNum].getBrand() + ". ");
                    Vaccine.Brands newBrand = this.brandSelector();
                    inventory[vacNum].setBrand(newBrand);
                    System.out.println("Brand updated!");
                    System.out.println(inventory[vacNum].toString());
                    break;
                case 2:
                    System.out.println("Current dose " + inventory[vacNum].getDose() + ". Enter new dose: ");
                    double newDose = Double.parseDouble(sc.nextLine());
                    inventory[vacNum].setDose(newDose);
                    System.out.println("Dose updated!");
                    System.out.println(inventory[vacNum].toString());
                    break;
                case 3:
                    System.out.println("Current expiry " + inventory[vacNum].getExpiry() + ". Enter new expiry date:");
                    String newExpiry = sc.nextLine();
                    inventory[vacNum].setExpiry(newExpiry);
                    System.out.println("Expiry updated!");
                    System.out.println(inventory[vacNum].toString());
                    break;
                case 4:
                    System.out.println("Current price " + inventory[vacNum].getPriceTag() + ". Enter new price:");
                    double newPrice = Double.parseDouble(sc.nextLine());
                    inventory[vacNum].setPriceTag(newPrice);
                    System.out.println("Price updated!");
                    System.out.println(inventory[vacNum].toString());
                    break;
                case 5:
                    return;
            }
        }
    }

    /**
     * Method handles the process to create and add new vaccines to inventory.
     */
    private void enterNewVaccines() {
        // Prompt for number of vaccines
        System.out.print("Number of vaccines: ");
        int numOfVaccine;
        while (true) {
            try {
                numOfVaccine = sc.nextInt();
                sc.nextLine();
                break;
            } catch (InputMismatchException error) {
                System.out.print("Please enter a valid number: ");
                sc.nextLine();
            }
        }

        // Check if there is space in inventory
        boolean spaceInInventory = (this.currentInventoryIndex + numOfVaccine) <= this.maxVaccines;

        // Loop: create and add vaccines to inventory
        if (spaceInInventory) {
            for (int i = 0; i < numOfVaccine; i++) {
                System.out.println("Enter vaccine information. Press enter to use default values.");
                this.createVaccineObject();
            }
        } else {
            System.out.println("Not enough space in inventory. Only " + (this.maxVaccines - this.currentInventoryIndex) + " slots available.");
        }
    }

    /**
     * Method prints out the main menu to console.
     */
    private void displayMainMenu() {
        System.out.print(
                "\nWhat do you want to do?\n" +
                        "\t1. Enter new vaccines (password required)\n" +
                        "\t2. Change information of a vaccine (password required)\n" +
                        "\t3. Display all vaccines by a specific brand\n" +
                        "\t4. Display all vaccines under a certain a price\n" +
                        "\t5. Quit\n" +
                        "Please enter your choice > "
        );
    }

    /**
     * Verify the entered password. User gets 3 attempts.
     *
     * @return returns true if correct password is entered in 3 tries. Otherwise, returns false.
     */
    private boolean verifyPassword() {
        System.out.println("Enter password: ");
        for (int i = 0; i < 3; i++) {
            String enteredPassword = sc.nextLine();
            if (enteredPassword.equals(PASSWORD)) {
                this.attempts = 0;
                return true;
            }
            this.attempts++;
        }
        return false;
    }

    /**
     * Creates the inventory where vaccines will be stored.
     */
    private void setupInventory() {
        System.out.print("Please specify your inventory size: ");
        while (true) {
            try {
                this.maxVaccines = sc.nextInt();
                break;
            } catch (InputMismatchException error) {
                System.out.print("Please enter a valid inventory size: ");
                sc.nextLine();
            }
        }
        sc.nextLine();
        inventory = new Vaccine[this.maxVaccines];
    }

    /**
     * Creates a vaccine object and adds to the inventory.
     *
     * @param vacNum index in the inventory.
     */
    private void createVaccineObject(int vacNum) {
        // Set brand: default brand Pfizer
        Vaccine.Brands brand = this.brandSelector();
        // Set dose
        System.out.print("Enter dose: ");
        String inputDose = sc.nextLine();
        double dose = (inputDose.equals("")) ? -1 : Double.parseDouble(inputDose);
        // Set expiry
        System.out.print("Enter expiry: ");
        String expiry = sc.nextLine();
        // Set price
        System.out.print("Enter price: ");
        String inputPrice = sc.nextLine();
        double price = (inputPrice.equals("")) ? -1 : Double.parseDouble(inputPrice);

        // Create vaccine object
        Vaccine newVax;
        if (dose < 0 && expiry.equals("") && price < 0) {
            newVax = new Vaccine(brand);
        } else {
            newVax = new Vaccine(brand, dose, expiry, price);
        }

        // Store vaccine in inventory
        this.inventory[vacNum] = newVax;
    }

    private Vaccine.Brands brandSelector() {
        System.out.println("Enter brand: ");
        System.out.print(
                "\t1. Pfizer\n" +
                        "\t2. Moderna\n" +
                        "\t3. AstraZeneca\n" +
                        "\t4. Janssen\n" +
                        "Your choice > "
        );
        String userChoiceInput = sc.nextLine();
        int choice = (userChoiceInput.equals("")) ? 0 : Integer.parseInt(userChoiceInput) - 1;
        return Vaccine.Brands.values()[choice];
    }

    private void createVaccineObject() {
        this.createVaccineObject(this.currentInventoryIndex);
        this.currentInventoryIndex++;
    }
}

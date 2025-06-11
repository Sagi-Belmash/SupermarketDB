package Models;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput {

    public static String getSellerNameFromUser() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter the name of the seller or enter \".\" to return to the main menu: ");
        String name = reader.next();
        if (name.equals(".")) {
            return "";
        }
        return name;
    }

    public static String getPasswordFromUser() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter a password: ");
        return reader.next();
    }

    public static String getBuyerNameFromUser() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter the name of the buyer or enter \".\" to return to the main menu: ");
        String name = reader.next();
        if (name.equals(".")) {
            return "";
        }
        return name;
    }

    public static Address getBuyerAddressFromUser() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter buyer's street name: ");
        String street = reader.next();
        System.out.println("Please enter buyer's building number: ");
        while (true) {
            try {
                int building = reader.nextInt();
                System.out.println("Please enter buyer's city: ");
                String city = reader.next();
                System.out.println("Please enter buyer's country: ");
                String country = reader.next();
                return AddressFactory.createAddress(street, building, city, country);
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer, please enter an answer with the correct type:");
                reader.nextLine();
            }
        }
    }

    public static String getProductNameFromUser() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter the name of the product: ");
        return reader.next();
    }

    public static float getProductPriceFromUser() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter the price of the product: ");
        while (true) {
            try {
                return reader.nextFloat();
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number, please enter an answer with the correct type:");
                reader.nextLine();
            }
        }
    }

    public static float getPackagePriceFromUser() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter the price of the package (if there is no special package enter 0):");
        while (true) {
            try {
                return reader.nextFloat();
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number, please enter an answer with the correct type:");
                reader.nextLine();
            }
        }
    }

    public static Category getCategoryFromUser() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Select a category:");
        for (Category category : Category.values()) {
            System.out.println((category.ordinal() + 1) + ") " + category);
        }
        while (true) {
            try {
                int categoryIndex = reader.nextInt();
                while (categoryIndex < 1 || categoryIndex > Category.values().length) {
                    System.out.println("The input should be between 1-" + Category.values().length + ", please select again:");
                    categoryIndex = reader.nextInt();
                }
                return Category.values()[categoryIndex - 1];
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer, please enter an answer with the correct type:");
                reader.nextLine();
            }
        }
    }

    public static String getUserChoice(String msg) {
        Scanner reader = new Scanner(System.in);
        System.out.println(msg);
        String answer = reader.next();
        while (!answer.equals("Y") && !answer.equals("N")) {
            System.out.println(msg);
            answer = reader.next();
        }
        return answer;
    }

    public static int getOrderNumberFromUser(int numOfOrders) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Please select an order to be the buyer's new cart, if you wish to cancel enter \"0\":");
        while (true) {
            try {
                int orderNum = reader.nextInt();
                while (orderNum < 0 || orderNum > numOfOrders) {
                    System.out.println("This option is not available, please choose from the options above:");
                    orderNum = reader.nextInt();
                }
                return orderNum;
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer, please enter an answer with the correct type:");
                reader.nextLine();
            }
        }
    }
}

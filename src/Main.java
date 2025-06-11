
import Models.MenuInvoker;

import java.util.*;

private static Scanner reader;
private static MenuInvoker invoker;


public static void main() {
    run();
    reader.close();
}

public static void run() {
    reader = new Scanner(System.in);
    invoker = MenuInvoker.getInstance();
    menu();
}

public static void menu() throws InputMismatchException {
    int input = 0;
    do {
        System.out.println();
        System.out.println("""
                Choose one of the following options:
                0: Exit
                1: Add seller
                2: Add buyer
                3: Add product to seller
                4: Add product to buyer
                5: Order payment for buyer
                6: Show details of all buyers
                7: Show details of all sellers
                8: Show every product of a specific category
                9: Create new cart for buyer from previous cart
                99: Print buyers' names by original order
                100: Print buyers' names by original order with no duplicates with number of occurrences
                101: Print number of occurrences of a specific name in the buyers array
                102: Print the buyer's names with doubles in reverse
                103: Print the buyer's names sorted by name length
                104: Save the current buyer array list
                105: Restore buyer array list from last save
                """);
        boolean acceptableAnswer = false;
        while (!acceptableAnswer) {
            try {
                input = reader.nextInt();
                acceptableAnswer = true;
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer, please enter an answer with the correct type:");
                reader.nextLine();
            }
        }
        invoker.executeCommand(input);
    } while (input != 0);
}

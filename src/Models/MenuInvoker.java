package Models;

import Commands.*;

import java.sql.SQLException;
import java.util.HashMap;

public class MenuInvoker {
    private final HashMap<Integer, Command> commands = new HashMap<>();
    private static MarketFacade market;
    private static MenuInvoker instance;

    private MenuInvoker() throws SQLException {
        market = MarketFacade.getInstance();
        commands.put(-1, new generateExamplesCommand(market));
        commands.put(0, new exitCommand());
        commands.put(1, new addSellerCommand(market));
        commands.put(2, new addBuyerCommand(market));
        commands.put(3, new addProductToSellerCommand(market));
        commands.put(4, new addProductBuyerCommand(market));
        commands.put(5, new orderPaymentCommand(market));
        commands.put(6, new printBuyersCommand(market));
        commands.put(7, new printSellersCommand(market));
        commands.put(8, new printByCategoryCommand(market));
        commands.put(9, new createCartFromHistoryCommand(market));
        commands.put(99, new printNamesCommand(market));
        commands.put(100, new printNamesNoDoublesCommand(market));
        commands.put(101, new printNameOccurrencesCommand(market));
        commands.put(102, new printReversedDoublesCommand(market));
        commands.put(103, new printNamesSortedByLengthCommand(market));
        commands.put(104, new saveArrayListCommand(market));
        commands.put(105, new restoreArrayListCommand(market));
    }

    public void executeCommand(int option) throws SQLException {
        Command command = commands.get(option);
        if (command != null) {
            command.execute();
        } else {
            System.out.println("Invalid option, please try again");
        }
    }

    public static MenuInvoker getInstance() throws SQLException {
        if (instance == null)
            instance = new MenuInvoker();
        return instance;
    }

}

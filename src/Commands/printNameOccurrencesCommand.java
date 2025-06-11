package Commands;

import Models.MarketFacade;

import java.util.Scanner;

public class printNameOccurrencesCommand implements Command {
    private MarketFacade market;

    public printNameOccurrencesCommand(MarketFacade market) {
        this.market = market;
    }

    @Override
    public void execute() {
        Scanner reader = new Scanner(System.in);
        market.printNameOccurrences(reader);
        reader.close();
    }
}

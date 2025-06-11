package Commands;

import Models.MarketFacade;

public class printNamesSortedByLengthCommand implements Command {
    private MarketFacade market;

    public printNamesSortedByLengthCommand(MarketFacade market) {
        this.market = market;
    }

    @Override
    public void execute() {
        market.printNamesSortedByLength();
    }
}

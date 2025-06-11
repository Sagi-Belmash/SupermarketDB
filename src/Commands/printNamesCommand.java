package Commands;

import Models.MarketFacade;

public class printNamesCommand implements Command {
    private MarketFacade market;

    public printNamesCommand(MarketFacade market) {
        this.market = market;
    }

    @Override
    public void execute() {
        market.printNames();
    }
}

package Commands;

import Models.MarketFacade;

public class printNamesNoDoublesCommand implements Command {
    private MarketFacade market;

    public printNamesNoDoublesCommand(MarketFacade market) {
        this.market = market;
    }

    @Override
    public void execute() {
        market.printNamesNoDoubles();
    }
}

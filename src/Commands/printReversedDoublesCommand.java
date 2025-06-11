package Commands;

import Models.MarketFacade;

public class printReversedDoublesCommand implements Command {
    private MarketFacade market;

    public printReversedDoublesCommand(MarketFacade market) {
        this.market = market;
    }

    @Override
    public void execute() {
        market.printReversedDoubles();
    }
}

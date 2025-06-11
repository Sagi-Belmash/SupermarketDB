package Commands;

import Models.MarketFacade;

public class printBuyersCommand implements Command {
    private MarketFacade market;

    public printBuyersCommand(MarketFacade market) {
        this.market = market;
    }

    @Override
    public void execute() {
        market.printBuyers();
    }
}

package Commands;

import Models.MarketFacade;

public class printSellersCommand implements Command {
    private MarketFacade market;

    public printSellersCommand(MarketFacade market) {
        this.market = market;
    }

    @Override
    public void execute() {
        market.printSellers();
    }
}

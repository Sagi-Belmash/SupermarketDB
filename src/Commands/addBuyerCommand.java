package Commands;

import Models.MarketFacade;

public class addBuyerCommand implements Command {
    private MarketFacade market;

    public addBuyerCommand(MarketFacade market) {
        this.market = market;
    }

    @Override
    public void execute() {
        market.addBuyer();
    }
}

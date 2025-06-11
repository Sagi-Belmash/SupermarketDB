package Commands;

import Models.MarketFacade;

public class addProductBuyerCommand implements Command {
    private MarketFacade market;

    public addProductBuyerCommand(MarketFacade market){
        this.market = market;
    }

    @Override
    public void execute() {
        market.addProductBuyer();
    }
}

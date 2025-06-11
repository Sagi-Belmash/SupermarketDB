package Commands;

import Models.MarketFacade;

public class addProductToSellerCommand implements Command {
    private MarketFacade market;

    public addProductToSellerCommand(MarketFacade market){
        this.market = market;
    }

    @Override
    public void execute() {
        market.addProductToSeller();
    }
}

package Commands;

import Models.MarketFacade;

public class addSellerCommand implements Command {
    private MarketFacade market;

    public addSellerCommand(MarketFacade market){
        this.market = market;
    }

    @Override
    public void execute() {
        market.addSeller();
    }
}

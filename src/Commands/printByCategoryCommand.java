package Commands;

import Models.MarketFacade;

public class printByCategoryCommand implements Command {
    private MarketFacade market;

    public printByCategoryCommand(MarketFacade market) {
        this.market = market;
    }

    @Override
    public void execute() {
        market.printByCategory();
    }
}

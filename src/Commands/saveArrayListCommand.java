package Commands;

import Models.MarketFacade;

public class saveArrayListCommand implements Command {
    private MarketFacade market;

    public saveArrayListCommand(MarketFacade market) {
        this.market = market;
    }

    @Override
    public void execute() {
        market.saveArrayList();
    }
}


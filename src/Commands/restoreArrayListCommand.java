package Commands;

import Models.MarketFacade;

public class restoreArrayListCommand implements Command {

    private final MarketFacade market;

    public restoreArrayListCommand(MarketFacade market) {
        this.market = market;
    }

    @Override
    public void execute() {
        market.restoreArrayList();
    }

}

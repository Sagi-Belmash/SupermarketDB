package Commands;

import Models.MarketFacade;

public class createCartFromHistoryCommand implements Command {
    private MarketFacade market;

    public createCartFromHistoryCommand(MarketFacade market) {
        this.market = market;
    }

    @Override
    public void execute() {
        market.createCartFromHistory();
    }
}

package Commands;

import Models.MarketFacade;

import java.sql.SQLException;

public class addBuyerCommand implements Command {
    private MarketFacade market;

    public addBuyerCommand(MarketFacade market) {
        this.market = market;
    }

    @Override
    public void execute() throws SQLException {
        market.addBuyer();
    }
}

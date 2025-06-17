package Commands;

import Models.MarketFacade;

import java.sql.SQLException;

public class addSellerCommand implements Command {
    private MarketFacade market;

    public addSellerCommand(MarketFacade market){
        this.market = market;
    }

    @Override
    public void execute() throws SQLException {
        market.addSeller();
    }
}

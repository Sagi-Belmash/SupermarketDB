package Commands;

import Models.MarketFacade;

import java.sql.SQLException;

public class generateExamplesCommand implements Command {
    private MarketFacade market;

    public generateExamplesCommand(MarketFacade market) {
        this.market = market;
    }

    @Override
    public void execute() throws SQLException {
        market.generateExamples();
    }
}

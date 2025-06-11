package Commands;

import Models.MarketFacade;

public class generateExamplesCommand implements Command {
    private MarketFacade market;

    public generateExamplesCommand(MarketFacade market) {
        this.market = market;
    }

    @Override
    public void execute() {
        market.generateExamples();
    }
}

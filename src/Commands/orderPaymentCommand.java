package Commands;

import Models.MarketFacade;

public class orderPaymentCommand implements Command {
    private MarketFacade market;

    public orderPaymentCommand(MarketFacade market) {
        this.market = market;
    }

    @Override
    public void execute() {
        market.orderPayment();
    }
}

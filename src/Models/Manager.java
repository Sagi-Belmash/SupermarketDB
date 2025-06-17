package Models;

import java.sql.SQLException;

public class Manager{
    private BuyerManager buyerManger;
    private SellerManager sellerManger;

    public Manager() throws SQLException {
        buyerManger = new BuyerManager();
        sellerManger = new SellerManager();
    }


    public BuyerManager getBuyerManager() {
        return buyerManger;
    }
    public SellerManager getSellerManager() {
        return sellerManger;
    }
}

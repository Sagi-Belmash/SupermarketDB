package Models;

public class Manager{
    private BuyerManager buyerManger;
    private SellerManager sellerManger;

    public Manager() {
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

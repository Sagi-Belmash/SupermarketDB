package Models;

public class ShoppingCartFactory {
    public static ShoppingCart createShoppingCart(Buyer buyer){
        return new ShoppingCart(buyer);
    }
    public static ShoppingCart createShoppingCart(ShoppingCart otherShoppingCart){
        return new ShoppingCart(otherShoppingCart);
    }
}

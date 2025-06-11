package Models;

public class SellerFactory {
    public static Seller createSeller(String name, String password) {
        return new Seller(name, password);
    }

    public static Seller createSeller() {
        String name = UserInput.getSellerNameFromUser();
        if (name.isEmpty()) return null;
        String password = UserInput.getPasswordFromUser();
        return createSeller(name, password);
    }

}

package Models;

public class BuyerFactory {
    public static Buyer createBuyer(String name, String password, Address address) {
        return new Buyer(name, password, address);
    }

    public static Buyer createBuyer() {
        String name = UserInput.getBuyerNameFromUser();
        if (name.isEmpty()) return null;
        String password = UserInput.getPasswordFromUser();
        return createBuyer(name, password, UserInput.getBuyerAddressFromUser());
    }
}

package Models;

import java.sql.SQLException;

public class SellerFactory {
    public static Seller createSeller(String name, String password) throws SQLException {
        return new Seller(name, password);
    }

    public static Seller createSeller() throws SQLException {
        String name = UserInput.getSellerNameFromUser();
        if (name.isEmpty()) return null;
        String password = UserInput.getPasswordFromUser();
        return createSeller(name, password);
    }

}

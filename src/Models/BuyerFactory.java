package Models;

import java.sql.*;

public class BuyerFactory {
    static ConnectionUtil db =new ConnectionUtil();
    static Connection conn= db.connect_to_db("Supermarket","postgres","Matan25");

    public static Buyer createBuyer(String name, String password, Address address) throws SQLException {
        return new Buyer(name, password, address, address.getID());
    }

    public static Buyer createBuyer() throws SQLException {
        String name = UserInput.getBuyerNameFromUser();
        if (name.isEmpty()) return null;
        String password = UserInput.getPasswordFromUser();
        return createBuyer(name, password, UserInput.getBuyerAddressFromUser());
    }

    public static Buyer getBuyerFromDB(String buyerName) throws SQLException {
        ResultSet resBuyer;
        String name = buyerName;
        String password = "";
        int addrID = 0;
        try {
            String query = "SELECT * FROM buyers WHERE name=?;";
            PreparedStatement psGetBuyer = conn.prepareStatement(query);
            psGetBuyer.setString(1, name);
            resBuyer = psGetBuyer.executeQuery();
            resBuyer.next();
            password = resBuyer.getString("password");
            addrID = resBuyer.getInt("addrID");
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Buyer(name,password,AddressFactory.getAddress(addrID),addrID);
    }
}

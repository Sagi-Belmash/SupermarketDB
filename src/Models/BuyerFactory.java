package Models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BuyerFactory {
    static ConnectionUtil db =new ConnectionUtil();
    static Connection conn= db.connect_to_db("Supermarket","postgres","070103Sb");
    private static int addressIDCounter;

    public static Buyer createBuyer(String name, String password, Address address) throws SQLException {
        try {
            String query = "SELECT id FROM public.addresses HAVING id=max(id);";
            Statement getMaxAddrID = conn.createStatement();
            addressIDCounter=getMaxAddrID.executeQuery(query).getInt("id");
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Buyer(name, password, address, addressIDCounter);
    }

    public static Buyer createBuyer() throws SQLException {
        String name = UserInput.getBuyerNameFromUser();
        if (name.isEmpty()) return null;
        String password = UserInput.getPasswordFromUser();
        return createBuyer(name, password, UserInput.getBuyerAddressFromUser());
    }

    public static Buyer getBuyerFromDB(int buyerID) throws SQLException {
        ResultSet resBuyer;
        String name = "";
        String password = "";
        int addrID = 0;
        try {
            String query = STR."SELECT * FROM buyers WHERE id=\"\{buyerID}\";";
            Statement getBuyer = conn.createStatement();
            resBuyer = getBuyer.executeQuery(query);
            resBuyer.next();
            name = resBuyer.getString("name");
            password = resBuyer.getString("password");
            addrID = resBuyer.getInt("addrID");
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Buyer(name,password,AddressFactory.getAddress(addrID),addrID);
    }
}

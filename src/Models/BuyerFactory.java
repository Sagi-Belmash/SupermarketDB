package Models;

import java.sql.Connection;
import java.sql.Statement;

public class BuyerFactory {
    static ConnectionUtil db =new ConnectionUtil();
    static Connection conn= db.connect_to_db("postgres","postgres","Matan25");
    private static int addressIDCounter;

    public static Buyer createBuyer(String name, String password, Address address) {
        try {
            String query = "SELECT id FROM addresses HAVING id=max(id);";
            Statement getMaxAddrID = conn.createStatement();
            addressIDCounter=getMaxAddrID.executeQuery(query).getInt("id");
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Buyer(name, password, address, addressIDCounter);
    }

    public static Buyer createBuyer() {
        String name = UserInput.getBuyerNameFromUser();
        if (name.isEmpty()) return null;
        String password = UserInput.getPasswordFromUser();
        return createBuyer(name, password, UserInput.getBuyerAddressFromUser());
    }

    public static Buyer getBuyerFromDB(int buyerID){
        String resBuyerName="";
        String resBuyerPass="";
        int resBuyerAddrID=0;
        try {
            String query = STR."SELECT * FROM buyers WHERE id=\"\{buyerID}\";";
            Statement getBuyer = conn.createStatement();
            resBuyerName= getBuyer.executeQuery(query).getString("name");
            resBuyerPass= getBuyer.executeQuery(query).getString("password");
            resBuyerAddrID= getBuyer.executeQuery(query).getInt("addrID");
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Buyer(resBuyerName,resBuyerPass,AddressFactory.getAddress(resBuyerAddrID),resBuyerAddrID);
    }
}

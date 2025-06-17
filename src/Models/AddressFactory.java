package Models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddressFactory {
    static ConnectionUtil db =new ConnectionUtil();
    static Connection conn= db.connect_to_db("Supermarket","postgres","070103Sb");
    private static int addressIDCounter;

    public static Address createAddress(String street, int building, String city, String country) {

        try {
            String query = "SELECT id FROM public.addresses HAVING id=MAX(public.addresses.id);";
            Statement getMaxAddrID = conn.createStatement();
            addressIDCounter=getMaxAddrID.executeQuery(query).getInt("id");
        } catch (Exception e) {
            System.out.println(e);
        }
        Statement insertNewAddr;
        try {
            String query = STR."INSERT INTO public.addresses(id, country, city, street, building) VALUES (\{addressIDCounter},\{country},\{city},\{street},\{building});";
            insertNewAddr = conn.createStatement();
            insertNewAddr.executeUpdate(query);
            System.out.println("row inserted");

        } catch (Exception e) {
            System.out.println(e);
        }
        return new Address(country, city, street, building);

    }
    public static Address createAddress(Address address) { return new Address(address); }

    public static Address getAddress(int addressID) {
        ResultSet resAddr;
        String country = "";
        String city = "";
        String street = "";
        int building = 0;
        try {
            String query = STR."SELECT id FROM addresses WHERE id=\{addressID};";
            Statement getAddrFromDB = conn.createStatement();
            resAddr = getAddrFromDB.executeQuery(query);
            street = resAddr.getString("street");
            country = resAddr.getString("country");
            city = resAddr.getString("city");
            building = resAddr.getInt("building");
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Address(country, city, street,building);
    }

}

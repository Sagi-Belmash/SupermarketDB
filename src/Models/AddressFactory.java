package Models;

import java.sql.Connection;
import java.sql.Statement;

public class AddressFactory {
    static ConnectionUtil db =new ConnectionUtil();
    static Connection conn= db.connect_to_db("postgres","postgres","Matan25");
    private static int addressIDCounter;

    public static Address createAddress(String street, int building, String city, String country) {

        try {
            String query = "SELECT id FROM addresses HAVING id=MAX(public.addresses.id);";
            Statement getMaxAddrID = conn.createStatement();
            addressIDCounter=getMaxAddrID.executeQuery(query).getInt("id");
        } catch (Exception e) {
            System.out.println(e);
        }
        Statement insertNewAddr;
        try {
            String query = STR."INSERT INTO addresses(id, country, city, street, building) VALUES (\{addressIDCounter},\{country},\{city},\{street},\{building});";
            insertNewAddr = conn.createStatement();
            insertNewAddr.executeUpdate(query);
            System.out.println("row inserted");

        } catch (Exception e) {
            System.out.println(e);
        }
        return new Address(street,building,city,country);

    }
    public static Address createAddress(Address address) { return new Address(address); }

    public static Address getAddress(int addressID) {
        String resAddrStreet="";
        String resAddrCity="";
        String resAddrCountry="";
        int resAddrBuilding=0;
        try {
            String query = STR."SELECT id FROM addresses WHERE id=\{addressID};";
            Statement getAddrFromDB = conn.createStatement();
            resAddrStreet=getAddrFromDB.executeQuery(query).getString("street");
            resAddrCountry=getAddrFromDB.executeQuery(query).getString("country");
            resAddrCity=getAddrFromDB.executeQuery(query).getString("city");
            resAddrBuilding=getAddrFromDB.executeQuery(query).getInt("building");
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Address(resAddrStreet,resAddrBuilding,resAddrCity,resAddrCountry);
    }

}

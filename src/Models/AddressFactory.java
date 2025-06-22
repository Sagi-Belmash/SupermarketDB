package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddressFactory {
    static ConnectionUtil db =new ConnectionUtil();
    static Connection conn= db.connect_to_db("Supermarket","postgres","Matan25");
    private static int addressIDCounter;

    public static Address createAddress(String street, int building, String city, String country) {
        try {
            String query = "SELECT * FROM public.addresses WHERE country=? AND street=? AND building=? AND city=?;";
            PreparedStatement psExistingAddress = conn.prepareStatement(query);
            psExistingAddress.setString(1, country);
            psExistingAddress.setString(2, street);
            psExistingAddress.setInt(3, building);
            psExistingAddress.setString(4, city);
            ResultSet rs = psExistingAddress.executeQuery();
            if(!rs.next()) {
                query = "INSERT INTO public.addresses(country, city, street, building) VALUES (?,?,?,?);";
                PreparedStatement psInsertNewAddr = conn.prepareStatement(query);
                psInsertNewAddr.setString(1, country);
                psInsertNewAddr.setString(2, city);
                psInsertNewAddr.setString(3, street);
                psInsertNewAddr.setInt(4, building);
                psInsertNewAddr.executeUpdate();
                System.out.println("added new Address");
                Statement getNewID=conn.createStatement();
                query="SELECT id FROM public.addresses ORDER BY id DESC LIMIT 1;";
                ResultSet rsID=getNewID.executeQuery(query);
                rsID.next();
                addressIDCounter=rsID.getInt(1);
            }else
                addressIDCounter=rs.getInt("id");
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Address(country, city, street, building,addressIDCounter);

    }
    public static Address createAddress(Address address) { return new Address(address); }

    public static Address getAddress(int addressID) {
        ResultSet resAddr;
        String country = "";
        String city = "";
        String street = "";
        int building = 0;
        int id = 0;
        try {
            String query = "SELECT id FROM addresses WHERE id=?;";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, addressID);
            resAddr = ps.executeQuery();
            resAddr.next();
            street = resAddr.getString("street");
            country = resAddr.getString("country");
            city = resAddr.getString("city");
            building = resAddr.getInt("building");
            id = resAddr.getInt("id");
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Address(country, city, street,building,id);
    }

}

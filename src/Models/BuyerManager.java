package Models;

import java.sql.*;
import java.util.*;

public class BuyerManager {
    private Buyer[] buyers;
    private int numOfBuyers = 0;
    ConnectionUtil db = new ConnectionUtil();
    Connection conn = db.connect_to_db("Supermarket","postgres","Matan25");

    public BuyerManager() throws SQLException {
        buyers = new Buyer[0];
        Statement stBuyers = conn.createStatement();
        ResultSet rsBuyers = stBuyers.executeQuery("SELECT * FROM public.buyers");

        // Print rows
        String name;
        String password;
        int addressID;
        String country;
        String city;
        String street;
        int building;
        while (rsBuyers.next()) {
            name = rsBuyers.getString("name");
            password = rsBuyers.getString("password");
            addressID = rsBuyers.getInt("addrID");

            String query ="SELECT * FROM public.addresses WHERE id = ?;";
            PreparedStatement psAddressID = conn.prepareStatement(query);
            psAddressID.setInt(1, addressID);
            ResultSet rsAddressID = psAddressID.executeQuery();
            rsAddressID.next();
            country = rsAddressID.getString("country");
            city = rsAddressID.getString("city");
            street = rsAddressID.getString("street");
            building = rsAddressID.getInt("building");
            expandBuyers();
            buyers[numOfBuyers++] = new Buyer(name, password, new Address(country, city, street, building,addressID), addressID);
            System.out.println();
        };
    }

    public boolean areThereBuyers() {
        return numOfBuyers > 0;
    }

    public Buyer getBuyer(String name) {
        for (int i = 0; i < numOfBuyers; i++) {
            if (buyers[i].getName().equals(name)) {
                return buyers[i];
            }
        }
        return null;
    }

    public void printBuyers() {
        for (int i = 0; i < numOfBuyers; i++) {
            System.out.println(buyers[i] + "\n");
        }
    }


    public boolean buyerExists(String name) {
        try{
            String query = "SELECT * FROM public.buyers WHERE name = ?;";
            PreparedStatement psExistingBuyer = conn.prepareStatement(query);
            psExistingBuyer.setString(1, name);
            ResultSet rsExistingBuyer = psExistingBuyer.executeQuery();
            if (rsExistingBuyer.next()) {
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }

    public void addBuyer(Buyer buyer) {
        expandBuyers();
        buyers[numOfBuyers++] = buyer;
        try {
            String query = "INSERT INTO buyers(name, password, addrID) VALUES (?,?,?);";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, buyer.getName());
            ps.setString(2, buyer.getPassword());
            ps.setInt(3, buyer.getAddressID());
            ps.executeUpdate();
            System.out.println("Added new buyer");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void expandBuyers() {
        if (buyers.length == 0) {
            buyers = new Buyer[1];
        } else if (buyers.length == numOfBuyers) {
            buyers = Arrays.copyOf(buyers, buyers.length * 2);
        }
    }

    public TreeSet<Buyer> getBuyerTreeSet() {
        TreeSet<Buyer> buyersTree = new TreeSet<>((b1, b2) -> {
            int x = b1.getName().length() - b2.getName().length();
            if (b1.getName().equalsIgnoreCase(b2.getName())) return 0;
            if (x == 0) return 1;
            return x;
        });
        for (Buyer b : buyers) {
            if (b != null) {

                buyersTree.add(b);
            }
        }
        return buyersTree;
    }

    public ArrayList<String> getArrayNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Buyer b : buyers) {
            if (b != null) {
                names.add(b.getName());
            }
        }
        return names;
    }

    public LinkedHashMap<String, Integer> getLinkedHashmapNames() {
        LinkedHashMap<String, Integer> names = new LinkedHashMap<>();
        for (Buyer b : buyers) {
            if (b != null) {
                String name = b.getName().toLowerCase();
                if (names.containsKey(name)) {
                    names.put(name, names.get(name) + 1);
                } else
                    names.put(name, 1);
            }
        }
        return names;
    }

    public ArrayList<String> createNameArrayList() {
        HashMap<String, Integer> names = getLinkedHashmapNames(); // The names we saved without duplicates
        ArrayList<String> doublesList = new ArrayList<>();
        ListIterator<String> iterator = doublesList.listIterator();
        for (String key : names.keySet()) {
            iterator.add(key);
            iterator.add(key);
        }
        return doublesList;
    }

    public ArrayListMemento createNameArrayListMemento() {
        return new ArrayListMemento(createNameArrayList());
    }
    public ArrayList<String> restoreNameArrayListMemento(ArrayListMemento memento){
        return memento.getList();
    }

}
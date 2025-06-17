package Models;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;

public class Buyer implements Comparable<Buyer> {
    private final String name;
    private final String password;
    private final Address address;
    private final int addressID;
    private ShoppingCart shoppingCart;
    private ShoppingCart[] orders;
    private int numOfOrders;
    private static ConnectionUtil db =new ConnectionUtil();
    private static Connection conn= db.connect_to_db("postgres","postgres","Matan25");


    public Buyer(String name, String password, Address address, int addressID) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.addressID = addressID;
        this.shoppingCart = ShoppingCartFactory.createNewShoppingCart(this);
        this.orders = new ShoppingCart[0];
        this.numOfOrders = 0;
    }

    public int getAddressID() {
        return addressID;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return AddressFactory.createAddress(address);
    }

    public ShoppingCart getShoppingCart() {
        return ShoppingCartFactory.createShoppingCart(shoppingCart);
    }

    public void addItemToCart(Product product, int... quantity) {
        shoppingCart.addProduct(product);
        int buyerID=0;
        Statement getBuyerID;
        try {
            String query = "SELECT * FROM buyers WHERE buyers.name = '"+this.name+"'";
            getBuyerID = conn.createStatement();
            buyerID=getBuyerID.executeQuery(query).getInt("id");
        } catch (Exception e) {
            System.out.println(e);
        }
        int cartID=0;
        Statement getCartID;
        try {
            String query = "SELECT * FROM carts WHERE buyerid = '"+buyerID+"' HAVING id=max(id)";
            getCartID = conn.createStatement();
            cartID=getCartID.executeQuery(query).getInt("id");
        } catch (Exception e) {
            System.out.println(e);
        }
        int productID=0;
        Statement getProdID;
        try {
            String query = "SELECT * FROM sellers WHERE products.name = '"+ product.getName() +"'";
            getProdID = conn.createStatement();
            productID=getProdID.executeQuery(query).getInt("id");
        } catch (Exception e) {
            System.out.println(e);
        }
        Statement addProductToCart;
        try {
            String query;
            if (quantity.length == 0) {
                query = STR."INSERT INTO cartsproducts(CID, PID, amount) VALUES (\{cartID},\{productID},1);";
            }else
                query = STR."INSERT INTO cartsproducts(CID, PID, amount) VALUES (\{cartID},\{productID},\{quantity});";
            addProductToCart = conn.createStatement();
            addProductToCart.executeUpdate(query);
            System.out.println("row inserted");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void purchase() {
        try {
            if (shoppingCart.getProducts().length == 0) {
                throw new EmptyCartException();
            }
            System.out.println(STR."Total price: \{shoppingCart.getTotal()}ILS");
            shoppingCart.setDate();
            expandList();
            orders[numOfOrders++] = shoppingCart;
            shoppingCart = ShoppingCartFactory.createNewShoppingCart(this);
        } catch (EmptyCartException e) {
            System.out.println(e.getMessage());
        }
    }

    public void expandList() {
        if (orders.length == 0) {
            orders = new ShoppingCart[1];
        } else if (orders.length == numOfOrders) {
            orders = Arrays.copyOf(orders, orders.length * 2);
        }
    }

    public int printOrderHistory() {
        for (int i = 0; i < numOfOrders; i++) {
            System.out.println((i + 1) + ":\n" + orders[i]);
            System.out.println();
        }
        return numOfOrders;
    }

    public ShoppingCart getPrevOrder(int orderNum) {
        return ShoppingCartFactory.getPastCart(orderNum);
    }

    public void setCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public int compareTo(Buyer o) {
        return name.compareTo(o.name);
    }

    public boolean hasPrevOrders() {
        return numOfOrders > 0;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n")
                .append("Models.Address: ").append(address).append("\n")
                .append("Current Shopping Cart:\n").append(shoppingCart).append("\n")
                .append("Order History:");

        for (int i = 1; i < numOfOrders + 1; i++) {
            sb.append("\nOrder #").append(i).append(":\n").append(orders[i - 1].toString()).append("\n");
        }

        return sb.toString();
    }


    public String getPassword() {
        return password;
    }
}




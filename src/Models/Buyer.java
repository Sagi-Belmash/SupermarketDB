package Models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;

public class Buyer implements Comparable<Buyer> {
    private final String name;
    private final String password;
    private final Address address;
    private final int addressID;
    private ShoppingCart shoppingCart;
    private ShoppingCart[] orders;
    private int numOfOrders;
    private static ConnectionUtil db =new ConnectionUtil();
    private static Connection conn= db.connect_to_db("Supermarkte","postgres","070103Sb");


    public Buyer(String name, String password, Address address, int addressID) throws SQLException {
        this.name = name;
        this.password = password;
        this.address = address;
        this.addressID = addressID;
        this.shoppingCart = ShoppingCartFactory.createNewShoppingCart(this);

        orders = new ShoppingCart[0];
        numOfOrders = 0;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM public.buyers WHERE name = '" + name + "'");
        rs.next();
        rs = st.executeQuery("SELECT * FROM public.carts WHERE buyerID = " + rs.getInt("id"));
        Statement stProducts = conn.createStatement();
        float totalPrice;
        Date date;
        int id;
        Product[] products;
        int numOfProducts;
        while (rs.next()) {
            expandList();
            numOfProducts = 0;
            id = rs.getInt("id");
            ResultSet rsCountProducts = stProducts.executeQuery("SELECT COUNT(*) FROM public.cartsProducts WHERE CID = " + rs.getInt("id"));
            rsCountProducts.next();
            products = new Product[rsCountProducts.getInt(1)];
            ResultSet rsProducts = stProducts.executeQuery("SELECT * FROM public.cartsProducts WHERE CID = " + id);
            while (rsProducts.next()) {
                products[numOfProducts++] = ProductFactory.getProductById(rsProducts.getInt("PID"));
                System.out.println("Bye!!!");
            }
            totalPrice = rs.getFloat("totalPrice");
            date = rs.getDate("date");
            orders[numOfOrders++] = new ShoppingCart(this, products, date, totalPrice, id);
        }
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
            String query = "SELECT * FROM public.buyers WHERE public.buyers.name = '"+this.name+"'";
            getBuyerID = conn.createStatement();
            buyerID=getBuyerID.executeQuery(query).getInt("id");
        } catch (Exception e) {
            System.out.println(e);
        }
        int cartID=0;
        Statement getCartID;
        try {
            String query = "SELECT * FROM public.carts WHERE buyerid = '"+buyerID+"' HAVING id=max(id)";
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
                query = STR."INSERT INTO public.cartsProducts(CID, PID, amount) VALUES (\{cartID},\{productID},1);";
            }else
                query = STR."INSERT INTO public.cartsProducts(CID, PID, amount) VALUES (\{cartID},\{productID},\{quantity});";
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
        } catch (EmptyCartException | SQLException e) {
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
        return ShoppingCartFactory.getPastCart(orders[orderNum].getID());
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




package Models;

import java.sql.*;
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
    private static Connection conn= db.connect_to_db("Supermarket","postgres","Matan25");


    public Buyer(String name, String password, Address address, int addressID) throws SQLException {
        this.name = name;
        this.password = password;
        this.address = address;
        this.addressID = addressID;
        orders = new ShoppingCart[0];
        numOfOrders = 0;
        String query="SELECT * FROM public.buyers WHERE name = ?;";
        PreparedStatement psExistingBuyer = conn.prepareStatement(query);
        psExistingBuyer.setString(1, name);
        ResultSet rs = psExistingBuyer.executeQuery();
        if(rs.next()) {
            try{
                query="SELECT * FROM public.carts WHERE BName = ?;";
                PreparedStatement psGetBuyerCarts = conn.prepareStatement(query);
                psGetBuyerCarts.setString(1, name);
                ResultSet rsGetBuyerCarts = psGetBuyerCarts.executeQuery();
                float totalPrice;
                Date date;
                int cartID;
                Product[] products;
                int numOfProducts;
                while (rsGetBuyerCarts.next()) {
                    expandList();
                    numOfProducts = 0;
                    cartID = rs.getInt("id");
                    query="SELECT COUNT(*) FROM public.cartsProducts WHERE CID = ?";
                    PreparedStatement psCountBuyerCarts = conn.prepareStatement(query);
                    psCountBuyerCarts.setInt(1, cartID);
                    ResultSet rsCountBuyerCarts = psCountBuyerCarts.executeQuery();
                    products = new Product[rsCountBuyerCarts.getInt(1)];
                    query = "SELECT * FROM public.cartsProducts WHERE CID = ?;";
                    PreparedStatement psGetProductsInCarts = conn.prepareStatement(query);
                    psGetProductsInCarts.setInt(1, cartID);
                    ResultSet rsGetProductsInCarts = psGetProductsInCarts.executeQuery();
                    while (rsGetProductsInCarts.next()) {
                        products[numOfProducts++] = ProductFactory.getProductById(rsGetProductsInCarts.getInt("PID"));
                        System.out.println("added product: " + products[numOfProducts]);
                    }
                    totalPrice = rs.getFloat("totalPrice");
                    date = rs.getDate("date");
                    orders[numOfOrders++] = new ShoppingCart(this, products, date, totalPrice, cartID);
                }
            }
            catch(SQLException e) {
                System.out.println("this Buyer has an no past carts");
            }
        }
        else {
            System.out.println("Adding new Buyer");
            query = "INSERT INTO public.buyers(name,password,addrID) VALUES(?,?,?);";
            PreparedStatement psNewBuyer = conn.prepareStatement(query);
            psNewBuyer.setString(1, name);
            psNewBuyer.setString(2, password);
            psNewBuyer.setInt(3, addressID);
            psNewBuyer.executeUpdate();
            this.shoppingCart = ShoppingCartFactory.createNewShoppingCart(this);
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
        int cartID=0;
        int productID=0;
        try {
            shoppingCart=ShoppingCartFactory.createNewShoppingCart(this);
            String query = "SELECT id FROM public.carts WHERE BName = ?;";
            PreparedStatement psGetOrCreateCart = conn.prepareStatement(query);
            psGetOrCreateCart.setString(1,name);
            ResultSet resultSet = psGetOrCreateCart.executeQuery();
            if(resultSet.next()) {
                cartID = resultSet.getInt("id");
            } else{
                cartID=shoppingCart.getID();
            }
            shoppingCart.addProduct(product);

            query = "SELECT * FROM public.products WHERE name = ?;";
            PreparedStatement psGetProdID = conn.prepareStatement(query);
            psGetProdID.setString(1, product.getName());
            ResultSet rsGetProdID = psGetProdID.executeQuery();
            rsGetProdID.next();
            productID = rsGetProdID.getInt(1);

            query = "INSERT INTO public.cartsProducts(CID, PID, amount) VALUES (?,?,?);";
            PreparedStatement psPutProductInCart = conn.prepareStatement(query);
            psPutProductInCart.setInt(1, cartID);
            psPutProductInCart.setInt(2, productID);
            psPutProductInCart.setInt(3,1);
            psPutProductInCart.executeUpdate();
            System.out.println("Added item "+product.getName()+" to cart number "+cartID);

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




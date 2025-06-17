package Models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class Seller implements Comparable<Seller> {
    private final String name;
    private final String password;
    private Product[] products;
    int numOfProducts;
    ConnectionUtil db  =new ConnectionUtil();
    Connection conn = db.connect_to_db("Supermarket","postgres","070103Sb");


    public Seller(String name, String password) throws SQLException {
        this.name = name;
        this.password = password;
        products = new Product[0];
        numOfProducts = 0;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM public.sellers WHERE name = '" + name + "'");
        rs.next();
        rs = st.executeQuery("SELECT * FROM public.products WHERE sellerID = " + rs.getInt("id"));
        String prodName;
        float prodPrice;
        float prodPackagePrice;
        Category prodCategory;
        int prodSerialNum;

        while (rs.next()) {
            prodName = rs.getString("name");
            prodPrice = rs.getFloat("price");
            prodPackagePrice = rs.getFloat("packagePrice");
            prodCategory = Models.Category.valueOf(rs.getString("category"));
            prodSerialNum = rs.getInt("id");
            expandList();
            products[numOfProducts++] = new Product(prodName, prodPrice, prodCategory, prodPackagePrice, prodSerialNum);
        };
    }

    public String getName() {
        return name;
    }

    public Product[] getProducts() {
        return Arrays.copyOf(products, numOfProducts);
    }

    public Product getProductByName(String name) {
        for (int i = 0; i < numOfProducts; i++) {
            if (products[i].getName().equals(name)) {
                return ProductFactory.createProduct(products[i]);
            }
        }
        return null;
    }

    public void addProduct(Product product) {
        expandList();
        products[numOfProducts++] = product;
        int sellerID=0;
        Statement getSellerID;
        try {
            String query = "SELECT * FROM public.sellers WHERE public.sellers.name = '"+this.name+"'";
            getSellerID = conn.createStatement();
            sellerID=getSellerID.executeQuery(query).getInt("id");
        } catch (Exception e) {
            System.out.println(e);
        }
        Statement addProduct;
        try {
            String query = STR."INSERT INTO public.products(id, name, category, price, packageprice, sellerid) VALUES (\{products.length},\{product.getName()},\{product.getCategory()},\{product.getPrice()},\{product.getPackagePrice()},\{sellerID});";
            addProduct = conn.createStatement();
            addProduct.executeUpdate(query);
            System.out.println("row inserted");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void expandList() {
        if (products.length == 0) {
            products = new Product[1];
        } else if (products.length == numOfProducts) {
            products = Arrays.copyOf(products, products.length * 2);
        }
    }

    public void printProducts() {
        System.out.println("Products:");
        for (int i = 1; i < numOfProducts + 1; i++) {
            System.out.println(i + ") " + products[i - 1]);
        }
        System.out.println();
    }

    @Override
    public int compareTo(Seller o) {
        return -Integer.compare(numOfProducts, o.numOfProducts);
    }

    @Override
    public String toString(){
        return "Name: " + name;
    }

    public String getPassword() {
        return this.password;
    }
}

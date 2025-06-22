package Models;

import java.sql.*;
import java.util.Arrays;

public class Seller implements Comparable<Seller> {
    private final String name;
    private final String password;
    private Product[] products;
    int numOfProducts;
    ConnectionUtil db  =new ConnectionUtil();
    Connection conn = db.connect_to_db("Supermarket","postgres","Matan25");


    public Seller(String name, String password) throws SQLException {
        this.name = name;
        this.password = password;
        products = new Product[0];
        numOfProducts = 0;
        Statement retrieveSellerData = conn.createStatement();
        String query = "SELECT * FROM public.sellers WHERE name = ?;";
        PreparedStatement retrieveSeller = conn.prepareStatement(query);
        retrieveSeller.setString(1, name);
        ResultSet rs = retrieveSeller.executeQuery();
        if(rs.next()) {
            try{
                query="SELECT * FROM public.products WHERE SName = ?;";
                PreparedStatement retrieveProduct = conn.prepareStatement(query);
                String sellerName = rs.getString("name");
                retrieveProduct.setString(1, sellerName);
                ResultSet rsProduct = retrieveProduct.executeQuery();
                String prodName;
                float prodPrice;
                float prodPackagePrice;
                Category prodCategory;
                int prodSerialNum;

                while (rsProduct.next()) {
                    prodName = rsProduct.getString("name");
                    prodPrice = rsProduct.getFloat("price");
                    prodPackagePrice = rsProduct.getFloat("packagePrice");
                    prodCategory = Models.Category.valueOf(rsProduct.getString("category"));
                    prodSerialNum = rsProduct.getInt("id");
                    expandList();
                    products[numOfProducts++] = new Product(prodName, prodPrice, prodCategory, prodPackagePrice, prodSerialNum);
                }
            }
            catch(Exception e) {
                System.out.println("This seller has no products on display");
            }
        }
        else{
            System.out.println("Adding new Seller");
            Statement insertNewSeller = conn.createStatement();
            query="INSERT INTO public.sellers(name,password) VALUES(?,?);";
            PreparedStatement insertSeller = conn.prepareStatement(query);
            insertSeller.setString(1, name);
            insertSeller.setString(2, password);
            insertSeller.executeUpdate();
        }
    }

    public String getName() {
        return name;
    }

    public Product[] getProducts() {
        return Arrays.copyOf(products, numOfProducts);
    }

    public Product getProductByName(String name, String sellerName) {
        for (int i = 0; i < numOfProducts; i++) {
            if (products[i].getName().equals(name)) {
                return ProductFactory.getProductByName(products[i].getName(),sellerName);
            }
        }
        return null;
    }

    public void addProduct(Product product) {
        expandList();
        products[numOfProducts++] = product;
        System.out.println("Added new product: "+product.getName()+" to seller ");
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

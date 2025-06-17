package Models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductFactory {
    static ConnectionUtil db = new ConnectionUtil();
    static Connection conn = db.connect_to_db("Supermarket","postgres","070103Sb");

    public static Product createProduct(String name, float price, Category category, float packagePrice) {
        int prodSerial=0;
        try {
            String query = "SELECT id FROM public.products HAVING id=max(id);";
            Statement getMaxAddrID = conn.createStatement();
            ResultSet rs = getMaxAddrID.executeQuery(query);
            rs.next();
            prodSerial = rs.getInt("id") + 1;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Product(name, price, category, packagePrice, prodSerial);
    }
    public static Product createProduct(Product other) {
        return createProduct(other.getName(),other.getPrice(),other.getCategory(),other.getPackagePrice());
    }
    public static Product createProduct(String name){
        float price = UserInput.getProductPriceFromUser();
        Category category = UserInput.getCategoryFromUser();
        float packagePrice = UserInput.getPackagePriceFromUser();
        return createProduct(name, price, category, packagePrice);
    }
    public static Product getProductById(int productID) {
        Category category=Category.CHILD;
        int prodSerial=0;
        float price=0;
        float packagePrice=0;
        String name="";
        try {
            Statement getProductFromDB= conn.createStatement();
            String query = STR."SELECT * FROM public.products WHERE id = " + productID;
            ResultSet rs = getProductFromDB.executeQuery(query);
            rs.next();
            category = Models.Category.valueOf(rs.getString("category"));
            prodSerial = rs.getInt("id");
            price = rs.getFloat("price");
            packagePrice = rs.getFloat("packagePrice");
            name = rs.getString("name");
            System.out.println("hi!!!");
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Product(name, price, category, packagePrice, prodSerial);

    }
}

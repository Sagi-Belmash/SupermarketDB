package Models;

import java.sql.Connection;
import java.sql.Statement;

public class ProductFactory {
    static ConnectionUtil db =new ConnectionUtil();
    static Connection conn= db.connect_to_db("postgres","postgres","Matan25");

    public static Product createProduct(String name, float price, Category category, float packagePrice) {
        int prodSerial=0;
        try {
            String query = "SELECT id FROM products HAVING id=max(id);";
            Statement getMaxAddrID = conn.createStatement();
            prodSerial=getMaxAddrID.executeQuery(query).getInt("id");
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
        Category getProductCategory=Category.CHILD;
        int resProdSerial=0;
        float resProdPrice=0;
        float resProdPackagePrice=0;
        String resProdName="";
        try {
            Statement getProductFromDB= conn.createStatement();
            String query = STR."SELECT * FROM products WHERE id=\"\{productID}\";";
            getProductCategory=getProductFromDB.executeQuery(query).getObject("category", Category.class);
            resProdSerial= getProductFromDB.executeQuery(query).getInt("id");
            resProdPrice= getProductFromDB.executeQuery(query).getFloat("id");
            resProdPackagePrice= getProductFromDB.executeQuery(query).getFloat("id");
            resProdName= getProductFromDB.executeQuery(query).getString("name");
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Product(resProdName,resProdPrice,getProductCategory,resProdPackagePrice,resProdSerial);

    }
}

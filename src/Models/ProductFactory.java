package Models;

import java.sql.*;

public class ProductFactory {
    static ConnectionUtil db = new ConnectionUtil();
    static Connection conn = db.connect_to_db("Supermarket","postgres","Matan25");

    public static Product createProduct(String name, float price, Category category, float packagePrice, String sName) {
        int prodSerial=0;
        try {
            String query = "INSERT INTO public.products (name, category, price, packagePrice,SName) VALUES (?,?,?,?,?);";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setObject(2, category.name(), Types.OTHER);
            ps.setDouble(3, price);
            ps.setDouble(4, packagePrice);
            ps.setString(5, sName);
            ps.executeUpdate();
            query = "SELECT MAX(id) FROM public.products";
            Statement getNewProductID = conn.createStatement();
            ResultSet rs = getNewProductID.executeQuery(query);
            rs.next();
            prodSerial = rs.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Product(name, price, category, packagePrice, prodSerial);
    }
    public static Product createProduct(Product other, String sName) {
        return createProduct(other.getName(),other.getPrice(),other.getCategory(),other.getPackagePrice(),sName);
    }
    public static Product createProduct(String name,String sellerName){
        float price = UserInput.getProductPriceFromUser();
        Category category = UserInput.getCategoryFromUser();
        float packagePrice = UserInput.getPackagePriceFromUser();
        return createProduct(name, price, category, packagePrice,sellerName);
    }
    public static Product getProductByName(String name,String sellerName){
        try {
            String query = "SELECT id FROM public.products WHERE name = ? and SName = ?;";
            PreparedStatement psGetProduct= conn.prepareStatement(query);
            psGetProduct.setString(1, name);
            psGetProduct.setString(2, sellerName);
            ResultSet rsGetProduct= psGetProduct.executeQuery();
            rsGetProduct.next();
            return getProductById(rsGetProduct.getInt(1));
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    public static Product getProductById(int productID) {
        Category category=Category.CHILD;
        int prodSerial=0;
        float price=0;
        float packagePrice=0;
        String name="";
        try {
            String query = "SELECT * FROM public.products WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, productID);
            ResultSet rs = ps.executeQuery();
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

package Models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class ShoppingCartFactory {
    static ConnectionUtil db =new ConnectionUtil();
    static Connection conn= db.connect_to_db("postgres","postgres","Matan25");
    public static ShoppingCart createNewShoppingCart(Buyer buyer){
        int buyerID=0;
        Statement getBuyerID;
        try {
            String query = "SELECT * FROM buyers WHERE buyers.name = '"+buyer.getName()+"'";
            getBuyerID = conn.createStatement();
            buyerID=getBuyerID.executeQuery(query).getInt("id");
        } catch (Exception e) {
            System.out.println(e);
        }
        Statement insertNewCart;
        Date today = new Date();
        try {
            String query = STR."INSERT INTO carts(date,totalprice,buyerid) VALUES (\{today},0,\{buyerID});";
            insertNewCart = conn.createStatement();
            insertNewCart.executeUpdate(query);
            System.out.println("row inserted");

        } catch (Exception e) {
            System.out.println(e);
        }
        return new ShoppingCart(buyer);
    }
    public static ShoppingCart createExistingShoppingCart(Buyer buyer){
        return new ShoppingCart(buyer);
    }
    public static ShoppingCart createShoppingCart(ShoppingCart otherShoppingCart){
        return new ShoppingCart(otherShoppingCart);
    }

    public static ShoppingCart getPastCart(int orderNum){

        ShoppingCart pastCart=null;
        int cartBuyerID=0;
        Product p;
        try(Statement getPastCartFromDB = conn.createStatement()){
            String resCartBuyerIDQuery = STR."SELECT * FROM carts WHERE id=\{orderNum});";
            cartBuyerID=getPastCartFromDB.executeQuery(resCartBuyerIDQuery).getInt("buyerID");
            pastCart= createExistingShoppingCart(BuyerFactory.getBuyerFromDB(cartBuyerID));

            String resCartProductIDQuery = STR."SELECT * FROM products WHERE id IN (SELECT PID FROM cartsProducts WHERE CID='\{orderNum}')";
            ResultSet res =getPastCartFromDB.executeQuery(resCartProductIDQuery);
            while(res.next()){
                int productID=res.getInt("id");
                String productName=res.getString("name");
                Category productCategory=res.getObject(3,Category.class);
                float productPrice=res.getFloat("price");
                float productPackagePrice=res.getFloat("packagePrice");
                pastCart.addProduct(ProductFactory.createProduct(productName,productPrice,productCategory,productPackagePrice));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

        return pastCart;
    }
}

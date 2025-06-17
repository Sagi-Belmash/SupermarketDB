package Models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class ShoppingCartFactory {
    static ConnectionUtil db =new ConnectionUtil();
    static Connection conn= db.connect_to_db("Supermarket","postgres","070103Sb");
    public static ShoppingCart createNewShoppingCart(Buyer buyer) throws SQLException {
        int buyerID=0;
        Statement getBuyerID;
        try {
            String query = "SELECT * FROM public.buyers WHERE public.buyers.name = '"+buyer.getName()+"'";
            getBuyerID = conn.createStatement();
            ResultSet rs = getBuyerID.executeQuery(query);
            rs.next();
            buyerID=rs.getInt("id");
        } catch (Exception e) {
            System.out.println(e);
        }
        Statement insertNewCart;
        try {
            String query = STR."INSERT INTO public.carts(buyerid) VALUES (\{buyerID});";
            insertNewCart = conn.createStatement();
            insertNewCart.executeUpdate(query);
            System.out.println("row inserted");

        } catch (Exception e) {
            System.out.println(e);
        }
        Statement getCartID = conn.createStatement();
        ResultSet rs = getCartID.executeQuery("SELECT MAX(id) FROM public.carts WHERE buyerID = " + buyerID);
        rs.next();
        return new ShoppingCart(buyer, rs.getInt(1));
    }
    public static ShoppingCart createExistingShoppingCart(Buyer buyer, int cartID){
        return new ShoppingCart(buyer, cartID);
    }
    public static ShoppingCart createShoppingCart(ShoppingCart otherShoppingCart){
        return new ShoppingCart(otherShoppingCart);
    }

    public static ShoppingCart getPastCart(int cartID){

        ShoppingCart pastCart=null;
        int cartBuyerID=0;
        Product p;
        try(Statement getPastCartFromDB = conn.createStatement()){
            String resCartBuyerIDQuery = STR."SELECT * FROM public.carts WHERE id = " + cartID;
            ResultSet rs = getPastCartFromDB.executeQuery(resCartBuyerIDQuery);
            rs.next();
            cartBuyerID = rs.getInt("buyerID");
            pastCart = createExistingShoppingCart(BuyerFactory.getBuyerFromDB(cartBuyerID), cartID);

            String resCartProductIDQuery = STR."SELECT * FROM public.products WHERE id IN (SELECT PID FROM public.cartsProducts WHERE CID='\{cartID}')";
            ResultSet res = getPastCartFromDB.executeQuery(resCartProductIDQuery);
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

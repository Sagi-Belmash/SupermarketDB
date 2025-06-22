package Models;

import java.sql.*;

public class ShoppingCartFactory {
    static ConnectionUtil db =new ConnectionUtil();
    static Connection conn= db.connect_to_db("Supermarket","postgres","Matan25");
    public static ShoppingCart createNewShoppingCart(Buyer buyer) throws SQLException {
        try {
            String query = "INSERT INTO public.carts(BName) VALUES (?);";
            PreparedStatement psNewCart = conn.prepareStatement(query);
            psNewCart.setString(1, buyer.getName());
            psNewCart.executeUpdate();
            System.out.println("Added new cart to database");
        } catch (Exception e) {
            System.out.println(e);
        }
        String query="SELECT MAX(id) FROM public.carts WHERE BName = ?;";
        PreparedStatement psCartID = conn.prepareStatement(query);
        psCartID.setString(1, buyer.getName());
        ResultSet rs = psCartID.executeQuery();
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
        String cartBuyerName="";
        try{
            String resCartID = "SELECT * FROM carts WHERE id = ?;";
            PreparedStatement psGetCartID = conn.prepareStatement(resCartID);
            psGetCartID.setInt(1,cartID);
            ResultSet rsGetCartID = psGetCartID.executeQuery();
            rsGetCartID.next();
            cartBuyerName = rsGetCartID.getString("BName");
            pastCart = createExistingShoppingCart(BuyerFactory.getBuyerFromDB(cartBuyerName), cartID);

            String resProductIDInCart ="SELECT * FROM public.products WHERE id IN (SELECT PID FROM public.cartsProducts WHERE CID=?);";
            PreparedStatement psProductIDInCart = conn.prepareStatement(resProductIDInCart);
            psProductIDInCart.setInt(1,cartID);
            ResultSet res=psProductIDInCart.executeQuery();
            while(res.next()){
                String productName=res.getString("name");
                Category productCategory=res.getObject(3,Category.class);
                float productPrice=res.getFloat("price");
                float productPackagePrice=res.getFloat("packagePrice");
                String productSellerName=res.getString("SName");
                pastCart.addProduct(ProductFactory.createProduct(productName,productPrice,productCategory,productPackagePrice,productSellerName));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

        return pastCart;
    }
}

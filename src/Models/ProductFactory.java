package Models;

public class ProductFactory {
    public static Product createProduct(String name, float price, Category category, float packagePrice) {
        return new Product(name, price, category, packagePrice);
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
}

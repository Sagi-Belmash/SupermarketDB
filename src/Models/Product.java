package Models;

public class Product {
    private final String name;
    private final float price;
    private final int serialNum;

    private final Category category;
    private final float packagePrice;

    public Product(String name, float price, Category category, float packagePrice, int serialNum) {
        this.name = name;
        this.price = price;
        this.serialNum = serialNum;
        this.category = category;
        this.packagePrice = packagePrice;
    }
    public float getPackagePrice() { return packagePrice; }
    public String getName() {
        return name;
    }

    public float getPrice() {
        return price + packagePrice;
    }

    public int getSerialNum() {return serialNum;}

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name);
        sb.append(" (").append(category).append(") [")
                .append(serialNum).append("] ---> ").append(price).append(" ILS");
        if (packagePrice != 0) {
            sb.append(". This product's package cost ").append(packagePrice).append(" ILS. Total is ").append(price + packagePrice).append(" ILS");
        }
        return sb.toString();
    }
}

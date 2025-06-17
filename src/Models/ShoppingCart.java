package Models;

import java.util.Arrays;
import java.util.Date;

public class ShoppingCart {
    private Product[] products;
    private int numOfProducts;
    private Date date;
    private float totalPrice;
    private final Buyer buyer;
    private int id;

    public ShoppingCart(Buyer buyer, int id) {
        products = new Product[0];
        numOfProducts = 0;
        totalPrice = 0;
        this.buyer = buyer;
        this.id = id;
    }

    public ShoppingCart(Buyer buyer, Product[] products, Date date, float totalPrice, int id) {
        this.buyer = buyer;
        this.products = Arrays.copyOf(products, products.length);
        this.date = date;
        this.totalPrice = totalPrice;
        this.numOfProducts = products.length;
        this.id = id;
    }

    public ShoppingCart(ShoppingCart other) {
        products = other.products;
        numOfProducts = other.numOfProducts;
        totalPrice = other.totalPrice;
        buyer = other.buyer;
        this.id = other.id;
    }


    public void addProduct(Product product) {
        expandList();
        products[numOfProducts++] = product;
        totalPrice += product.getPrice();
    }

    public void expandList() {
        if (products.length == 0) {
            products = new Product[1];
        }
        else if (numOfProducts == products.length) {
            products = Arrays.copyOf(products, products.length * 2);
        }
    }

    public void setDate() {
        date = new Date();
    }

    public Product[] getProducts() {
        return Arrays.copyOf(products, numOfProducts);
    }

    public boolean isEmpty() { return numOfProducts == 0; }

    public float getTotal() {
        return totalPrice;
    }

    public int getID() { return id; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (date != null) {
            sb.append("Date: ").append(date).append("\n")
                    .append("Total price: ").append(totalPrice).append("ILS").append("\n");
        }
        for (int i = 1; i < numOfProducts + 1; i++) {
            sb.append(i).append(") ").append(products[i - 1]).append("\n");
        }
        return sb.toString();
    }
}

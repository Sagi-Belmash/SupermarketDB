package Models;

import java.util.Arrays;
import java.util.Date;

public class ShoppingCart {
    private Product[] products;


    private int numOfProducts;
    private Date date;
    private float totalPrice;
    private final Buyer buyer;

    public ShoppingCart(Buyer buyer) {
        products = new Product[0];
        numOfProducts = 0;
        totalPrice = 0;
        this.buyer = buyer;
    }

    public ShoppingCart(ShoppingCart other) {
        products = other.products;
        numOfProducts = other.numOfProducts;
        totalPrice = other.totalPrice;
        buyer = other.buyer;
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

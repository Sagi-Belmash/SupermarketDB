package Models;

import java.util.Arrays;

public class Seller implements Comparable<Seller> {
    private final String name;
    private final String password;
    private Product[] products;
    int numOfProducts;
    public Seller(String name, String password) {
        this.name = name;
        this.password = password;
        products = new Product[0];
        numOfProducts = 0;
    }

    public String getName() {
        return name;
    }

    public Product[] getProducts() {
        return Arrays.copyOf(products, numOfProducts);
    }

    public Product getProductByName(String name) {
        for (int i = 0; i < numOfProducts; i++) {
            if (products[i].getName().equals(name)) {
                return ProductFactory.createProduct(products[i]);
            }
        }
        return null;
    }

    public void addProduct(Product product) {
        expandList();
        products[numOfProducts++] = product;
    }

    private void expandList() {
        if (products.length == 0) {
            products = new Product[1];
        } else if (products.length == numOfProducts) {
            products = Arrays.copyOf(products, products.length * 2);
        }
    }

    public void printProducts() {
        System.out.println("Products:");
        for (int i = 1; i < numOfProducts + 1; i++) {
            System.out.println(i + ") " + products[i - 1]);
        }
        System.out.println();
    }

    @Override
    public int compareTo(Seller o) {
        return -Integer.compare(numOfProducts, o.numOfProducts);
    }

    @Override
    public String toString(){
        return "Name: " + name;
    }
}

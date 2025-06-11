package Models;

import java.util.Arrays;

public class SellerManager {
    private Seller[] sellers;
    private int numOfSellers;

    public SellerManager() {
        sellers = new Seller[0];
        numOfSellers = 0;
    }

    public void addSeller(Seller seller) {
        expandSellers();
        sellers[numOfSellers++] = seller;
    }

    public boolean sellerExists(String name) {
        for (int i = 0; i < numOfSellers; i++) {
            if (sellers[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean areThereSellers() {
        return numOfSellers > 0;
    }

    public void printSellers() {
        for (int i = 0; i < numOfSellers; i++) {
            System.out.println(sellers[i]);
            sellers[i].printProducts();
            System.out.println();
        }
    }

    public void expandSellers() {
        if (sellers.length == 0) {
            sellers = new Seller[1];
        } else if (sellers.length == numOfSellers) {
            sellers = Arrays.copyOf(sellers, sellers.length * 2);
        }
    }

    public boolean productExists(Seller seller, String productName) {
        for (Product p : seller.getProducts()) {
            if (p.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    public Seller getSeller(String name) {
        for (int i = 0; i < numOfSellers; i++) {
            if (sellers[i].getName().equals(name)) {
                return sellers[i];
            }
        }
        return null;
    }


    public Product[] getItemsByCategory(Category c) {
        Product[] products = new Product[0];
        int count = 0;
        for (int i = 0; i < numOfSellers; i++) {
            if (sellers[i] != null) {
                for (int j = 0; j < sellers[i].getProducts().length; j++) {
                    Product p = sellers[i].getProducts()[j];
                    if (c.equals(p.getCategory())) {
                        if (products.length == 0) {
                            products = new Product[1];
                        } else if (products.length == count) {
                            products = Arrays.copyOf(products, products.length * 2);
                        }
                        products[count++] = p;
                    }
                }
            }
        }
        return products;
    }
}

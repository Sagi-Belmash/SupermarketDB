package Models;

import java.util.Arrays;

public class Buyer implements Comparable<Buyer> {
    private final String name;
    private final String password;
    private final Address address;
    private ShoppingCart shoppingCart;
    private ShoppingCart[] orders;
    private int numOfOrders;

    public Buyer(String name, String password, Address address) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.shoppingCart = ShoppingCartFactory.createShoppingCart(this);
        this.orders = new ShoppingCart[0];
        this.numOfOrders = 0;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return AddressFactory.createAddress(address);
    }

    public ShoppingCart getShoppingCart() {
        return ShoppingCartFactory.createShoppingCart(shoppingCart);
    }

    public void addItemToCart(Product product) {
        shoppingCart.addProduct(product);
    }

    public void purchase() {
        try {
            if (shoppingCart.getProducts().length == 0) {
                throw new EmptyCartException();
            }
            System.out.println("Total price: " + shoppingCart.getTotal() + "ILS");
            shoppingCart.setDate();
            expandList();
            orders[numOfOrders++] = shoppingCart;
            shoppingCart = ShoppingCartFactory.createShoppingCart(this);
        } catch (EmptyCartException e) {
            System.out.println(e.getMessage());
        }
    }

    public void expandList() {
        if (orders.length == 0) {
            orders = new ShoppingCart[1];
        } else if (orders.length == numOfOrders) {
            orders = Arrays.copyOf(orders, orders.length * 2);
        }
    }

    public int printOrderHistory() {
        for (int i = 0; i < numOfOrders; i++) {
            System.out.println((i + 1) + ":\n" + orders[i]);
            System.out.println();
        }
        return numOfOrders;
    }

    public ShoppingCart getPrevOrder(int orderNum) {
        return ShoppingCartFactory.createShoppingCart(orders[orderNum]);
    }

    public void setCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public int compareTo(Buyer o) {
        return name.compareTo(o.name);
    }

    public boolean hasPrevOrders() {
        return numOfOrders > 0;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n")
                .append("Models.Address: ").append(address).append("\n")
                .append("Current Shopping Cart:\n").append(shoppingCart).append("\n")
                .append("Order History:");

        for (int i = 1; i < numOfOrders + 1; i++) {
            sb.append("\nOrder #").append(i).append(":\n").append(orders[i - 1].toString()).append("\n");
        }

        return sb.toString();
    }


}




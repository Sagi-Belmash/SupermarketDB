package Models;

public class EmptyCartException extends Exception{
    private static final String EMPTY_CART_EXCEPTION_MESSAGE="Unable to complete a purchase if the cart is empty";

    public EmptyCartException() {
        super(EMPTY_CART_EXCEPTION_MESSAGE);
    }
}

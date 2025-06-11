package Models;

public class AddressFactory {
    public static Address createAddress(String street, int building, String city, String country) {
        return new Address(street,building,city,country);
    }
    public static Address createAddress(Address address) {
        return new Address(address);
    }
}

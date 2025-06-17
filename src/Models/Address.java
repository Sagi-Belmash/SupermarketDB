package Models;

public class Address {

    private final String street;
    private final int building;
    private final String city;
    private final String country;

    public Address(String country, String city, String street, int building) {
        this.street = street;
        this.building = building;
        this.city = city;
        this.country = country;
    }

    public Address(Address other) {
        this.street = other.street;
        this.building = other.building;
        this.city = other.city;
        this.country = other.country;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(street);
        sb.append(" ").append(building).append(", ").append(city).append(", ").append(country);
        return sb.toString();
    }
}

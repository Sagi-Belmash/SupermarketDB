package Models;

public class Address {

    private final String street;
    private final int building;
    private final String city;
    private final String country;
    private final int ID;

    public Address(String country, String city, String street, int building, int ID) {
        this.street = street;
        this.building = building;
        this.city = city;
        this.country = country;
        this.ID=ID;
    }

    public Address(Address other) {
        this.street = other.street;
        this.building = other.building;
        this.city = other.city;
        this.country = other.country;
        this.ID = other.ID;
    }

    public int getID(){
        return ID;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(street);
        sb.append(" ").append(building).append(", ").append(city).append(", ").append(country);
        return sb.toString();
    }
}

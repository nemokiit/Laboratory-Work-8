// POJO class representing a cryptocurrency record
public class CryptoRecord {
    private String name;
    private double price;

    public CryptoRecord(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    // Overridden toString for easy file saving
    @Override
    public String toString() {
        return name + "," + price;
    }
}

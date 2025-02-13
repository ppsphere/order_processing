public class Product {
    
    private String code;
    private String name;
    private int price;
    private int totalCash;
    private int totalUnits;

    public Product(String code, String name, int price) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.totalCash = 0;
        this.totalUnits = 0;
    }

    public void addSales(int units) {
        this.totalUnits += units;
        this.totalCash += units * price;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getTotalUnitsSold() { return totalUnits; }
    public int getTotalRevenue() { return totalCash; }

    @Override
    public String toString() {
        return "Product: " + name + " (Code: " + code + ") - Price: " + price +
               ", Units Sold: " + totalUnits + ", Total Revenue: " + totalCash;
    }

}



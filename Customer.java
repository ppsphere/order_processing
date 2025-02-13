public class Customer {
    private String name;
    private int points;
    private int totalOrders;

    public Customer(String name) {
        this.name = name;
        this.points = 0;
        this.totalOrders = 0;
    }

    public void addPoints(double amountSpent) {
        this.points += (int) (amountSpent / 500);
    }

    public boolean canRedeemPoints() {
        return points >= 100;
    }

    public void usePoints() {
        if (points >= 100) {
            points -= 100;
        }
    }

    public void incrementTotalOrders() {
        totalOrders++;
    }

    public String getName() { return name; }
    public int getPoints() { return points; }
    public int getTotalOrders() { return totalOrders; }

    @Override
    public String toString() {
        return name + " - Points: " + points + ", Orders: " + totalOrders;
    }
}


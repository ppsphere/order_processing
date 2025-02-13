public class Order {
    private int id;
    private Customer customer;
    private Product product;
    private int units;
    private int installmentMonths;
    private double subTotal1;
    private double subTotal2;
    private double totalPayment;
    private double monthlyPayment;
    private double totalInterest;
    private int earnedPoints;
    private Installment installmentPlan;
    private double discount;
    private int previousPoints;

    public Order(int id, Customer customer, Product product, int units, int installmentMonths, Installment installmentPlan) {
        this.id = id;
        this.customer = customer;
        this.product = product;
        this.units = units;
        this.installmentMonths = installmentMonths;
        this.installmentPlan = installmentPlan;
    }

    public void processOrder() {
        subTotal1 = product.getPrice() * units;
        
        // ✅ Store previous points before adding new ones
        previousPoints = customer.getPoints(); 
        
        // ✅ Calculate points (1 point for every 500 THB)
        earnedPoints = (int) (subTotal1 / 500);
        
        // ✅ Calculate discount
        this.discount = 0;  
       


        if (customer.getTotalOrders() == 0) {
            this.discount = 200;  // ✅ First order discount
        } else if (previousPoints >= 100) {  // ✅ Check BEFORE adding new points
            this.discount = 0.05 * subTotal1;  // ✅ 5% discount
            customer.usePoints();  // ✅ Deduct 100 points
        }


        subTotal2 = subTotal1 - this.discount;
        
        customer.addPoints(earnedPoints);
        
        totalPayment = subTotal2;
        totalInterest = 0;
        monthlyPayment = 0;

        // ✅ Calculate installment-based payment
        if (installmentMonths > 0) {
            double interestRate = installmentPlan.getInterestRate(installmentMonths) / 100; // Convert % to decimal
            totalInterest = subTotal2 * interestRate * installmentMonths;
            totalPayment = subTotal2 + totalInterest;
            monthlyPayment = totalPayment / installmentMonths;
        }

        product.addSales(units);
        customer.incrementTotalOrders();
    }

    @Override
    public String toString() {
        String paymentMethod = (installmentMonths == 0) ? "Full Payment" : installmentMonths + "-month installments";
        
        // ✅ Correctly compute previous points (avoid negative values)
        int displayedPoints = Math.max(0, previousPoints);

        
        return String.format("""
                             %d. %s (%d pts)
                                  - order = %s x %d
                                  - sub-total(1) = %,.2f   (+ %3d pts next order)
                                  - discount = %,.2f
                                  - sub-total(2) = %,.2f
                                  - %s
                                  - total interest = %,.2f
                                  - total = %,.2f
                                  - monthly total = %,.2f""" // ✅ "Full Payment" or "X-month installments"
        ,
                id, customer.getName(), displayedPoints, 
                product.getName(), units, 
                subTotal1, earnedPoints, discount, subTotal2, 
                paymentMethod, totalInterest, totalPayment, monthlyPayment);
    }
}

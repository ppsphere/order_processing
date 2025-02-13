import java.io.*;
import java.util.*;

public class OrderProcessor {
    private List<Order> orders = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>(); // ✅ No HashMap
    private ProductList productList;
    private Installment installmentPlan;

    public OrderProcessor(String filename, ProductList productList, Installment installmentPlan) {
        this.productList = productList;
        this.installmentPlan = installmentPlan;
        
        System.out.println("\nRead from " + filename);
        loadOrdersWithValidation(filename);
    }

    private void loadOrdersWithValidation(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",\\s*");

                if (parts.length < 5) {
                    System.err.println("[ERROR] Skipping line (missing values): " + line);
                    continue;
                }

                try {
                    int orderId = Integer.parseInt(parts[0]);
                    String customerName = parts[1];
                    String productCode = parts[2];
                    int units = parseInteger(parts[3]);
                    int installmentMonths = parseInteger(parts[4]);

                    Product product = productList.getProduct(productCode);
                    if (product == null) {
                        System.err.println("[ERROR] Invalid product code in order: " + line);
                        continue;
                    }

                    if (units <= 0) {
                        System.err.println("[ERROR] Invalid units (<= 0) in order: " + line);
                        continue;
                    }

                    if (installmentMonths != 0 && installmentPlan.getInterestRate(installmentMonths) == -1) {
                        System.err.println("[ERROR] Invalid installment plan in order: " + line);
                        continue;
                    }

                    // ✅ Find or create customer
                    Customer customer = findCustomerByName(customerName);
                    if (customer == null) {
                        customer = new Customer(customerName);
                        customers.add(customer);
                    }

                    // ✅ Add order
                    orders.add(new Order(orderId, customer, product, units, installmentMonths, installmentPlan));

                    // ✅ Print in required format
                    System.out.printf("Order %2d >> %-8s %-15s x %-2d  %2d-month-installments\n",
                            orderId, customerName, product.getName(), units, installmentMonths);

                } catch (NumberFormatException e) {
                    System.err.println("[ERROR] Format error in order: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("[ERROR] Could not read file: " + e.getMessage());
        }
    }

    private int parseInteger(String value) {
        try {
            return Integer.parseInt(value.replace("O", "0"));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // ✅ Method to find a customer in the list
    private Customer findCustomerByName(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name)) {
                return customer;
            }
        }
        return null;
    }

    public void processOrders() {
        System.out.println("\n== Order processing ==");
        for (Order order : orders) {
            order.processOrder();
            System.out.println(order);
        }
    }

    public void generateReport() {
        System.out.println("\n==== Order Summary ====");
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}

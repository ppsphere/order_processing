import java.io.*;
import java.util.*;

public class CustomerList {
    private Map<String, Customer> customers = new HashMap<>();
    private List<Order> orders = new ArrayList<>();
    private ProductList productList;
    private Installment installmentPlan;

    public CustomerList(String filename, ProductList productList, Installment installmentPlan) {
        this.productList = productList;
        this.installmentPlan = installmentPlan;
        loadCustomers(filename);
    }

    private void loadCustomers(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",\\s*");

                if (parts.length < 5) {
                    System.err.println("[ERROR] Skipping invalid line (missing values): " + line);
                    continue;
                }

                try {
                    int orderId = Integer.parseInt(parts[0]);
                    String customerName = parts[1];
                    String productCode = parts[2];
                    int units = Integer.parseInt(parts[3]);
                    int installmentMonths = Integer.parseInt(parts[4]);

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

                    customers.putIfAbsent(customerName, new Customer(customerName));
                    orders.add(new Order(orderId, customers.get(customerName), product, units, installmentMonths, installmentPlan));

                } catch (NumberFormatException e) {
                    System.err.println("[ERROR] Format error in order: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("[ERROR] Could not read file: " + e.getMessage());
        }
    }

    public void processOrders() {
        for (Order order : orders) {
            order.processOrder();
        }
    }

    public void generateReport() {
        System.out.println("\n==== Customer Summary ====");
        for (Customer customer : customers.values()) {
            System.out.println(customer);
        }
    }
}

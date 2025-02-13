import java.io.*;
import java.util.*;

public class ProductList {
    private List<Product> products = new ArrayList<>();

    public ProductList(String filename) {
        System.out.println("\nRead from " + filename);
        loadProducts(filename);
    }

    private void loadProducts(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",\\s*");
                if (parts.length == 3) {
                    String code = parts[0];
                    String name = parts[1];
                    int price = Integer.parseInt(parts[2]);
                    products.add(new Product(code, name, price));
                    
                    System.out.printf("%-20s unit price = %,d\n", name + " (" + code + ")", price);
                }
            }
            System.out.println("Products loaded successfully.");
        } catch (IOException e) {
            System.err.println("[ERROR] Error loading products: " + e.getMessage());
        }
    }

    public Product getProduct(String code) {
        for (Product p : products) {
            if (p.getCode().equals(code)) {
                return p;
            }
        }
        return null;
    }

    public List<Product> getAllProducts() {
        return products;
    }
}

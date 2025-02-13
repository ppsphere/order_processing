import java.io.*;
import java.util.*;

public class Installment {
    private Map<Integer, Float> interestRates = new HashMap<>();

    public Installment(String filename) {
        System.out.println("\nRead from " + filename);
        loadInstallmentData(filename);
    }

    private void loadInstallmentData(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",\\s*");
                int months = Integer.parseInt(parts[0]);
                float interest = Float.parseFloat(parts[1]);

                interestRates.put(months, interest);
                
                System.out.printf("%2d-month plan      monthly interest = %.2f%%\n", months, interest);
            }
        } catch (IOException e) {
            System.err.println("Error loading installment plans: " + e.getMessage());
        }
    }

    public float getInterestRate(int months) {
        return interestRates.getOrDefault(months, -1.0f);
    }
}

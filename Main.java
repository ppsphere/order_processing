import java.io.*;
import java.util.Scanner;



public class Main
{	
	public static void main(String []args)
	{
            System.out.println("Initializing ProductList...");
            ProductList productList = new ProductList("src/main/java/?????/products.txt");

            System.out.println("Initializing Installment...");
            Installment installmentPlans = new Installment("src/main/java/?????/installments.txt");

            System.out.println("\nInitializing OrderProcessor...");
            OrderProcessor orderProcessor = new OrderProcessor("src/main/java/?????/orders.txt", productList, installmentPlans);

            
            orderProcessor.processOrders();

            System.out.println("\n==== Generating Reports ====");
            orderProcessor.generateReport();
	
	}

	void parseProduct(String filename)
	{
		boolean pass = false;
		Scanner fread = null;

        // Try to open the file until it is successfully found
        while (!pass) {
            try {
                fread = new Scanner(new File(filename));
                pass = true;  // Exit loop if the file is found
            } catch (FileNotFoundException e1) {
                System.err.println("File not found: " + filename + ". Retrying...");
            }
        }

        // Skip the header line
        if (fread != null && fread.hasNextLine()) {
            fread.nextLine();
        }

        // Read each product line and parse it
        while (fread != null && fread.hasNext()) {
            String line = fread.nextLine();
            String[] parts = line.split(",\\s*");

            if (parts.length == 3) {
                String code = parts[0];
                String name = parts[1];
                int price = Integer.parseInt(parts[2]);

                // Debug output to check if products are parsed correctly
                System.out.println("Parsed Product: " + code + " | " + name + " | " + price);
            }
        }

        // Close the file scanner
        if (fread != null) {
            fread.close();
        }
    }

}

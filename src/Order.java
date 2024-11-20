import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private static final double TAX_RATE = 0.08;
    private static final double WAFFLE_CONE_COST = 5.0;
    private static final double DEFAULT_CONTAINER_COST = 0.0;

    private static int idCounter = 1;

    private int orderID;
    private Map<IcecreamFlavor, Integer> flavors = new HashMap<>();
    private Map<IcecreamToppings, Integer> toppings = new HashMap<>();
    private String container;
    private double containerCost;

    public Order(String container) {
        this.orderID = idCounter++;
        this.container = container;
        this.containerCost = container.equalsIgnoreCase("Waffle Cone") ? WAFFLE_CONE_COST : DEFAULT_CONTAINER_COST;
    }

    public void addFlavor(IcecreamFlavor flavor, int quantity) {
        flavors.put(flavor, flavors.getOrDefault(flavor, 0) + quantity);
    }

    public void addTopping(IcecreamToppings topping, int quantity) {
        toppings.put(topping, toppings.getOrDefault(topping, 0) + quantity);
    }

    public double calculateSubtotal() {
        double subtotal = containerCost;
        for (Map.Entry<IcecreamFlavor, Integer> entry : flavors.entrySet()) {
            subtotal += entry.getKey().price * entry.getValue();
        }
        for (Map.Entry<IcecreamToppings, Integer> entry : toppings.entrySet()) {
            subtotal += entry.getKey().price * entry.getValue();
        }
        return subtotal;
    }

    public double calculateTotalWithTax() {
        double subtotal = calculateSubtotal();
        return subtotal + (subtotal * TAX_RATE);
    }

    public void generateInvoice(String fileName) {
        double subtotal = calculateSubtotal();
        double tax = subtotal * TAX_RATE;
        double total = calculateTotalWithTax();

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Ice Cream Shop Invoice\n");
            writer.write("Order ID: " + orderID + "\n\n");

            writer.write("Flavors:\n");
            for (Map.Entry<IcecreamFlavor, Integer> entry : flavors.entrySet()) {
                writer.write(entry.getKey() + " - " + entry.getValue() + " scoop(s): $"
                        + (entry.getKey().price * entry.getValue()) + "\n");
            }

            writer.write("\nToppings:\n");
            for (Map.Entry<IcecreamToppings, Integer> entry : toppings.entrySet()) {
                writer.write(entry.getKey() + " - " + entry.getValue() + " time(s): $"
                        + (entry.getKey().price * entry.getValue()) + "\n");
            }

            writer.write("\nContainer: " + container + " - $" + containerCost + "\n");
            writer.write("Subtotal: $" + subtotal + "\n");
            writer.write("Tax: $" + tax + "\n");
            writer.write("Total Amount Due: $" + total + "\n");
        } catch (IOException e) {
            System.err.println("Error writing invoice: " + e.getMessage());
        }
    }

    public int getOrderID() {
        return orderID;
    }

    public String getContainer() {
        return container;
    }

    public double getContainerCost() {
        return containerCost;
    }
}

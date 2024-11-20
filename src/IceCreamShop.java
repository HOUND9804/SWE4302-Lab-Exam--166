public class IceCreamShop {
    public static void main(String[] args) {

        IcecreamFlavor mint = new MintChocolateChip();
        IcecreamFlavor chocolate = new ChocolateFudge();

        IcecreamToppings freshStrawberries = new FreshStrawberries();
        IcecreamToppings chocolateChips = new ChocolateChips();

        Order order = new Order("Waffle Cone");
        order.addFlavor(chocolate, 1);
        order.addFlavor(mint, 1);
        order.addTopping(chocolateChips, 1);
        order.addTopping(freshStrawberries, 2);

        try {
            order.generateInvoice("invoice.txt");
            System.out.println("Invoice generated successfully!");
        } catch (Exception e) {
            System.err.println("Error generating invoice: " + e.getMessage());
        }
    }
}

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        //Products
        Product productA = new Product("Cheese", 3.5, "Food");
        Product productB = new Product("Water", 4.3, "Drink");
        Product productC = new Product("Vitamin C", 5.6, "Health");
        Product productD = new Product("WorldCup News", 2.99, "Magazine");
        Product productE = new Product("TV", 149.99, "Electronic");
        Product productF = new Product("Radio", 62.99, "Electronic");
        Product productG = new Product("Phone", 502.99, "Electronic");
        Product productH = new Product("Computer", 999.99, "Electronic");

        //List of products
        List<Product> list = Arrays.asList(productA, productB, productC, productD, productE,
                productF, productG, productH);

        System.out.println("---------------------- Filter by prices higher than 50 and order alphabetically ----------------------");

        list.stream()
                .filter(p -> p.getPrice() > 50)
                .sorted(Comparator.comparing(Product::getName)).toList()
                .forEach(product -> System.out.println(product.getName() + " costs " + product.getPrice()));

        System.out.println("---------------------- Average price of Electronics ----------------------");

        double averagePrice = list.stream()
                .filter(p -> "ELECTRONIC".equalsIgnoreCase(p.getCategory().toString()))
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(0.0);

        System.out.println("The average price of Electronics is: " + averagePrice);

        System.out.println("---------------------- Get the product with the higher price ----------------------");

        //Order by category
        Optional<Product> highestPrice = list.stream().max(Comparator.comparingDouble(Product::getPrice));

        System.out.println("The most expensive product is: " + highestPrice.get().getName());

        System.out.println("---------------------- Order by higher price ----------------------");

        //Order by higher price
        list.sort(Comparator.comparingDouble(Product::getPrice).reversed());

        for(Product product : list){
            System.out.println(product.getName() + " costs " + product.getPrice());
        }

        System.out.println("---------------------- Order by category ----------------------");

        //Order by category
        List<Product> orderedByCategory = list.stream().sorted(Comparator.comparing(Product::getCategory)).toList();

        for(Product product : orderedByCategory){
            System.out.println(product.getName() + " it's in category " + product.getCategory().toString());
        }

        System.out.println("---------------------- Change the name to upper case ----------------------");

        //Change the name of the product to upper case.
        List<Product> upperCaseProducts = list.stream()
                .map(p -> new Product(p.getName().toUpperCase(), p.getPrice(), p.getCategory().toString()))
                .toList();

        for(Product product : upperCaseProducts){
            System.out.println(product.getName());
        }

        System.out.println("---------------------- Add 1 dollar to the price of the products ----------------------");

        //Change the name of the product to upper case.
        List<Product> increaseProductsPrice = list.stream()
                .map(p -> new Product(p.getName(), p.getPrice() + 1, p.getCategory().toString()))
                .toList();

        for(Product product : increaseProductsPrice){
            System.out.println(product.getName() + " costs " + product.getPrice());
        }

    }
}

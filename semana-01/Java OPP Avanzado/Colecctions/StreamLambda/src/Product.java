import Exceptions.ValidationException;

/**
 * Invariante de representación:
 * - name != null && !name.isEmpty()
 * - price >= 0
 * - category != null && es un valor válido de Category
 */
public class Product {

    public String name;
    public double price;
    public Category category;

    /* Constructor */
    public Product(String name, double price, String category){
        setName(name);
        setPrice(price);
        setCategory(category);
    }

    //Setters
    public void setName(String name) {
        /* Check the name it's not empty or null */
        if (name == null || name.isEmpty()){
            throw new ValidationException("Name can't be null or empty");
        }
        this.name = name;
    }

    public void setPrice(double price) {
        /* Check the price it's not null */
        if(price < 0){
            throw new ValidationException("The price can't be negative");
        }
        this.price = price;
    }

    public void setCategory(String category) {
        /* Check the category exist in the enum and it's not null */
        if (category == null || !Category.existEnum(category)){
            throw new ValidationException("This category is not valid");
        }
        this.category = Category.valueOf(category.toUpperCase());
    }

    //Getters
    public String getName() { return name; }

    public double getPrice() { return price; }

    public Category getCategory() { return category; }

}

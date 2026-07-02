public class Product {

    public String name;
    public double price;
    public Category category;

    //Constructor
    public Product(String name, double price, String category){
        setName(name);
        setPrice(price);
        setCategory(category);
    }

    //Setters
    public void setName(String name) {
        if(name != null){
            this.name = name;
        }
    }

    public void setPrice(double price) {
        this.price = Math.max(price, 0);
    }

    public void setCategory(String category) {
        if (category != null && Category.existEnum(category)){
            this.category = Category.valueOf(category.toUpperCase());
        }
    }

    //Getters
    public String getName() { return name; }

    public double getPrice() { return price; }

    public Category getCategory() { return category; }

}

public enum Category {
    FOOD, HEALTH, DRINK, MAGAZINE, ELECTRONIC;

    //Check the enum exist
    public static boolean existEnum(String category) {
        try {
            // Checks of the value exists
            Category.valueOf(category.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

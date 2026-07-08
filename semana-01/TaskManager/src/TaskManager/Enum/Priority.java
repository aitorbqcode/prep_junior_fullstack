package TaskManager.Enum;

public enum Priority {
    LOW, MEDIUM, HIGH;

    //Check the enum exist
    public static boolean existEnum(String priority) {
        try {
            // Checks of the value exists
            Priority.valueOf(priority.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

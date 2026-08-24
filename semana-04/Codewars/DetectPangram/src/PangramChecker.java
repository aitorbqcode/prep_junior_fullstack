public class PangramChecker {
    public boolean check(String sentence){
        return sentence.toLowerCase()
                .chars()
                .filter(Character::isLetter)
                .distinct()
                .count() == 26;
    }
}
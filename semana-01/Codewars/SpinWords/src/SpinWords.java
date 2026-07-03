public class SpinWords {

    public static String spinWords2(String sentence) {
        //TODO: Code stuff here
        //We separate the sentence in words
        String []words = sentence.split("\\s+");

        //String builder to add the new words correctly
        StringBuilder stringBuilder= new StringBuilder();

        //Loop where we check if the word has more of 4 character to reverse it, if not we append it normal
        for(String word : words){
            if (word.length() >= 5){
                stringBuilder.append(new StringBuilder(word).reverse()).append(" ");
            } else {
                stringBuilder.append(word).append(" ");
            }
        }

        //We delete the last whitespace
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        //We return the sentence
        return stringBuilder.toString();
    }

    public static String spinWords(String sentence) {
        //We separate the sentence in words
        String []words = sentence.split("\\s+");

        //StringBuilder to manipulate and return the word
        StringBuilder wordReversed = new StringBuilder();
        StringBuilder stringBuilder= new StringBuilder();

        //Loop where we check if the word has more of 4 character to reverse it, if not we append it normal,we reverse it manually
        for(String word : words){
            if(word.length() >= 5){
                for(int i = word.length() - 1; i >= 0; i--){
                    wordReversed.append(word.charAt(i));
                }
                stringBuilder.append(wordReversed).append(" ");
                wordReversed.setLength(0);
            } else {
                stringBuilder.append(word).append(" ");
            }
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    public static void main(String[] args) {

        System.out.println(spinWords("This is another test"));
    }
}
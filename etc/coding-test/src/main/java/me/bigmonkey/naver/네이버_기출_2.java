package me.bigmonkey.naver;

public class 네이버_기출_2 {
/*    static String sentence = "i love coding";
    static String keyword = "mask";
    static int[] skips = {0, 0, 3, 2, 3, 4};*/

 /*   static String sentence = "i love coding";
    static String keyword = "mode";
    static int[] skips = {0, 10};*/

   /* static String sentence = "abcde fghi";
    static String keyword = "xyz";
    static int[] skips = {10, 0, 1};*/

    static String sentence = "encrypt this sentence";
    static String keyword = "something";
    static int[] skips = {0, 1, 3, 2, 1, 2, 0, 3, 0, 2, 4, 1, 3};

    public static void main(String[] args) {

        int numCharOfSentence = sentence.length();
        int keywordIndex = 0;
        int skipIndex = 0;
        StringBuilder ret = new StringBuilder();

        for (int i = 0; i < numCharOfSentence+1; ) {
            if(skipIndex == skips.length) {
                ret.append(sentence.substring(i, numCharOfSentence));
                break;
            }

            int skip = skips[skipIndex++];
            char keywordChar = keyword.charAt(keywordIndex++ % keyword.length());
            int skipCount = 0;

            if(i + skip > numCharOfSentence) {
                ret.append(sentence.substring(i, numCharOfSentence));
                break;
            }
            String substring = sentence.substring(i, i + skip);
            int findIndex = substring.indexOf(keywordChar);
            if(findIndex == -1) skipCount = skip;
            else skipCount = findIndex;

            ret.append(sentence.substring(i, i+skipCount));
            ret.append(keywordChar);

            i += skipCount;
        }

        System.out.println(ret.toString());
    }

}

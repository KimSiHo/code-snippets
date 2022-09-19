package me.bigmonkey.naver;

public class 네이버_기출_2 {
/*    static String sentence = "i love coding";
    static String keyword = "mask";
    static int[] skips = {0, 0, 3, 2, 3, 4};*/

//    static String sentence = "i love coding";
//    static String keyword = "mode";
//    static int[] skips = {0, 10};

   /* static String sentence = "abcde fghi";
    static String keyword = "xyz";
    static int[] skips = {10, 0, 1};*/

    static String sentence = "encrypt this sentence";
    static String keyword = "something";
    static int[] skips = {0, 1, 3, 2, 1, 2, 0, 3, 0, 2, 4, 1, 3};

    public static void main(String[] args) {

        int sizeOfSentence = sentence.length();
        int curSentenceIndex = 0;
        int keywordIndex = 0;
        StringBuilder ret = new StringBuilder();

        for (int skip : skips) {
            int dupCharIndex = sentence.indexOf(keyword.charAt(keywordIndex % keyword.length()), curSentenceIndex);

            if (dupCharIndex != -1) {
                dupCharIndex++;
                if (dupCharIndex - curSentenceIndex < skip) {
                    skip = dupCharIndex - curSentenceIndex;
                }
            }

            if (curSentenceIndex + skip > sizeOfSentence) {
                ret.append(sentence, curSentenceIndex, sizeOfSentence);
                break;
            }

            ret.append(sentence, curSentenceIndex, curSentenceIndex + skip);
            ret.append(keyword.charAt(keywordIndex++ % keyword.length()));
            curSentenceIndex += skip;
        }

        if(curSentenceIndex < sizeOfSentence) {
            ret.append(sentence, curSentenceIndex, sizeOfSentence);
        }

        System.out.println(ret.toString());
    }
}

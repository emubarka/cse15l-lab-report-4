//https://howtodoinjava.com/java/io/java-read-file-to-string-examples/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {

    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then read link upto next )
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            int openBracket = markdown.indexOf("[", currentIndex);
            if (openBracket == -1) {
                return toReturn;
            }
            if (markdown.charAt(openBracket-1) == '!') {
                openBracket = markdown.indexOf("[", openBracket+1);
            }
            if (openBracket == -1) { //why check -1 here again?
                return toReturn;
            }
            //should check for -1 for other vairbales 
            //returning too ealry as it could have invalid at the top and valid links following that 
            int closeBracket = markdown.indexOf("]", openBracket);
            int finalOpenParen = markdown.indexOf("(", closeBracket);
            int finalCloseParen = markdown.indexOf(")", finalOpenParen);
            int nextOpenBracket = markdown.indexOf("[", finalOpenParen);
            if (nextOpenBracket == -1) {
                toReturn.add(markdown.substring(finalOpenParen + 1, finalCloseParen));
                break;
            }
            else {
                toReturn.add(markdown.substring(finalOpenParen + 1, finalCloseParen));
                currentIndex = finalCloseParen + 1;
            }
        }
        return toReturn;
    }


    public static void main(String[] args) throws IOException {
        Path fileName = Path.of(args[0]);
        String content = Files.readString(fileName);
        ArrayList<String> links = getLinks(content);
	System.out.println(links);
	System.out.println("For lab report 3 purpose");
    }
}

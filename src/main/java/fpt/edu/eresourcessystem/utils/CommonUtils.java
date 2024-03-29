package fpt.edu.eresourcessystem.utils;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CommonUtils {

    // Paging format
    public static List<Integer> pagingFormat(int totalPage, int pageIndex) {
        int delta = 2;
        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= totalPage; i++) {
            if (i == 1 || i == totalPage || (i >= pageIndex - delta && i <= pageIndex + delta)) {
                pages.add(i);
            } else if (i == pageIndex - (delta + 1) || i == pageIndex + (delta + 1)) {
                pages.add(-1); // "..."
            }
        }
        return pages;
    }

    public static String extractTextFromFile(InputStream fileBytes) throws IOException, TikaException, SAXException {
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler(100000000);
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        parser.parse(fileBytes, handler, metadata, context);
        return handler.toString();
    }

    public static String convertToPlainText(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.body().getAllElements();
        StringBuilder sb = new StringBuilder();

        for (Element element : elements) {
            String text = element.ownText();
            if (!text.isEmpty()) {
                sb.append(text).append("\n");
            }
        }

        return sb.toString();
    }

    public static String convertString(String str) {
        String[] words = str.split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                String firstLetter = word.substring(0, 1).toUpperCase();
                String restOfWord = word.substring(1).toLowerCase();
                result.append(firstLetter).append(restOfWord).append(" ");
            }
        }

        return result.toString().trim();
    }

}

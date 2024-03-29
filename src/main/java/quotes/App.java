/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package quotes;

import java.io.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class App {
    protected Quote[] createJsonObject(String jsonString) {
        //convert the json to a Quote object)
        Gson gson = new GsonBuilder().serializeNulls().create();
        return  gson.fromJson(jsonString, Quote[].class);
    }

    protected Quote getRandomQuote(String str) {
        Quote[] quotes2 = createJsonObject(str);
        int randomNumber = (int) Math.floor(Math.random() *  quotes2.length);

        return quotes2[randomNumber];
    }

    protected void saveQuoteToFile(String quoteAsString) {

        try {
            // remove last line
            RandomAccessFile fileToRemoveLastLine = new RandomAccessFile("assets/recentquotes.json", "rw");
            long length = fileToRemoveLastLine.length();
            fileToRemoveLastLine.setLength(length - 1);
            fileToRemoveLastLine.close();

            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("assets/recentquotes.json", true));
            writer.append(",\n" + quoteAsString + "\n]");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getQuoteFromWeb() {
        // read file;
        try {

            URL url = new URL("http://swquotesapi.digitaljedi.dk/api/SWQuote/RandomStarWarsQuote");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // this line of code actually goes to the internet!
            BufferedReader reader = new BufferedReader(new InputStreamReader((con.getInputStream())));
            String quoteString = reader.readLine().replace("starWarsQuote", "text");

            int index = quoteString.lastIndexOf("— ");
            String quoteStringWithAuthor;

            if(index == -1) {
                quoteStringWithAuthor = quoteString;
            } else {
                quoteStringWithAuthor = quoteString.substring(0, index) + "\",\"author\":\"" + quoteString.substring(index + 2);
            }

            Gson gson = new Gson();

            Quote quote = gson.fromJson(quoteStringWithAuthor, Quote.class);
            saveQuoteToFile(quoteStringWithAuthor);
            return quote.getText();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return getQuoteFromFile();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return getQuoteFromFile();
        } catch (IOException e) {
            e.printStackTrace();
            return getQuoteFromFile();
        }
    }

    public String getQuoteFromFile() {
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("assets/recentquotes.json"));
            StringBuilder str = new StringBuilder();
            String quoteString = reader.readLine();

            while(quoteString != null) {
                str = str.append(quoteString);
                quoteString = reader.readLine();
            }

            return getRandomQuote(str.toString()).toString();

        } catch (IOException e) {
            e.printStackTrace();
            return "Two is the smallest prime number";
        }
    }



    public static void main(String[] args) {
        App app = new App();
        System.out.println(app.getQuoteFromWeb());

    }

}

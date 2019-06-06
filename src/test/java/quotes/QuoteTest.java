package quotes;

import org.junit.Test;
import static org.junit.Assert.*;

public class QuoteTest {
    @Test
    public void testNewQuote() {
        Quote quote = new Quote("Me", "This is a quote");
        assertEquals("Authote should be Me", "Me", quote.getAuthor());
        assertEquals("Text should be This is a quote", "This is a quote", quote.getText());
    }
}

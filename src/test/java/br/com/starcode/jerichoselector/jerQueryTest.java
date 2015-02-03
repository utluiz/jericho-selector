package br.com.starcode.jerichoselector;

import static br.com.starcode.jerichoselector.jerQuery.$;
import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import net.htmlparser.jericho.Source;

import org.junit.Test;

public class jerQueryTest {
	
    @Test
    public void test1() throws Exception {
        Source html = new Source(getClass().getResourceAsStream("teste-01.html"));
        String pageTitle = $(html, "title").getText();
        assertEquals("Page Title", pageTitle);
    }
    
    @Test
	public void test2() throws Exception {
    	 InputStream is = getClass().getResourceAsStream("teste-01.html");
         assertEquals(2, $(is, ".my-text").length());
	}
    
    @Test
	public void test3() throws Exception {
    	 InputStream is = getClass().getResourceAsStream("teste-01.html");
         assertEquals("Paragraph", $(is, "p.my-text").getText());
	}
    
}

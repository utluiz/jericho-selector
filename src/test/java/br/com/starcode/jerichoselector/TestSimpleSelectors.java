package br.com.starcode.jerichoselector;

import static br.com.starcode.jerichoselector.jerQuery.$;
import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.junit.Test;

public class TestSimpleSelectors {
	
    @Test
    public void typeSelector() throws Exception {
        Source html = new Source(getClass().getResourceAsStream("test-01.html"));
        String pageTitle = $(html, "title").getText();
        assertEquals("Page Title", pageTitle);
    }
    
    @Test
	public void classSelector() throws Exception {
    	 InputStream is = getClass().getResourceAsStream("test-01.html");
         assertEquals(2, $(is, ".my-text").length());
	}
    
    @Test
	public void typeAndClassSelector() throws Exception {
    	String html = "<html>"+
			"<head>"+
			"<title>Page Title</title>"+
			"</head>"+
			"<body>"+
			"<h1 id=\"id1\" class=\"top-title\" it=\"page.title\">Title</h1>"+
			"<p class=\"my-text\">Paragraph</p>"+
			"<p class=\"my-text-2\">Another Paragraph</p>"+
			"<span class=\"my-text\">this is <strong>my</strong> page!</span>"+
			"</body>"+
			"</html>"; 
        assertEquals("Paragraph", $(html, "p.my-text").getText());
	}
    
    @Test
	public void universal() throws Exception {
    	 Reader r = new InputStreamReader(getClass().getResourceAsStream("test-01.html"));
    	 List<Element> elements = $(r, "*").getSelectedElements();
         assertEquals(10, elements.size());
         assertEquals("html", elements.get(0).getName());
         assertEquals("head", elements.get(1).getName());
         assertEquals("title", elements.get(2).getName());
         assertEquals("body", elements.get(3).getName());
         assertEquals("h1", elements.get(4).getName());
         assertEquals("p", elements.get(5).getName());
         assertEquals("p", elements.get(6).getName());
         assertEquals("span", elements.get(7).getName());
         assertEquals("strong", elements.get(8).getName());
         assertEquals("p", elements.get(9).getName());
	}
    
}

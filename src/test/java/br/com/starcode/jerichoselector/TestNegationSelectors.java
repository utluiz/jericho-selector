package br.com.starcode.jerichoselector;

import static br.com.starcode.jerichoselector.jerQuery.$;
import static org.junit.Assert.*;

import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.junit.Before;
import org.junit.Test;

public class TestNegationSelectors {
	
	Source source;
	
	@Before
	public void setup() throws Exception {
		source = new Source(getClass().getResourceAsStream("test-01.html"));
	}
	
	@Test
    public void notTypeSelector() throws Exception {
		List<Element> elements = $(source, ":not(title)").getSelectedElements();
        assertEquals(9, elements.size());
        assertFalse(elements.contains($(source, "title").getSelectedElements().get(0)));
    }
    
    @Test
	public void notClassSelector() throws Exception {
    	List<Element> elements = $(source, "p:not(.my-text)").getSelectedElements();
        assertEquals(2, elements.size());
        assertEquals("my-text-2", elements.get(0).getAttributeValue("class"));
        assertNotEquals("my-text", elements.get(1).getAttributeValue("class"));
	}
    
    @Test
	public void notAttributeSelector() throws Exception {
    	
    	 List<Element> elements = $(source, ":not([style=\"\"])").getSelectedElements();
    	 assertEquals(9, elements.size());
    	 assertFalse(elements.contains($(source, "[style=\"\"]").getSelectedElements().get(0)));
         
         elements = $(source, ":not([it='page.title'])").getSelectedElements();
         assertEquals(9, elements.size());
         assertFalse(elements.contains($(source, "[it='page.title']").getSelectedElements().get(0)));
         
         elements = $(source, ":not([it^='page'])").getSelectedElements();
         assertEquals(9, elements.size());
         assertFalse(elements.contains($(source, "[it^='page']").getSelectedElements().get(0)));
         
         elements = $(source, ":not([it$='title'])").getSelectedElements();
         assertEquals(9, elements.size());
         assertFalse(elements.contains($(source, "[it$='title']").getSelectedElements().get(0)));
         
         elements = $(source, ":not([it*='ge.ti'])").getSelectedElements();
         assertEquals(9, elements.size());
         assertFalse(elements.contains($(source, "[it*='ge.ti']").getSelectedElements().get(0)));
         
         elements = $(source, ":not([class~='otherClass'])").getSelectedElements();
         assertEquals(9, elements.size());
         assertFalse(elements.contains($(source, "[class~='otherClass']").getSelectedElements().get(0)));
         
         elements = $(source, ":not([class~='my-text'])").getSelectedElements();
         assertEquals(8, elements.size());
         assertFalse(elements.contains($(source, "[class~='my-text']").getSelectedElements().get(0)));
         assertFalse(elements.contains($(source, "[class~='my-text']").getSelectedElements().get(1)));
         
         elements = $(source, ":not([class|='top'])").getSelectedElements();
         assertEquals(9, elements.size());
         assertFalse(elements.contains($(source, "[class|='top']").getSelectedElements().get(0)));
         
         elements = $(source, ":not([class])").getSelectedElements();
         assertEquals(6, elements.size());
         assertFalse(elements.contains($(source, "[class]").getSelectedElements().get(0)));
         assertFalse(elements.contains($(source, "[class]").getSelectedElements().get(1)));
         assertFalse(elements.contains($(source, "[class]").getSelectedElements().get(2)));
         assertFalse(elements.contains($(source, "[class]").getSelectedElements().get(3)));
         
	}
	
}

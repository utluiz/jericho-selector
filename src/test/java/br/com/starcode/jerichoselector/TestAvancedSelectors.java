package br.com.starcode.jerichoselector;

import static br.com.starcode.jerichoselector.jerQuery.$;
import static org.junit.Assert.assertEquals;

import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.junit.Before;
import org.junit.Test;

public class TestAvancedSelectors {
	
	Source source;
	
	@Before
	public void setup() throws Exception {
		source = new Source(getClass().getResourceAsStream("test-01.html"));
	}
	
	@Test
	public void doubleSelector() throws Exception {
    	 List<Element> elements = $(source.getAllElements("html").toArray(new Element[1]), "#id1, .my-text").getSelectedElements();
         assertEquals(3, elements.size());
         assertEquals("h1", elements.get(0).getName());
         assertEquals("p", elements.get(1).getName());
         assertEquals("span", elements.get(2).getName());
	}
    
    @Test
	public void triple() throws Exception {
    	 List<Element> elements = $(source, "#id1, .my-text,.my-text-2").getSelectedElements();
         assertEquals(4, elements.size());
         assertEquals("h1", elements.get(0).getName());
         assertEquals("p", elements.get(1).getName());
         assertEquals("p", elements.get(2).getName());
         assertEquals("span", elements.get(3).getName());
	}
    
    @Test
	public void noResults() throws Exception {
    	 List<Element> elements = $(source.getChildElements().get(0), "#id2").getSelectedElements();
    	 //no elements
         assertEquals(0, elements.size());
	}
    
    @Test
	public void onlyOneResult() throws Exception {
    	 List<Element> elements = $(source.getAllElements("html"), "#id1, .my-text-5").getSelectedElements();
         assertEquals(1, elements.size());
         assertEquals("h1", elements.get(0).getName());
	}
    
    @Test
	public void attributeSelector() throws Exception {
    	
    	 List<Element> elements = $(source, "[style=\"\"]").getSelectedElements();
         assertEquals(1, elements.size());
         assertEquals("span", elements.get(0).getName());
         
         elements = $(source, "[it='page.title']").getSelectedElements();
         assertEquals(1, elements.size());
         assertEquals("h1", elements.get(0).getName());
         
         elements = $(source, "[it^='page']").getSelectedElements();
         assertEquals(1, elements.size());
         assertEquals("h1", elements.get(0).getName());
         
         elements = $(source, "[it$='title']").getSelectedElements();
         assertEquals(1, elements.size());
         assertEquals("h1", elements.get(0).getName());
         
         elements = $(source, "[it*='ge.ti']").getSelectedElements();
         assertEquals(1, elements.size());
         assertEquals("h1", elements.get(0).getName());
         
         elements = $(source, "[class~='otherClass']").getSelectedElements();
         assertEquals(1, elements.size());
         assertEquals("span", elements.get(0).getName());
         
         elements = $(source, "[class~='my-text']").getSelectedElements();
         assertEquals(2, elements.size());
         assertEquals("p", elements.get(0).getName());
         assertEquals("span", elements.get(1).getName());
         
         elements = $(source, "[class|='top']").getSelectedElements();
         assertEquals(1, elements.size());
         assertEquals("h1", elements.get(0).getName());
         
         elements = $(source, "[class]").getSelectedElements();
         assertEquals(4, elements.size());
         
	}
    
    @Test
	public void descendantCombinator() throws Exception {
    	 List<Element> elements = $(source, "span strong").getSelectedElements();
         assertEquals(1, elements.size());
         assertEquals("strong", elements.get(0).getName());
    }
    
    @Test
	public void adjascentSiblingCombinator() throws Exception {
    	List<Element> elements = $(source, "body p + span").getSelectedElements();
    	assertEquals(1, elements.size());
    	assertEquals("span", elements.get(0).getName());
    	assertEquals("my-text otherClass", elements.get(0).getAttributeValue("class"));
    }
    
    @Test
	public void adjascentSiblingCombinatorEmptyResult() throws Exception {
    	List<Element> elements = $(source, "body span+ p").getSelectedElements();
    	assertEquals(0, elements.size());
    }
    
    @Test
	public void childCombinator() throws Exception {
    	List<Element> elements = $(source, "body >p").getSelectedElements();
    	assertEquals(2, elements.size());
    }
    
    @Test
	public void generalSiblingCombinator() throws Exception {
    	List<Element> elements = $(source, "body h1~p").getSelectedElements();
    	assertEquals(2, elements.size());
    }
    
}

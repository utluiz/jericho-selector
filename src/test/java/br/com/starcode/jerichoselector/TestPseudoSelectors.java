package br.com.starcode.jerichoselector;

import static br.com.starcode.jerichoselector.jerQuery.$;
import static org.junit.Assert.*;

import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.junit.Before;
import org.junit.Test;

public class TestPseudoSelectors {
	
	Source source;
	
	@Before
	public void setup() throws Exception {
		source = new Source(getClass().getResourceAsStream("test-02.html"));
	}
	
	@Test
    public void rootSelector() throws Exception {
		List<Element> elements = $(source, ":root").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("html", elements.get(0).getName());
    }
	
	@Test
    public void nthChildSelector() throws Exception {
		List<Element> elements = $(source, "body h1:nth-child(1)").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("h1", elements.get(0).getName());
        
        elements = $(source, "body > *").getSelectedElements();
        assertEquals(7, elements.size());
        
        elements = $(source, "body > *:nth-child(7)").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("button", elements.get(0).getName());
        
        elements = $(source, "html:nth-child(1)").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("html", elements.get(0).getName());
    }
	
	@Test
    public void nthChildSelectorNoResults() throws Exception {
		List<Element> elements = $(source, "body:nth-child(0)").getSelectedElements();
        assertEquals(0, elements.size());
        
		elements = $(source, "body:nth-child()").getSelectedElements();
        assertEquals(0, elements.size());
        
		elements = $(source, "body:nth-child(-1)").getSelectedElements();
        assertEquals(0, elements.size());
        
		elements = $(source, "body:nth-child(6)").getSelectedElements();
        assertEquals(0, elements.size());
        
        elements = $(source, "html:nth-child()").getSelectedElements();
        assertEquals(0, elements.size());
        
        elements = $(source, "html:nth-child(2)").getSelectedElements();
        assertEquals(0, elements.size());
    }
	
	@Test
    public void nthLastChildSelector() throws Exception {
		List<Element> elements = $(source, "body > *:nth-last-child(1)").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("button", elements.get(0).getName());
        
        elements = $(source, "body > *:nth-last-child(7)").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("h1", elements.get(0).getName());

        elements = $(source, "html:nth-last-child(1)").getSelectedElements();
        assertEquals("html", elements.get(0).getName());
	}
	
	@Test
    public void nthLastChildSelectorNoResults() throws Exception {
		List<Element> elements = $(source, "body:nth-last-child(0)").getSelectedElements();
        assertEquals(0, elements.size());
        
		elements = $(source, "body:nth-last-child()").getSelectedElements();
        assertEquals(0, elements.size());
        
		elements = $(source, "body:nth-last-child(-1)").getSelectedElements();
        assertEquals(0, elements.size());
        
		elements = $(source, "body:nth-last-child(6)").getSelectedElements();
        assertEquals(0, elements.size());
        
        elements = $(source, "html:nth-last-child()").getSelectedElements();
        assertEquals(0, elements.size());
        
        elements = $(source, "html:nth-last-child(2)").getSelectedElements();
        assertEquals(0, elements.size());
    }
	
	@Test
    public void nthTypeSelector() throws Exception {
		List<Element> elements = $(source, "body > p:nth-of-type(1)").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("my-text", elements.get(0).getAttributeValue("class"));
        
		elements = $(source, "body p:nth-of-type(2)").getSelectedElements();
        assertEquals("my-text-2", elements.get(0).getAttributeValue("class"));

        elements = $(source, "html:nth-of-type(1)").getSelectedElements();
        assertEquals("html", elements.get(0).getName());
        
        elements = $(source, "body:nth-of-type()").getSelectedElements();
        assertEquals(0, elements.size());
        
        elements = $(source, "body:nth-of-type(2)").getSelectedElements();
        assertEquals(0, elements.size());

        elements = $(source, "html:nth-of-type()").getSelectedElements();
        assertEquals(0, elements.size());

        elements = $(source, "html:nth-of-type(2)").getSelectedElements();
        assertEquals(0, elements.size());
	}
	
	@Test
    public void nthLastTypeSelector() throws Exception {
		List<Element> elements = $(source, "body > p:nth-last-of-type(1)").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("my-text-2", elements.get(0).getAttributeValue("class"));
        
		elements = $(source, "body p:nth-last-of-type(2)").getSelectedElements();
        assertEquals("my-text", elements.get(0).getAttributeValue("class"));
        
        elements = $(source, "html:nth-last-of-type(1)").getSelectedElements();
        assertEquals("html", elements.get(0).getName());
        
        elements = $(source, "body:nth-last-of-type()").getSelectedElements();
        assertEquals(0, elements.size());
        
        elements = $(source, "body:nth-last-of-type(2)").getSelectedElements();
        assertEquals(0, elements.size());

        elements = $(source, "html:nth-last-of-type()").getSelectedElements();
        assertEquals(0, elements.size());

        elements = $(source, "html:nth-last-of-type(2)").getSelectedElements();
        assertEquals(0, elements.size());
    }
	
	@Test
    public void firstChild() throws Exception {
		List<Element> elements = $(source, "body h1:first-child").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("h1", elements.get(0).getName());
        
		elements = $(source, "button p:first-child").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("p", elements.get(0).getName());
        assertNull(elements.get(0).getAttributeValue("id"));
        
        elements = $(source, "html:first-child").getSelectedElements();
        assertEquals("html", elements.get(0).getName());
    }
	
	@Test
    public void lastChild() throws Exception {
		List<Element> elements = $(source, "body button:last-child").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("button", elements.get(0).getName());
        
		elements = $(source, "button :last-child").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("p", elements.get(0).getName());
        assertEquals("p2", elements.get(0).getAttributeValue("id"));
        
        elements = $(source, "html:last-child").getSelectedElements();
        assertEquals("html", elements.get(0).getName());
    }
	
	@Test
    public void firstType() throws Exception {
		List<Element> elements = $(source, "body h1:first-of-type").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("h1", elements.get(0).getName());
        
		elements = $(source, "body > p:first-of-type").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("p", elements.get(0).getName());
        assertEquals("my-text", elements.get(0).getAttributeValue("class"));
        
        elements = $(source, "html:first-of-type").getSelectedElements();
        assertEquals("html", elements.get(0).getName());
    }
	
	@Test
    public void lastType() throws Exception {
		List<Element> elements = $(source, "body button:last-of-type").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("button", elements.get(0).getName());
        
        elements = $(source, "body > p:last-of-type").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("p", elements.get(0).getName());
        assertEquals("my-text-2", elements.get(0).getAttributeValue("class"));
        
		elements = $(source, "body p:last-of-type").getSelectedElements();
        assertEquals(2, elements.size());
        assertEquals("p", elements.get(0).getName());
        assertEquals("p2", elements.get(1).getAttributeValue("id"));
        
        elements = $(source, "html:last-of-type").getSelectedElements();
        assertEquals("html", elements.get(0).getName());
    }
	
	@Test
    public void onlyChild() throws Exception {
		List<Element> elements = $(source, "title:only-child").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("title", elements.get(0).getName());
        
		elements = $(source, "button p:only-child").getSelectedElements();
        assertEquals(0, elements.size());
        
        elements = $(source, "html:only-child").getSelectedElements();
        assertEquals(1, elements.size());
    }
	
	@Test
    public void onlyType() throws Exception {
		List<Element> elements = $(source, "title:only-of-type").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("title", elements.get(0).getName());
        
		elements = $(source, "body > button:only-of-type").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("button", elements.get(0).getName());
        
		elements = $(source, "button p:only-of-type").getSelectedElements();
        assertEquals(0, elements.size());
        
        elements = $(source, "html:only-of-type").getSelectedElements();
        assertEquals(1, elements.size());
    }
	
	@Test
    public void empty() throws Exception {
		List<Element> elements = $(source, ":empty").getSelectedElements();
        assertEquals(3, elements.size());
        assertEquals("input", elements.get(0).getName());
        assertEquals("div", elements.get(1).getName());
        assertEquals("img", elements.get(2).getName());
	}
	
}

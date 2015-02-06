package br.com.starcode.jerichoselector;

import static br.com.starcode.jerichoselector.jerQuery.$;
import static org.junit.Assert.*;

import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.junit.Before;
import org.junit.Test;

public class TestPseudoExpressionsSelectors {
	
	Source source;
	
	@Before
	public void setup() throws Exception {
		source = new Source(getClass().getResourceAsStream("test-04.html"));
	}
	
	@Test
    public void nthChildSelectorOdd() throws Exception {
		List<Element> elements = $(source, "#ul3 li:nth-child(2n + 1)").getSelectedElements();
        assertEquals(3, elements.size());
        assertEquals("li3-1", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-3", elements.get(1).getAttributeValue("id"));
        assertEquals("li3-5", elements.get(2).getAttributeValue("id"));
        
        elements = $(source, "#ul3 li:nth-child(odd)").getSelectedElements();
        assertEquals(3, elements.size());
        assertEquals("li3-1", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-3", elements.get(1).getAttributeValue("id"));
        assertEquals("li3-5", elements.get(2).getAttributeValue("id"));
        
        elements = $(source, "#ul3 li:nth-child('odd')").getSelectedElements();
        assertEquals(3, elements.size());
        assertEquals("li3-1", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-3", elements.get(1).getAttributeValue("id"));
        assertEquals("li3-5", elements.get(2).getAttributeValue("id"));
    }
	
	@Test
    public void nthChildSelectorEven() throws Exception {
		List<Element> elements = $(source, "#ul3 li:nth-child(2n+0)").getSelectedElements();
        assertEquals(3, elements.size());
        assertEquals("li3-2", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-4", elements.get(1).getAttributeValue("id"));
        assertEquals("li3-6", elements.get(2).getAttributeValue("id"));
        
        elements = $(source, "#ul3 li:nth-child(2n)").getSelectedElements();
        assertEquals(3, elements.size());
        assertEquals("li3-2", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-4", elements.get(1).getAttributeValue("id"));
        assertEquals("li3-6", elements.get(2).getAttributeValue("id"));
        
        elements = $(source, "#ul3 li:nth-child(even)").getSelectedElements();
        assertEquals(3, elements.size());
        assertEquals("li3-2", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-4", elements.get(1).getAttributeValue("id"));
        assertEquals("li3-6", elements.get(2).getAttributeValue("id"));
        
        elements = $(source, "#ul3 li:nth-child('even')").getSelectedElements();
        assertEquals(3, elements.size());
        assertEquals("li3-2", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-4", elements.get(1).getAttributeValue("id"));
        assertEquals("li3-6", elements.get(2).getAttributeValue("id"));
    }
	
	@Test
    public void nthChildSelectorNumber() throws Exception {
		List<Element> elements = $(source, "#ul1 li:nth-child(1)").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("li1-1", elements.get(0).getAttributeValue("id"));
        
        elements = $(source, "#ul1 li:nth-child(2)").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("li1-2", elements.get(0).getAttributeValue("id"));
        
        elements = $(source, "#ul1 li:nth-child(3)").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("li1-3", elements.get(0).getAttributeValue("id"));
        
        elements = $(source, "#ul1 li:nth-child(4)").getSelectedElements();
        assertEquals(0, elements.size());
        
        elements = $(source, "#ul1 li:nth-child(0)").getSelectedElements();
        assertEquals(0, elements.size());
    }
	
	@Test
    public void nthChildSelectorVariations() throws Exception {
		List<Element> elements = $(source, "#ul3 li:nth-child(3n+0)").getSelectedElements();
        assertEquals(2, elements.size());
        assertEquals("li3-3", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-6", elements.get(1).getAttributeValue("id"));
        
        elements = $(source, "#ul3 li:nth-child(3n+1)").getSelectedElements();
        assertEquals(2, elements.size());
        assertEquals("li3-1", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-4", elements.get(1).getAttributeValue("id"));
        
        elements = $(source, "#ul3 li:nth-child(3n+2)").getSelectedElements();
        assertEquals(2, elements.size());
        assertEquals("li3-2", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-5", elements.get(1).getAttributeValue("id"));
        
        elements = $(source, "#ul3 li:nth-child(3n+3)").getSelectedElements();
        assertEquals(0, elements.size());
        
        elements = $(source, "#ul3 li:nth-child(0n+0)").getSelectedElements();
        assertEquals(0, elements.size());
	}
	
	@Test
    public void nthChildSelectorVariationsBNegative() throws Exception {
		List<Element> elements = $(source, "#ul3 li:nth-child(3n-0)").getSelectedElements();
        assertEquals(2, elements.size());
        assertEquals("li3-3", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-6", elements.get(1).getAttributeValue("id"));
        
        elements = $(source, "#ul3 li:nth-child(3n-2)").getSelectedElements();
        assertEquals(2, elements.size());
        assertEquals("li3-1", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-4", elements.get(1).getAttributeValue("id"));
        
        elements = $(source, "#ul3 li:nth-child(3n-1)").getSelectedElements();
        assertEquals(2, elements.size());
        assertEquals("li3-2", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-5", elements.get(1).getAttributeValue("id"));
        
        elements = $(source, "#ul3 li:nth-child(3n-3)").getSelectedElements();
        assertEquals(2, elements.size());
        assertEquals("li3-3", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-6", elements.get(1).getAttributeValue("id"));
	}
	
	@Test
    public void nthChildSelectorVariationsANegative() throws Exception {
		List<Element> elements = $(source, "#ul3 li:nth-child(-3n-0)").getSelectedElements();
        assertEquals(0, elements.size());
        
        elements = $(source, "#ul3 li:nth-child(-3n+0)").getSelectedElements();
        assertEquals(0, elements.size());
        
        elements = $(source, "#ul3 li:nth-child(-3n-1)").getSelectedElements();
        assertEquals(0, elements.size());
        
        elements = $(source, "#ul3 li:nth-child(-3n+1)").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("li3-1", elements.get(0).getAttributeValue("id"));
        
        elements = $(source, "#ul3 li:nth-child(-3n+3)").getSelectedElements();
        assertEquals(3, elements.size());
        assertEquals("li3-1", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-2", elements.get(1).getAttributeValue("id"));
        assertEquals("li3-3", elements.get(2).getAttributeValue("id"));
        
        elements = $(source, "#ul3 li:nth-child(-3n+5)").getSelectedElements();
        assertEquals(5, elements.size());
        assertEquals("li3-1", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-5", elements.get(4).getAttributeValue("id"));
        
        elements = $(source, "#ul3 li:nth-child(-3n+7)").getSelectedElements();
        assertEquals(6, elements.size());
        assertEquals("li3-1", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-6", elements.get(5).getAttributeValue("id"));
        
	}
	
	@Test
    public void otherSelectorVariations() throws Exception {
		List<Element> elements = $(source, "#ul3 li:nth-last-child(-3n+2)").getSelectedElements();
        assertEquals(2, elements.size());
        assertEquals("li3-5", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-6", elements.get(1).getAttributeValue("id"));
        
        elements = $(source, "#ul3 li:nth-last-child(2n+1)").getSelectedElements();
        assertEquals(3, elements.size());
        assertEquals("li3-2", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-4", elements.get(1).getAttributeValue("id"));
        assertEquals("li3-6", elements.get(2).getAttributeValue("id"));
        
        elements = $(source, "#ul3 li:nth-last-child(odd)").getSelectedElements();
        assertEquals(3, elements.size());
        assertEquals("li3-2", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-4", elements.get(1).getAttributeValue("id"));
        assertEquals("li3-6", elements.get(2).getAttributeValue("id"));
        
        elements = $(source, "#ul3 li:nth-last-child(even)").getSelectedElements();
        assertEquals(3, elements.size());
        assertEquals("li3-1", elements.get(0).getAttributeValue("id"));
        assertEquals("li3-3", elements.get(1).getAttributeValue("id"));
        assertEquals("li3-5", elements.get(2).getAttributeValue("id"));
        
        elements = $(source, "body > ul:nth-of-type(even)").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("ul2", elements.get(0).getAttributeValue("id"));
        
        elements = $(source, "body > ul:nth-of-type(odd)").getSelectedElements();
        assertEquals(2, elements.size());
        assertEquals("ul1", elements.get(0).getAttributeValue("id"));
        assertEquals("ul3", elements.get(1).getAttributeValue("id"));
        
        elements = $(source, "body > ul:nth-of-type(2)").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("ul2", elements.get(0).getAttributeValue("id"));
        
        elements = $(source, "body > ul:nth-last-of-type(2)").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("ul2", elements.get(0).getAttributeValue("id"));
        
        elements = $(source, "body > ul:nth-last-of-type(1)").getSelectedElements();
        assertEquals(1, elements.size());
        assertEquals("ul3", elements.get(0).getAttributeValue("id"));
        
	}        
	
}

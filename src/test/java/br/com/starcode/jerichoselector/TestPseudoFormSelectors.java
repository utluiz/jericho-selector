package br.com.starcode.jerichoselector;

import static br.com.starcode.jerichoselector.jerQuery.$;
import static org.junit.Assert.*;

import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.junit.Before;
import org.junit.Test;

public class TestPseudoFormSelectors {
	
	Source source;
	
	@Before
	public void setup() throws Exception {
		source = new Source(getClass().getResourceAsStream("test-03.html"));
	}
	
	@Test
    public void enabled() throws Exception {
		List<Element> elements = $(source, ":enabled").getSelectedElements();
        assertEquals(8, elements.size());
        assertEquals("input-1", elements.get(0).getAttributeValue("id"));
        assertEquals("input-2", elements.get(1).getAttributeValue("id"));
        assertEquals("input-3", elements.get(2).getAttributeValue("id"));
        assertEquals("input-5", elements.get(3).getAttributeValue("id"));
        assertEquals("input-7", elements.get(4).getAttributeValue("id"));
        assertEquals("input-8", elements.get(5).getAttributeValue("id"));
        assertEquals("input-11", elements.get(6).getAttributeValue("id"));
        assertEquals("input-13", elements.get(7).getAttributeValue("id"));
    }
	
	@Test
    public void disabled() throws Exception {
		List<Element> elements = $(source, ":disabled").getSelectedElements();
        assertEquals(5, elements.size());
        assertEquals("input-4", elements.get(0).getAttributeValue("id"));
        assertEquals("input-6", elements.get(1).getAttributeValue("id"));
        assertEquals("input-9", elements.get(2).getAttributeValue("id"));
        assertEquals("input-10", elements.get(3).getAttributeValue("id"));
        assertEquals("input-12", elements.get(4).getAttributeValue("id"));
    }
	
	@Test
    public void checked() throws Exception {
		List<Element> elements = $(source, ":checked").getSelectedElements();
        assertEquals(2, elements.size());
        assertEquals("input-1", elements.get(0).getAttributeValue("id"));
        assertEquals("input-8", elements.get(1).getAttributeValue("id"));
        
        elements = $(source, "input[type='checkbox']:not(:checked),"
        		+ "input[type='radio']:not(:checked),"
        		+ "option:not(:checked)").getSelectedElements();
        assertEquals(2, elements.size());
        assertEquals("input-2", elements.get(0).getAttributeValue("id"));
        assertEquals("input-9", elements.get(1).getAttributeValue("id"));
        
        elements = $(source, "option:checked").getSelectedElements();
        assertEquals(1, elements.size());
        
    }
	
	@Test
    public void none() throws Exception {
		List<Element> elements = $(source, ":checked2").getSelectedElements();
        assertEquals(0, elements.size());
    }
	
}
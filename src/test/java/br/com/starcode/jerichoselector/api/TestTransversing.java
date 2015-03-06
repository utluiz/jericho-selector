package br.com.starcode.jerichoselector.api;

import static br.com.starcode.jerichoselector.jerQuery.$;
import static org.junit.Assert.*;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.junit.Before;
import org.junit.Test;

import br.com.starcode.jerichoselector.IEachFunction;
import br.com.starcode.jerichoselector.IMapFunction;
import br.com.starcode.jerichoselector.jerQuery;

public class TestTransversing {
	
	Source source;
	
	@Before
	public void setup() throws Exception {
		source = new Source(getClass().getResourceAsStream("test-05.html"));
	}
	
	@Test
	public void find() throws Exception {
		jerQuery jq = $(source, "h1");
        assertEquals(2, jq.length());
        assertEquals(2,  jq.find("strong").length());
        
        jq = $(source, "ul").find("li");
        assertEquals(6, jq.length());
	}
    
	@Test
	public void add() throws Exception {
		jerQuery jq1 = $(source, "h1");
		jerQuery jq2 = $(source, "li");
		assertEquals(2, jq1.length());
		
        jerQuery jq = jq1.add(jq2.get(0));
        assertEquals(3, jq.length());
        jq = jq.add(jq2.get(0));
        assertEquals(3, jq.length());
        
        jq = jq.add(jq2.get(1));
        assertEquals(4, jq.length());
        
        jq = jq.add(jq2.getSelectedElements());
        assertEquals(8, jq.length());
	}
    
	@Test
	public void addBack() throws Exception {
		jerQuery jq1 = $(source, "html");
		jq1 = jq1.find("h1");
		assertEquals(2, jq1.length());
		
		jq1 = jq1.addBack();
		assertEquals(3, jq1.length());
		
		jq1 = jq1.addBack("*");
		assertEquals(3, jq1.length());
		
		assertEquals("html", jq1.get(0).getName());
		assertEquals("h1", jq1.get(1).getName());
		assertEquals("h1", jq1.get(2).getName());
		
		jq1 = $(source, "html");
		assertEquals(1, jq1.length());
		jq1 = jq1.addBack();
		assertEquals(1, jq1.length());
		
		jerQuery jq = $(source, "ul").find("li");
		assertEquals(6, jq.length());
		jq = jq.addBack("#ul2");
		assertEquals(7, jq.length());
		jq = jq.addBack("#ul1");
		assertEquals(7, jq.length());
		
	}
    
	@Test
	public void children() throws Exception {
		jerQuery jq = $(source, "html").children();
		assertEquals(2, jq.length());
		
		assertEquals("head", jq.get(0).getName());
		assertEquals("body", jq.get(1).getName());
		
		jq = $(source, "html").children("body");
		assertEquals(1, jq.length());
		
		assertEquals("body", jq.get(0).getName());
	}
    
	@Test
	public void contents() throws Exception {
		//jerQuery jq = $(source, "h1:first");
		//assertEquals("t1", jq.contents());
		//fail();
	}
    
	@Test
	public void each() throws Exception {
		final String[] ids = new String[2];
		$(source, "h1").each(new IEachFunction() {
			@Override
			public void execute(int index, Element e) {
				ids[index - 1] = e.getAttributeValue("id");
			}
		});
		
		assertEquals("t1", ids[0]);
		assertEquals("t2", ids[1]);
	}
    
	@Test
	public void end() throws Exception {
		jerQuery jq = $(source, "ul").find("li").end();
		assertEquals(2, jq.length());
		assertEquals("ul1", jq.attr("id"));
	}
    
	@Test
	public void eq() throws Exception {
		jerQuery jq = $(source, "li");
		assertEquals(0, jq.eq(-1).length());
		assertEquals(0, jq.eq(0).length());
		assertEquals(1, jq.eq(1).length());
		assertEquals(1, jq.eq(6).length());
		assertEquals(0, jq.eq(7).length());
		assertEquals("li1-1", jq.eq(1).attr("id"));
	}
    
	@Test
	public void first() throws Exception {
		jerQuery jq = $(source, "h1").first();
        assertEquals(1, jq.length());
        assertEquals("t1", jq.attr("id"));

		jq = $(source, "body > h1").first();
        assertEquals(1, jq.length());
        assertEquals("t1", jq.attr("id"));

		jq = $(source, "li").first();
        assertEquals(1, jq.length());
        assertEquals("li1-1", jq.attr("id"));
        
        jq = $(source, "li li").first();
        assertEquals(1, jq.length());
        assertEquals("li2-1", jq.attr("id"));
	}
    
	@Test
	public void filter() throws Exception {
		jerQuery jq = $(source, "ul").filter("#ul2");
        assertEquals(1, jq.length());
        
        jq = $(source, "ul").filter("#ul2");
        assertEquals(1, jq.length());
	}
    
	@Test
	public void has() throws Exception {
		jerQuery jq = $(source, "ul");
		assertEquals(0, jq.has("body").length());
		assertEquals(1, jq.has("h1").length());
		assertEquals(2, jq.has("li").length());
	}
    
	@Test
	public void is() throws Exception {
		jerQuery jq = $(source, "h1");
		assertTrue(jq.is("h1"));
		assertTrue(jq.is("#t1"));
		
		assertTrue(jq.eq(1).is("#t1"));
		assertFalse(jq.eq(2).is("#t1"));
		
		assertFalse(jq.eq(1).is("#t2"));
		assertTrue(jq.eq(2).is("#t2"));
	}
    
	@Test
	public void closest() throws Exception {
		jerQuery jq = $(source, "#li2-3");
		assertEquals("li2-3", jq.closest("li").attr("id"));
		assertEquals("ul2", jq.closest("ul").attr("id"));
		assertEquals(1, jq.closest("body").length());
		assertEquals(0, jq.closest("strong").length());
	}
    
	@Test
	public void last() throws Exception {
		jerQuery jq = $(source, "h1").last();
        assertEquals(1, jq.length());
        assertEquals("t2", jq.attr("id"));

		jq = $(source, "body > h1").last();
        assertEquals(1, jq.length());
        assertEquals("t1", jq.attr("id"));

		jq = $(source, "li").last();
        assertEquals(1, jq.length());
        assertEquals("li2-3", jq.attr("id"));
	}
	
	@Test
	public void map() throws Exception {
		jerQuery jq = $(source, "h1").map(new IMapFunction() {
			@Override
			public boolean map(int index, Element e) {
				return "t2".equals(e.getAttributeValue("id"));
			}
		});
		assertEquals(1, jq.length());
		assertEquals("t2", jq.attr("id"));
	}
    
}

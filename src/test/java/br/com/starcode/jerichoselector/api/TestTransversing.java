package br.com.starcode.jerichoselector.api;

import static br.com.starcode.jerichoselector.jerQuery.$;
import static org.junit.Assert.assertEquals;
import net.htmlparser.jericho.Source;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.starcode.jerichoselector.jerQuery;

public class TestTransversing {
	
	Source source;
	
	@Before
	public void setup() throws Exception {
		source = new Source(getClass().getResourceAsStream("test-05.html"));
	}
	
	@Test
	public void find() throws Exception {
		Assert.fail();
	}
    
	@Test
	public void add() throws Exception {
		Assert.fail();
	}
    
	@Test
	public void addBack() throws Exception {
		Assert.fail();
	}
    
	@Test
	public void children() throws Exception {
		Assert.fail();
	}
    
	@Test
	public void contents() throws Exception {
		Assert.fail();
	}
    
	@Test
	public void each() throws Exception {
		Assert.fail();
	}
    
	@Test
	public void end() throws Exception {
		Assert.fail();
	}
    
	@Test
	public void eq() throws Exception {
		Assert.fail();
	}
    
	@Test
	public void first() throws Exception {
		jerQuery jq = $(source, "h1").first();
        assertEquals(1, jq.length());
        assertEquals("t1", jq.get(0).getAttributeValue("id"));

		jq = $(source, "body > h1").first();
        assertEquals(1, jq.length());
        assertEquals("t1", jq.get(0).getAttributeValue("id"));

		jq = $(source, "li").first();
        assertEquals(1, jq.length());
        assertEquals("li1-1", jq.get(0).getAttributeValue("id"));
        
        jq = $(source, "li li").first();
        assertEquals(1, jq.length());
        assertEquals("li2-1", jq.get(0).getAttributeValue("id"));
	}
    
	@Test
	public void filter() throws Exception {
		Assert.fail();
	}
    
	@Test
	public void has() throws Exception {
		Assert.fail();
	}
    
	@Test
	public void is() throws Exception {
		Assert.fail();
	}
    
	@Test
	public void closest() throws Exception {
		Assert.fail();
	}
    
	@Test
	public void last() throws Exception {
		jerQuery jq = $(source, "h1").last();
        assertEquals(1, jq.length());
        assertEquals("t2", jq.get(0).getAttributeValue("id"));

		jq = $(source, "body > h1").last();
        assertEquals(1, jq.length());
        assertEquals("t1", jq.get(0).getAttributeValue("id"));

		jq = $(source, "li").last();
        assertEquals(1, jq.length());
        assertEquals("li2-3", jq.get(0).getAttributeValue("id"));
	}
	
	@Test
	public void map() throws Exception {
		Assert.fail();
	}
    
}

package br.com.starcode.jerichoselector.matcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.junit.Before;
import org.junit.Test;

import br.com.starcode.parccser.model.AttributeOperator;
import br.com.starcode.parccser.model.AttributeSelector;
import br.com.starcode.parccser.model.ClassSelector;
import br.com.starcode.parccser.model.HashSelector;
import br.com.starcode.parccser.model.StringValue;
import br.com.starcode.parccser.model.TypeSelector;

public class TestMatchers {
	
	Source source;
	List<Element> elements;
	MatcherRegistry matcherRegistry;
	
	@Before
	public void setup() throws IOException {
		source = new Source(getClass().getResourceAsStream("matcher-test.html"));
		elements = source.getAllElementsByClass("class1");
		matcherRegistry = new MatcherRegistry();
		assertEquals(4, elements.size());
	}

	@Test
	public void typeMatcher() throws Exception {
		
		TypeSelector ts = new TypeSelector("p", null);
		assertTrue(matcherRegistry.get(ts).matches(elements.get(0), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(1), ts));
		assertTrue(matcherRegistry.get(ts).matches(elements.get(2), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(3), ts));
		
		ts = new TypeSelector("span", null);
		assertFalse(matcherRegistry.get(ts).matches(elements.get(0), ts));
		assertTrue(matcherRegistry.get(ts).matches(elements.get(1), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(2), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(3), ts));
		
		ts = new TypeSelector("input", null);
		assertFalse(matcherRegistry.get(ts).matches(elements.get(0), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(1), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(2), ts));
		assertTrue(matcherRegistry.get(ts).matches(elements.get(3), ts));
		
		ts = new TypeSelector("INPUT", null);
		assertFalse(matcherRegistry.get(ts).matches(elements.get(0), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(1), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(2), ts));
		assertTrue(matcherRegistry.get(ts).matches(elements.get(3), ts));
		
	}
	
	@Test
	public void classMatcher() throws Exception {
		
		ClassSelector ts = new ClassSelector("class1", null);
		assertTrue(matcherRegistry.get(ts).matches(elements.get(0), ts));
		assertTrue(matcherRegistry.get(ts).matches(elements.get(1), ts));
		assertTrue(matcherRegistry.get(ts).matches(elements.get(2), ts));
		assertTrue(matcherRegistry.get(ts).matches(elements.get(3), ts));
		
		ts = new ClassSelector("class2", null);
		assertFalse(matcherRegistry.get(ts).matches(elements.get(0), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(1), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(2), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(3), ts));
		
		ts = new ClassSelector("Class2", null);
		assertFalse(matcherRegistry.get(ts).matches(elements.get(0), ts));
		assertTrue(matcherRegistry.get(ts).matches(elements.get(1), ts));
		assertTrue(matcherRegistry.get(ts).matches(elements.get(2), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(3), ts));
		
		ts = new ClassSelector("class3", null);
		assertFalse(matcherRegistry.get(ts).matches(elements.get(0), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(1), ts));
		assertTrue(matcherRegistry.get(ts).matches(elements.get(2), ts));
		assertTrue(matcherRegistry.get(ts).matches(elements.get(3), ts));
		
		ts = new ClassSelector("class4", null);
		assertFalse(matcherRegistry.get(ts).matches(elements.get(0), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(1), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(2), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(3), ts));
		
		ts = new ClassSelector("class", null);
		assertFalse(matcherRegistry.get(ts).matches(elements.get(0), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(1), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(2), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(3), ts));
		
	}
	
	@Test
	public void hashMatcher() throws Exception {
		
		HashSelector ts = new HashSelector("id1", null);
		assertTrue(matcherRegistry.get(ts).matches(elements.get(0), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(1), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(2), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(3), ts));
		
		ts = new HashSelector("id2", null);
		assertFalse(matcherRegistry.get(ts).matches(elements.get(0), ts));
		assertTrue(matcherRegistry.get(ts).matches(elements.get(1), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(2), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(3), ts));
		
		ts = new HashSelector("Id3", null);
		assertFalse(matcherRegistry.get(ts).matches(elements.get(0), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(1), ts));
		assertTrue(matcherRegistry.get(ts).matches(elements.get(2), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(3), ts));
		
		ts = new HashSelector("id3", null);
		assertFalse(matcherRegistry.get(ts).matches(elements.get(0), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(1), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(2), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(3), ts));
		
		ts = new HashSelector("id4", null);
		assertFalse(matcherRegistry.get(ts).matches(elements.get(0), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(1), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(2), ts));
		assertTrue(matcherRegistry.get(ts).matches(elements.get(3), ts));
		
		ts = new HashSelector("id", null);
		assertFalse(matcherRegistry.get(ts).matches(elements.get(0), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(1), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(2), ts));
		assertFalse(matcherRegistry.get(ts).matches(elements.get(3), ts));
		
	}
	
	@Test
	public void attrEqualsMatcher() throws Exception {
		
		AttributeSelector s = new AttributeSelector("disabled", null, null, null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("id", AttributeOperator.EQUALS, null, null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("id", AttributeOperator.EQUALS, new StringValue(null, "id1"), null);
		assertTrue(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("iD", AttributeOperator.EQUALS, new StringValue(null, "id1"), null);
		assertTrue(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("iD", AttributeOperator.EQUALS, new StringValue(null, "iD1"), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("id", AttributeOperator.EQUALS, new StringValue(null, ""), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("style", AttributeOperator.EQUALS, new StringValue(null, ""), null);
		assertTrue(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("disabled", AttributeOperator.EQUALS, new StringValue(null, ""), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(3), s));
		
	}
	
	@Test
	public void attrIncludesMatcher() throws Exception {
		
		AttributeSelector s = new AttributeSelector("class", AttributeOperator.INCLUDES, null, null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("class", AttributeOperator.INCLUDES, new StringValue(null, ""), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("class", AttributeOperator.INCLUDES, new StringValue(null, "class"), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("class", AttributeOperator.INCLUDES, new StringValue(null, "Class2"), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(1), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("class", AttributeOperator.INCLUDES, new StringValue(null, "class2"), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("class", AttributeOperator.INCLUDES, new StringValue(null, "class3"), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(2), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("class", AttributeOperator.INCLUDES, new StringValue(null, "class1"), null);
		assertTrue(matcherRegistry.get(s).matches(elements.get(0), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(1), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(2), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(3), s));
		
	}
	
	@Test
	public void attrDashMatcher() throws Exception {
		
		AttributeSelector s = new AttributeSelector("lang", AttributeOperator.DASH_MATCH, null, null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.DASH_MATCH, new StringValue(null, ""), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.DASH_MATCH, new StringValue(null, "e"), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.DASH_MATCH, new StringValue(null, "en"), null);
		assertTrue(matcherRegistry.get(s).matches(elements.get(0), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.DASH_MATCH, new StringValue(null, "En"), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.DASH_MATCH, new StringValue(null, "en-US"), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.DASH_MATCH, new StringValue(null, "en-U"), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.DASH_MATCH, new StringValue(null, "en-"), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
	}
	
	@Test
	public void attrPrefixMatcher() throws Exception {
		
		AttributeSelector s = new AttributeSelector("lang", AttributeOperator.PREFIX_MATCH, null, null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.PREFIX_MATCH, new StringValue(null, ""), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.PREFIX_MATCH, new StringValue(null, "e"), null);
		assertTrue(matcherRegistry.get(s).matches(elements.get(0), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.PREFIX_MATCH, new StringValue(null, "en"), null);
		assertTrue(matcherRegistry.get(s).matches(elements.get(0), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.PREFIX_MATCH, new StringValue(null, "en-"), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
	}
	
	@Test
	public void attrSubstringMatcher() throws Exception {
		
		AttributeSelector s = new AttributeSelector("lang", AttributeOperator.SUBSTRING_MATCH, null, null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.SUBSTRING_MATCH, new StringValue(null, ""), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.SUBSTRING_MATCH, new StringValue(null, "n"), null);
		assertTrue(matcherRegistry.get(s).matches(elements.get(0), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.SUBSTRING_MATCH, new StringValue(null, "US"), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.SUBSTRING_MATCH, new StringValue(null, "en"), null);
		assertTrue(matcherRegistry.get(s).matches(elements.get(0), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.SUBSTRING_MATCH, new StringValue(null, "-"), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.SUBSTRING_MATCH, new StringValue(null, "en-US"), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
	}
	
	@Test
	public void attrSuffixMatcher() throws Exception {
		
		AttributeSelector s = new AttributeSelector("lang", AttributeOperator.SUFFIX_MATCH, null, null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.SUFFIX_MATCH, new StringValue(null, ""), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.SUFFIX_MATCH, new StringValue(null, "n"), null);
		assertTrue(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.SUFFIX_MATCH, new StringValue(null, "US"), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.SUFFIX_MATCH, new StringValue(null, "en"), null);
		assertTrue(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.SUFFIX_MATCH, new StringValue(null, "-"), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
		
		s = new AttributeSelector("lang", AttributeOperator.SUFFIX_MATCH, new StringValue(null, "en-US"), null);
		assertFalse(matcherRegistry.get(s).matches(elements.get(0), s));
		assertTrue(matcherRegistry.get(s).matches(elements.get(1), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(2), s));
		assertFalse(matcherRegistry.get(s).matches(elements.get(3), s));
	}
	
}

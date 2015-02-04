package br.com.starcode.jerichoselector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import br.com.starcode.jerichoselector.matcher.MatcherRegistry;
import br.com.starcode.jerichoselector.matcher.SelectorMatcher;
import br.com.starcode.parccser.ParserException;

public class jerQuery {

    protected List<Element> selectedElements;
    protected SelectorMatcher selectorMatcher; 
    protected MatcherRegistry matcherRegistry; 
    
    /*
     * Static calls
     */
    
    public static jerQuery $(final List<Element> e, final String initialSelector) throws ParserException {
		if (e == null) {
    		throw new IllegalArgumentException("Null element list!");
    	}
		if (initialSelector == null) {
    		throw new IllegalArgumentException("Null selector!");
    	}
    	return new jerQuery(e, initialSelector);
    }
    
    public static jerQuery $(final Element e, final String initialSelector) throws ParserException {
		if (e == null) {
    		throw new IllegalArgumentException("Null element!");
    	}
		if (initialSelector == null) {
    		throw new IllegalArgumentException("Null selector!");
    	}
    	return new jerQuery(e, initialSelector);
    }
    
    public static jerQuery $(final Element[] e, final String initialSelector) throws ParserException {
		if (e == null) {
    		throw new IllegalArgumentException("Null element array!");
    	}
		if (initialSelector == null) {
    		throw new IllegalArgumentException("Null selector!");
    	}
    	return new jerQuery(e, initialSelector);
    }
    
    public static jerQuery $(final Source source, final String initialSelector) throws ParserException {
		if (source == null) {
    		throw new IllegalArgumentException("Null source!");
    	}
		if (initialSelector == null) {
    		throw new IllegalArgumentException("Null selector!");
    	}
    	return new jerQuery(source, initialSelector);
    }
    
    public static jerQuery $(String html, String initialSelector) throws ParserException {
    	if (html == null) {
    		throw new IllegalArgumentException("Null HTML code!");
    	}
		if (initialSelector == null) {
    		throw new IllegalArgumentException("Null selector!");
    	}
    	return new jerQuery(html, initialSelector);
    }
    
    public static jerQuery $(File file, String initialSelector) throws FileNotFoundException, IOException, ParserException {
    	if (file == null) {
    		throw new IllegalArgumentException("Null file!");
    	}
		if (initialSelector == null) {
    		throw new IllegalArgumentException("Null selector!");
    	}
    	return new jerQuery(file, initialSelector);
    }
    
    public static jerQuery $(InputStream input, String initialSelector) throws IOException, ParserException {
    	if (input == null) {
    		throw new IllegalArgumentException("Null input stream!");
    	}
		if (initialSelector == null) {
    		throw new IllegalArgumentException("Null selector!");
    	}
    	return new jerQuery(input, initialSelector);
    }
    
    public static jerQuery $(Reader reader, String initialSelector) throws IOException, ParserException {
    	if (reader == null) {
    		throw new IllegalArgumentException("Null reader!");
    	}
		if (initialSelector == null) {
    		throw new IllegalArgumentException("Null selector!");
    	}
    	return new jerQuery(reader, initialSelector);
    }
    
    /*
     * Constructors with only elements
     */
    
    public jerQuery(List<Element> elements) {
    	this.selectedElements = new ArrayList<Element>();
    	this.matcherRegistry = new MatcherRegistry();
    	this.selectorMatcher = new SelectorMatcher(matcherRegistry); 
        this.selectedElements.addAll(elements);
    }
    
    public jerQuery(Element[] elements) {
    	this(Arrays.asList(elements));
    }
    
    public jerQuery(Element e) {
        this(new Element[] { e });
    }
    
	public jerQuery(Source source) {
    	this(source.getChildElements());
    }
    
    public jerQuery(String html) {
        this(new Source(html));
    }
    
    public jerQuery(File file) throws FileNotFoundException, IOException {
        this(new Source(new FileInputStream(file)));
    }
    
    public jerQuery(InputStream input) throws IOException {
        this(new Source(input));
    }
    
    public jerQuery(Reader reader) throws IOException {
        this(new Source(reader));
    }
    
    /*
     * Constructors with selector
     */
    
    public jerQuery(List<Element> elements, String selector) throws ParserException {
        this(elements);
        find(selector);
    }
    
    public jerQuery(Element[] elements, String selector) throws ParserException {
    	this(elements);
    	find(selector);
    }
    
    public jerQuery(Element e, String selector) throws ParserException {
    	this(e);
    	find(selector);
    }
    
	public jerQuery(Source source, String selector) throws ParserException {
    	this(source);
    	find(selector);
    }
    
    public jerQuery(String html, String selector) throws ParserException {
        this(html);
        find(selector);
    }
    
    public jerQuery(File file, String selector) throws FileNotFoundException, IOException, ParserException {
        this(new FileInputStream(file));
        find(selector);
    }
    
    public jerQuery(InputStream input, String selector) throws IOException, ParserException {
        this(input);
        find(selector);
    }
    
    public jerQuery(Reader reader, String selector) throws IOException, ParserException {
        this(reader);
        find(selector);
    }
    
    /*
     * Aux Methods
     */
    
    protected Set<Element> applySelector(Element e, String selector) throws ParserException {
        return selectorMatcher.applySelector(e, selector);
    }
    
    /*
     * API methods
     */
    
    public jerQuery find(String selector) throws ParserException {
        List<Element> newSelectedElements = new ArrayList<Element>();
        for (Element e : selectedElements) {
            newSelectedElements.addAll(applySelector(e, selector));
        }
        this.selectedElements = newSelectedElements;
        return this;
    }
    
    protected String getText() throws IOException {
    	StringBuilder sb = new StringBuilder();
    	for (Element element : selectedElements) {
    		element.getTextExtractor().appendTo(sb);
		}
    	return sb.toString();
    }
    
    public int length() {
    	return selectedElements.size();
    }
    
    public MatcherRegistry getMatcherRegistry() {
		return matcherRegistry;
	}
    
    public void setMatcherRegistry(MatcherRegistry matcherRegistry) {
		this.matcherRegistry = matcherRegistry;
	}
    
    public List<Element> getSelectedElements() {
		return selectedElements;
	}
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	for (Element element : selectedElements) {
    		sb.append(element.toString());
		}
    	return sb.toString();
    }
    
    
}

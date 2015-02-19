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

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import br.com.starcode.jerichoselector.matcher.MatcherRegistry;
import br.com.starcode.jerichoselector.matcher.SelectorMatcher;
import br.com.starcode.parccser.ParserException;

public class jerQuery {

    protected List<Element> selectedElements;
    protected SelectorMatcher selectorMatcher; 
    protected MatcherRegistry matcherRegistry; 
    protected Source source;
    protected jerQuery previousQuery;
    
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
     * Complete constructor
     */
    public jerQuery(
    		List<Element> elements,
    		MatcherRegistry matcherRegistry,
    		SelectorMatcher selectorMatcher,
    		Source source,
    		jerQuery previousQuery) {
    	this.selectedElements = new ArrayList<Element>(elements);
    	this.matcherRegistry = matcherRegistry;
    	this.selectorMatcher = selectorMatcher; 
    	this.source = source; 
    	this.previousQuery = previousQuery; 
    }
    
    /*
     * Constructors with only elements
     */
    public jerQuery(List<Element> elements) {
    	if (elements == null || elements.isEmpty()) {
    		throw new IllegalArgumentException("No elements informed!");
    	}
    	if (hasNullElement(elements)) {
    		throw new IllegalArgumentException("List has null elements!");
    	}
    	this.selectedElements = new ArrayList<Element>(elements);
    	this.matcherRegistry = new MatcherRegistry();
    	this.selectorMatcher = new SelectorMatcher(matcherRegistry); 
    	this.source = elements.get(0).getSource(); 
    	this.previousQuery = null; 
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
        findInitial(selector);
    }
    
    public jerQuery(Element[] elements, String selector) throws ParserException {
    	this(elements);
    	findInitial(selector);
    }
    
    public jerQuery(Element e, String selector) throws ParserException {
    	this(e);
    	findInitial(selector);
    }
    
	public jerQuery(Source source, String selector) throws ParserException {
    	this(source);
    	findInitial(selector);
    }
    
    public jerQuery(String html, String selector) throws ParserException {
        this(html);
        findInitial(selector);
    }
    
    public jerQuery(File file, String selector) throws FileNotFoundException, IOException, ParserException {
        this(new FileInputStream(file));
        findInitial(selector);
    }
    
    public jerQuery(InputStream input, String selector) throws IOException, ParserException {
        this(input);
        findInitial(selector);
    }
    
    public jerQuery(Reader reader, String selector) throws IOException, ParserException {
        this(reader);
        findInitial(selector);
    }
    
    /*
     * Transversing API methods:
     * http://api.jquery.com/category/traversing/ 
     */
    
    public void findInitial(String selector) throws ParserException {
        List<Element> newSelectedElements = new ArrayList<Element>();
        for (Element e : selectedElements) {
            newSelectedElements.addAll(selectorMatcher.applySelector(e, selector));
        }
        selectedElements = newSelectedElements;
    }
    
    /**
     * Get the descendants of each element in the current set of matched elements, filtered by a selector, jQuery object, or element.
     * {@link http://api.jquery.com/find/} 
     */
    public jerQuery find(String selector) throws ParserException {
        List<Element> newSelectedElements = new ArrayList<Element>();
        for (Element e : selectedElements) {
            newSelectedElements.addAll(selectorMatcher.applySelector(e, selector));
        }
        return new jerQuery(newSelectedElements, matcherRegistry, selectorMatcher, source, this);
    }
    
    /**
     * Find elements starting from root element
     * {@link http://api.jquery.com/find/} 
     */
    protected jerQuery findFromRoot(String selector) throws ParserException {
        List<Element> newSelectedElements = new ArrayList<Element>();
        for (Element e : source.getChildElements()) {
            newSelectedElements.addAll(selectorMatcher.applySelector(e, selector));
        }
        return new jerQuery(newSelectedElements, matcherRegistry, selectorMatcher, source, null);
    }
    
    /**
     * Create a new jQuery object with elements added to the set of matched elements.
     * {@link http://api.jquery.com/add/} 
     */
    public jerQuery add(Element e) throws ParserException {
        List<Element> newSelectedElements = new ArrayList<Element>(getSelectedElements());
        newSelectedElements.add(e);
        return new jerQuery(newSelectedElements, matcherRegistry, selectorMatcher, source, this);
    }
    
    /**
     * Create a new jQuery object with elements added to the set of matched elements.
     * {@link http://api.jquery.com/add/} 
     */
    public jerQuery add(List<Element> list) throws ParserException {
        List<Element> newSelectedElements = new ArrayList<Element>(getSelectedElements());
        newSelectedElements.addAll(list);
        return new jerQuery(newSelectedElements, matcherRegistry, selectorMatcher, source, this);
    }
    
    /**
     * Add the previous set of elements on the stack to the current set, optionally filtered by a selector.
     * {@link http://api.jquery.com/addBack/} 
     */
    public jerQuery addBack() throws ParserException {
        List<Element> newSelectedElements = new ArrayList<Element>(getSelectedElements());
        if (getPreviousQuery() != null) {
        	newSelectedElements.addAll(getPreviousQuery().getSelectedElements());
        }
        return new jerQuery(newSelectedElements, matcherRegistry, selectorMatcher, source, this);
    }
    
    /**
     * Add the previous set of elements on the stack to the current set, optionally filtered by a selector.
     * {@link http://api.jquery.com/addBack/} 
     */
    public jerQuery addBack(String selector) throws ParserException {
        List<Element> newSelectedElements = new ArrayList<Element>(getSelectedElements());
        if (getPreviousQuery() != null) {
        	newSelectedElements.addAll(getPreviousQuery().filter(selector).getSelectedElements());
        }
        return new jerQuery(newSelectedElements, matcherRegistry, selectorMatcher, source, this);
    }
    
    /**
     * Get the children of each element in the set of matched elements, optionally filtered by a selector.
     * {@link http://api.jquery.com/children/} 
     * TODO exclude text nodes
     */
    public jerQuery children() throws ParserException {
        List<Element> newSelectedElements = new ArrayList<Element>();
    	for (Element element : getSelectedElements()) {
			newSelectedElements.addAll(element.getChildElements());
		}
        return new jerQuery(newSelectedElements, matcherRegistry, selectorMatcher, source, this);
    }
    
    /**
     * Get the children of each element in the set of matched elements, optionally filtered by a selector.
     * {@link http://api.jquery.com/children/} 
     * TODO exclude text nodes
     */
    public jerQuery children(String selector) throws ParserException {
        List<Element> newSelectedElements = new ArrayList<Element>();
        jerQuery jq = findFromRoot(selector);
    	for (Element element : getSelectedElements()) {
    		for (Element child : element.getChildElements()) {
	    		if (jq.getSelectedElements().contains(child)) {
	    			newSelectedElements.add(child);
	    		}
    		}
		}
        return new jerQuery(newSelectedElements, matcherRegistry, selectorMatcher, source, this);
    }
    
    /**
     * Get the children of each element in the set of matched elements, including text and comment nodes.
     * {@link http://api.jquery.com/contents/} 
     */
    public jerQuery contents() throws ParserException {
        List<Element> newSelectedElements = new ArrayList<Element>();
    	for (Element element : getSelectedElements()) {
			newSelectedElements.addAll(element.getChildElements());
		}
        return new jerQuery(newSelectedElements, matcherRegistry, selectorMatcher, source, this);
    }
    
    /**
     * Iterate over a jQuery object, executing a function for each matched element.
     * {@link http://api.jquery.com/each/} 
     */
    public jerQuery each(IEachFunction function) throws ParserException {
    	int index = 0;
    	for (Element element : getSelectedElements()) {
    		function.execute(index++, element);
		}
        return this;
    }
    
    /**
     * End the most recent filtering operation in the current chain and return the set of matched elements to its previous state.
     * {@link http://api.jquery.com/end/} 
     */
    public jerQuery end() throws ParserException {
        return getPreviousQuery();
    }
    
    /**
     * Reduce the set of matched elements to the one at the specified index.
     * {@link http://api.jquery.com/eq/} 
     */
    public jerQuery eq(int index) throws ParserException {
    	if (index < 0) {
    		index = length() - index;
    	} else {
    		index--;
    	}
    	List<Element> newSelectedElements = new ArrayList<Element>();
    	if (index >= 0 && index < length()) {
    		newSelectedElements.add(getSelectedElements().get(index));
    	}
    	return new jerQuery(newSelectedElements, matcherRegistry, selectorMatcher, source, this);
    }
    
    /**
     * Reduce the set of matched elements to those that match the selector or pass the function’s test.
     * {@link http://api.jquery.com/first/} 
     */
    public jerQuery first() throws ParserException {
    	return eq(1);
    }
    
    
    /**
     * Reduce the set of matched elements to those that match the selector or pass the function's test.
     * {@link http://api.jquery.com/filter/} 
     */
    public jerQuery filter(String selector) throws ParserException {
        List<Element> newSelectedElements = new ArrayList<Element>();
        jerQuery jq = findFromRoot(selector);
        if (getPreviousQuery() != null) {
        	for (Element element : getPreviousQuery().getSelectedElements()) {
    			if (jq.getSelectedElements().contains(element)) {
    				newSelectedElements.add(element);
    			}
    		}
        }
        return new jerQuery(newSelectedElements, matcherRegistry, selectorMatcher, source, this);
    }
    
    /**
     * Reduce the set of matched elements to those that have a descendant that matches the selector or DOM element.
     * @see {@link http://api.jquery.com/has/} 
     */
    public jerQuery has(String selector) throws ParserException {
    	jerQuery jq = findFromRoot(selector);
    	List<Element> newSelectedElements = new ArrayList<Element>();
    	for (Element element : getSelectedElements()) {
			if (jq.getSelectedElements().contains(element)) {
				newSelectedElements.add(element);
			}
		}
    	return new jerQuery(newSelectedElements, matcherRegistry, selectorMatcher, source, this);
    }
    
    /**
     * Check the current matched set of elements against a selector, element, or jQuery object and return true if at least one of these elements matches the given arguments.
     * @see {@link http://api.jquery.com/is/} 
     */
    public boolean is(String selector) throws ParserException {
    	jerQuery jq = findFromRoot(selector);
    	for (Element element : getSelectedElements()) {
			if (jq.getSelectedElements().contains(element)) {
				return true;
			}
		}
    	return false;
    }

    /**
     * For each element in the set, get the first element that matches the selector by testing the element itself and traversing up through its ancestors in the DOM tree.
     * @see {@link http://api.jquery.com/closest/} 
     */
    public jerQuery closest(String selector) throws ParserException {
    	List<Element> newSelectedElements = new ArrayList<Element>();
    	jerQuery jq = findFromRoot(selector);
    	for (Element e : getSelectedElements()) {
    		Element parent = e.getParentElement();
    		while (parent != null) {
    			if (jq.getSelectedElements().contains(parent)) {
    				newSelectedElements.add(parent);
    				break;
    			}
    			parent = parent.getParentElement();
    		}
    	}
    	return new jerQuery(newSelectedElements, matcherRegistry, selectorMatcher, source, this);
    }
    
    /**
     * Reduce the set of matched elements to the final one in the set.
     * {@link http://api.jquery.com/last/} 
     */
    public jerQuery last() throws ParserException {
    	return eq(length());
    }
    
    /**
     * Pass each element in the current matched set through a function, producing a new jQuery object containing the return values.
     * {@link http://api.jquery.com/map/} 
     */
    public jerQuery map(IMapFunction function) throws ParserException {
    	List<Element> newSelectedElements = new ArrayList<Element>();
    	int index = 0;
    	for (Element element : getSelectedElements()) {
    		if (function.map(index++, element)) {
    			newSelectedElements.add(element);
    		}
		}
    	return new jerQuery(newSelectedElements, matcherRegistry, selectorMatcher, source, this);
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
    
    public Element get(int index) {
    	return selectedElements.get(index);
    }
    
    public Source getSource() {
		return source;
	}
    
    public jerQuery getPreviousQuery() {
		return previousQuery;
	}
    
    protected boolean hasNullElement(List<Element> elements) {
    	for (Element element : elements) {
			if (element == null) {
				return true;
			}
		}
    	return false;
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

package br.com.starcode.jerichoselector.matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.htmlparser.jericho.Element;
import br.com.starcode.parccser.Parser;
import br.com.starcode.parccser.ParserException;
import br.com.starcode.parccser.model.Combinator;
import br.com.starcode.parccser.model.Selector;
import br.com.starcode.parccser.model.SimpleSelector;
import br.com.starcode.parccser.model.SimpleSelectorSequence;

public class SelectorMatcher {
	
	private MatcherRegistry matcherRegistry;
	
	public SelectorMatcher(MatcherRegistry matcherRegistry) {
		if (matcherRegistry == null) {
			throw new IllegalArgumentException("Matcher cannot be null!");
		}
		this.matcherRegistry = matcherRegistry;
	}

	public Set<Element> applySelector(Element e, String selectorStr) throws ParserException {
		List<Selector> selectors = Parser.parse(selectorStr);
		Set<Element> elements = new TreeSet<Element>();
		for (Selector selector : selectors) {
			elements.addAll(matchSelector(e, selector));
		}
		return elements;
	}
	
	protected Set<Element> matchSelector(Element e, Selector selector) {
		int i = 0;
		Set<Element> elements = new TreeSet<Element>();
		for (SimpleSelectorSequence simpleSelectorSequence : selector.getSelectors()) {
			if (i == 0) {
				elements = matchSimpleSelectorSequence(e, simpleSelectorSequence);
			} else {
				Combinator combinator = selector.getCombinators().get(i - 1);
				elements = matchSimpleSelectorSequence(e, simpleSelectorSequence, combinator, elements);
			}
			i++;
		}
		return elements;
	}
	
	protected Set<Element> matchSimpleSelectorSequence(
			Element e, 
			SimpleSelectorSequence simpleSelectorSequence) {
		List<SimpleSelector> simpleSelectors = simpleSelectorSequence.getSimpleSelectors();
		return walkAndMatchSimpleSelectorList(e, simpleSelectors, e.getAllElements());
	}
	
	protected Set<Element> matchSimpleSelectorSequence(
			Element e, 
			SimpleSelectorSequence simpleSelectorSequence, 
			Combinator combinator,
			Set<Element> elements) {
		List<SimpleSelector> simpleSelectors = simpleSelectorSequence.getSimpleSelectors();
		Set<Element> returnedElements = new TreeSet<Element>();
		for (Element element : elements) {
			walkAndCombineSimpleSelectorList(element, simpleSelectors, combinator, returnedElements);
		}
		return returnedElements;
	}
	
	protected Set<Element> walkAndMatchSimpleSelectorList(
			Element e, 
			List<SimpleSelector> simpleSelectors, 
			List<Element> elements) {
		Set<Element> resultElements = new TreeSet<Element>();
		for (Element element : elements) {
			if (matchSimpleSelectorList(element, simpleSelectors)) {
				resultElements.add(element);
			}
		}
		return resultElements;
	}
	
	protected void walkAndCombineSimpleSelectorList(
			Element e, 
			List<SimpleSelector> simpleSelectors,
			Combinator combinator,
			Set<Element> elements) {
		
		List<Element> combinedElements = new ArrayList<Element>();
		switch (combinator) {
		case DESCENDANT:
			combinedElements.addAll(e.getAllElements());
			combinedElements.remove(e);
			break;
		case CHILD:
			combinedElements.addAll(e.getChildElements());
			break;
		case ADJASCENT_SIBLING:
			if (e.getParentElement() != null) {
				List<Element> siblings = e.getParentElement().getChildElements();
				int pos = siblings.indexOf(e);
				if (pos < siblings.size() - 1) {
					combinedElements.add(siblings.get(pos + 1));
				}
			}
			break;
		case GENERAL_SIBLING:
			if (e.getParentElement() != null) {
				combinedElements.addAll(e.getParentElement().getChildElements());
				combinedElements.remove(e);
			}
			break;
		default:
			return;
		}
		
		elements.addAll(walkAndMatchSimpleSelectorList(e, simpleSelectors, combinedElements));
	}
	
	protected boolean matchSimpleSelectorList(
			Element e, 
			List<SimpleSelector> simpleSelectors) {
		for (SimpleSelector simpleSelector : simpleSelectors) {
			if (!matcherRegistry.matches(e, simpleSelector)) {
				return false;
			}
		}
		return true;
	}
	
}

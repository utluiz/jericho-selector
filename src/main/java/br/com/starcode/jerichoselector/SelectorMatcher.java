package br.com.starcode.jerichoselector;

import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import br.com.starcode.parccser.Parser;
import br.com.starcode.parccser.ParserException;
import br.com.starcode.parccser.model.Selector;
import br.com.starcode.parccser.model.SimpleSelectorSequence;

public class SelectorMatcher {

	public List<Element> applySelector(Element e, String selectorStr) throws ParserException {
		List<Selector> selectors = Parser.parse(selectorStr);
		List<Element> elements = new ArrayList<Element>();
		for (Selector selector : selectors) {
			matchSelector(e, selector, elements);
		}
		return elements;
	}
	
	public List<Element> matchSelector(Element e, Selector selector, List<Element> elements) {
		int i = 0;
		for (SimpleSelectorSequence simpleSelectorSequence : selector.getSelectors()) {
			simpleSelectorSequence.getSimpleSelectors();
			i++;
		}
		return null;
	}
	
}

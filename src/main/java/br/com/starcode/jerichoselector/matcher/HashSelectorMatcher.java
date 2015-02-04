package br.com.starcode.jerichoselector.matcher;

import net.htmlparser.jericho.Element;
import br.com.starcode.parccser.model.HashSelector;

public class HashSelectorMatcher implements SimpleSelectorMatcher<HashSelector> {

	public boolean matches(Element e, HashSelector simpleSelector) {
		String id = e.getAttributeValue("id");
		return id != null && e.getAttributeValue("id").equals(simpleSelector.getName());
	}

}

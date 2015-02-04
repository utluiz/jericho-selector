package br.com.starcode.jerichoselector.matcher;

import net.htmlparser.jericho.Element;
import br.com.starcode.parccser.model.TypeSelector;

public class TypeSelectorMatcher implements SimpleSelectorMatcher<TypeSelector> {

	public boolean matches(Element e, TypeSelector simpleSelector) {
		return simpleSelector.isUniversal() || e.getName().equalsIgnoreCase(simpleSelector.getType());
	}

}

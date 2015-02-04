package br.com.starcode.jerichoselector.matcher;

import net.htmlparser.jericho.Element;
import br.com.starcode.parccser.model.SimpleSelector;

public interface SimpleSelectorMatcher<T extends SimpleSelector> {
	
	boolean matches(Element e, T simpleSelector);

}

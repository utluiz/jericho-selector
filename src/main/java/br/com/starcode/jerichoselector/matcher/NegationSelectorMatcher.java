package br.com.starcode.jerichoselector.matcher;

import net.htmlparser.jericho.Element;
import br.com.starcode.parccser.model.NegationSelector;

public class NegationSelectorMatcher implements SimpleSelectorMatcher<NegationSelector> {
	
	MatcherRegistry matcherRegistry = new MatcherRegistry();
	
	public NegationSelectorMatcher(MatcherRegistry matcherRegistry) {
		this.matcherRegistry = matcherRegistry;
	}

	public boolean matches(Element e, NegationSelector simpleSelector) {
		return !matcherRegistry.get(simpleSelector.getSimpleSelector()).matches(e, simpleSelector);
	}

}

package br.com.starcode.jerichoselector.matcher;

import java.util.HashMap;
import java.util.Map;

import net.htmlparser.jericho.Element;
import br.com.starcode.parccser.model.AttributeSelector;
import br.com.starcode.parccser.model.ClassSelector;
import br.com.starcode.parccser.model.HashSelector;
import br.com.starcode.parccser.model.SimpleSelector;
import br.com.starcode.parccser.model.TypeSelector;

public class MatcherRegistry {
	
	protected Map<Class<? extends SimpleSelector>, SimpleSelectorMatcher<? extends SimpleSelector>> simpleSelectorMatchers;
	
	public MatcherRegistry() {
		simpleSelectorMatchers = new HashMap<Class<? extends SimpleSelector>, SimpleSelectorMatcher<? extends SimpleSelector>>();
		simpleSelectorMatchers.put(AttributeSelector.class, new AttributeSelectorMatcher());
		simpleSelectorMatchers.put(HashSelector.class, new HashSelectorMatcher());
		simpleSelectorMatchers.put(TypeSelector.class, new TypeSelectorMatcher());
		simpleSelectorMatchers.put(ClassSelector.class, new ClassSelectorMatcher());
	}
	
	public <T extends SimpleSelector> SimpleSelectorMatcher<T> get(T simpleSelector) {
		if (simpleSelector == null) {
			throw new IllegalArgumentException("Parameter cannot be null!");
		}
		@SuppressWarnings("unchecked")
		SimpleSelectorMatcher<T> matcher = (SimpleSelectorMatcher<T>) simpleSelectorMatchers.get(simpleSelector.getClass());
		if (matcher == null) {
			throw new IllegalArgumentException("Matcher not exist for type " + simpleSelector.getClass().getName() + "!");
		}
		return matcher;
	}
	
	public <T extends SimpleSelector> boolean matches(Element e, T simpleSelector) {
		SimpleSelectorMatcher<T> matcher = get(simpleSelector);
		return matcher.matches(e, simpleSelector);
	}

}

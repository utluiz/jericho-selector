package br.com.starcode.jerichoselector.matcher;

import java.util.HashMap;
import java.util.Map;

import net.htmlparser.jericho.Element;
import br.com.starcode.parccser.model.AttributeSelector;
import br.com.starcode.parccser.model.ClassSelector;
import br.com.starcode.parccser.model.HashSelector;
import br.com.starcode.parccser.model.NegationSelector;
import br.com.starcode.parccser.model.PseudoSelector;
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
		simpleSelectorMatchers.put(NegationSelector.class, new NegationSelectorMatcher(this));
		simpleSelectorMatchers.put(PseudoSelector.class, new PseudoSelectorMatcher());
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
	
	public <T extends SimpleSelector> void register(SimpleSelectorMatcher<T> matcher, T simpleSelector) {
		if (matcher == null || simpleSelector == null) {
			throw new IllegalArgumentException("Null parameter!");
		}
		simpleSelectorMatchers.put(simpleSelector.getClass(), matcher);
	}

}

package br.com.starcode.jerichoselector.matcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.htmlparser.jericho.Element;
import br.com.starcode.jerichoselector.matcher.util.PseudoExpressionResolver;
import br.com.starcode.parccser.model.PseudoSelector;
import br.com.starcode.parccser.model.PseudoType;

public class PseudoSelectorMatcher implements SimpleSelectorMatcher<PseudoSelector> {

	PseudoExpressionResolver resolver = new PseudoExpressionResolver();
	
	/**
	 * http://www.w3.org/TR/css3-selectors/#selectors
	 * TODO test indexes to not fail
	 */
	public boolean matches(Element e, PseudoSelector simpleSelector) {
		
		String name = simpleSelector.getName();
		if (PseudoType.PSEUDO_CLASS.equals(simpleSelector.getType())) {
			
			try {
				if ("root".equals(name)) {

					assertNullExpression(simpleSelector);
					return e.equals(e.getSource().getChildElements().get(0));
					
				} else if ("nth-child".equals(name)) {
					
					return resolver.match(e, getSiblings(e), simpleSelector, false);
					
				} else if ("nth-last-child".equals(name)) {
					
					return resolver.match(e, getSiblings(e), simpleSelector, true);
					
				} else if ("nth-of-type".equals(name)) {
					
					return resolver.match(e, getSiblingsOfType(e), simpleSelector, false);
					
				} else if ("nth-last-of-type".equals(name)) {
					
					return resolver.match(e, getSiblingsOfType(e), simpleSelector, true);
					
				} else if ("first-child".equals(name)) {
					
					assertNullExpression(simpleSelector);
					return e.getParentElement() == null || e.equals(getSibling(e, 1, false));
					
				} else if ("last-child".equals(name)) {
					
					assertNullExpression(simpleSelector);
					return e.getParentElement() == null || e.equals(getSibling(e, 1, true));
					
				} else if ("first-of-type".equals(name)) {
					
					assertNullExpression(simpleSelector);
					return e.getParentElement() == null 
							|| e.equals(getSiblingOfType(e, 1, false));
					
				} else if ("last-of-type".equals(name)) {
					
					assertNullExpression(simpleSelector);
					return e.getParentElement() == null 
							|| e.equals(getSiblingOfType(e, 1, true));
					
				} else if ("only-child".equals(name)) {
					
					assertNullExpression(simpleSelector);
					return e.getParentElement() == null || 
							e.getParentElement().getChildElements().size() == 1; 
					
				} else if ("only-of-type".equals(name)) {
					
					assertNullExpression(simpleSelector);
					if (e.getParentElement() == null) {
						return true;
					}
					return getSiblingsOfType(e).size() == 1;
					
				} else if ("empty".equals(name)) {
					
					assertNullExpression(simpleSelector);
					return e.getChildElements().size() == 0 && e.isEmpty();
					
				/*
				 * } else if ("link".equals(name)) {
					
					//TODO
					
				} else if ("target".equals(name)) {
					
					//TODO
					
				} else if ("lang".equals(name)) {
					
					//TODO
				 */					
				} else if ("enabled".equals(name)) {
					
					assertNullExpression(simpleSelector);
					return isUserInterfaceElementCanBeDisabled(e) && e.getAttributes().get("disabled") == null;
					
				} else if ("disabled".equals(name)) {
					
					assertNullExpression(simpleSelector);
					return isUserInterfaceElementCanBeDisabled(e) && e.getAttributes().get("disabled") != null;
					
				} else if ("checked".equals(name)) {
					
					assertNullExpression(simpleSelector);
					return isUserInterfaceElementChecked(e);
					
				}
				
			} catch (InvalidPseudoExpression ex) {
				//TODO handle exception
			}
				
		} else if (PseudoType.PSEUDO_ELEMENT.equals(simpleSelector.getType())) {
			//nothing to do
		}
		return false;
		
	}
	
	protected List<Element> getSiblings(Element e) {
		if (e.getParentElement() == null) {
			return Arrays.asList(new Element[] { e });
		}
		return e.getParentElement().getChildElements();
	}
	
	protected List<Element> getSiblingsOfType(Element e) {
		if (e.getParentElement() == null) {
			return Arrays.asList(new Element[] { e });
		}
		List<Element> children = new ArrayList<Element>(e.getParentElement().getChildElements());
		for (Iterator<Element> it = children.iterator(); it.hasNext();) {
			if (!e.getName().equals(it.next().getName())) it.remove();
		}
		return children;
	}
	
	protected Element getSibling(Element e, int position, boolean reverse) {
		List<Element> children = e.getParentElement().getChildElements();
		if (position > 0 && position <= children.size()) {
			return children.get(reverse ? (children.size() - position) : (position - 1));
		}
		return null;
	}
	
	protected Element getSiblingOfType(Element e, int position, boolean reverse) {
		List<Element> children = getSiblingsOfType(e);
		if (position > 0 && position <= children.size()) {
			return children.get(reverse ? (children.size() - position) : (position - 1));
		}
		return null;
	}
	
	protected void assertNullExpression(PseudoSelector simpleSelector) {
		if (simpleSelector.getExpression() != null) {
			throw new InvalidPseudoExpression("Expression not expected", simpleSelector);
		}
	}
	
	/**
	 * http://www.w3.org/TR/html5/disabled-elements.html
	 */
	protected boolean isUserInterfaceElementCanBeDisabled(Element e) {
		return "a".equals(e.getName())
				|| "area".equals(e.getName())
				|| "link".equals(e.getName())
				|| "input".equals(e.getName())
				|| "button".equals(e.getName())
				|| "select".equals(e.getName())
				|| "textarea".equals(e.getName())
				|| "optgroup".equals(e.getName())
				|| "option".equals(e.getName())
				|| "fieldset".equals(e.getName());
	}
	
	/**
	 * http://www.w3.org/TR/html5/disabled-elements.html
	 */
	protected boolean isUserInterfaceElementChecked(Element e) {
		String type = e.getAttributeValue("type");
		if ("input".equals(e.getName())) {
			return e.getAttributes().get("checked") != null 
					&& ("checkbox".equals(type) || "radio".equals(type)); 
		}
		return "option".equals(e.getName()) && e.getAttributes().get("selected") != null;
	}
	
}

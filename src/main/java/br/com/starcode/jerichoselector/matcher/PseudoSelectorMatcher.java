package br.com.starcode.jerichoselector.matcher;

import java.util.List;

import net.htmlparser.jericho.Element;
import br.com.starcode.parccser.model.PseudoSelector;
import br.com.starcode.parccser.model.PseudoSelector.PseudoType;

public class PseudoSelectorMatcher implements SimpleSelectorMatcher<PseudoSelector> {

	/**
	 * http://www.w3.org/TR/css3-selectors/#selectors
	 * TODO test indexes to not fail
	 */
	public boolean matches(Element e, PseudoSelector simpleSelector) {
		String name = simpleSelector.getName();
		if (PseudoType.PseudoClass.equals(simpleSelector.getType())) {
			if ("root".equals(name)) {
				return e.equals(e.getSource().getChildElements().get(0));
			} else if ("nth-child".equals(name)) {
				int index = new Integer(simpleSelector.getExpression().getItems().get(0).getValue());
				return e.equals(e.getChildElements().get(index - 1));
			} else if ("nth-last-child".equals(name)) {
				int index = new Integer(simpleSelector.getExpression().getItems().get(0).getValue());
				List<Element> children = e.getChildElements();
				return e.equals(e.getChildElements().get(children.size() - index));
			} else if ("nth-of-type".equals(name)) {
			} else if ("nth-last-of-type".equals(name)) {
			} else if ("first-child".equals(name)) {
			} else if ("last-child".equals(name)) {
			} else if ("first-of-type".equals(name)) {
			} else if ("last-of-type".equals(name)) {
			} else if ("only-child".equals(name)) {
			} else if ("only-of-type".equals(name)) {
			} else if ("empty".equals(name)) {
			} else if ("link".equals(name)) {
			} else if ("target".equals(name)) {
			} else if ("lang".equals(name)) {
			} else if ("disabled".equals(name)) {
			} else if ("checked".equals(name)) {
			}
		} else if (PseudoType.PseudoElement.equals(simpleSelector.getType())) {
			//nothing to do
		}
		return false;
	}

}

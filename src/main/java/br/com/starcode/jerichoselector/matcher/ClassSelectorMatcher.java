package br.com.starcode.jerichoselector.matcher;

import java.util.regex.Pattern;

import net.htmlparser.jericho.Element;
import br.com.starcode.parccser.model.ClassSelector;

public class ClassSelectorMatcher implements SimpleSelectorMatcher<ClassSelector> {

	public boolean matches(Element e, ClassSelector simpleSelector) {
		String attrClass = e.getAttributeValue("class");
		return attrClass != null && Pattern.compile(
				".*(\\s|^)" + Pattern.quote(simpleSelector.getClassName()) + "(\\s|$).*", Pattern.DOTALL)
				.matcher(attrClass)
				.matches();
	}

}

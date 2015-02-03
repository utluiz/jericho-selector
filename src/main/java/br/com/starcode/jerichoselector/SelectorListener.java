package br.com.starcode.jerichoselector;

import java.util.List;

import net.htmlparser.jericho.Attribute;
import net.htmlparser.jericho.Element;
import br.com.starcode.jerichoselector.ElementList.Callback;
import br.com.starcode.jerichoselector.ElementList.CallbackFilter;
import br.com.starcode.parccser.ParserListener;
import br.com.starcode.parccser.model.AttributeOperator;
import br.com.starcode.parccser.model.AttributeSelector;
import br.com.starcode.parccser.model.ClassSelector;
import br.com.starcode.parccser.model.Combinator;
import br.com.starcode.parccser.model.HashSelector;
import br.com.starcode.parccser.model.PseudoSelector;
import br.com.starcode.parccser.model.Selector;
import br.com.starcode.parccser.model.SimpleSelectorSequence;
import br.com.starcode.parccser.model.TypeSelector;


public class SelectorListener implements ParserListener {
        
	final protected Element e;
	final protected ElementList selectedGeneral = new ElementList();
	protected ElementList selected = new ElementList();
	protected Combinator combinator;
	
	public SelectorListener(Element e) {
		this.e = e;
	}
	
	public void beginGroup(int number, int position) {
		selected.clear();
		selected.add(e);
		combinator = null;
	}
	
	public void endGroup(Selector group) {
		selectedGeneral.addAll(selected);
	}

	public void typeSelector(TypeSelector typeSelector) {
		System.out.println("typeSelector: " + typeSelector);
		selected.clear();
		if (typeSelector.isUniversal()) {
			selected.addAll(e.getAllElements());
		} else {
			selected.addAll(e.getAllElements(typeSelector.getType()));
		}
	}
	
	public void selectorSequence(SimpleSelectorSequence simpleSelector, Combinator combinator) {
		System.out.println("selectorSequence: " + simpleSelector + " / " + combinator);
		this.combinator = combinator;
	}

	public void classSelector(final ClassSelector classSelector) {
		System.out.println("classSelector: " + classSelector);
		selected = selected.apply(new Callback() {
			public List<Element> listen(Element e) {
				return e.getAllElementsByClass(classSelector.getClassName());
			}
		});
	}

	public void idSelector(final HashSelector hashSelector) {
		System.out.println("idSelector: " + hashSelector);
		selected = selected.walk(new CallbackFilter() {
			public boolean test(Element e) {
				return hashSelector.getName().equalsIgnoreCase(e.getAttributeValue("id"));
			}
		});
	}
	
	protected static class AttributeCallbackFilter implements CallbackFilter {
		
		private boolean testAbsence;
		private AttributeSelector attributeSelector;
		
		public AttributeCallbackFilter(AttributeSelector attributeSelector, boolean testAbsence) {
			this.attributeSelector = attributeSelector;
			this.testAbsence = testAbsence;
		}
		
		public boolean test(Element e) {
			Attribute attr = e.getAttributes().get(attributeSelector.getName());
			if (attr != null) {
				
				AttributeOperator op = attributeSelector.getOperator();
				if (op == null) {
					return testAbsence ^ true;
				} else {
					String selectorValue = attributeSelector.getValue().getActualValue();
					String attributeValue = attr.getValue();
					return testAbsence ^ Comparator.compare(
							op, 
							(attributeValue == null ? "" : selectorValue), 
							(selectorValue == null ? "" : selectorValue));
				}
				
			}
			return testAbsence ^ false;
		}
		
	}

	public void attributeSelector(final AttributeSelector attributeSelector) {
		selected = selected.walk(new AttributeCallbackFilter(attributeSelector, false));
	}

	public void pseudoSelector(final PseudoSelector pseudoSelector) {
		//TODO
	}

	public void negationTypeSelector(final TypeSelector typeSelector) {
		selected = selected.filter(new CallbackFilter() {
			public boolean test(Element e) {
				return !typeSelector.getType().equalsIgnoreCase(e.getName());
			}
		});
	}

	public void negationClassSelector(final ClassSelector classSelector) {
		//TODO
	}

	public void negationAttributeSelector(final AttributeSelector attributeSelector) {
		selected = selected.filter(new AttributeCallbackFilter(attributeSelector, true));
	}

	public void negationIdSelector(final HashSelector hashSelector) {
		selected = selected.filter(new CallbackFilter() {
			public boolean test(Element e) {
				return !hashSelector.getName().equalsIgnoreCase(e.getAttributeValue("id"));
			}
		});
	}

	public void negationPseudoSelector(final PseudoSelector pseudoSelector) {
		//TODO
	}

	public List<Element> getSelected() {
		return selectedGeneral;
	}

}
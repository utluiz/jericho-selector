package br.com.starcode.jerichoselector;

import br.com.starcode.jerichoselector.model.AttributeSelector;
import br.com.starcode.jerichoselector.model.ClassSelector;
import br.com.starcode.jerichoselector.model.Combinator;
import br.com.starcode.jerichoselector.model.HashSelector;
import br.com.starcode.jerichoselector.model.PseudoSelector;
import br.com.starcode.jerichoselector.model.Selector;
import br.com.starcode.jerichoselector.model.SimpleSelectorSequence;
import br.com.starcode.jerichoselector.model.TypeSelector;

public abstract class AbstractParserListener implements ParserListener {
	
	public void beginGroup(int number, int position) {
	}

	public void endGroup(Selector group) {
	}

	public void selectorSequence(SimpleSelectorSequence simpleSelector, Combinator combinator) {
	}

	public void typeSelector(TypeSelector typeSelector) {
	}

	public void classSelector(ClassSelector classSelector) {
	}

	public void idSelector(HashSelector hashSelector) {
	}

	public void attributeSelector(AttributeSelector attributeSelector) {
	}

	public void pseudoSelector(PseudoSelector pseudoSelector) {
	}

	public void negationTypeSelector(TypeSelector typeSelector) {
	}

	public void negationClassSelector(ClassSelector classSelector) {
	}

	public void negationIdSelector(HashSelector hashSelector) {
	}

	public void negationAttributeSelector(AttributeSelector attributeSelector) {
	}

	public void negationPseudoSelector(PseudoSelector pseudoSelector) {
	}
	
}

package br.com.starcode.jerichoselector;

import java.util.ArrayList;
import java.util.List;

import br.com.starcode.jerichoselector.model.AttributeSelector;
import br.com.starcode.jerichoselector.model.ClassSelector;
import br.com.starcode.jerichoselector.model.Combinator;
import br.com.starcode.jerichoselector.model.HashSelector;
import br.com.starcode.jerichoselector.model.PseudoSelector;
import br.com.starcode.jerichoselector.model.Selector;
import br.com.starcode.jerichoselector.model.SimpleSelectorSequence;
import br.com.starcode.jerichoselector.model.TypeSelector;


public class Listener implements ParserListener {
        
	final List<String> lista = new ArrayList<String>();
	private int number;
	
	public void beginGroup(int number, int position) {
		lista.add("beginGroup=" + number);
		this.number = number;
	}
	
	public void endGroup(Selector group) {
		lista.add("endGroup=" + number);
	}

	public void typeSelector(TypeSelector typeSelector) {
		lista.add("typeSelector=" + typeSelector.getType());
	}
	
	public void selectorSequence(SimpleSelectorSequence simpleSelector, Combinator combinator) {
		String c = combinator == null ? "null" : combinator.getSign();
		lista.add("selectorSequence=" + c + "|" + simpleSelector);
	}

	public void classSelector(ClassSelector classSelector) {
		lista.add("classSelector=" + classSelector.getClassName());
	}

	public void idSelector(HashSelector hashSelector) {
		lista.add("idSelector=" + hashSelector.getName());
	}

	public void attributeSelector(AttributeSelector attributeSelector) {
		lista.add("attributeSelector=" + attributeSelector);
	}

	public List<String> getLista() {
		return lista;
	}

	public void pseudoSelector(PseudoSelector pseudoSelector) {
		lista.add("pseudoSelector=" + pseudoSelector.getType().toString() + "|" + pseudoSelector);
	}

	public void negationTypeSelector(TypeSelector typeSelector) {
		lista.add("negationTypeSelector=" + typeSelector.getType());
	}

	public void negationClassSelector(ClassSelector classSelector) {
		lista.add("negationClassSelector=" + classSelector.getClassName());
	}

	public void negationAttributeSelector(AttributeSelector attributeSelector) {
		lista.add("negationAttributeSelector=" + attributeSelector);
	}

	public void negationIdSelector(HashSelector hashSelector) {
		lista.add("negationIdSelector=" + hashSelector.getName());
	}

	public void negationPseudoSelector(PseudoSelector pseudoSelector) {
		lista.add("negationPseudoSelector=" + pseudoSelector.getType().toString() + "|" + pseudoSelector);
	}


}
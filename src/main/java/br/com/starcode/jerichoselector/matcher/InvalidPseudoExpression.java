package br.com.starcode.jerichoselector.matcher;

import br.com.starcode.parccser.model.PseudoSelector;

public class InvalidPseudoExpression extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;
	
	private PseudoSelector simpleSelector;

	public InvalidPseudoExpression(String s, PseudoSelector simpleSelector) {
		super(s);
		this.simpleSelector = simpleSelector;
	}

	public InvalidPseudoExpression(Throwable cause, PseudoSelector simpleSelector) {
		super(cause);
		this.simpleSelector = simpleSelector;
	}

	public InvalidPseudoExpression(String message, Throwable cause, PseudoSelector simpleSelector) {
		super(message, cause);
		this.simpleSelector = simpleSelector;
	}
	
	public PseudoSelector getSimpleSelector() {
		return simpleSelector;
	}

}

package br.com.starcode.jerichoselector;

import br.com.starcode.jerichoselector.model.AttributeSelector;
import br.com.starcode.jerichoselector.model.Combinator;
import br.com.starcode.jerichoselector.model.Context;
import br.com.starcode.jerichoselector.model.PseudoSelector;

public abstract class AbstractParserListener implements ParserListener {

    public void beginSelectorGroup(int number, Context context) {
    }

    public void endGroup(int number, Context context) {
    }

    public void typeSelector(Context context) {
    }

    public void classSelector(int number, Context context) {
    }

    public void idSelector(int number, Context context) {
    }

    public void attributeSelector(int number, AttributeSelector as,
            Context context) {
    }

    public void pseudoSelector(int number, PseudoSelector pseudoSelector,
            Context context) {
    }

    public void simpleSelector(int number, Combinator combinator,
            Context context) {
    }

    public void negationTypeSelector(int number, Context context) {
    }

    public void negationClassSelector(int number, Context context) {
    }

    public void negationAttributeSelector(int number, AttributeSelector type,
            Context context) {
    }

    public void negationIdSelector(int number, Context context) {
    }

    public void negationPseudoSelector(int number, PseudoSelector pseudoSelector,
            Context context) {
    }

}

package br.com.starcode.jerichoselector.model;

public class NegationSelector extends SimpleSelector {

    private SimpleSelector simpleSelector;
    
    public NegationSelector(
            SimpleSelector simpleSelector, 
            Combinator combinator,
            Context context) {
        super(combinator, context);
        this.simpleSelector = simpleSelector;
    }
    
    public SimpleSelector getSimpleSelector() {
        return simpleSelector;
    }

}

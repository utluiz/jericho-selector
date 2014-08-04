package br.com.starcode.jerichoselector.model;

public abstract class SimpleSelector extends AbstractContext  {

    protected Combinator combinator;
    
    protected SimpleSelector(Combinator combinator, Context context) {
        super(context);
        this.combinator = combinator;
    }
    
    public Combinator getCombinator() {
        return combinator;
    }

}

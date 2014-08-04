package br.com.starcode.jerichoselector.model;

public class PseudoSelector extends SimpleSelector {

    public static enum PseudoType { PseudoElement, PseudoClass }
    
    private String name;
    private PseudoType type;
    private boolean doubleColon;
    private PseudoExpression expression;
    
    public PseudoSelector(
            String name, 
            PseudoType type,
            boolean doubleColon,
            PseudoExpression expression,
            Combinator combinator,
            Context context) {
        super(combinator, context);
        this.name = name;
        this.type = type;
        this.doubleColon = doubleColon;
        this.expression = expression;
    }
    
    public String getName() {
        return name;
    }
    
    public PseudoType getType() {
        return type;
    }
    
    public boolean getDoubleColon() {
        return doubleColon;
    }
    
    public PseudoExpression getExpression() {
        return expression;
    }
    
}

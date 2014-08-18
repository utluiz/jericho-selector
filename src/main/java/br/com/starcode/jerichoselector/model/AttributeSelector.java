package br.com.starcode.jerichoselector.model;

public class AttributeSelector extends SimpleSelector {

    private String name;
    private AttributeOperator operator;
    private String value;
    
    public AttributeSelector(
            String name, 
            AttributeOperator operator,
            String value,
            Context context) {
        super(context);
        this.name = name;
        this.operator = operator;
        this.value = value;
    }
    
    public String getName() {
        return name;
    }
    
    public AttributeOperator getOperator() {
        return operator;
    }
    
    public String getValue() {
        return value;
    }
    
}

package br.com.starcode.jerichoselector;

public class AttributeSelector {

    private String name;
    private AttributeOperator operator;
    private String value;
    private String context;
    
    public AttributeSelector(
            String name, 
            AttributeOperator operator,
            String value,
            String context) {
        this.name = name;
        this.operator = operator;
        this.value = value;
        this.context = context;
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
    
    public String getContext() {
        return context;
    }
    
}

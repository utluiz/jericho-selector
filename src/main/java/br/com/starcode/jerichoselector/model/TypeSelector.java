package br.com.starcode.jerichoselector.model;

public class TypeSelector extends SimpleSelector {
    
    private String type;
    private boolean isUniversal;
    
    public TypeSelector(
            String type, 
            Combinator combinator,
            Context context) {
        super(combinator, context);
        this.type = type;
        this.isUniversal = "*".equals(type);
    }

    public String getType() {
        return type;
    }
    
    public boolean isUniversal() {
        return isUniversal;
    }
    
}

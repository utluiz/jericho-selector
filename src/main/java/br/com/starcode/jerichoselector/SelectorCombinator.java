package br.com.starcode.jerichoselector;

public enum SelectorCombinator {
    
    ADJASCENT_SIBLING("+"), CHILD(">"), GENERAL_SIBLING("~"), DESCENDANT(" ");
    
    private String sign;
    
    private SelectorCombinator(String sign) {
        this.sign = sign;
    }
    
    public String getSign() {
        return sign;
    }
    
}

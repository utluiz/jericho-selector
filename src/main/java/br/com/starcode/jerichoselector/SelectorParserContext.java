package br.com.starcode.jerichoselector;

/**
 * TODO implements as map?
 */
public class SelectorParserContext {

    private String selector;
    private String context;
    private int position;
    
    public SelectorParserContext(String selector, String context, int position) {
        this.selector = selector;
        this.context = context;
        this.position = position;
    }
    
    public String getSelector() {
        return selector;
    }
    
    public String getContext() {
        return context;
    }
    
    public int getPosition() {
        return position;
    }
    
    @Override
    public String toString() {
        return context;
    }
    
}

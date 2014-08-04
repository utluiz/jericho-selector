package br.com.starcode.jerichoselector.model;

public class Context {

    private String selector;
    private String context;
    private int startPosition;
    private int endPosition;
    
    public Context(
            String selector, 
            String context, 
            int startPosition,
            int endPosition) {
        this.selector = selector;
        this.context = context;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }
    
    public String getSelector() {
        return selector;
    }
    
    public int getStartPosition() {
        return startPosition;
    }
    
    public int getEndPosition() {
        return endPosition;
    }
    
    @Override
    public String toString() {
        return context;
    }
    
}

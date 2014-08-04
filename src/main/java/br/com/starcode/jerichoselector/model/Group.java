package br.com.starcode.jerichoselector.model;


public class Group extends AbstractContext {

    private Selector selector;
    
    public Group(Selector selector, Context context) {
        super(context);
        this.selector = selector;
    }
    
    public Selector getSelectors() {
        return selector;
    }
    
}

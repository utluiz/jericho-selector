package br.com.starcode.jerichoselector.model;

import java.util.List;

public class Selector extends AbstractContext {

    private List<SimpleSelectorSequence> simpleSelectors;
    
    public Selector(List<SimpleSelectorSequence> simpleSelectors, Context context) {
        super(context);
        this.simpleSelectors = simpleSelectors;
    }
    
    public List<SimpleSelectorSequence> getSelectors() {
        return simpleSelectors;
    }
    
}

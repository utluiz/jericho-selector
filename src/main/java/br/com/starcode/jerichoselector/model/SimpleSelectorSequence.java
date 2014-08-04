package br.com.starcode.jerichoselector.model;

import java.util.List;

public class SimpleSelectorSequence extends AbstractContext {
    
    private List<SimpleSelector> simpleSelectors;
    
    public SimpleSelectorSequence(List<SimpleSelector> simpleSelectors, Context context) {
        super(context);
        this.simpleSelectors = simpleSelectors;
    }
    
    public List<SimpleSelector> getSimpleSelectors() {
        return simpleSelectors;
    }

}

package br.com.starcode.jerichoselector.model;

public class HashSelector extends SimpleSelector {
    
    private String hash;
    
    public HashSelector(
            String hash, 
            Combinator combinator,
            Context context) {
        super(combinator, context);
        this.hash = hash;
    }

    public String getName() {
        return hash;
    }

}

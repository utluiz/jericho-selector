package br.com.starcode.jerichoselector.model;

import java.util.List;

public class PseudoExpression {
    
    public static enum Type { Signal, Number, Dimension, StringType, Identifier };

    public static class Item {
        
        private Type type;
        private String value;
        
        public Item(Type type, String value) {
            this.type = type;
            this.value = value;
        }
        
        public Type getType() {
            return type;
        }
        
        public String getValue() {
            return value;
        }
        
    }
    
    private List<Item> items;
    private String context;
    
    public PseudoExpression(List<Item> items, String context) {
        this.items = items;
        this.context = context;
    }
    
    public String getContext() {
        return context;
    }
    
    public List<Item> getItems() {
        return items;
    }
    
}

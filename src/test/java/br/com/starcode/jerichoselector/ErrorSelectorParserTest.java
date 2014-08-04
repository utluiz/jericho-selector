package br.com.starcode.jerichoselector;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.starcode.jerichoselector.model.AttributeSelector;
import br.com.starcode.jerichoselector.model.Context;
import br.com.starcode.jerichoselector.model.PseudoSelector;
import br.com.starcode.jerichoselector.model.Combinator;

/**
 * Selector list:
 * http://www.w3.org/TR/css3-selectors/#selectors
 *
 */
public class ErrorSelectorParserTest {

    @Test
    public void nullValue() {
        
        boolean hasException = false;
        try {
            new Parser(null, null);
        } catch (IllegalArgumentException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void empty() {
        
        boolean hasException = false;
        try {
            new Parser("", null);
        } catch (IllegalArgumentException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void testNull() {
        
        boolean hasException = false;
        try {
            new Parser("*", null);
        } catch (IllegalArgumentException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void endUnexpected() {
        
        boolean hasException = false;
        try {
            new Parser("a > ", new Listener()).interpret();
        } catch (ParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void contentLeft() {
        
        boolean hasException = false;
        try {
            new Parser("a z ^", new Listener()).interpret();
        } catch (ParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
   /* @Test
    public void expectIdentifier() {
        
        boolean hasException = false;
        try {
            new Parser("[a=]", new Listener()).interpret();
        } catch (ParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }*/

    @Test
    public void expectIdentifier2() {
        
        boolean hasException = false;
        try {
            new Parser("[=]", new Listener()).interpret();
        } catch (ParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void expectToken() {
        
        boolean hasException = false;
        try {
            new Parser("[a", new Listener()).interpret();
        } catch (ParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void expectIdentifier3() {
        
        boolean hasException = false;
        try {
            new Parser("a#", new Listener()).interpret();
        } catch (ParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void expectIdentifier4() {
        
        boolean hasException = false;
        try {
            new Parser("x.", new Listener()).interpret();
        } catch (ParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void valueOnlyNumber() {
        
        boolean hasException = false;
        try {
            Listener l = new Listener();
            new Parser("[val=1]", l).interpret();
        } catch (ParserException e) {
            //e.printStackTrace();
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }

    private class Listener implements ParserListener {
        
        final List<String> lista = new ArrayList<String>();
        
        public void endGroup(int number, Context context) {
            lista.add("endSelectorGroup=" + number);
        }
        
        public void beginSelectorGroup(int number, Context context) {
            lista.add("beginSelectorGroup=" + number);
        }

        public void typeSelector(Context context) {
            lista.add("typeSelector=" + context.getContext());
        }

        public void simpleSelector(int number,
                Combinator combinator, Context context) {
            lista.add("simpleSelector=" + number + "|" 
                + (combinator != null ? combinator.getSign() : null) + "|" + context.getContext());
        }

        public void classSelector(int number,
                Context context) {
            lista.add("classSelector=" + number + "|" + context.getContext());
        }

        public void idSelector(int number,
                Context context) {
            lista.add("idSelector=" + number + "|" + context.getContext());
        }

        public void attributeSelector(int number,
                AttributeSelector as, Context context) {
            lista.add("attributeSelector=" + number + "|" + context.getContext());
        }
        
        public void pseudoSelector(int number, PseudoSelector pseudoSelector, Context context) {
            lista.add("pseudoSelector=" + number + "|" + pseudoSelector.getType().toString() + "|" + context.getContext());
        }

        public void negationTypeSelector(int number, Context context) {
            lista.add("negationTypeSelector=" + number + "|" + context.getContext());
        }

        public void negationClassSelector(int number, Context context) {
            lista.add("negationClassSelector=" + number + "|" + context.getContext());
        }

        public void negationAttributeSelector(int number, AttributeSelector type, Context context) {
            lista.add("negationAttributeSelector=" + number + "|" + context.getContext());
        }

        public void negationIdSelector(int number, Context context) {
            lista.add("negationIdSelector=" + number + "|" + context.getContext());
        }

        public void negationPseudoSelector(int number, PseudoSelector pseudoSelector, Context context) {
            lista.add("negationPseudoSelector=" + number + "|" + context.getContext());
        }
        
    }
    
}

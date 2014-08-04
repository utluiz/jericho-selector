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
public class PseudoSelectorParserTest {

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
        
        public List<String> getLista() {
            return lista;
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
            lista.add("negationPseudoSelector=" + number + "|" + pseudoSelector.getType().toString() + "|" + context.getContext());
        }
        
    }
    
    @Test
    public void pseudoClassSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("input:enabled", l).interpret();
        //System.out.println(l.getLista());
        Assert.assertEquals(5, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=input", l.getLista().get(1));
        Assert.assertEquals("pseudoSelector=0|PseudoClass|enabled", l.getLista().get(2));
        Assert.assertEquals("simpleSelector=0|null|input:enabled", l.getLista().get(3));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(4));
        
    }
    
    @Test
    public void pseudoElementSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("input::anything", l).interpret();
        //System.out.println(l.getLista());
        Assert.assertEquals(5, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=input", l.getLista().get(1));
        Assert.assertEquals("pseudoSelector=0|PseudoElement|anything", l.getLista().get(2));
        Assert.assertEquals("simpleSelector=0|null|input::anything", l.getLista().get(3));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(4));
        
    }
    
    @Test
    public void specialPseudoElementSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("input:first-line", l).interpret();
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=input", l.getLista().get(1));
        Assert.assertEquals("pseudoSelector=0|PseudoElement|first-line", l.getLista().get(2));
        Assert.assertEquals("simpleSelector=0|null|input:first-line", l.getLista().get(3));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(4));
        
        l = new Listener();
        new Parser("input:first-letter", l).interpret();
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=input", l.getLista().get(1));
        Assert.assertEquals("pseudoSelector=0|PseudoElement|first-letter", l.getLista().get(2));
        Assert.assertEquals("simpleSelector=0|null|input:first-letter", l.getLista().get(3));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(4));
        
        l = new Listener();
        new Parser("input:before", l).interpret();
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=input", l.getLista().get(1));
        Assert.assertEquals("pseudoSelector=0|PseudoElement|before", l.getLista().get(2));
        Assert.assertEquals("simpleSelector=0|null|input:before", l.getLista().get(3));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(4));
        
        l = new Listener();
        new Parser("input:after", l).interpret();
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=input", l.getLista().get(1));
        Assert.assertEquals("pseudoSelector=0|PseudoElement|after", l.getLista().get(2));
        Assert.assertEquals("simpleSelector=0|null|input:after", l.getLista().get(3));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(4));
        
    }
    
    @Test
    public void pseudoClassMultiple() throws Exception {
        
        Listener l = new Listener();
        new Parser("input:enabled:hover", l).interpret();
        //System.out.println(l.getLista());
        Assert.assertEquals(6, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=input", l.getLista().get(1));
        Assert.assertEquals("pseudoSelector=0|PseudoClass|enabled", l.getLista().get(2));
        Assert.assertEquals("pseudoSelector=1|PseudoClass|hover", l.getLista().get(3));
        Assert.assertEquals("simpleSelector=0|null|input:enabled:hover", l.getLista().get(4));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(5));
        
    }
    
    @Test
    public void doublePseudoElement() {
        
        boolean hasException = false;
        try {
            Listener l = new Listener();
            new Parser("input::anything::other", l).interpret();
        } catch (ParserException e) {
            //e.printStackTrace();
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void pseudoClassParameter() throws Exception {
        
        Listener l = new Listener();
        new Parser("input:eq(1)", l).interpret();
        Assert.assertEquals(6, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=input", l.getLista().get(1));
        Assert.assertEquals("pseudoSelector=0|PseudoClass|eq(1)", l.getLista().get(2));
        Assert.assertEquals("simpleSelector=0|null|input:enabled:hover", l.getLista().get(4));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(5));
        
    }
    
    /*public void pseudoSelector(int number, PseudoSelector pseudoSelector, Context context) {
        lista.add("pseudoSelector=" + number + "|" + context.getContext());
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
    }*/
    
}

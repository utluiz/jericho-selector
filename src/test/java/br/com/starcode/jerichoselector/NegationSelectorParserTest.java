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
public class NegationSelectorParserTest {

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
    public void doubleNegation() {
        
        boolean hasException = false;
        try {
            Listener l = new Listener();
            new Parser(":not(:not(#id))", l).interpret();
        } catch (ParserException e) {
            //e.printStackTrace();
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void nagationPseudoElement() {
        
        boolean hasException = false;
        try {
            Listener l = new Listener();
            new Parser(":not(::first-letter)", l).interpret();
        } catch (ParserException e) {
            //e.printStackTrace();
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
        hasException = false;
        try {
            Listener l = new Listener();
            new Parser(":not(:first-letter)", l).interpret();
        } catch (ParserException e) {
            //e.printStackTrace();
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
        hasException = false;
        try {
            Listener l = new Listener();
            new Parser(":not(::anyothger)", l).interpret();
        } catch (ParserException e) {
            //e.printStackTrace();
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void negateIdSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("input:not(#ident)", l).interpret();
        //System.out.println(l.getLista());
        Assert.assertEquals(5, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=input", l.getLista().get(1));
        Assert.assertEquals("negationIdSelector=0|ident", l.getLista().get(2));
        Assert.assertEquals("simpleSelector=0|null|input:not(#ident)", l.getLista().get(3));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(4));
        
    }
    
    @Test
    public void negateClassSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser(":not(._minha_classe)", l).interpret();
        //System.out.println(l.getLista());
        Assert.assertEquals(4, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("negationClassSelector=0|_minha_classe", l.getLista().get(1));
        Assert.assertEquals("simpleSelector=0|null|:not(._minha_classe)", l.getLista().get(2));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(3));
        
    }
    
    @Test
    public void negateUniversalSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("*:not(*)", l).interpret();
        ///System.out.println(l.getLista());
        Assert.assertEquals(5, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=*", l.getLista().get(1));
        Assert.assertEquals("negationTypeSelector=0|*", l.getLista().get(2));
        Assert.assertEquals("simpleSelector=0|null|*:not(*)", l.getLista().get(3));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(4));
        
    }
    
    @Test
    public void negateTypeSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("tabl:not(table)", l).interpret();
        ///System.out.println(l.getLista());
        Assert.assertEquals(5, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=tabl", l.getLista().get(1));
        Assert.assertEquals("negationTypeSelector=0|table", l.getLista().get(2));
        Assert.assertEquals("simpleSelector=0|null|tabl:not(table)", l.getLista().get(3));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(4));
        
    }
    
    @Test
    public void negateAttributeSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("a:not([href])", l).interpret();
        ///System.out.println(l.getLista());
        Assert.assertEquals(5, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=a", l.getLista().get(1));
        Assert.assertEquals("negationAttributeSelector=0|href", l.getLista().get(2));
        Assert.assertEquals("simpleSelector=0|null|a:not([href])", l.getLista().get(3));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(4));
        
    }
    
    @Test
    public void negateAttributeSelector2() throws Exception {
        
        Listener l = new Listener();
        new Parser("a:not([href='1'])", l).interpret();
        ///System.out.println(l.getLista());
        Assert.assertEquals(5, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=a", l.getLista().get(1));
        Assert.assertEquals("negationAttributeSelector=0|href='1'", l.getLista().get(2));
        Assert.assertEquals("simpleSelector=0|null|a:not([href='1'])", l.getLista().get(3));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(4));
        
    }
    
    @Test
    public void negatePseudoClass() throws Exception {
        
        Listener l = new Listener();
        new Parser("a:not(:disable)", l).interpret();
        ///System.out.println(l.getLista());
        Assert.assertEquals(5, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=a", l.getLista().get(1));
        Assert.assertEquals("negationPseudoSelector=0|PseudoClass|disable", l.getLista().get(2));
        Assert.assertEquals("simpleSelector=0|null|a:not(:disable)", l.getLista().get(3));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(4));
        
    }
    
}

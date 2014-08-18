package br.com.starcode.jerichoselector;

import org.junit.Assert;
import org.junit.Test;

/**
 * Selector list:
 * http://www.w3.org/TR/css3-selectors/#selectors
 *
 */
public class NegationSelectorParserTest {

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
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=input", l.getLista().get(1));
        Assert.assertEquals("negationIdSelector=ident", l.getLista().get(2));
        Assert.assertEquals("selectorSequence=null|input:not(#ident)", l.getLista().get(3));
        Assert.assertEquals("endGroup=0", l.getLista().get(4));
        
    }
    
    @Test
    public void negateClassSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser(":not(._minha_classe)", l).interpret();
        //System.out.println(l.getLista());
        Assert.assertEquals(4, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("negationClassSelector=_minha_classe", l.getLista().get(1));
        Assert.assertEquals("selectorSequence=null|:not(._minha_classe)", l.getLista().get(2));
        Assert.assertEquals("endGroup=0", l.getLista().get(3));
        
    }
    
    @Test
    public void negateUniversalSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("*:not(*)", l).interpret();
        ///System.out.println(l.getLista());
        Assert.assertEquals(5, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=*", l.getLista().get(1));
        Assert.assertEquals("negationTypeSelector=*", l.getLista().get(2));
        Assert.assertEquals("selectorSequence=null|*:not(*)", l.getLista().get(3));
        Assert.assertEquals("endGroup=0", l.getLista().get(4));
        
    }
    
    @Test
    public void negateTypeSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("tabl:not(table)", l).interpret();
        ///System.out.println(l.getLista());
        Assert.assertEquals(5, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=tabl", l.getLista().get(1));
        Assert.assertEquals("negationTypeSelector=table", l.getLista().get(2));
        Assert.assertEquals("selectorSequence=null|tabl:not(table)", l.getLista().get(3));
        Assert.assertEquals("endGroup=0", l.getLista().get(4));
        
    }
    
    @Test
    public void negateAttributeSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("a:not([href])", l).interpret();
        ///System.out.println(l.getLista());
        Assert.assertEquals(5, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=a", l.getLista().get(1));
        Assert.assertEquals("negationAttributeSelector=href", l.getLista().get(2));
        Assert.assertEquals("selectorSequence=null|a:not([href])", l.getLista().get(3));
        Assert.assertEquals("endGroup=0", l.getLista().get(4));
        
    }
    
    @Test
    public void negateAttributeSelector2() throws Exception {
        
        Listener l = new Listener();
        new Parser("a:not([href='1'])", l).interpret();
        ///System.out.println(l.getLista());
        Assert.assertEquals(5, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=a", l.getLista().get(1));
        Assert.assertEquals("negationAttributeSelector=href='1'", l.getLista().get(2));
        Assert.assertEquals("selectorSequence=null|a:not([href='1'])", l.getLista().get(3));
        Assert.assertEquals("endGroup=0", l.getLista().get(4));
        
    }
    
    @Test
    public void negatePseudoClass() throws Exception {
        
        Listener l = new Listener();
        new Parser("a:not(:disable)", l).interpret();
        ///System.out.println(l.getLista());
        Assert.assertEquals(5, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=a", l.getLista().get(1));
        Assert.assertEquals("negationPseudoSelector=PseudoClass|disable", l.getLista().get(2));
        Assert.assertEquals("selectorSequence=null|a:not(:disable)", l.getLista().get(3));
        Assert.assertEquals("endGroup=0", l.getLista().get(4));
        
    }
    
}

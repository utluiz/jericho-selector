package br.com.starcode.jerichoselector;

import org.junit.Assert;
import org.junit.Test;

/**
 * Selector list:
 * http://www.w3.org/TR/css3-selectors/#selectors
 *
 */
public class PseudoSelectorParserTest {

    @Test
    public void pseudoClassSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("input:enabled", l).interpret();
        //System.out.println(l.getLista());
        Assert.assertEquals(5, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=input", l.getLista().get(1));
        Assert.assertEquals("pseudoSelector=PseudoClass|enabled", l.getLista().get(2));
        Assert.assertEquals("selectorSequence=null|input:enabled", l.getLista().get(3));
        Assert.assertEquals("endGroup=0", l.getLista().get(4));
        
    }
    
    @Test
    public void pseudoElementSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("input::anything", l).interpret();
        //System.out.println(l.getLista());
        Assert.assertEquals(5, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=input", l.getLista().get(1));
        Assert.assertEquals("pseudoSelector=PseudoElement|anything", l.getLista().get(2));
        Assert.assertEquals("selectorSequence=null|input::anything", l.getLista().get(3));
        Assert.assertEquals("endGroup=0", l.getLista().get(4));
        
    }
    
    @Test
    public void specialPseudoElementSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("input:first-line", l).interpret();
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=input", l.getLista().get(1));
        Assert.assertEquals("pseudoSelector=PseudoElement|first-line", l.getLista().get(2));
        Assert.assertEquals("selectorSequence=null|input:first-line", l.getLista().get(3));
        Assert.assertEquals("endGroup=0", l.getLista().get(4));
        
        l = new Listener();
        new Parser("input:first-letter", l).interpret();
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=input", l.getLista().get(1));
        Assert.assertEquals("pseudoSelector=PseudoElement|first-letter", l.getLista().get(2));
        Assert.assertEquals("selectorSequence=null|input:first-letter", l.getLista().get(3));
        Assert.assertEquals("endGroup=0", l.getLista().get(4));
        
        l = new Listener();
        new Parser("input:before", l).interpret();
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=input", l.getLista().get(1));
        Assert.assertEquals("pseudoSelector=PseudoElement|before", l.getLista().get(2));
        Assert.assertEquals("selectorSequence=null|input:before", l.getLista().get(3));
        Assert.assertEquals("endGroup=0", l.getLista().get(4));
        
        l = new Listener();
        new Parser("input:after", l).interpret();
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=input", l.getLista().get(1));
        Assert.assertEquals("pseudoSelector=PseudoElement|after", l.getLista().get(2));
        Assert.assertEquals("selectorSequence=null|input:after", l.getLista().get(3));
        Assert.assertEquals("endGroup=0", l.getLista().get(4));
        
    }
    
    @Test
    public void pseudoClassMultiple() throws Exception {
        
        Listener l = new Listener();
        new Parser("input:enabled:hover", l).interpret();
        //System.out.println(l.getLista());
        Assert.assertEquals(6, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=input", l.getLista().get(1));
        Assert.assertEquals("pseudoSelector=PseudoClass|enabled", l.getLista().get(2));
        Assert.assertEquals("pseudoSelector=PseudoClass|hover", l.getLista().get(3));
        Assert.assertEquals("selectorSequence=null|input:enabled:hover", l.getLista().get(4));
        Assert.assertEquals("endGroup=0", l.getLista().get(5));
        
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
        Assert.assertEquals(5, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=input", l.getLista().get(1));
        Assert.assertEquals("pseudoSelector=PseudoClass|eq(1)", l.getLista().get(2));
        Assert.assertEquals("selectorSequence=null|input:eq(1)", l.getLista().get(3));
        Assert.assertEquals("endGroup=0", l.getLista().get(4));
        
    }
    
    @Test
    public void pseudoClassParameterExpression() throws Exception {
        
        Listener l = new Listener();
        new Parser("input:eq(2n+1)", l).interpret();
        Assert.assertEquals("pseudoSelector=PseudoClass|eq(2n+1)", l.getLista().get(2));
        
        l = new Listener();
        new Parser("input:eq( 2n + 1 )", l).interpret();
        Assert.assertEquals("pseudoSelector=PseudoClass|eq(2n+1)", l.getLista().get(2));
        
        l = new Listener();
        new Parser("input:eq( +1.5n + 3.5)", l).interpret();
        Assert.assertEquals("pseudoSelector=PseudoClass|eq(+1.5n+3.5)", l.getLista().get(2));
        
        l = new Listener();
        new Parser("input:eq(10px)", l).interpret();
        Assert.assertEquals("pseudoSelector=PseudoClass|eq(10px)", l.getLista().get(2));
        
    }
    
    @Test
    public void pseudoClassParameterExpressionMinus() throws Exception {
        
        Listener l = new Listener();
        new Parser("input:eq(2n-1)", l).interpret();
        Assert.assertEquals("pseudoSelector=PseudoClass|eq(2n-1)", l.getLista().get(2));
        
        l = new Listener();
        new Parser("input:eq( -2n - 1)", l).interpret();
        Assert.assertEquals("pseudoSelector=PseudoClass|eq(-2n-1)", l.getLista().get(2));
        
    }
    
    @Test
    public void pseudoClassParameterConstant() throws Exception {
    	
    	Listener l = new Listener();
        new Parser("input:eq(n)", l).interpret();
        Assert.assertEquals("pseudoSelector=PseudoClass|eq(n)", l.getLista().get(2));
        
    	l = new Listener();
        new Parser("input:lang(pt-br)", l).interpret();
        Assert.assertEquals("pseudoSelector=PseudoClass|lang(pt-br)", l.getLista().get(2));
        
    	l = new Listener();
        new Parser("input:lang('pt-br')", l).interpret();
        Assert.assertEquals("pseudoSelector=PseudoClass|lang('pt-br')", l.getLista().get(2));
        
    	l = new Listener();
        new Parser("input:lang(\"pt-br\")", l).interpret();
        Assert.assertEquals("pseudoSelector=PseudoClass|lang(\"pt-br\")", l.getLista().get(2));
        
    }
    
}

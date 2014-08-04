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
public class SimpleSelectorParserTest {

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
            lista.add("negationPseudoSelector=" + number + "|" + context.getContext());
        }
        
    }
    
    @Test
    public void universal() throws Exception {
        
        Listener l = new Listener();
        new Parser("*", l).interpret();
        Assert.assertEquals(4, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=*", l.getLista().get(1));
        Assert.assertEquals("simpleSelector=0|null|*", l.getLista().get(2));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(3));
        //System.out.println(lista);
        
    }
    
    @Test
    public void element() throws Exception {
        
        Listener l = new Listener();
        new Parser("table", l).interpret();
        Assert.assertEquals(4, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=table", l.getLista().get(1));
        Assert.assertEquals("simpleSelector=0|null|table", l.getLista().get(2));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(3));
        //System.out.println(l.getLista());
        
    }
    
    @Test
    public void classSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser(".class", l).interpret();
        Assert.assertEquals(4, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("classSelector=0|class", l.getLista().get(1));
        Assert.assertEquals("simpleSelector=0|null|.class", l.getLista().get(2));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(3));
        //System.out.println(l.getLista());
        
    }
    
    @Test
    public void idSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("#identifier", l).interpret();
        Assert.assertEquals(4, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("idSelector=0|identifier", l.getLista().get(1));
        Assert.assertEquals("simpleSelector=0|null|#identifier", l.getLista().get(2));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(3));
        //System.out.println(l.getLista());
        
    }
    
    @Test
    public void attributeSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("[name='test']", l).interpret();
        Assert.assertEquals(4, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("attributeSelector=0|name='test'", l.getLista().get(1));
        Assert.assertEquals("simpleSelector=0|null|[name='test']", l.getLista().get(2));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(3));
        //System.out.println(l.getLista());
        
    }
    
    @Test
    public void attributeEscapeSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("[name='\\'test\\'']", l).interpret();
        Assert.assertEquals(4, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("attributeSelector=0|name=''test''", l.getLista().get(1));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(3));
        //System.out.println(l.getLista());
        
    }
    
    @Test
    public void childSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("#id > .class", l).interpret();
        Assert.assertEquals(6, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("idSelector=0|id", l.getLista().get(1));
        Assert.assertEquals("simpleSelector=0|null|#id", l.getLista().get(2));
        Assert.assertEquals("classSelector=0|class", l.getLista().get(3));
        Assert.assertEquals("simpleSelector=1|>|.class", l.getLista().get(4));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(5));
        //System.out.println(l.getLista());
        
    }
    
    @Test
    public void siblingDescendantSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("a ~ #id .class", l).interpret();
        Assert.assertEquals(8, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=a", l.getLista().get(1));
        Assert.assertEquals("simpleSelector=0|null|a", l.getLista().get(2));
        Assert.assertEquals("idSelector=0|id", l.getLista().get(3));
        Assert.assertEquals("simpleSelector=1|~|#id", l.getLista().get(4));
        Assert.assertEquals("classSelector=0|class", l.getLista().get(5));
        Assert.assertEquals("simpleSelector=2| |.class", l.getLista().get(6));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(7));
        //System.out.println(l.getLista());
        
    }
    
    @Test
    public void composedSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("div.class1#id.class2", l).interpret();
        //System.out.println(l.getLista());
        Assert.assertEquals(7, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=div", l.getLista().get(1));
        Assert.assertEquals("classSelector=0|class1", l.getLista().get(2));
        Assert.assertEquals("idSelector=1|id", l.getLista().get(3));
        Assert.assertEquals("classSelector=2|class2", l.getLista().get(4));
        Assert.assertEquals("simpleSelector=0|null|div.class1#id.class2", l.getLista().get(5));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(6));
        
    }
    
    @Test
    public void attributeMultipleSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("*[_onlyName], a[href~=https] ,.class[name|=\"name\"] , "
                + "span#composed-id[attr=_val],[z-indez*='1'],"
                + "[href][data-aria^='1']", l).interpret();
        //System.out.println(l.getLista());
        
        //Assert.assertEquals(7, l.getLista().size());
        
        //group 0
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=*", l.getLista().get(1));
        Assert.assertEquals("attributeSelector=0|_onlyName", l.getLista().get(2));
        Assert.assertEquals("simpleSelector=0|null|*[_onlyName]", l.getLista().get(3));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(4));
        
        //group 1
        Assert.assertEquals("beginSelectorGroup=1", l.getLista().get(5));
        Assert.assertEquals("typeSelector=a", l.getLista().get(6));
        Assert.assertEquals("attributeSelector=0|href~=https", l.getLista().get(7));
        Assert.assertEquals("simpleSelector=0|null|a[href~=https]", l.getLista().get(8));
        Assert.assertEquals("endSelectorGroup=1", l.getLista().get(9));
        
        //group 2
        Assert.assertEquals("beginSelectorGroup=2", l.getLista().get(10));
        Assert.assertEquals("classSelector=0|class", l.getLista().get(11));
        Assert.assertEquals("attributeSelector=1|name|=\"name\"", l.getLista().get(12));
        Assert.assertEquals("simpleSelector=0|null|.class[name|=\"name\"]", l.getLista().get(13));
        Assert.assertEquals("endSelectorGroup=2", l.getLista().get(14));
        
        //group 3
        Assert.assertEquals("beginSelectorGroup=3", l.getLista().get(15));
        Assert.assertEquals("typeSelector=span", l.getLista().get(16));
        Assert.assertEquals("idSelector=0|composed-id", l.getLista().get(17));
        Assert.assertEquals("attributeSelector=1|attr=_val", l.getLista().get(18));
        Assert.assertEquals("simpleSelector=0|null|span#composed-id[attr=_val]", l.getLista().get(19));
        Assert.assertEquals("endSelectorGroup=3", l.getLista().get(20));
        
        //group 4
        Assert.assertEquals("beginSelectorGroup=4", l.getLista().get(21));
        Assert.assertEquals("attributeSelector=0|z-indez*='1'", l.getLista().get(22));
        Assert.assertEquals("simpleSelector=0|null|[z-indez*='1']", l.getLista().get(23));
        Assert.assertEquals("endSelectorGroup=4", l.getLista().get(24));
        
        //group 5
        Assert.assertEquals("beginSelectorGroup=5", l.getLista().get(25));
        Assert.assertEquals("attributeSelector=0|href", l.getLista().get(26));
        Assert.assertEquals("attributeSelector=1|data-aria^='1'", l.getLista().get(27));
        Assert.assertEquals("simpleSelector=0|null|[href][data-aria^='1']", l.getLista().get(28));
        Assert.assertEquals("endSelectorGroup=5", l.getLista().get(29));
        
    }
    
    @Test
    public void adjascentGroupSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("li + li.class1.class2,table#id tr", l).interpret();
        //System.out.println(l.getLista());
        Assert.assertEquals(15, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=li", l.getLista().get(1));
        Assert.assertEquals("simpleSelector=0|null|li", l.getLista().get(2));
        Assert.assertEquals("typeSelector=li", l.getLista().get(3));
        Assert.assertEquals("classSelector=0|class1", l.getLista().get(4));
        Assert.assertEquals("classSelector=1|class2", l.getLista().get(5));
        Assert.assertEquals("simpleSelector=1|+|li.class1.class2", l.getLista().get(6));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(7));
        Assert.assertEquals("beginSelectorGroup=1", l.getLista().get(8));
        Assert.assertEquals("typeSelector=table", l.getLista().get(9));
        Assert.assertEquals("idSelector=0|id", l.getLista().get(10));
        Assert.assertEquals("simpleSelector=0|null|table#id", l.getLista().get(11));
        Assert.assertEquals("typeSelector=tr", l.getLista().get(12));
        Assert.assertEquals("simpleSelector=1| |tr", l.getLista().get(13));
        Assert.assertEquals("endSelectorGroup=1", l.getLista().get(14));
        
    }
    
}

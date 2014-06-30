package br.com.starcode.jerichoselector;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Selector list:
 * http://www.w3.org/TR/css3-selectors/#selectors
 *
 */
public class SelectorParserTest {

    @Test
    public void nullValue() {
        
        boolean hasException = false;
        try {
            new SelectorParser(null, null);
        } catch (IllegalArgumentException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void empty() {
        
        boolean hasException = false;
        try {
            new SelectorParser("", null);
        } catch (IllegalArgumentException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void testNull() {
        
        boolean hasException = false;
        try {
            new SelectorParser("*", null);
        } catch (IllegalArgumentException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void endUnexpected() {
        
        boolean hasException = false;
        try {
            new SelectorParser("a > ", new Listener()).interpret();
        } catch (SelectorParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void contentLeft() {
        
        boolean hasException = false;
        try {
            new SelectorParser("a z ^", new Listener()).interpret();
        } catch (SelectorParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
   /* @Test
    public void expectIdentifier() {
        
        boolean hasException = false;
        try {
            new SelectorParser("[a=]", new Listener()).interpret();
        } catch (SelectorParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }*/

    @Test
    public void expectIdentifier2() {
        
        boolean hasException = false;
        try {
            new SelectorParser("[=]", new Listener()).interpret();
        } catch (SelectorParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void expectToken() {
        
        boolean hasException = false;
        try {
            new SelectorParser("[a", new Listener()).interpret();
        } catch (SelectorParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void expectIdentifier3() {
        
        boolean hasException = false;
        try {
            new SelectorParser("a#", new Listener()).interpret();
        } catch (SelectorParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }
    
    @Test
    public void expectIdentifier4() {
        
        boolean hasException = false;
        try {
            new SelectorParser("x.", new Listener()).interpret();
        } catch (SelectorParserException e) {
            hasException = true;
        }
        Assert.assertTrue(hasException);
        
    }

    private class Listener implements SelectorParserListener {
        
        final List<String> lista = new ArrayList<String>();
        
        public void endSelectorGroup(int number, SelectorParserContext context) {
            lista.add("endSelectorGroup=" + number);
        }
        
        public void beginSelectorGroup(int number, SelectorParserContext context) {
            lista.add("beginSelectorGroup=" + number);
        }

        public void typeSelector(SelectorParserContext context) {
            lista.add("typeSelector=" + context.getContext());
        }

        public void simpleSelector(int number,
                SelectorCombinator combinator, SelectorParserContext context) {
            lista.add("simpleSelector=" + number + "|" 
                + (combinator != null ? combinator.getSign() : null) + "|" + context.getContext());
        }

        public void classSelector(int number,
                SelectorParserContext context) {
            lista.add("classSelector=" + number + "|" + context.getContext());
        }

        public void idSelector(int number,
                SelectorParserContext context) {
            lista.add("idSelector=" + number + "|" + context.getContext());
        }

        public void attributeSelector(int number,
                AttributeSelector as, SelectorParserContext context) {
            lista.add("attributeSelector=" + number + "|" + context.getContext());
        }
        
        public List<String> getLista() {
            return lista;
        }
        
    }
    
    @Test
    public void universal() throws Exception {
        
        Listener l = new Listener();
        new SelectorParser("*", l).interpret();
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
        new SelectorParser("table", l).interpret();
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
        new SelectorParser(".class", l).interpret();
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
        new SelectorParser("#identifier", l).interpret();
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
        new SelectorParser("[name='test']", l).interpret();
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
        new SelectorParser("[name='\\'test\\'']", l).interpret();
        Assert.assertEquals(4, l.getLista().size());
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("attributeSelector=0|name=''test''", l.getLista().get(1));
        Assert.assertEquals("endSelectorGroup=0", l.getLista().get(3));
        //System.out.println(l.getLista());
        
    }
    
    @Test
    public void childSelector() throws Exception {
        
        Listener l = new Listener();
        new SelectorParser("#id > .class", l).interpret();
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
        new SelectorParser("a ~ #id .class", l).interpret();
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
        new SelectorParser("div.class1#id.class2", l).interpret();
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
        new SelectorParser("*[onlyName], a[href~=https] ,.class[name|=\"name\"] , "
                + "span#composed-id[attr=val],[z-indez*='1'],"
                + "[href][data-aria^='1']", l).interpret();
        System.out.println(l.getLista());
        
        //Assert.assertEquals(7, l.getLista().size());
        
        //group 0
        Assert.assertEquals("beginSelectorGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=*", l.getLista().get(1));
        Assert.assertEquals("attributeSelector=0|onlyName", l.getLista().get(2));
        Assert.assertEquals("simpleSelector=0|null|*[onlyName]", l.getLista().get(3));
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
        Assert.assertEquals("attributeSelector=1|attr=val", l.getLista().get(18));
        Assert.assertEquals("simpleSelector=0|null|span#composed-id[attr=val]", l.getLista().get(19));
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
        new SelectorParser("li + li.class1.class2,table#id tr", l).interpret();
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

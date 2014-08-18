package br.com.starcode.jerichoselector;

import org.junit.Assert;
import org.junit.Test;

/**
 * Selector list:
 * http://www.w3.org/TR/css3-selectors/#selectors
 *
 */
public class SimpleSelectorParserTest {

    @Test
    public void universal() throws Exception {
        
        Listener l = new Listener();
        new Parser("*", l).interpret();
        Assert.assertEquals(4, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=*", l.getLista().get(1));
        Assert.assertEquals("selectorSequence=null|*", l.getLista().get(2));
        Assert.assertEquals("endGroup=0", l.getLista().get(3));
        //System.out.println(lista);
        
    }
    
    @Test
    public void element() throws Exception {
        
        Listener l = new Listener();
        new Parser("table", l).interpret();
        Assert.assertEquals(4, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=table", l.getLista().get(1));
        Assert.assertEquals("selectorSequence=null|table", l.getLista().get(2));
        Assert.assertEquals("endGroup=0", l.getLista().get(3));
        //System.out.println(l.getLista());
        
    }
    
    @Test
    public void classSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser(".class", l).interpret();
        Assert.assertEquals(4, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("classSelector=class", l.getLista().get(1));
        Assert.assertEquals("selectorSequence=null|.class", l.getLista().get(2));
        Assert.assertEquals("endGroup=0", l.getLista().get(3));
        //System.out.println(l.getLista());
        
    }
    
    @Test
    public void idSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("#identifier", l).interpret();
        Assert.assertEquals(4, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("idSelector=identifier", l.getLista().get(1));
        Assert.assertEquals("selectorSequence=null|#identifier", l.getLista().get(2));
        Assert.assertEquals("endGroup=0", l.getLista().get(3));
        //System.out.println(l.getLista());
        
    }
    
    @Test
    public void attributeSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("[name='test']", l).interpret();
        Assert.assertEquals(4, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("attributeSelector=name='test'", l.getLista().get(1));
        Assert.assertEquals("selectorSequence=null|[name='test']", l.getLista().get(2));
        Assert.assertEquals("endGroup=0", l.getLista().get(3));
        //System.out.println(l.getLista());
        
    }
    
    @Test
    public void attributeEscapeSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("[name='\\'test\\'']", l).interpret();
        Assert.assertEquals(4, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("attributeSelector=name=''test''", l.getLista().get(1));
        Assert.assertEquals("endGroup=0", l.getLista().get(3));
        //System.out.println(l.getLista());
        
    }
    
    @Test
    public void childSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("#id > .class", l).interpret();
        Assert.assertEquals(6, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("idSelector=id", l.getLista().get(1));
        Assert.assertEquals("selectorSequence=null|#id", l.getLista().get(2));
        Assert.assertEquals("classSelector=class", l.getLista().get(3));
        Assert.assertEquals("selectorSequence=>|.class", l.getLista().get(4));
        Assert.assertEquals("endGroup=0", l.getLista().get(5));
        //System.out.println(l.getLista());
        
    }
    
    @Test
    public void siblingDescendantSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("a ~ #id .class", l).interpret();
        Assert.assertEquals(8, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=a", l.getLista().get(1));
        Assert.assertEquals("selectorSequence=null|a", l.getLista().get(2));
        Assert.assertEquals("idSelector=id", l.getLista().get(3));
        Assert.assertEquals("selectorSequence=~|#id", l.getLista().get(4));
        Assert.assertEquals("classSelector=class", l.getLista().get(5));
        Assert.assertEquals("selectorSequence= |.class", l.getLista().get(6));
        Assert.assertEquals("endGroup=0", l.getLista().get(7));
        //System.out.println(l.getLista());
        
    }
    
    @Test
    public void composedSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("div.class1#id.class2", l).interpret();
        //System.out.println(l.getLista());
        Assert.assertEquals(7, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=div", l.getLista().get(1));
        Assert.assertEquals("classSelector=class1", l.getLista().get(2));
        Assert.assertEquals("idSelector=id", l.getLista().get(3));
        Assert.assertEquals("classSelector=class2", l.getLista().get(4));
        Assert.assertEquals("selectorSequence=null|div.class1#id.class2", l.getLista().get(5));
        Assert.assertEquals("endGroup=0", l.getLista().get(6));
        
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
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=*", l.getLista().get(1));
        Assert.assertEquals("attributeSelector=_onlyName", l.getLista().get(2));
        Assert.assertEquals("selectorSequence=null|*[_onlyName]", l.getLista().get(3));
        Assert.assertEquals("endGroup=0", l.getLista().get(4));
        
        //group 1
        Assert.assertEquals("beginGroup=1", l.getLista().get(5));
        Assert.assertEquals("typeSelector=a", l.getLista().get(6));
        Assert.assertEquals("attributeSelector=href~=https", l.getLista().get(7));
        Assert.assertEquals("selectorSequence=null|a[href~=https]", l.getLista().get(8));
        Assert.assertEquals("endGroup=1", l.getLista().get(9));
        
        //group 2
        Assert.assertEquals("beginGroup=2", l.getLista().get(10));
        Assert.assertEquals("classSelector=class", l.getLista().get(11));
        Assert.assertEquals("attributeSelector=name|=\"name\"", l.getLista().get(12));
        Assert.assertEquals("selectorSequence=null|.class[name|=\"name\"]", l.getLista().get(13));
        Assert.assertEquals("endGroup=2", l.getLista().get(14));
        
        //group 3
        Assert.assertEquals("beginGroup=3", l.getLista().get(15));
        Assert.assertEquals("typeSelector=span", l.getLista().get(16));
        Assert.assertEquals("idSelector=composed-id", l.getLista().get(17));
        Assert.assertEquals("attributeSelector=attr=_val", l.getLista().get(18));
        Assert.assertEquals("selectorSequence=null|span#composed-id[attr=_val]", l.getLista().get(19));
        Assert.assertEquals("endGroup=3", l.getLista().get(20));
        
        //group 4
        Assert.assertEquals("beginGroup=4", l.getLista().get(21));
        Assert.assertEquals("attributeSelector=z-indez*='1'", l.getLista().get(22));
        Assert.assertEquals("selectorSequence=null|[z-indez*='1']", l.getLista().get(23));
        Assert.assertEquals("endGroup=4", l.getLista().get(24));
        
        //group 5
        Assert.assertEquals("beginGroup=5", l.getLista().get(25));
        Assert.assertEquals("attributeSelector=href", l.getLista().get(26));
        Assert.assertEquals("attributeSelector=data-aria^='1'", l.getLista().get(27));
        Assert.assertEquals("selectorSequence=null|[href][data-aria^='1']", l.getLista().get(28));
        Assert.assertEquals("endGroup=5", l.getLista().get(29));
        
    }
    
    @Test
    public void adjascentGroupSelector() throws Exception {
        
        Listener l = new Listener();
        new Parser("li + li.class1.class2,table#id tr", l).interpret();
        //System.out.println(l.getLista());
        Assert.assertEquals(15, l.getLista().size());
        Assert.assertEquals("beginGroup=0", l.getLista().get(0));
        Assert.assertEquals("typeSelector=li", l.getLista().get(1));
        Assert.assertEquals("selectorSequence=null|li", l.getLista().get(2));
        Assert.assertEquals("typeSelector=li", l.getLista().get(3));
        Assert.assertEquals("classSelector=class1", l.getLista().get(4));
        Assert.assertEquals("classSelector=class2", l.getLista().get(5));
        Assert.assertEquals("selectorSequence=+|li.class1.class2", l.getLista().get(6));
        Assert.assertEquals("endGroup=0", l.getLista().get(7));
        Assert.assertEquals("beginGroup=1", l.getLista().get(8));
        Assert.assertEquals("typeSelector=table", l.getLista().get(9));
        Assert.assertEquals("idSelector=id", l.getLista().get(10));
        Assert.assertEquals("selectorSequence=null|table#id", l.getLista().get(11));
        Assert.assertEquals("typeSelector=tr", l.getLista().get(12));
        Assert.assertEquals("selectorSequence= |tr", l.getLista().get(13));
        Assert.assertEquals("endGroup=1", l.getLista().get(14));
        
    }
    
}

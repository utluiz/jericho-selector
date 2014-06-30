package br.com.starcode.jerichoselector;


public interface SelectorParserListener {

    void beginSelectorGroup(int number, SelectorParserContext context);
    
    void endSelectorGroup(int number, SelectorParserContext context);

    void typeSelector(SelectorParserContext context);

    void simpleSelector(
            int number, 
            SelectorCombinator combinator,
            SelectorParserContext context);

    void classSelector(int number, SelectorParserContext context);

    void idSelector(int number, SelectorParserContext context);

    void attributeSelector(
            int number, 
            AttributeSelector as,
            SelectorParserContext context);
    
}

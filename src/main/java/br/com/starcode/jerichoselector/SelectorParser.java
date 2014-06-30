package br.com.starcode.jerichoselector;

/**
 * Selector list:
 * http://www.w3.org/TR/css3-selectors/#selectors
 *
 */
public class SelectorParser {

    protected String content;
    protected int pos;
    protected int len;
    protected Character current;
    protected SelectorParserListener parserListener;
    
    public SelectorParser(String selector, SelectorParserListener parserListener) {
        if (selector == null || selector.trim().isEmpty()) {
            throw new IllegalArgumentException("Selector cannot be null or empty!");
        }
        if (parserListener == null) {
            throw new IllegalArgumentException("SelectorParserListener cannot be null!");
        }
        this.parserListener = parserListener;
        this.content = selector;
        this.pos = -1;
        this.len = selector.length();
    }
    
    protected SelectorParserContext context(String context) {
        return new SelectorParserContext(content, context, pos);
    }
    
    protected Character next() {
        if (pos < len) {
            pos++;
        }
        if (pos < len) {
            return current = content.charAt(pos);
        }
        return current = null;
    }
    
    protected boolean end() {
        return pos >= len;
    }
    
    public void interpret() throws SelectorParserException {
        next();
        groups();
    } 
    
    /**
     * selectors_group
     *  : selector [ COMMA S* selector ]*
     *  ;
     */
    public void groups() throws SelectorParserException {

        int groupNumber = 0;
        while (!end()) {
            ignoreWhitespaces();
            parserListener.beginSelectorGroup(groupNumber, context(null));
            String selector = selector();
            parserListener.endSelectorGroup(groupNumber, context(selector));
            groupNumber++;
            ignoreWhitespaces();
            if (!end() && current != ',') {
                throw new SelectorParserException("There is something left in the selector at position " + pos);
            }
            next();
        }
    }
    
    /**
     * selector
     *  : simple_selector_sequence [ combinator simple_selector_sequence ]*
     *  ;
     *  
     *  combinator
     *  : PLUS S* | GREATER S* | TILDE S* | S+
     *  ;
     */
    public String selector() throws SelectorParserException {
        int selectorCount = 0;
        SelectorCombinator combinator = null;
        StringBuilder sb = new StringBuilder();
        while (!end()) {
            String simpleSelector = simpleSelectorSequence();
            sb.append(simpleSelector);
            //sends combinator here (the first case it's null)
            parserListener.simpleSelector(selectorCount, combinator, context(simpleSelector));
            //clear last result
            combinator = null;
            //stores if it has spaces until the next token
            boolean hasWhitespace = false;
            if (!end() && Character.isWhitespace(current)) {
                hasWhitespace = true;
                ignoreWhitespaces();
            }
            if (!end()) {
                //implements "combinator" rule
                if (current == '+') combinator = SelectorCombinator.ADJASCENT_SIBLING;
                else if (current == '>') combinator = SelectorCombinator.CHILD;
                else if (current == '~') combinator = SelectorCombinator.GENERAL_SIBLING;
                //if hasn't any but had spaces
                else if (hasWhitespace) combinator = SelectorCombinator.DESCENDANT;
                if (combinator == null || current == ',') {
                    break;
                }
                sb.append(current);
                //don't advance because spaces were just advanced
                if (combinator != SelectorCombinator.DESCENDANT) {
                    next();
                }
                ignoreWhitespaces();
                selectorCount++;
                if (end()) {
                    throw new SelectorParserException("Unexpected end of selector at position " + pos);
                }
            }
        }
        return sb.toString();
    }
    
    /**
     * simple_selector_sequence
     *  : [ type_selector | universal ]
     *    [ HASH | class | attrib | pseudo | negation ]*
     *  | [ HASH | class | attrib | pseudo | negation ]+
     *  ;
     *  
     *  type_selector
     *  : [ namespace_prefix ]? element_name
     *  ;
     *
     * namespace_prefix ** not implemented
     *  : [ IDENT | '*' ]? '|'
     *  ;
     *
     * element_name
     *  : IDENT
     *  ;
     *
     * universal
     *  : [ namespace_prefix ]? '*'
     *  ;
     *
     * class
     *  : '.' IDENT
     *  ;
     */
    public String simpleSelectorSequence() throws SelectorParserException {
        
        StringBuilder sb = new StringBuilder();
        boolean hasSimpleSelector = false;
        if (current == '*') {
            //universal selector
            sb.append(current);
            hasSimpleSelector = true;
            parserListener.typeSelector(context("*"));
            next();
        } else if (Character.isLetter(current)) {
            //type selector
            String type = identifier();
            sb.append(type);
            hasSimpleSelector = true;
            parserListener.typeSelector(context(type));
        }
        
        int selectorCount = 0;
        while (!end()) {
            if (current == '.') {
                //class selector
                sb.append(current);
                next();
                String className = !end() ? identifier() : null;
                if (className == null || className.isEmpty()) {
                    throw new SelectorParserException("Expected className at position " + pos);
                }
                sb.append(className);
                parserListener.classSelector(selectorCount, context(className));
            } else if (current == '#') {
                //hash selector
                sb.append(current);
                next();
                String id = !end() ? identifier() : null;
                if (id == null || id.isEmpty()) {
                    throw new SelectorParserException("Expected id at position " + pos);
                }
                sb.append(id);
                parserListener.idSelector(selectorCount, context(id));
            } else if (current == '[') {
                //attribute selectors
                sb.append(current);
                next();
                AttributeSelector as = attribute();
                sb.append(as.getContext());
                sb.append(']');
                parserListener.attributeSelector(selectorCount, as, context(as.getContext()));
            } else if (current == ':') {
                //TODO pseudo or negation selector
                sb.append(current);
                next();
                if (!end() && current == ':') {
                    next();
                }
                String ident = !end() ? identifier() : null;
                if (ident == null || ident.isEmpty()) {
                    throw new SelectorParserException("Expected identifier at position " + pos);
                }
                if ("not".equalsIgnoreCase(ident)) {
                    not();
                } else {
                    pseudo();
                }
            } else {
                break;
            }
            selectorCount++;
        }
        if (!hasSimpleSelector && selectorCount == 0) {
            throw new SelectorParserException("No real selector found at position " + pos);
        }
            
        return sb.toString();
        
    }
    
    /**
     * attrib
     *  : '[' S* [ namespace_prefix ]? IDENT S*
     *        [ [ PREFIXMATCH |
     *            SUFFIXMATCH |
     *            SUBSTRINGMATCH |
     *            '=' |
     *            INCLUDES |
     *            DASHMATCH ] S* [ IDENT | STRING ] S*
     *        ]? ']'
     *  ;
     */
    private AttributeSelector attribute() throws SelectorParserException {
        
        StringBuilder sb = new StringBuilder();
        ignoreWhitespaces();
        String name = !end() ? identifier() : null;
        if (name == null || name.isEmpty()) {
            throw new SelectorParserException("Expected attribute name at position " + pos);
        }
        sb.append(name);
        ignoreWhitespaces();
        if (end()) {
            throw new SelectorParserException("Unexpected end of selector selector at position " + pos);
        }
        AttributeOperator operator = null;
        if (current == '=') {
            operator = AttributeOperator.EQUALS;
            next();
        } else if (current != ']') {
            if (current == '~') operator = AttributeOperator.INCLUDES; 
            else if (current == '|') operator = AttributeOperator.DASH_MATCH; 
            else if (current == '^') operator = AttributeOperator.PREFIX_MATCH; 
            else if (current == '$') operator = AttributeOperator.SUFFIX_MATCH; 
            else if (current == '*') operator = AttributeOperator.SUBSTRING_MATCH;
            else throw new SelectorParserException("Invalid operator ('" + current + "') at position " + pos);
            next();
            if (end() || current != '=') {
                throw new SelectorParserException("Expected '=' sign at position " + pos);
            }
            next();
        }
        
        ignoreWhitespaces();
        if (end()) {
            throw new SelectorParserException("Unexpected end of attribute selector at position " + pos);
        }
        
        //value
        String value = null;
        if (operator != null) {
            sb.append(operator.getSign());
            if (current == '\'' || current == '"') {
                char quote = current;
                value = string();
                sb.append(quote);
                sb.append(value);
                sb.append(quote);
            } else {
                value = identifier();
                sb.append(value);
            }
        }
        
        ignoreWhitespaces();
        
        //end of attribute
        if (end() || current != ']') {
            throw new SelectorParserException("Token ']' expected at position " + pos);
        }
        next();
        
        return new AttributeSelector(name, operator, value, sb.toString());
        
    }

    //TODO revisar
    private String string() throws SelectorParserException {
        Character openQuote = current;
        boolean escape = false;
        next();
        StringBuilder sb = new StringBuilder(); 
        while (!end()) {
            if (escape) {
                //TODO implement UNICODE
                if (current == openQuote || current == '\\') {
                    sb.append(current);
                    escape = false;
                } else {
                    throw new SelectorParserException("Invalid escape character at position " + pos);
                }
            } else {
                if (current == '\\') {
                    escape = true;
                } else {
                    sb.append(current);
                }
            }
            next();
            if (!escape && current == openQuote) break;
        }
        
        if (current != openQuote) {
            throw new SelectorParserException("String not closed!");
        }
        next();
        return sb.toString();
        
    }

    /**
     * negation
     *  : NOT S* negation_arg S* ')'
     *  ;
     *
     * negation_arg
     *  : type_selector | universal | HASH | class | attrib | pseudo
     *  ;
     */
    private void not() throws SelectorParserException {
        // TODO Auto-generated method stub
    }

    /**
     * pseudo
     *  // '::' starts a pseudo-element, ':' a pseudo-class 
     *  // Exceptions: :first-line, :first-letter, :before and :after. 
     *  // Note that pseudo-elements are restricted to one per selector and 
     *  // occur only in the last simple_selector_sequence. 
     *  : ':' ':'? [ IDENT | functional_pseudo ]
     *  ;
     *  
     * functional_pseudo
     *  : FUNCTION S* expression ')'
     *  ;
     *
     * expression
     *  // In CSS3, the expressions are identifiers, strings, 
     *  // or of the form "an+b" 
     *  : [ [ PLUS | '-' | DIMENSION | NUMBER | STRING | IDENT ] S* ]+
     *  ;
     */
    private void pseudo() throws SelectorParserException {
        // TODO Auto-generated method stub
    }

    protected void ignoreWhitespaces() throws SelectorParserException {
        if (!end() && Character.isWhitespace(current)) {
            while (!end() && Character.isWhitespace(current)) {
                next();
            }
        }
    }
    
    /**
     * ident     [-]?{nmstart}{nmchar}*
     * name      {nmchar}+
     * nmstart   [_a-z]|{nonascii}|{escape}
     * nonascii  [^\0-\177]
     * unicode   \\[0-9a-f]{1,6}(\r\n|[ \n\r\t\f])?
     * escape    {unicode}|\\[^\n\r\f0-9a-f]
     * nmchar    [_a-z0-9-]|{nonascii}|{escape}
     * num       [0-9]+|[0-9]*\.[0-9]+
     */
    protected String identifier() throws SelectorParserException {
        //TODO implement unicode, escape, [-], nonascii
        StringBuilder sb = new StringBuilder();
        
        //validates first character
        if (!end() && !Character.isLetter(current) && current != '_') {
            return "";
        }
        while (!end() && (Character.isLetterOrDigit(current) || current == '-' || current == '_')) {
            sb.append(current);
            next();
        }
        return sb.toString();
    }
    
}

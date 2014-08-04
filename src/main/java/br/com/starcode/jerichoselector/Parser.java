package br.com.starcode.jerichoselector;

import java.util.ArrayList;
import java.util.List;

import br.com.starcode.jerichoselector.model.AttributeOperator;
import br.com.starcode.jerichoselector.model.AttributeSelector;
import br.com.starcode.jerichoselector.model.ClassSelector;
import br.com.starcode.jerichoselector.model.Combinator;
import br.com.starcode.jerichoselector.model.Context;
import br.com.starcode.jerichoselector.model.Group;
import br.com.starcode.jerichoselector.model.HashSelector;
import br.com.starcode.jerichoselector.model.NegationSelector;
import br.com.starcode.jerichoselector.model.PseudoExpression;
import br.com.starcode.jerichoselector.model.PseudoExpression.Item;
import br.com.starcode.jerichoselector.model.PseudoExpression.Type;
import br.com.starcode.jerichoselector.model.PseudoSelector;
import br.com.starcode.jerichoselector.model.PseudoSelector.PseudoType;
import br.com.starcode.jerichoselector.model.Selector;
import br.com.starcode.jerichoselector.model.SimpleSelector;
import br.com.starcode.jerichoselector.model.SimpleSelectorSequence;
import br.com.starcode.jerichoselector.model.TypeSelector;

/**
 * Selector list:
 * http://www.w3.org/TR/css3-selectors/#selectors
 *
 */
public class Parser {

    protected String content;
    protected int pos;
    protected int len;
    protected Character current;
    protected ParserListener parserListener;
    
    public Parser(String selector, ParserListener parserListener) {
        if (selector == null || selector.trim().isEmpty()) {
            throw new IllegalArgumentException("Selector cannot be null or empty!");
        }
        if (parserListener == null) {
            throw new IllegalArgumentException("ParserListener cannot be null!");
        }
        this.parserListener = parserListener;
        this.content = selector;
        this.pos = -1;
        this.len = selector.length();
    }
    
    protected Context context(String context) {
        return new Context(content, context, pos, pos);
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
    
    public void interpret() throws ParserException {
        next();
        groups();
    } 
    
    /**
     * selectors_group
     *  : selector [ COMMA S* selector ]*
     *  ;
     */
    public List<Group> groups() throws ParserException {

        List<Group> groups = new ArrayList<Group>();
        int groupNumber = 0;
        while (!end()) {
            ignoreWhitespaces();
            parserListener.beginGroup(groupNumber, pos);
            Selector selector = selector();
            Group group = new Group(selector, context(selector.toString()));
            groups.add(group);
            parserListener.endGroup(group);
            groupNumber++;
            ignoreWhitespaces();
            if (!end() && current != ',') {
                throw new ParserException("There is something left in the selector at position " + pos);
            }
            next();
        }
        return groups;
        
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
    public List<SimpleSelector> selector() throws ParserException {
        int selectorCount = 0;
        Combinator combinator = null;
        StringBuilder sb = new StringBuilder();
        List<SimpleSelector> simpleSelectors = new ArrayList<SimpleSelector>();
        while (!end()) {
            SimpleSelector simpleSelector = simpleSelectorSequence();
            sb.append(simpleSelector);
            //sends combinator here (the first case it's null)
            simpleSelectors.add(simpleSelector);
            parserListener.selectorSequence(simpleSelector);
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
                if (current == '+') combinator = Combinator.ADJASCENT_SIBLING;
                else if (current == '>') combinator = Combinator.CHILD;
                else if (current == '~') combinator = Combinator.GENERAL_SIBLING;
                //if hasn't any but had spaces
                else if (hasWhitespace) combinator = Combinator.DESCENDANT;
                if (combinator == null || current == ',') {
                    break;
                }
                sb.append(current);
                //don't advance because spaces were just advanced
                if (combinator != Combinator.DESCENDANT) {
                    next();
                }
                ignoreWhitespaces();
                selectorCount++;
                if (end()) {
                    throw new ParserException("Unexpected end of selector at position " + pos);
                }
            }
        }
        return new Selector(simpleSelectors, context(sb.toString()));;
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
    public SimpleSelector simpleSelectorSequence() throws ParserException {
        
        List<SimpleSelector> list = new ArrayList<SimpleSelector>();
        StringBuilder sb = new StringBuilder();
        boolean hasSimpleSelector = false;
        if (current == '*') {
            //universal selector
            sb.append(current);
            hasSimpleSelector = true;
            TypeSelector ts = new TypeSelector("*", context("*"));
            parserListener.typeSelector(ts);
            list.add(ts);
            next();
        } else if (Character.isLetter(current)) {
            //type selector
            String type = identifier();
            sb.append(type);
            hasSimpleSelector = true;
            TypeSelector ts = new TypeSelector(type, context(type));
            parserListener.typeSelector(ts);
            list.add(ts);
        }
        
        int selectorCount = 0;
        boolean hasPseudoElement = false;
        while (!end()) {
            if (current == '.') {
                //class selector
                sb.append(current);
                next();
                String className = !end() ? identifier() : null;
                if (className == null || className.isEmpty()) {
                    throw new ParserException("Expected className at position " + pos);
                }
                sb.append(className);
                ClassSelector cs = new ClassSelector(className, context("." + className));
                list.add(cs);
                parserListener.classSelector(cs);
            } else if (current == '#') {
                //hash selector
                sb.append(current);
                next();
                String id = !end() ? identifier() : null;
                if (id == null || id.isEmpty()) {
                    throw new ParserException("Expected id at position " + pos);
                }
                HashSelector hs = new HashSelector(id, context("#" + id));
                list.add(hs);
                parserListener.idSelector(hs);
                sb.append(id);
            } else if (current == '[') {
                //attribute selectors
                sb.append(current);
                next();
                AttributeSelector as = attribute();
                list.add(as);
                parserListener.attributeSelector(as);
                sb.append(as);
                sb.append(']');
            } else if (current == ':') {
                //pseudo-element, pseudo-class or negation
                sb.append(current);
                next();
                if (end()) {
                    throw new ParserException("Expected pseudo-element or pseudo-class at " + pos);
                }
                boolean doubleColon = false;
                if (current == ':') {
                    doubleColon = true;
                    sb.append(current);
                    next();
                }
                String ident = !end() ? identifier() : null;
                if (ident == null || ident.isEmpty()) {
                    throw new ParserException("Expected identifier at position " + pos);
                }
                boolean special = isPseudoSpecialCase(ident);
                if (special || doubleColon) {
                    //pseudo-element (double colon or special cases)
                    //allow just one
                    if (hasPseudoElement) {
                        throw new ParserException("Only one pseudo-element is allowed for each simple selector and a second one was found at position " + pos);
                    }
                    PseudoSelector ps = pseudo(ident, PseudoType.PseudoElement, doubleColon);
                    list.add(ps);
                    parserListener.pseudoSelector(ps);
                    sb.append(ps);
                    hasPseudoElement = true;
                } else if ("not".equalsIgnoreCase(ident)) {
                    //negation
                    NegationSelector n = negation(selectorCount);
                    list.add(n);
                    sb.append(ident);
                    sb.append(n);
                } else {
                    //pseudo-class
                    PseudoSelector ps = pseudo(ident, PseudoType.PseudoClass, false);
                    list.add(ps);
                    parserListener.pseudoSelector(ps);
                    sb.append(ps);
                }
            } else {
                break;
            }
            selectorCount++;
        }
        if (!hasSimpleSelector && selectorCount == 0) {
            throw new ParserException("No real selector found at position " + pos);
        }
            
        return list
        
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
    protected AttributeSelector attribute() throws ParserException {
        
        StringBuilder sb = new StringBuilder();
        ignoreWhitespaces();
        String name = !end() ? identifier() : null;
        if (name == null || name.isEmpty()) {
            throw new ParserException("Expected attribute name at position " + pos);
        }
        sb.append(name);
        ignoreWhitespaces();
        if (end()) {
            throw new ParserException("Unexpected end of selector selector at position " + pos);
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
            else throw new ParserException("Invalid operator ('" + current + "') at position " + pos);
            next();
            if (end() || current != '=') {
                throw new ParserException("Expected '=' sign at position " + pos);
            }
            next();
        }
        
        ignoreWhitespaces();
        if (end()) {
            throw new ParserException("Unexpected end of attribute selector at position " + pos);
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
            throw new ParserException("Token ']' expected at position " + pos);
        }
        next();
        
        return new AttributeSelector(name, operator, value, context(sb.toString()));
        
    }

    /**
     * string    {string1}|{string2}
     * string1   \"([^\n\r\f\\"]|\\{nl}|{nonascii}|{escape})*\"
     * string2   \'([^\n\r\f\\']|\\{nl}|{nonascii}|{escape})*\'
     * nonascii  [^\0-\177]
     * escape    {unicode}|\\[^\n\r\f0-9a-f]
     * unicode   \\[0-9a-f]{1,6}(\r\n|[ \n\r\t\f])?
     * nl        \n|\r\n|\r|\f
     */
    protected String string() throws ParserException {
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
                    throw new ParserException("Invalid escape character at position " + pos);
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
            throw new ParserException("String not closed!");
        }
        next();
        return sb.toString();
        
    }
    
    /**
     * num       [0-9]+|[0-9]*\.[0-9]+ 
     */
    protected String number() throws ParserException {
        StringBuilder sb = new StringBuilder();
        sb.append(current);
        next();
        boolean hasDot = false;
        while (!end() && (Character.isDigit(current) || current == '.')) {
            if (current == '.') {
                if (hasDot) {
                    throw new ParserException("Unexpected '.' at position " + pos);
                }
                hasDot = true;
            }
            sb.append(current);
            next();
        }
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
    protected NegationSelector negation(int number) throws ParserException {
        
        StringBuilder sb = new StringBuilder();
        ignoreWhitespaces();
        if (end() || current != '(') {
            throw new ParserException("Expected '(' at position " + pos);
        }
        sb.append(current);
        next();
        ignoreWhitespaces();
        if (end()) {
            throw new ParserException("Selector expected at position " + pos);
        }

        SimpleSelector simpleSelector = null;
        if (current == '*') {
            //universal selector
            sb.append(current);
            TypeSelector ts = new TypeSelector("*", context("*"));
            parserListener.negationTypeSelector(ts);
            simpleSelector = ts;
            next();
        } else if (Character.isLetter(current)) {
            //type selector
            String type = identifier();
            sb.append(type);
            TypeSelector ts = new TypeSelector(type, context(type));
            parserListener.negationTypeSelector(ts);
            simpleSelector = ts;
        } else if (current == '.') {
            //class selector
            sb.append(current);
            next();
            String className = !end() ? identifier() : null;
            if (className == null || className.isEmpty()) {
                throw new ParserException("Expected className at position " + pos);
            }
            ClassSelector cs = new ClassSelector(className, context("." + className));
            simpleSelector = cs;
            parserListener.negationClassSelector(cs);
            sb.append(className);
        } else if (current == '#') {
            //hash selector
            sb.append(current);
            next();
            String id = !end() ? identifier() : null;
            if (id == null || id.isEmpty()) {
                throw new ParserException("Expected id at position " + pos);
            }
            HashSelector hs = new HashSelector(id, context("#" + id));
            simpleSelector = hs;
            parserListener.negationIdSelector(hs);
            sb.append(id);
        } else if (current == '[') {
            //attribute selectors
            sb.append(current);
            next();
            AttributeSelector as = attribute();
            simpleSelector = as;
            parserListener.negationAttributeSelector(as);
            sb.append(as.getContext());
            sb.append(']');
        } else if (current == ':') {
            //pseudo-class only
            sb.append(current);
            next();
            if (end()) {
                throw new ParserException("Expected pseudo-element or pseudo-class at " + pos);
            }
            boolean doubleColon = false;
            if (current == ':') {
                doubleColon = true;
                sb.append(current);
                next();
            }
            String ident = !end() ? identifier() : null;
            if (ident == null || ident.isEmpty()) {
                throw new ParserException("Expected identifier at position " + pos);
            }
            if ("not".equalsIgnoreCase(ident) || isPseudoSpecialCase(ident) || doubleColon) {
                throw new ParserException("Expected pseudo-class (starting with ':', except ':not', ':first-line', ':first-letter', ':before' and ':after') at position " + pos);
            }
            PseudoSelector ps = pseudo(ident, PseudoType.PseudoClass, doubleColon);
            simpleSelector = ps;
            parserListener.negationPseudoSelector(ps);
            sb.append(ps.getContext());
        } else {
            throw new ParserException("Selector expected at position " + pos);
        }
        
        ignoreWhitespaces();
        if (end() || current != ')') {
            throw new ParserException("Expected ')' at position " + pos);
        }
        sb.append(current);
        next();
            
        return new NegationSelector(simpleSelector, context(sb.toString()));
        
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
     */
    protected PseudoSelector pseudo(String ident, PseudoType type, boolean doubleColon) throws ParserException {
        
        StringBuilder sb = new StringBuilder();
        sb.append(ident);
        PseudoExpression expression = null;
        if (!end() && current == '(') {
            sb.append(current);
            next();
            ignoreWhitespaces();
            if (end()) {
                throw new ParserException("Expected expression at position " + pos);
            }
            
            //expression
            expression = expression();
            sb.append(expression.getContext());
            
            //close parenthesis
            ignoreWhitespaces();
            if (end() || current != ')') {
                throw new ParserException("Expected ')' at position " + pos);
            }
            sb.append(current);
            next();
        }
        return new PseudoSelector(ident, type, doubleColon, expression, context(sb.toString()));
        
    }
    
    /**
     * expression
     *  // In CSS3, the expressions are identifiers, strings, 
     *  // or of the form "an+b" 
     *  : [ [ PLUS | '-' | DIMENSION | NUMBER | STRING | IDENT ] S* ]+
     *  ;
     *  
     *  {num}{ident}     return DIMENSION;
     *  {num}            return NUMBER;
     */
    protected PseudoExpression expression() throws ParserException {
        
        List<Item> items = new ArrayList<Item>();
        StringBuilder sb = new StringBuilder();
        while (!end()) {
            
            if (current == '+') {
                items.add(new Item(Type.Signal, "+"));
                sb.append(current);
                next();
            } else if (current == '-') {
                items.add(new Item(Type.Signal, "-"));
                sb.append(current);
                next();
            } else if (Character.isLetter(current)) {
                //identifier
                String ident = identifier();
                sb.append(ident);
                items.add(new Item(Type.Identifier, ident));
            } else if (Character.isDigit(current)) {
                //number or dimension
                String number = number();
                sb.append(number);
                if (!end() && Character.isLetter(current)) {
                    String ident = identifier();
                    sb.append(ident);
                    items.add(new Item(Type.Dimension, number + ident));
                } else {
                    items.add(new Item(Type.Number, number));
                }
            } else if (current == '\'' || current == '"') {
                String s = string();
                sb.append(s);
                items.add(new Item(Type.StringType, s));
            } else {
                break;
            }
            ignoreWhitespaces();
            
        }
        return new PseudoExpression(items, sb.toString());
        
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
    protected String identifier() throws ParserException {
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
    
    /**
     * pseudo (Special cases)
     *  // '::' starts a pseudo-element, ':' a pseudo-class 
     *  // Exceptions: :first-line, :first-letter, :before and :after. 
     */
    protected boolean isPseudoSpecialCase(String identifier) {
        return "first-line".equalsIgnoreCase(identifier) ||
                "first-letter".equalsIgnoreCase(identifier) ||
                "before".equalsIgnoreCase(identifier) ||
                "after".equalsIgnoreCase(identifier);
    }

    protected void ignoreWhitespaces() throws ParserException {
        if (!end() && Character.isWhitespace(current)) {
            while (!end() && Character.isWhitespace(current)) {
                next();
            }
        }
    }
    
}

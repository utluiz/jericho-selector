package br.com.starcode.jerichoselector;

import br.com.starcode.jerichoselector.model.AttributeSelector;
import br.com.starcode.jerichoselector.model.Combinator;
import br.com.starcode.jerichoselector.model.Context;
import br.com.starcode.jerichoselector.model.PseudoSelector;

public class MultipleParserListener implements ParserListener {

    private ParserListener[] listeners;
    
    public MultipleParserListener(ParserListener... listeners) {
        if (listeners == null) {
            throw new IllegalArgumentException("Null parameter!");
        }
       this.listeners = listeners;
    }

    public void beginSelectorGroup(int number, Context context) {
        for (ParserListener listener : listeners) {
            listener.beginSelectorGroup(number, context);
        }
    }

    public void endGroup(int number, Context context) {
        for (ParserListener listener : listeners) {
            listener.endGroup(number, context);
        }
    }

    public void typeSelector(Context context) {
        for (ParserListener listener : listeners) {
            listener.typeSelector(context);
        }
    }

    public void classSelector(int number, Context context) {
        for (ParserListener listener : listeners) {
            listener.classSelector(number, context);
        }
    }

    public void idSelector(int number, Context context) {
        for (ParserListener listener : listeners) {
            listener.idSelector(number, context);
        }
    }

    public void attributeSelector(int number, AttributeSelector as,
            Context context) {
        for (ParserListener listener : listeners) {
            listener.attributeSelector(number, as, context);
        }
    }

    public void pseudoSelector(int number, PseudoSelector pseudoSelector,
            Context context) {
        for (ParserListener listener : listeners) {
            listener.pseudoSelector(number, pseudoSelector, context);
        }
    }

    public void simpleSelector(int number, Combinator combinator,
            Context context) {
        for (ParserListener listener : listeners) {
            listener.simpleSelector(number, combinator, context);
        }
    }

    public void negationTypeSelector(int number, Context context) {
        for (ParserListener listener : listeners) {
            listener.negationTypeSelector(number, context);
        }
    }

    public void negationClassSelector(int number, Context context) {
        for (ParserListener listener : listeners) {
            listener.negationClassSelector(number, context);
        }
    }

    public void negationAttributeSelector(int number, AttributeSelector type,
            Context context) {
        for (ParserListener listener : listeners) {
            listener.negationAttributeSelector(number, type, context);
        }
    }

    public void negationIdSelector(int number, Context context) {
        for (ParserListener listener : listeners) {
            listener.negationIdSelector(number, context);
        }
    }

    public void negationPseudoSelector(int number, PseudoSelector pseudoSelector,
            Context context) {
        for (ParserListener listener : listeners) {
            listener.negationPseudoSelector(number, pseudoSelector, context);
        }
    }
    
}

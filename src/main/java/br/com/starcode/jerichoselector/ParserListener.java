package br.com.starcode.jerichoselector;

import br.com.starcode.jerichoselector.model.AttributeSelector;
import br.com.starcode.jerichoselector.model.ClassSelector;
import br.com.starcode.jerichoselector.model.Group;
import br.com.starcode.jerichoselector.model.HashSelector;
import br.com.starcode.jerichoselector.model.PseudoSelector;
import br.com.starcode.jerichoselector.model.SimpleSelectorSequence;
import br.com.starcode.jerichoselector.model.TypeSelector;

public interface ParserListener {

    void beginGroup(int number, int position);
    void endGroup(Group group);
    void selectorSequence(SimpleSelectorSequence simpleSelector);
    
    void typeSelector(TypeSelector typeSelector);
    void classSelector(ClassSelector classSelector);
    void idSelector(HashSelector hashSelector);
    void attributeSelector(AttributeSelector attributeSelector);
    void pseudoSelector(PseudoSelector pseudoSelector);

    void negationTypeSelector(TypeSelector typeSelector);
    void negationClassSelector(ClassSelector classSelector);
    void negationIdSelector(HashSelector hashSelector);
    void negationAttributeSelector(AttributeSelector attributeSelector);
    void negationPseudoSelector(PseudoSelector pseudoSelector);
    
}
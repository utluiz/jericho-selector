package br.com.starcode.jerichoselector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import au.id.jericho.lib.html.Element;
import au.id.jericho.lib.html.Source;

public class $ {

    protected Source source;
    protected List<Element> selectedElements;
    
    /**
     * Main constructor
     * @param source
     */
    public $(Source source, String initialSelector) {
        this.source = source;
    }
    
    public $(String html, String initialSelector) {
        this(new Source(html), initialSelector);
    }
    
    public $(File file, String initialSelector) throws FileNotFoundException, IOException {
        this(new Source(new FileInputStream(file)), initialSelector);
    }
    
    public $(InputStream input, String initialSelector) throws IOException {
        this(new Source(input), initialSelector);
    }
    
    public $(Reader reader, String initialSelector) throws IOException {
        this(new Source(reader), initialSelector);
    }
    
    public $ find(String selector) {
        List<Element> newSelectedElements = new ArrayList<Element>();
        for (Element e : selectedElements) {
            newSelectedElements.addAll(applySelector(e, selector));
        }
        this.selectedElements = newSelectedElements;
        return this;
    }
    
    protected List<Element> applySelector(Element e, String selector) {
        List<Element> newSelectedElements = new ArrayList<Element>();
        
        
        return newSelectedElements;
    }
    
}

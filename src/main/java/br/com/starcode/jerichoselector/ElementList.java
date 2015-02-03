package br.com.starcode.jerichoselector;

import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;

public class ElementList extends ArrayList<Element> {

	private static final long serialVersionUID = 1L;

	public ElementList() {
	}
	
	public ElementList(List<Element> elements) {
		super(elements);
	}
	
	public static interface CallbackFilter {
		boolean test(Element e);
	}
	
	public static interface Callback {
		List<Element> listen(Element e);
	}
	
	/**
	 * Test each element through callback and keep only those who return true
	 * @param callback
	 * @return Returns a new list (does not modify the original)
	 */
	public ElementList filter(CallbackFilter callback) {
		ElementList result = new ElementList();
		for (Element current : this) {
			
			if (callback.test(current)) {
				result.add(current);
			}
			
		}
		return result;
	}

	/**
	 * Walk recursively though all elements and their children and test them all, keeping only those who return true 
	 * @param callback
	 * @return Returns a new list
	 */
	public ElementList walk(CallbackFilter callback) {
		ElementList result = new ElementList();
		for (Element current : this) {
			
			for (Element descendant : current.getAllElements()) {
				if (callback.test(descendant)) {
					result.add(descendant);
				}
			}
			
		}
		return result;
	}
	
	/**
	 * Execute some action for each item and make a list of the items returned
	 * @param callback
	 * @return Returns a new list
	 */
	public ElementList apply(Callback callback) {
		ElementList result = new ElementList();
		for (Element current : this) {
			
			List<Element> partialResult = callback.listen(current);
			if (partialResult != null) {
				result.addAll(partialResult);
			}
			
		}
		return result;
	}

	/*public ElementList filter(FilterCallback callback, boolean recursive) {
		ElementList result = new ElementList();
		for (Element current : this) {
			
			if (!recursive) {
				if (callback.test(current)) {
					result.add(current);
				}
			} else {
				for (Element descendant : current.getAllElements()) {
					if (callback.test(descendant)) {
						result.add(descendant);
					}
				}
			}
			
		}
		return result;
	}*/

/*	public ElementList walk(Callback callback) {
		ElementList result = new ElementList();
		for (Element current : this) {
			
			List<Element> partialResult = callback.listen(current);
			if (partialResult != null) {
				result.add(current);
			}
			
			List<Element> descendants = current.getAllElements();
			for (Element descendant : descendants) {
				
				partialResult = callback.listen(descendant);
				if (partialResult != null) {
					result.addAll(partialResult);
				}
				
			}
			
		}
		return result;
	}*/
	
}

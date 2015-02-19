Jericho Selector  [![Build Status](https://travis-ci.org/utluiz/parCSSer.svg?branch=master)](https://travis-ci.org/utluiz/jericho-selector)
================

jQuery-like selector for [Jericho HTML Parser][1].

## Why

Jericho is a great library for parsing and manipulating HTML. But it lacks selectors.

Yes, we have [jsoup][2] that has selectors, but there are differences between the projects.

For instance, Jericho allows you to modify just an excerpt of the HTML (keeping formatting and white spaces), while jsoup rewrite the entire structure. It happens because Jericho regards about each character in the document.

And even if I had not any other reason, I just like options. Let each library shine in their own way. ;)

## How 

Just add the following dependency:

	<dependency>
		<groupId>br.com.starcode.jerichoselector</groupId>
		<artifactId>jericho-selector</artifactId>
		<version>1.0.1-RELEASE</version>
	</dependency>
	
Import the static method `$` that is used as the entry point:

	import static br.com.starcode.jerichoselector.jerQuery.$;
	
Then you can query HTML elements just like jQuery:

	$(html, "p.my-text")
	
This method is overloaded several times, so you have a lot of input optionsL. Have a look at the unit test to see complete examples.

## Status

- CSS3 Selector parser library based on [W3C Official Specification][3]
	- Status: [**DONE**][5]
- Element selector implementation using Jericho HTML Parser and the parser from step
    - STATUS: **DONE**
    
## Roadmap

- Improve performance of specific selectors (shortcuts)
    - STATUS: TO DO
- [Transversing methods][9]
- [jQuery extended features][4]
- [DOM manipulation][6]
- [Attributes handling][7]
- [`val()` function][8]
- [`each()`](http://api.jquery.com/each/)  (allow lambda expressions), [`index()`](http://api.jquery.com/index/), [`length()`](http://api.jquery.com/length/), [`selector()`](http://api.jquery.com/selector/), [`contains()`](http://api.jquery.com/jQuery.contains/), [`grep`](http://api.jquery.com/jQuery.grep/)
    
## Get in touch

Please, fell free to tell me if you have any issue, suggestion, contribution, or comment. ;)

Just go to [http://luizricardo.org](http://luizricardo.org/en/who-i-am/#contact).

  [1]: http://jericho.htmlparser.net
  [2]: http://jsoup.org/
  [3]: http://www.w3.org/TR/css3-selectors/
  [4]: http://api.jquery.com/category/selectors/jquery-selector-extensions/
  [5]: https://github.com/utluiz/parCSSer
  [6]: http://api.jquery.com/category/manipulation/
  [7]: http://api.jquery.com/category/attributes/
  [8]: http://api.jquery.com/val/
  [9]: http://api.jquery.com/category/traversing/
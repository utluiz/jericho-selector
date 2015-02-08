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
	- Status: [**DONE**](https://github.com/utluiz/parCSSer)
- Jericho Implementation for selection using the parser from step 1
    - STATUS: **DONE**
    
## Roadmap

- Fluent API based on jQuery (find, not, closest, etc.)
    - STATUS: TO DO
4. Improve performance of specific selectors (shortcuts)
    - STATUS: TO DO
    
## Get in touch

Please, fell free to tell me if you have any issue, suggestion, contribution, or comment. ;)

Just go to [http://luizricardo.org](http://luizricardo.org/en/who-i-am/#contact).

  [1]: http://jericho.htmlparser.net
  [2]: http://jsoup.org/
  [3]: http://www.w3.org/TR/css3-selectors/
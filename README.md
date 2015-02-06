Jericho Selector  [![Build Status](https://travis-ci.org/utluiz/parCSSer.svg?branch=master)](https://travis-ci.org/utluiz/jericho-selector)
================

jQuery-like selector for [Jericho HTML Parser][1].

## Why

Jericho is a great library for parsing and manipulating HTML. But it lacks selectors.

Yes, we have [jsoup][2] that has selectors, but there are differences between the projects.

For instance, Jericho allows you to modify just an excerpt of the HTML (keeping formatting and white spaces), while jsoup rewrite the entire structure. It happens because Jericho regards about each character in the document.

And even if I had not any other reason, I just like options. Let each library shine in their own way. ;)

## Roadmap

1. Build a CSS3 Selector parser based on [W3C Official Specification][3]
	- Status: [**DONE**](https://github.com/utluiz/parCSSer)
2. Jericho Implementation for elements selection using the parser from step 1
    - STATUS: **DONE**
3. Fluent API
    - STATUS: **DONE**
4. Improve performance of specific selectors (shortcuts)
    -STATUS: TO DO

  [1]: http://jericho.htmlparser.net
  [2]: http://jsoup.org/
  [3]: http://www.w3.org/TR/css3-selectors/
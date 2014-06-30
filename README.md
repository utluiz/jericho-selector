Jericho Selector
================

jQuery-like selector capabilities to [Jericho HTML Parser][1].

## Why

Jericho is a great library for parsing and manipulating HTML. But it lacks selectors.

Yes, we have [jsoup][2] that has selectors, but there are differences between the projects.

For instance, Jericho allows you to modify just an excerpt of the HTML, while jsoup rewrite the entire structure. It happens because Jericho regards about spaces between tags and attributes.

And even if I had not any other reason, I just like options. Let each library shine in their own way. ;)

## How 

Roadmap:

1. Build a CSS3 Selector parser based on [W3C Official Specification][3] (Status: almost done, lacks pseudo-selector `:` and UTF-8 Characters Support)
2. Jericho Implementation for selection using the result of step 1 (`TODO`)
3. Fluent API (`TODO`).

  [1]: http://jericho.htmlparser.net
  [2]: http://jsoup.org/
  [3]: http://www.w3.org/TR/css3-selectors/
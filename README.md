
Wizard is a website development and administration framework

##Core Features:
- spliting a page into several self-contained modules(called widget)
- each widget works like a standalone app (fully mvc support)
- dynamic changing the behavior or view of a widget
- dynamic organizing the layout of a page

##Introduction:

A widget could be in two forms: module or container

- if a widget is a module, it is a simple display unit
- if a widget is a container, it could contain other widget (specified by its layout)


####Widget in form of module:
Widget in form of module has there parts:

- the business code (in a widgetName.groovy file)
- the view (in a widgetName.ftl file)
- the script (in a widgetName.js file)

The execution flow is as follows:

- execute the business script
- merge the view under the context returned by the business script


####Widget in form of container:
Widget in form of container has one more part:

- a layout

The execution flow is as follows:

- execute the business script
- parse the layout and execute the widget specified in it
- merge the view under the context returned by the business script and the it's sub-modules

###Getting Started
First retrieve a Widget through the WidgetRepo:
	
	WidgetRepo repo= WidgetRepoFactory.getRepo("default");
	Widget test=repo.loadByName("test");
	
Note that here the widget name is called test, so we must place three files under the classpath: test.groovy, test.ftl, and test.js(optional)

First, test.groovy, the script constructs a shop object, then puts it into a map, finally returns the map as the merging context;

	shop=['shopId':param.shopId,'name':'kfc'];
	return ['shop':shop];
	
in test.ftl, we simply display the shop info, since the merging context contains a shop object, so we can use it in the view

	shopId:${shop.shopId}
	name:${shop.name}
	
Finally, render the widget through a WidgetRenderer:

	Map<String,Object> params=new HashMap<String, Object>(); //construct the param
	params.put("shopId",500000);
	WidgetRenderer renderer=WidgetRendererFactory.getRenderer("default");
	renderer.render(widget, Widget.ModeType.Display.value, params);
	
The resultl will be:
	
	shopId:500000
	name:kfc


  
     






	








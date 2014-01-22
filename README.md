
Wizard is a website development and administration framework

## Core Features:
- spliting a page into several self-contained modules(called widget)
- each widget works as a standalone app (fully mvc support)
- dynamic changing the behavior or view of a widget
- dynamic organizing the layout of a page

## Introduction:

A widget is a display unit that consists of there parts:

- the business code (in a widgetName.groovy file)
- the view (in a widgetName.ftl file)
- the script (in a widgetName.js file)

The execution flow is as follows:

- execute the business script
- render the view with the template model returned by the business script


A widget could also contain other widgets, in this case, the container widget should include one more config:

- the layout

And the execution flow will be as follows:

- execute the business script
- parse the layout and execute the widget specified in it
- render the view with the template model and sub-widgets

## Getting Started:

For example, we need a widget to display basic shop info, let's call it "basicInfo". we need to place three files under the classpath: basicInfo.groovy, basicInfo.ftl, and basicInfo.js(optional).

First, basicInfo.groovy, the script constructs a shop object, then puts it into a map, finally returns the map as the template model.

	shop = ['shopId':param.shopId,'name':'kfc'];
	return ['shop':shop];
	
in basicInfo.ftl, it simply displays the shop info.

	shopId:${shop.shopId}
	name:${shop.name}
	
Finally, render the widget through a WidgetRenderer:

	Map<String,Object> params = new HashMap<String, Object>(); //construct the param
	params.put("shopId",500000);
	WidgetRenderer renderer = WidgetRendererFactory.getRenderer("default");
	RenderingResult result = renderer.render("basicInfo", Widget.ModeType.Display.value, params);
	
The result's output will be:
	
	shopId:500000
	name:kfc
	
## Designs

#### 1. Model & Repo
The two key models in the framework are:

- Widget: a self contained display unit  
- Layout: the display rule

We can retreive them through the corresponding repo (WidgetRepo and LayoutRepo). For example, get the "basicInfo" widget:

	Widget basicInfo = WidgetRepoFactory.getRepo("default").loadByName("basicInfo");

Since we need to support both the local mode and db mode, two kinds of repo are designed:

- WidgetLocalRepo & LayoutLocalRepo: they search the groovy file, ftl file, js file under the classpath, read the content in it, finally construct a fully-fledged Widget or Layout instance.
- WidgetDBRepo & LayoutDBRepo: they simply load the Widget or Layout instance from mongoDB by name

The LocalRepo and DBRepo are all registered in the RepoFactory, you can retreive them by calling:

	WidgetRepo localRepo=WidgetRepoFactory.getRepo("local");
	WidgetRepo dbRepo=WidgetRepoFactory.getRepo("db");

In runtime, we can choose to use theses two repos so that switch between the local mode and db mode can go smoothly.

#### 2. Execution Flow
The framework makes it easy to extend using an "Interceptor" pattern. When you call a WidgetRenderer to render a widget, it just invoke each interceptor, and all the work will be done by these interceptors.

The Interceptor interface is defined as follows:
	
	public interface Interceptor {
    	public String intercept(InvocationContext invocation) throws Exception;
	}

The InvocationContext object has all the information needed, for example, we can get the Widget instance and param from it.

Let's get our hand dirty. Suppose we need to calculate how much time it takes to render a widget, we can simply write a interceptor to achieve it:

	public class DebuggingInterceptor implements Interceptor{
    	@Override
    	public String intercept(InvocationContext invocation) throws 	Exception {
        	long start = System.currentTimeMillis();
        	String code=invocation.invoke();
        	long end= System.currentTimeMillis();
        	System.out.println(invocation.getWidget().name+": "+(end-start)+"ms");
        	return code;
    	}
	}
	
Then in the widget.yaml, register it with the name "debug" and put it into the default stack.

	interceptors:
    	factory:
        	debug: com.dianping.wizard.widget.interceptor.DebuggingInterceptor
    	stack:
        	default: debug|exception|merge|layout|business
        	
There are some built-in interceptors, they are:

- business: execute the groovy script
- layout: handle the layout-parsing related work if the widget has a layout
- merge: merge the view with the template model
- exception: handle the exception, if an exception happens, it will print it on the page or log it according to the config


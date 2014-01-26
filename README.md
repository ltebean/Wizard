
Wizard is a website development and administration framework

## Core Features:
- spliting a page into several self-contained modules(called widget)
- each widget works as a standalone app (fully mvc support)
- dynamic changing the behavior or view of a widget
- dynamic organizing the layout of a page

## Introduction:

A widget is a display unit that consists of there parts:

- business code (in a widgetName.groovy file)
- view (in a widgetName.ftl file)
- javascript code (in a widgetName.js file)

A widget could also contain other widgets, in this case, the container widget should include one more config:

- layout

And the execution flow will be as follows:

- execute the business script
- parse the layout and execute the widget specified in it
- render the view with the template model and sub-widgets

## Getting Started:

#### 1. Let the framework rock
To let the framework work, we need the put a config file called "widget.yaml" under the classpath:

	
	mode: local //use local mode or db mode
	modeConfig:
   	local:
      	enableDebug: false //enable debug mode or not
      	basePackage: widget //package root for search
    db:
        server:
            host: 127.0.0.1
            port: 27017
        dbName: test
	exception: print //print the exception or log it
	cache: com.dianping.wizard.repo.extensions.SimpleMemCache
	interceptors:
   		factory:
      		debug: com.dianping.wizard.widget.interceptor.extensions.DebuggingInterceptor
   		stack:
   			default: exception|merge|layout|cacheableTask|business
	freemarker:
    	properties: freemarker.properties //the location of freemarker config file
    	staticModels: //static models that can be used in ftl
            - com.dianping.wizard.extensions.HtmlFormater 
	extensions: //extensions that can be used in groovy code
   		service: com.dianping.wizard.extensions.ServiceLocator
	concurrent:
    	timeout: 1000
    	threadPool:
      		corePoolSize: 50
      		maximumPoolSize: 50
      		keepAliveTime: 0
      		blockingQueueCapacity: 1000

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

#### 2. Write a simple widget
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

#### 3. Write a Widget that has a layout	
Next let's learn how to write a widget that has a layout. For example, we want to build page like this:

	<html>
		<head></head>
		<body>
			<div class="wrapper"> 		
				<div class="main">
					<section>basicInfo</section>
					<section>reviewList</section>
				</div>
				<div class="aside">
					<section>banner</section>
				</div>
			</div>
			<script>
				console.log("script needed by baiscInfo");
				console.log("script needed by banner");
			</script>
		</body>
	</html>
	
The page itself is a widget, let's call it "index". After examine the html we could find that the "index" widget could has two sub-parts:"main" and "aside", and in each part it allows certain widgets to exist. So the "widget" widget could has a layout, the layout rule is specified in json format:

	{
		"main":["basicInfo","reviewList"],
		"aside":["aside"]
	}

Then in index.ftl, we could write this, the "index" widget actually acts as a container, it specifies a layout, then the framework will find all the widgets in that layout, execute them, and finally fill them into the container widget.
	
	<html>
		<head></head>
		<body>		
			<div class="wrapper"> 		
				<div class="main">
					${layout.main}
				</div>
				<div class="aside">
					${layout.aside}
				</div>
			</div>
			<script>
				${script}
			</script>
		</body>
	</html>
	



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

	WidgetRepo localRepo = WidgetRepoFactory.getRepo("local");
	WidgetRepo dbRepo = WidgetRepoFactory.getRepo("db");

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
    	public String intercept(InvocationContext invocation) throws Exception {
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
        	debug: com.dianping.wizard.widget.interceptor.extensions.DebuggingInterceptor
    	stack:
        	default: debug|exception|merge|layout|business
        	
There are some built-in interceptors, they are:

- business: execute the groovy script
- layout: handle the layout-parsing related work if the widget has a layout
- merge: merge the view with the template model
- exception: handle the exception, if an exception happens, it will print it on the page or log it according to the config


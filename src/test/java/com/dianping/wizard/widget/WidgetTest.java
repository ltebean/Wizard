package com.dianping.wizard.widget;

import com.dianping.wizard.repo.GenericRepo;
import com.dianping.wizard.repo.RepoFactory;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.yaml.snakeyaml.Yaml;

import javax.script.ScriptException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Unit test for simple App.
 */
public class WidgetTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public WidgetTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( WidgetTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() throws ScriptException, NoSuchMethodException {
        GenericRepo<Widget> widgetRepo= RepoFactory.getRepo(Widget.class);
        Widget widget=widgetRepo.loadByName("shopDisplay");
        widget.name="shopDisplay";
        Mode displayMode=new Mode();
        displayMode.code="name=service.get('shopService').getShopName(1);shop=['shopId':param.shopId,'name':name];return ['shop':shop];";
        displayMode.template="shopId:${shop.shopId} name:${HtmlFormater.capitalize(shop.name)}";
        widget.modes.put("display",displayMode);
        //repo.saveWidget(widget);

        Map<String,Object> params=new HashMap<String, Object>();
        params.put("shopId",500000);
        WidgetRenderer manager=WidgetRendererFactory.getRenderer("default");
        System.out.println(manager.renderWidget(widget, Widget.ModeType.Display.value, params));

//        ScriptEngineManager factory = new ScriptEngineManager();
//        ScriptEngine engine = factory.getEngineByName("JavaScript");
//
//        Bindings bindings=engine.createBindings();
//        //bindings.put("status",0);
//
//        Bindings bindings2=engine.createBindings();
//        //bindings2.put("status",0);
//
//        engine.eval("var status=0; status++; println(status);",bindings); //print 1
//        engine.eval("status++; println(status);",bindings2); //print 1

        //engine.put("status",0);
//        engine.eval("var status=0; status++; println(status);");
//        engine.eval("status++; println(status);");
        //Object obj = engine.get("status");
        //System.out.println(obj);
//        params=new HashMap<String, Object>();
//        System.out.println(manager.renderWidget(widget, Widget.ModeType.Display.value, params));
    }
}

package com.dianping.wizard.widget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        WidgetRepo repo=new WidgetRepoImpl();
        Widget widget=repo.loadWidgetByName("shopDisplay");
        widget.setName("shopDisplay");
        Mode displayMode=new Mode();
        displayMode.setCode("shop=['shopId':param.shopId];result=['shop':shop];result.widget=new com.dianping.wizard.widget.Widget();");
        displayMode.setTemplate("shopId:${shop.shopId} widget:${widget}");
        widget.getModes().put("display",displayMode);
        //repo.updateWidget(widget);

        Map<String,Object> params=new HashMap<String, Object>();
        params.put("shopId",500000);
        WidgetRenderer manager=new DefaultWidgetRenderer();
        System.out.println(manager.renderWidget(widget, Widget.ModeType.Display, params));
    }
}

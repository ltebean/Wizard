package com.dianping.wizard.widget;

import com.dianping.wizard.widget.repo.WidgetRepo;
import com.dianping.wizard.widget.repo.WidgetRepoFactory;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class WidgetTest
    extends TestCase
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
    public void testApp()
    {
        WidgetRepo repo= WidgetRepoFactory.getRepo("default");
        Widget widget=repo.loadWidgetByName("shopDisplay");
        widget.name="shopDisplay";
        Mode displayMode=new Mode();
        displayMode.code="shop=['shopId':param.shopId];result=['shop':shop];result.widget=new com.dianping.wizard.widget.Widget();";
        displayMode.template="shopId:${shop.shopId} widget:${widget}";
        widget.modes.put("display",displayMode);
        //repo.saveWidget(widget);

        Map<String,Object> params=new HashMap<String, Object>();
        params.put("shopId",500000);
        WidgetRenderer manager=WidgetRendererFactory.getRenderer("default");
        System.out.println(manager.renderWidget(widget, Widget.ModeType.Display.value, params));
    }
}

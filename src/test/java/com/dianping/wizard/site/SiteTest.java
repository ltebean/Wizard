package com.dianping.wizard.site;

import com.dianping.wizard.repo.WidgetRepo;
import com.dianping.wizard.repo.WidgetRepoFactory;
import com.dianping.wizard.widget.Widget;
import com.dianping.wizard.widget.WidgetRenderer;
import com.dianping.wizard.widget.WidgetRendererFactory;
import com.dianping.wizard.widget.concurrent.ConcurrentRenderer;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-27
 * Time: 上午11:06
 * To change this template use File | Settings | File Templates.
 */
public class SiteTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SiteTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SiteTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {

        WidgetRepo repo= WidgetRepoFactory.getRepo("default");
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("shopId",5000);

        Widget site= repo.loadByName("shop");

        WidgetRenderer renderer= WidgetRendererFactory.getRenderer("concurrent");
        System.out.println(renderer.render(site, Widget.ModeType.Display.value, params).output);

        long start=System.currentTimeMillis();


        System.out.println(renderer.render(site, Widget.ModeType.Display.value, params).output);

        System.out.println(System.currentTimeMillis()-start);
    }
}


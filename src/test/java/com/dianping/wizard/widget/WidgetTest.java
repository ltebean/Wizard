package com.dianping.wizard.widget;

import com.dianping.wizard.repo.WidgetRepo;
import com.dianping.wizard.repo.WidgetRepoFactory;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashMap;
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
    public void testApp() throws Exception {

        Map<String,Object> params=new HashMap<String, Object>();
        params.put("shopId",500000);
        WidgetRenderer renderer=WidgetRendererFactory.getRenderer("default");
        renderer.render("basicInfo", params);

        long s=System.currentTimeMillis();
        for(int i=0;i<500;i++){
            renderer.render("basicInfo", params);
        }
        System.out.println(System.currentTimeMillis()-s);

    }
}

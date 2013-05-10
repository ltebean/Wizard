package com.dianping.wizard.site;

import com.dianping.wizard.widget.Mode;
import com.dianping.wizard.widget.Widget;
import com.dianping.wizard.widget.WidgetRenderer;
import com.dianping.wizard.widget.WidgetRendererFactory;
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

//        Layout layout=new Layout();
//        layout.name="ad";
//        layout.config=new HashMap<String, List<String>>();
//        List<String> col=new ArrayList<String>();
//        col.add("shopDisplay");
//        col.add("ad");
//
//        layout.config.put("col", col);
//        LayoutRepo layoutRepo=new LayoutRepoImp();
//        layoutRepo.saveLayout(layout);


        Map<String,Object> params=new HashMap<String, Object>();
        params.put("shopId",5000);

        Widget site=new Widget();
        site.modes=new HashMap<String, Mode>();
        site.rule="if(param.shopId==500000){return 'once';}else{return 'ad';}";

        Mode displayMode=new Mode();
        displayMode.code="";
        displayMode.template="${layout.main}\n<script>${script}</script>";
        site.modes.put("display", displayMode);

        WidgetRenderer renderer= WidgetRendererFactory.getRenderer("default");
        System.out.println(renderer.renderWidget(site, Widget.ModeType.Display.value, params));

        long start=System.currentTimeMillis();
        //System.out.println(renderer.renderWidget(site, Widget.ModeType.Display.value, params));

        System.out.println(System.currentTimeMillis()-start);
    }
}


package com.dianping.wizard.widget.merger;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-24
 * Time: 上午9:22
 * To change this template use File | Settings | File Templates.
 */
public class FreemarkerMerger {

    private static FreemarkerMerger instance=new FreemarkerMerger();

    private Configuration cfg=new Configuration();

    public String merge(String templateString,Map<String,Object> context) throws Exception {
        Template template = new Template("strTemplate", new StringReader(templateString), cfg);
        StringWriter writer = new StringWriter();
        template.process(context,writer);
        return writer.getBuffer().toString();
    }

    private FreemarkerMerger() {
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        cfg.setTemplateLoader(stringLoader);
        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
    }

    public static FreemarkerMerger getInstance(){
        return instance;

    }

}

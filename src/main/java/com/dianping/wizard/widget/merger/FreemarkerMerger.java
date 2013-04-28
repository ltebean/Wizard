package com.dianping.wizard.widget.merger;

import com.dianping.wizard.config.Config;
import com.dianping.wizard.config.Constants;
import com.dianping.wizard.exception.WidgetException;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

/**
 * @author ltebean
 */
public class FreemarkerMerger {

    private static FreemarkerMerger instance=new FreemarkerMerger();

    private Configuration cfg;

    public String merge(String templateString,Map<String,Object> context) throws Exception {
        Template template = new Template("strTemplate", new StringReader(templateString), cfg);
        StringWriter writer = new StringWriter();
        template.process(context,writer);
        return writer.getBuffer().toString();
    }

    private FreemarkerMerger() {
        cfg=new Configuration();
        Properties properties = new Properties();
        String freemarkerProperties= Config.getConfiguraion().getString(Constants.FREEMARKER_PROPERTIES);
        if(StringUtils.isNotEmpty(freemarkerProperties)){
            try {
                properties.load(this.getClass().getClassLoader()
                        .getResourceAsStream(freemarkerProperties));
                cfg.setSettings(properties);
            } catch (Exception e) {
                throw new WidgetException("freemarker settings error",e);
            }
        }

        StringTemplateLoader stringLoader = new StringTemplateLoader();
        cfg.setTemplateLoader(stringLoader);
        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
    }

    public static FreemarkerMerger getInstance(){
        return instance;

    }

}

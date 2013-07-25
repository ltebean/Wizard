package com.dianping.wizard.widget.merger;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.exception.WidgetException;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ltebean
 */
public class FreemarkerMerger {

    private static final FreemarkerMerger instance = new FreemarkerMerger();

    private final freemarker.template.Configuration cfg;

    private final ConcurrentMap<String, Template> cache = new ConcurrentHashMap<String, Template>();

    public static FreemarkerMerger getInstance() {
        return instance;
    }

    private FreemarkerMerger() {
        cfg = new freemarker.template.Configuration();
        Properties properties = new Properties();
        String freemarkerProperties = Configuration.get("freemarker.properties", String.class);
        if (StringUtils.isNotEmpty(freemarkerProperties)) {
            try {
                properties.load(this.getClass().getClassLoader()
                        .getResourceAsStream(freemarkerProperties));
                cfg.setSettings(properties);
            } catch (Exception e) {
                throw new WidgetException("freemarker settings error", e);
            }
        }

        StringTemplateLoader stringLoader = new StringTemplateLoader();
        cfg.setTemplateLoader(stringLoader);
        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
    }

    public String merge(String templateName, String templateString, Map<String, Object> context) throws Exception {
        Template template = cache.get(templateString);
        if (template == null) {
            template = new Template(templateName, new StringReader(templateString), cfg);
            cache.putIfAbsent(templateString, template);
        }
        StringWriter writer = new StringWriter();
        template.process(context, writer);
        return writer.getBuffer().toString();
    }

}

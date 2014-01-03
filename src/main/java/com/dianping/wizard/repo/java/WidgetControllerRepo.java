package com.dianping.wizard.repo.java;

import com.dianping.wizard.exception.WidgetException;
import com.dianping.wizard.widget.java.WidgetConfig;
import com.dianping.wizard.widget.java.WidgetController;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * @author ltebean
 */
public class WidgetControllerRepo implements ApplicationContextAware,
        BeanFactoryPostProcessor {

    private ApplicationContext applicationContext;

    private String basePackage;


    public WidgetController loadByName(String name) {
        return (WidgetController)applicationContext.getBean(name);  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
                false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(
                WidgetConfig.class));
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) beanFactory;
        for (BeanDefinition beanDefinition : scanner
                .findCandidateComponents(basePackage)) {
            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
            Class<?> beanClass = null;
            String beanName = beanDefinition.getBeanClassName();
            try {
                beanClass = Class.forName(beanName);
            } catch (ClassNotFoundException e) {
                throw new WidgetException(e);
            }
            WidgetConfig widgetConfig = beanClass
                    .getAnnotation(WidgetConfig.class);
            factory.registerBeanDefinition(widgetConfig.name(), beanDefinition);

        }
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}

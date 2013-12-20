package com.dianping.wizard.widget.merger;

/**
 * @author ltebean
 */
public class Template {

    public final String name;

    public final String code;

    public Template(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static String generateName(String widgetName,String modeType,String codeType){
        StringBuilder builder=new StringBuilder(widgetName);
        builder.append(modeType);
        builder.append(codeType);
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Template)) return false;

        Template template = (Template) o;

        if (!code.equals(template.code)) return false;
        if (!name.equals(template.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }
}

package com.dianping.wizard.script;

/**
 * @author ltebean
 */
public class Script {

    public final String name;

    public final String code;

    public Script(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static String generateName(String widgetName,String modeType){
        StringBuilder builder=new StringBuilder(widgetName);
        builder.append(modeType);
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Script)) return false;

        Script script = (Script) o;

        if (!code.equals(script.code)) return false;
        if (!name.equals(script.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }
}

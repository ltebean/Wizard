/**
 * 
 */
package com.dianping.wizard.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.Id;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
 * @author cong.yu
 */
public class Model implements Serializable{
    @JsonProperty("_id")
    protected ObjectId id;

    @XmlJavaTypeAdapter(ObjectIdAdaptor.class)
    public org.bson.types.ObjectId getId() {
        return id;
    }

    public void setId(org.bson.types.ObjectId id) {
        this.id = id;
    }

    public String name="";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;

        Model model = (Model) o;

        if (!id.equals(model.id)) return false;
        if (!name.equals(model.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        if(id==null){
            return name.hashCode();
        }
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

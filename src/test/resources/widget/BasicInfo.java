package widget;

import com.dianping.wizard.widget.java.WidgetConfig;
import com.dianping.wizard.widget.java.WidgetController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ltebean
 */

@WidgetConfig(name="BasicInfo")
public class BasicInfo implements WidgetController {


    @Override
    public Map<String, Object> run(Map<String, Object> param) {
        Map<String,Object> result=new HashMap<String, Object>();
        result.put("shopId",500000);
        return  result;

    }


}

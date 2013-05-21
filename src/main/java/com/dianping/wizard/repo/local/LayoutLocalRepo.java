package com.dianping.wizard.repo.local;

import com.dianping.wizard.config.Yaml;
import com.dianping.wizard.repo.LayoutRepo;
import com.dianping.wizard.utils.FileUtils;
import com.dianping.wizard.widget.Layout;

import java.io.ByteArrayInputStream;
import java.util.Map;

/**
 * @author ltebean
 */
public class LayoutLocalRepo implements LayoutRepo {
    @Override
    public Layout loadByName(String name) {
        Layout layout=new Layout();
        String config= FileUtils.readFileOnClassPath(name,"layout");
        Yaml yaml=new Yaml(new ByteArrayInputStream(config.getBytes()));
        layout.name=yaml.get("name","",String.class);
        layout.config=yaml.get("config",null,Map.class);
        return layout;
    }

    @Override
    public Iterable<Layout> find(String query, Object... params) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Layout save(Layout layout) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException();
    }
}

package com.dianping.wizard.repo.local;

import com.dianping.wizard.repo.LayoutRepo;
import com.dianping.wizard.repo.WizardRepoException;
import com.dianping.wizard.repo.extensions.Cache;
import com.dianping.wizard.repo.extensions.CacheManager;
import com.dianping.wizard.repo.local.utils.FileUtils;
import com.dianping.wizard.widget.Layout;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * @author ltebean
 */
public class LayoutLocalRepo implements LayoutRepo {

    private final Cache cache;

    public LayoutLocalRepo() {
        this.cache = CacheManager.getInstance().getCache();
    }

    @Override
    public Layout loadByName(String name) {
        if (cache == null) {
            return constructLayout(name);
        }
        String key = cache.generateKey(Layout.class, name);
        Layout layout = (Layout) cache.get(key);
        if (layout == null) {
            layout = constructLayout(name);
            if (layout != null) {
                cache.add(key, layout);
            }
        }
        return layout;
    }

    private Layout constructLayout(String name) {
        Layout layout = new Layout();
        String config = FileUtils.readFileOnClassPath(name, "layout");
        layout.name = name;
        ObjectMapper mapper = new ObjectMapper();

        try {
            layout.config = mapper.readValue(config, Map.class);
        } catch (Exception e) {
            throw new WizardRepoException("error in parsing file: " + name + ".layout", e);
        }
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
    public Layout updateByName(Layout layout) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteByName(String name) {
        throw new UnsupportedOperationException();
    }
}

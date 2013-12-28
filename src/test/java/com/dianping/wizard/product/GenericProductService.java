package com.dianping.wizard.product;

import com.dianping.wizard.mongo.Model;

/**
 * Created with IntelliJ IDEA.
 * User: yucong
 * Date: 13-12-27
 * Time: 下午8:57
 * To change this template use File | Settings | File Templates.
 */
public interface GenericProductService<T extends Product,E extends Model> {

    public Product loadProductInfo(int shopId);

    public T loadProduct(int shopId);

    public void saveProduct(T product);
}

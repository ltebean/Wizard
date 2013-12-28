package com.dianping.wizard.product;

import com.dianping.wizard.mongo.JongoClient;
import com.dianping.wizard.mongo.Model;
import org.jongo.MongoCollection;

/**
 * Created with IntelliJ IDEA.
 * User: yucong
 * Date: 13-12-27
 * Time: 下午8:57
 * To change this template use File | Settings | File Templates.
 */
public class GenericProductServiceImpl<T extends Product,E extends Model> implements GenericProductService<T,E> {

    protected final MongoCollection col;

    protected Class<E> entityClass;

    protected Class<T>  dtoClass;

    protected String productType;

    public GenericProductServiceImpl(String productType) {
        this.productType=productType;
        this.col = JongoClient.getInstance().getCollection("product");
    }


    @Override
    public Product loadProductInfo(int shopId) {
        E entity =col.findOne("{shopId:#,productType:#}",shopId,productType).as(entityClass);
        Product dto=new Product();
        // copy properties
        return dto;
    }

    @Override
    public T loadProduct(int shopId) {
        try {
            E entity =col.findOne("{shopId:#,productType:#}",shopId,productType).as(entityClass);
            T dto=dtoClass.newInstance();
            // copy entity to dto;
            return dto;
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public void saveProduct(T product) {
        try {
            E entity =entityClass.newInstance();
            //convert dto 2 entity
            col.save(entity);
        }catch (Exception e){
            return;
        }
    }
}

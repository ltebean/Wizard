package com.dianping.wizard.product.client;

import com.dianping.wizard.product.*;

/**
 * Created with IntelliJ IDEA.
 * User: yucong
 * Date: 13-12-27
 * Time: 下午9:13
 * To change this template use File | Settings | File Templates.
 */
public class Client {

    public static void main(String[] args) {
        MenuService menuService=new MenuServiceImpl();
        MenuDTO menuDTO=menuService.loadProduct(500000);

        AlbumService albumService=new AlbumServiceImpl();
        AlbumDTO albumDTO=albumService.loadProduct(500000);
    }
}

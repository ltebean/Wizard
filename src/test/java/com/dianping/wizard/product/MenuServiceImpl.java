package com.dianping.wizard.product;

/**
 * Created with IntelliJ IDEA.
 * User: yucong
 * Date: 13-12-27
 * Time: 下午9:06
 * To change this template use File | Settings | File Templates.
 */
public class MenuServiceImpl  extends GenericProductServiceImpl<MenuDTO,Menu> implements MenuService {

    public MenuServiceImpl(){
        super("menu");
    }
}

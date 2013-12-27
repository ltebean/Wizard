package com.dianping.wizard.product;

/**
 * Created with IntelliJ IDEA.
 * User: yucong
 * Date: 13-12-27
 * Time: 下午9:12
 * To change this template use File | Settings | File Templates.
 */
public class AlbumServiceImpl extends GenericProductServiceImpl<AlbumDTO,Album> implements AlbumService {

    public AlbumServiceImpl() {
        super("album");
    }
}

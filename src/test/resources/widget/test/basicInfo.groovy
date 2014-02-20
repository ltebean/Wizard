package widget.test

name=service.get('shopService').getShopName(1);
shop=['shopId':param.shopId,'name':name];
cacheable.run("test",{
    println("by shop:info");
})

wz.require("shopService",{shopService ->
    def aShop=shopService.loadShop(10);
    shopService.saveShop();
})

return ['shop':shop];

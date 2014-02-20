package widget.test

name=service.get('shopService').getShopName(1);
shop=['shopId':param.shopId,'name':name];
cacheable.run("test",{
    println("by shop:info");
})

wz.require(["shopService","reviewService"],{shopService,reviewService ->
    def aShop=shopService.loadShop(10);
    def review=reviewService.loadReview(1);
})

return ['shop':shop];

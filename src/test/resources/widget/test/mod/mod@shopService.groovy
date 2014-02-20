package widget.test.mod

wz.define("shopService",{
    def counter=0;

    def loadShop= { shopId ->
        counter++;
        return "I am a shop "+shopId;
    }

    def saveShop= { shop ->
        print("shop saved");
    }

    return [
            'loadShop':loadShop,
            'saveShop':saveShop
    ]
})


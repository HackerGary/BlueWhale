package com.seecoder.BlueWhale;

import com.seecoder.BlueWhale.enums.GoodTypeEnum;
import com.seecoder.BlueWhale.service.OrderService;
import com.seecoder.BlueWhale.service.ProductService;
import com.seecoder.BlueWhale.vo.ProductCond;
import com.seecoder.BlueWhale.vo.ProductVO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BlueWhaleSearchTest {
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;

    @Test
    public void productSearchTest(){
        ProductCond productCondAccurately = new ProductCond("Z790", GoodTypeEnum.ELECTRONICS, 0, 10000);
        List<ProductVO> resultAccurately = productService.getProductByCond(productCondAccurately);
        Assert.assertEquals(1, resultAccurately.size());
        Assert.assertEquals(33, resultAccurately.get(0).getId().intValue());

        ProductCond productCondFuzzy = new ProductCond("R", GoodTypeEnum.ELECTRONICS, 0, 1000000);
        List<ProductVO> resultFuzzy = productService.getProductByCond(productCondFuzzy);
        Assert.assertEquals(4, resultFuzzy.size());
        int[] resultFuzzyId = new int[resultFuzzy.size()];
        int index = 0;
        for(ProductVO productVO: resultFuzzy){
            resultFuzzyId[index++] = productVO.getId();
        }
        Assert.assertArrayEquals(new int[]{34, 35, 36, 38},resultFuzzyId);

    }

}

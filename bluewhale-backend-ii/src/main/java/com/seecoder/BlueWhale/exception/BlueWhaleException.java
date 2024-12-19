package com.seecoder.BlueWhale.exception;

/**
 * @Author: DingXiaoyu
 * @Date: 0:26 2023/11/26
 * 你可以在这里自定义Exception
*/
public class BlueWhaleException extends RuntimeException{

    public BlueWhaleException(String message){
        super(message);
    }
    public static BlueWhaleException phoneAlreadyExists(){
        return new BlueWhaleException("手机号已经存在!");
    }

    public static BlueWhaleException notLogin(){
        return new BlueWhaleException("未登录!");
    }

    public static BlueWhaleException notManager(){
        return new BlueWhaleException("不是管理员!");
    }
    public static BlueWhaleException notCustomer(){
        return new BlueWhaleException("不是顾客!");
    }
    public static BlueWhaleException storeAlreadyExists(){
        return new BlueWhaleException("商店已经存在!");
    }
    public static BlueWhaleException phoneOrPasswordError(){
        return new BlueWhaleException("手机号或密码错误!");
    }

    public static BlueWhaleException fileUploadFail(){
        return new BlueWhaleException("文件上传失败!");
    }

    public static BlueWhaleException productAlreadyExists(){
        return new BlueWhaleException("商品已经存在!");
    }
    public static BlueWhaleException notStaff(){
        return new BlueWhaleException("不是工作人员!");
    }
    public static BlueWhaleException notPower(){throw new BlueWhaleException("您不是CEO或门店工作人员！无法下载订单报表！");}

    public static BlueWhaleException InventoryNotEnough(){return new BlueWhaleException("库存不足!");}

    public static BlueWhaleException ErrorStatus(){return new BlueWhaleException("状态错误!");}

    public static BlueWhaleException couponAlreadyReceive(){return new BlueWhaleException("此优惠券已经领取过!");}

    public static BlueWhaleException couponLeftNotEnough(){return new BlueWhaleException("优惠券已被一抢而空!");}

    public static BlueWhaleException coupontimeout(){return new BlueWhaleException("优惠券已过期");}
    public static BlueWhaleException payError(){
        return new BlueWhaleException("支付失败！");
    }
    public static BlueWhaleException refundError(){
        return new BlueWhaleException("退款失败！");
    }

    public static BlueWhaleException ErrorParam(){
        return new BlueWhaleException("前端传入参数错误！");
    }

    public static BlueWhaleException ErrorScore(){
        return new BlueWhaleException("评分错误！");
    }
}

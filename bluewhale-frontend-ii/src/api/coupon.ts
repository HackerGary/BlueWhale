import {axios} from "../utils/request.ts";
import {COUPON_MODULE} from "./_prefix.ts";

type StoreInfo = {
    storeId: number
}

type UserInfo = {
    userId: number
}

type CreateCouponGroupInfo = {
    discountRange: number,
    discountPrice: number,
    couponType: string,
    couponSum: number,
    couponGroupName: string,
    couponLeft: number,
    storeId: number,
    deadline: Date
}

type ReceiveInfo = {
    couponGroupId: number,
    userId: number,
}

type UsefulCouponInfo = {
    storeId: number,
    userId: number,
}

type CouponInfo = {
    price: number,
    couponId: number,
}

type UseCouponInfo = {
    couponId: number,
    orderId: number
}

export const createCouponGroup = (createCouponGroupInfo: CreateCouponGroupInfo) => {
    return axios.post(`${COUPON_MODULE}/createCouponGroup`, createCouponGroupInfo,
        {  headers: {'Content-Type': 'application/json'}})
        .then(res => {
            return res
        })
}

export const getCouponGroupByStoreId = (storeInfo: StoreInfo) => {
    return axios.get(`${COUPON_MODULE}/getCouponGroupByStoreId`, {params: storeInfo})
        .then(res => {
            return res
        })
}

export const getAllCouponGroup = () => {
    return axios.get(`${COUPON_MODULE}/getAllCouponGroup`)
        .then(res => {
            return res
        })
}

export const getCouponByUserId = (userInfo: UserInfo) => {
    return axios.get(`${COUPON_MODULE}/getCouponByUserId`, {params: userInfo})
        .then(res => {
            return res
        })
}

export const receiveCoupon = (receiveInfo: ReceiveInfo) => {
    return axios.get(`${COUPON_MODULE}/receiveCoupon`, {params: receiveInfo})
        .then(res => {
            return res
        })
}

export const getUsefulCouponByUserIdAndStoreId = (usefulCouponInfo: UsefulCouponInfo) => {
    return axios.get(`${COUPON_MODULE}/getUsefulCouponByUserIdAndStoreId`, {params: usefulCouponInfo})
        .then(res => {
            return res;
        })
}

export const previewCoupon = (couponInfo: CouponInfo) => {
    return axios.get(`${COUPON_MODULE}/previewCoupon`, {params: couponInfo})
        .then(res => {
            return res;
        })
}

export const useCoupon = (useCouponInfo: UseCouponInfo) => {
    return axios.get(`${COUPON_MODULE}/useCoupon`, {params: useCouponInfo})
        .then(res => {
            return res;
        })
}
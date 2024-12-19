//创建订单
import {ORDER_MODULE} from "./_prefix.ts";
import {axios} from '../utils/request'

type CreateOrderInfo = {
    productId: number,
    quantity: number,
    pricesum: number,
    pickGoodType: string,
    orderStatusEnum: string,
    receiver: string,
    telephone: string,
    address: string,
    userId: number
    storeId: number,
}
type UserInfo = {
    userId: number,
}
type StoreInfo = {
    storeId: number
}
type OrderInfo={
    orderId: number
}
type PayOrderInfo={
    orderId: number
    couponId: number
}
type UserIdInfo={
    userId: number
}
export const createOrder = (createOrderInfo: CreateOrderInfo) => {
    return axios.post(`${ORDER_MODULE}/createOrder`, createOrderInfo,
        {  headers: {'Content-Type': 'application/json'}})
        .then(res => {
            return res
        })
}
export const payOrder=(orderInfo:PayOrderInfo)=>{
    return axios.get(`${ORDER_MODULE}/payOrder`, {params: orderInfo})
        .then(res => {
            return res
        })
}

export const refundOrder=(orderInfo:OrderInfo)=>{
    return axios.get(`${ORDER_MODULE}/refundOrder`, {params: orderInfo})
        .then(res => {
            return res
        })
}
export const deliverOrder=(orderInfo:OrderInfo)=>{
    return axios.get(`${ORDER_MODULE}/deliverOrder`, {params: orderInfo})
        .then(res => {
            return res
        })
}
export const receiveOrder=(orderInfo:OrderInfo)=>{
    return axios.get(`${ORDER_MODULE}/receiveOrder`, {params: orderInfo})
        .then(res => {
            return res
        })
}
//获取订单列表
export const getOrderByUserId = (userInfo: UserInfo) => {
    return axios.get(`${ORDER_MODULE}/getOrderByUserId`, {params: userInfo})
        .then(res => {
            return res
        })
}
export const getOrderListByStoreId = (storeInfo: StoreInfo) => {
    return axios.get(`${ORDER_MODULE}/getOrderByStoreId`, {params: storeInfo})
        .then(res => {
            return res
        })
}
export const getAllOrderList = () => {
    return axios.get(`${ORDER_MODULE}/getAllOrder`)
        .then(res => {
            return res
        })
}

export const getOrderStatement = (userIdInfo: UserIdInfo) =>{
    return axios.get(`${ORDER_MODULE}/getOrderStatement`,{params:userIdInfo})
        .then(res =>{
            return res
        })
}
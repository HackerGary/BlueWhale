// Lab2新增
// 开发时请解除3-4行的注释
import {axios} from '../utils/request'
import {PRODUCT_MODULE} from './_prefix'

type CreateProductInfo = {
    name: string
    type: string
    introduction: string
    storeId: number
    price: number
    logoUrlList: string
    times: number
    score: number
}

type StoreInfo = {
    storeId: number
}

type ProductInfo = {
    productId: number
}

type InventoryInfo = {
    productId: number
    change: number
}
//创建商品
export const createProduct = (createProductInfo: CreateProductInfo) => {
    return axios.post(`${PRODUCT_MODULE}/createProduct`, createProductInfo,
        {  headers: {'Content-Type': 'application/json'}})
        .then(res => {
            return res
        })
}

//根据商店id获取物品列表
export const getproductList = (storeInfo: StoreInfo) => {
    return axios.get(`${PRODUCT_MODULE}/getProductListByStoreId`, {params: storeInfo})
        .then(res => {
            return res
        })
}
//根据物品id获取物品图片
export const getproductImgList = (productInfo: ProductInfo) => {
    return axios.get(`${PRODUCT_MODULE}/getProductImageById`, {params: productInfo})
        .then(res => {
            return res
        })
}
//根据物品id获取物品信息
export const getproductInfo = (productInfo: ProductInfo) => {
    return axios.get(`${PRODUCT_MODULE}/getProductById` , {params: productInfo})
        .then(res => {
            return res
        })
}

//改变库存
export const changeInventory = (inventoryInfo: InventoryInfo) => {
    return axios.get(`${PRODUCT_MODULE}/changeInventory`, {params: inventoryInfo})
        .then(res => {
            return res
        })
}
//根据商店id获取商店下的所有商品的第一张图片
export const getproductFirstImg = (storeInfo: StoreInfo) => {
    return axios.get(`${PRODUCT_MODULE}/getProductFirstImageListByStoreId`, {params: storeInfo})
        .then(res => {
            return res
        })
}

interface SearchInfo {
    name: string
    type: string
    minPrice: number
    maxPrice: number
}

//搜索商品
export const searchProduct = (searchInfo:SearchInfo) => {
    return axios.get(`${PRODUCT_MODULE}/getProductByCond`, {params: searchInfo})
        .then(res =>{
            return res
        })
}
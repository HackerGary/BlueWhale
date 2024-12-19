// Lab2新增
// 开发时请解除3-4行的注释
import {axios} from '../utils/request'
import {STORE_MODULE} from './_prefix'

type CreateStoreInfo = {
    name: string,
    introduction : string,
    logoUrl : string,
}

type StoreInfo = {
    storeId: number
}

//创建商店
export const createStore = (createStoreInfo: CreateStoreInfo) => {
    return axios.post(`${STORE_MODULE}/createStore`, createStoreInfo,
        {headers: {'Content-Type': 'application/json'}})
        .then(res => {
            return res
        })
}

//获取商店列表
export const storeList = () => {
    return axios.get(`${STORE_MODULE}`)
        .then(res => {
            return res
        })
}

//根据id获取商店
export const getstoreInfo = (storeInfo: StoreInfo) => {
    return axios.get(`${STORE_MODULE}/getStore` , {params: storeInfo})
        .then(res => {
            return res
        })
}
import {axios} from "../utils/request.ts";
import {COMMENT_MODULE} from "./_prefix.ts";

type CreateCommentInfo={
    text:string
    score:number
    userId:number
    productId:number
    orderId:number
    toCommentId:number
}
type ProductInfo={
    productId:number
}
type CommentIdInfo={
    commentId:number
}
export const createComment = (createCommentInfo:CreateCommentInfo) => {
    return axios.post(`${COMMENT_MODULE}/createComment`, createCommentInfo,
        {headers: {'Content-Type': 'application/json'}})
        .then(res => {
            return res
        })
}
export const getCommentListByProductId = (productInfo:ProductInfo) => {
    return axios.get(`${COMMENT_MODULE}/getCommentByProductId`, {params: productInfo})
        .then(res => {
            return res
        })
}

export const getUserNameByCommentId = (commentIdInfo:CommentIdInfo) => {
    return axios.get(`${COMMENT_MODULE}/getCommentUserNameByCommentId`, {params: commentIdInfo})
        .then(res => {
            return res
        })
}
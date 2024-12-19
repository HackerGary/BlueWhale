<!--Lab3新增-全部订单界面/主页-->
<script setup lang="ts" xmlns:el-table="http://www.w3.org/1999/html">
import {storeList} from "../../api/store.ts";
import {
  getAllOrderList,
  getOrderListByStoreId,
  getOrderByTelephone,
  payOrder,
  refundOrder,
  deliverOrder,
  receiveOrder,
  createOrder,
  getOrderStatement, getOrderByUserId
} from "../../api/order.ts";
import {parseTime, parsePickType, parseOrderStatus} from "../../utils";
import { computed,ref, unref } from 'vue'
import { ClickOutside as vClickOutside } from 'element-plus'
import {router} from "../../router";
import {createComment} from "../../api/comment.ts";
import {getUsefulCouponByUserIdAndStoreId, previewCoupon, useCoupon} from "../../api/coupon.ts";


const role = sessionStorage.getItem("role")
const userStoreId = parseInt(sessionStorage.getItem('storeId'))
//全部订单列表
const noworderList = ref([])
const comment = ref('')
const score = ref('')
//是否为空
const hasCommentInput = computed(() => comment.value != '')
const hasScoreInput = computed(() => score.value != '')

const dialogVisible = ref(false)
const nowUsedCouponId = ref('')
const nowTotalPrice = ref('')
const couponList = ref([])
const userId = parseInt(sessionStorage.getItem('userId'))

//按钮可用性
const comDisabled = computed(() => {
  return !(hasCommentInput.value && hasScoreInput.value)
})

if (role==='STAFF'){
  getOrderListByStoreId({
    storeId: userStoreId
  }).then(res => {
    noworderList.value = res.data.result
  })
}else if (role==='MANAGER'){
  getAllOrderList().then(res => {
    noworderList.value = res.data.result
  })
}else if (role==='CEO'){
  getAllOrderList().then(res => {
    noworderList.value = res.data.result
  })
}else if (role==='CUSTOMER'){
  getOrderByUserId({
    userId: userId
  }).then(res => {
    noworderList.value = res.data.result
  })
}

function getTime(row: any) {
  return parseTime(row.time)
}
function getPickType(row: any){
  return parsePickType(row.pickGoodType)
}
function getOrderStatus(row: any){
  return parseOrderStatus(row.orderStatusEnum)
}

function getUsefulCoupon(nowstoreId: number) {
  getUsefulCouponByUserIdAndStoreId({
    userId: userId,
    storeId: nowstoreId
  }).then(res => {
    couponList.value = res.data.result
  })
}

function previewUsedCoupon(price: number){
  console.log(nowUsedCouponId)
  previewCoupon({price: price, couponId: parseInt(nowUsedCouponId.value)}).then(
      res => {
        nowTotalPrice.value = res.data.result
      }
  )
}


function pay(orderId: number){
    console.log(parseInt(nowUsedCouponId.value))
  if (nowUsedCouponId.value===''){
    nowUsedCouponId.value = '-1'
  }


    payOrder({
      orderId: orderId,
      couponId: parseInt(nowUsedCouponId.value)
    }).then(res => {
      // const div = document.createElement('div')
      // div.innerHTML = res.data.result;
      // document.body.appendChild(div);
      // document.forms[0].setAttribute('target', '_blank');
      // document.forms[0].submit();
      // document.close()

      const div = document.createElement('divform');
      div.innerHTML = res.data.result;
      document.body.appendChild(div);
      document.forms['punchout_form'].setAttribute('target', '_blank')
      document.forms['punchout_form'].submit()
      // const form = document.forms['punchout_form'];
      // for (const element of form.elements) {
      //   element.value = '';
      // }
      if (res.data.code === '000') {  //类型守卫，它检查 res.data 对象中是否存在名为 code 的属性
        ElMessage({
          message: "支付成功",
          type: 'success',
          center: true,
        })
      } else if (res.data.code === '400') {
        ElMessage({
          message: "支付失败",
          type: 'error',
          center: true,
        })
      }
    })
  router.back()
}

function refund(orderId: number){
  refundOrder({
    orderId: orderId
  }).then(res => {
    // const div = document.createElement('div')
    // div.innerHTML = res.data.result;
    // document.body.appendChild(div);
    // document.forms[0].setAttribute('target', '_blank');
    // document.forms[0].submit();
    if (res.data.code === '000') {  //类型守卫，它检查 res.data 对象中是否存在名为 code 的属性
      ElMessage({
        message: "退款成功",
        type: 'success',
        center: true,
      })
      location.reload()
    } else if (res.data.code === '400') {
      ElMessage({
        message: "退款失败",
        type: 'error',
        center: true,
      })
      location.reload()
    }
  })


}

function del(orderId: number){
  deliverOrder({
    orderId: orderId
  }).then(res => {
    if (res.data.code === '000') {  //类型守卫，它检查 res.data 对象中是否存在名为 code 的属性
      ElMessage({
        message: "发货成功",
        type: 'success',
        center: true,
      })
    } else if (res.data.code === '400') {
      ElMessage({
        message: "发货失败",
        type: 'error',
        center: true,
      })
    }
    location.reload()
  })
}

function rec(orderId: number){
  receiveOrder({
    orderId: orderId
  }).then(res => {
    if (res.data.code === '000') {  //类型守卫，它检查 res.data 对象中是否存在名为 code 的属性
      ElMessage({
        message: "收货成功",
        type: 'success',
        center: true,
      })
    } else if (res.data.code === '400') {
      ElMessage({
        message: "收货失败",
        type: 'error',
        center: true,
      })
    }
    location.reload()
  })
}
const com = ref()
function handleCom(){
  createComment({
    text:comment.value,
    score:parseInt(score.value),
    userId:com.value.userId,
    productId:com.value.productId,
    orderId:com.value.orderId,
    toCommentId: -1
      }
  ).then(res => {
    if (res.data.code === '000') {  //类型守卫，它检查 res.data 对象中是否存在名为 code 的属性
      ElMessage({
        message: "评论成功!",
        type: 'success',
        center: true,
      })
      location.reload()
    } else if (res.data.code === '400') {
      ElMessage({
        message: res.data.msg,
        type: 'error',
        center: true,
      })
    }
  })
}

const getStatement = () => {
  getOrderStatement({
    userId:userId
  }).then(res =>{
    window.open(res.data.result)
      }
  )
}
function handleClose(){
  dialogVisible.value = false
}
function solve(storeId: number,pricesum: number,orderId:number){
  nowOrderId.value = String(orderId)
  bef.value = String(pricesum)
  getUsefulCoupon(storeId);
  nowTotalPrice.value = String(pricesum)
  dialogVisible.value = true
}

const bef = ref('')
const nowOrderId = ref('')
const dialogVisiblew = ref()
function handleClick(row: any){
  com.value = row
}
</script>


<template>
  <el-dialog
      v-model="dialogVisiblew"
      title="评论"
      width="500"
      style="z-index: 100"
  >
    <el-rate v-model="score" size="large" />
    <br />
    <br />
    <el-input
        v-model="comment"
        placeholder="评论"
        type="textarea"
    />
    <br />
    <br />
    <el-button @click.prevent="handleCom()"
               :disabled="comDisabled"
               type="primary">
      提交评论
    </el-button>
  </el-dialog>
  <el-main>
    <el-button v-if="role==='CEO'||role==='STAFF'" @click="getStatement()" type="primary">
      下载报表
    </el-button>
    <el-table :data="noworderList" height="100%" style="width: 100%;z-index: 0" >
      <el-table-column prop="orderId" label="订单号" width="100px"/>
      <el-table-column prop="productName" label="商品名" width="100px"/>
      <el-table-column prop="time" label="日期" width="200px" :formatter="getTime"/>
      <el-table-column prop="pricesum" label="总价" width="100px"/>
      <el-table-column prop="pickGoodType" label="提货方式" width="100px" :formatter="getPickType"/>
      <el-table-column prop="orderStatusEnum" label="订单状态" width="100px" :formatter="getOrderStatus"/>
      <el-table-column  label="详情">
        <template #default="scope">
          <el-button v-if="scope.row.orderStatusEnum==='UNPAID'" type="success" size="small">
            <el-text v-if="role==='CUSTOMER'" @click="solve(scope.row.storeId , scope.row.pricesum,scope.row.orderId)">支付</el-text>
            <el-text v-else>待支付</el-text>
            <el-dialog
                v-model="dialogVisible"
                title="付款"
                width="400px"
                :before-close="handleClose"
                append-to-body="true"
            >
              <el-form>
                <el-form-item label="原价： ">
                  &ensp;
                  <el-text type="info" size="large">{{bef}}￥</el-text>
                </el-form-item>

                <el-form-item label="优惠券">
                  <el-select v-model="nowUsedCouponId"
                             :popperAppendToBody="false"
                             placeholder="请选择"
                             style="width: 100%;"
                  >
                    <el-option
                        v-for="item in couponList"
                        :label="item.couponName"
                        :value="item.couponId"
                        @click.native="previewUsedCoupon(parseInt(bef))"
                    />
                  </el-select>
                </el-form-item>

                <el-form-item label="现价： ">
                  &ensp;
                  <el-text type="warning" size="large">{{nowTotalPrice}}￥</el-text>
                </el-form-item>
              </el-form>

              <template #footer>
                <div class="dialog-footer">
                  <el-button type="primary" @click="pay(parseInt(nowOrderId))">
                    确认付款
                  </el-button>
                </div>
              </template>
            </el-dialog>
          </el-button>
          <el-button v-if="scope.row.orderStatusEnum==='UNSEND'" type="success" size="small" >
            <el-text v-if="role==='STAFF'" @click="del(scope.row.orderId)">发货</el-text>
            <el-text v-else>待发货</el-text>
          </el-button>
          <el-button v-if="scope.row.orderStatusEnum==='UNGET'" type="success" size="small" >
            <el-text v-if="role==='CUSTOMER'" @click="rec(scope.row.orderId)">确认收货</el-text>
            <el-text v-else>待收货</el-text>
          </el-button>
          <el-button v-if="scope.row.orderStatusEnum==='UNCOMMENT'" type="success" size="small" >
            <el-text v-if="role==='CUSTOMER'"  @click = "dialogVisiblew = true;handleClick(scope.row)">评论</el-text>
            <el-text v-else>待评论</el-text>
          </el-button>
          <el-button v-if="scope.row.orderStatusEnum==='DONE'" type="success" size="small" >
            订单完成
          </el-button >
          <el-button v-if="scope.row.orderStatusEnum==='REFUNDED'" type="success" size="small" >
            已退款
          </el-button >
          <el-button v-if="scope.row.orderStatusEnum!=='UNPAID'&&scope.row.orderStatusEnum!=='REFUNDED'&&role==='CUSTOMER'" type="warning" size="small">
            <el-text @click="refund(scope.row.orderId)">退款</el-text>
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-main>

</template>



<style scoped>
.infinite-list {
  padding: 10px;
  margin: 10px;
  list-style: none;
}
.infinite-list .infinite-list-item {
  display: flex;
  align-items: stretch;
  justify-content: stretch;
  height: 250px;
  background: var(--el-color-primary-light-9);
  margin: 10px;
  color: var(--el-color-primary);
}
.infinite-list .infinite-list-item + .list-item {
  margin-top: 10px;
}
</style>

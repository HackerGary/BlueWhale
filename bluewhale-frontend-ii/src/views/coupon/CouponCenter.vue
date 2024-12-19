<script setup lang="ts" xmlns:el-table="http://www.w3.org/1999/html">
import {createCouponGroup} from "../../api/coupon.ts";
import {getAllCouponGroup, getCouponGroupByStoreId, receiveCoupon} from "../../api/coupon.ts";


import { ref, unref } from 'vue'
import {router} from "../../router";
import {parseCouponTypeName, parseTime} from "../../utils";
import {getstoreInfo} from "../../api/store.ts";

const popoverRef = ref()
const onClickOutside = () => {
  unref(popoverRef).popperRef?.delayHide?.()
}

import {ElMessageBox, FormProps, parseDate} from 'element-plus'

const dialogVisible = ref(false)

const handleClose = (done: () => void) => {
  ElMessageBox.confirm('Are you sure to close this dialog?')
      .then(() => {
        done()
      })
      .catch(() => {
        // catch error
      })
}
const role = sessionStorage.getItem("role")
let userStoreId = parseInt(sessionStorage.getItem('storeId'))
const userId = parseInt(sessionStorage.getItem('userId'))
//全部优惠券组列表
const nowCouponGroupList = ref([])
const storeName = ref("");
function getTime(row: any) {
  return parseTime(row.deadline)
}
function getStoreName(row: any) {
  if (row.storeId === -1){
    return "全部商店"
  }else{
    getstoreInfo({
      storeId: row.storeId
    }).then(res => {
      storeName.value = res.data.result.name
    })
    return storeName.value
  }
}


//发布优惠卷组时的输入框值
const discountRange = ref('')
const discountPrice = ref('')
const couponType = ref('')
const couponSum = ref('')
const couponGroupName = ref('')
const couponDDL = ref('')
//是否为空
function getCouponTypeName(row: any){
  return parseCouponTypeName(row.couponType)
}

if (role==='STAFF'){
  getCouponGroupByStoreId({
    storeId: userStoreId
  }).then(res => {
    nowCouponGroupList.value = res.data.result
  })
}else if (role==='MANAGER'){
  getAllCouponGroup().then(res => {
    nowCouponGroupList.value = res.data.result
  })
}else if (role==='CEO'){
  getAllCouponGroup().then(res => {
    nowCouponGroupList.value = res.data.result
  })
}else if (role==='CUSTOMER'){
  getAllCouponGroup().then(res => {
    nowCouponGroupList.value = res.data.result
  })
}

//发布优惠卷组按钮触发
function handleCreateCouponGroup() {
  if (role === 'CEO'){
    userStoreId = -1
  }
  createCouponGroup({
        discountRange: parseInt(discountRange.value),
        discountPrice: parseInt(discountPrice.value),
        couponType: couponType.value,
        couponSum: parseInt(couponSum.value),
        couponLeft: parseInt(couponSum.value),
        storeId: userStoreId,
        couponGroupName: couponGroupName.value,
        deadline: new Date(couponDDL.value)
      }
  ).then(res => {
    if (res.data.code === '000') {  //类型守卫，它检查 res.data 对象中是否存在名为 code 的属性
      ElMessage({
        message: "创建成功!",
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

function getCoupon(couponGroupId: number, userId: number){
  receiveCoupon({
    couponGroupId: couponGroupId,
    userId: userId
  }).then(res => {
    if (res.data.code === '000') {  //类型守卫，它检查 res.data 对象中是否存在名为 code 的属性
      ElMessage({
        message: "领取成功!",
        type: 'success',
        center: true,
      })
    } else if (res.data.code === '400') {
      ElMessage({
        message: res.data.msg,
        type: 'error',
        center: true,
      })
    }
  })

  // location.reload()
}



</script>


<template>
  <el-header style="padding-top: 20px">
    <el-button type="primary" v-if="role==='CEO'||role==='STAFF'" @click="dialogVisible = true">
      发布优惠券组
    </el-button>

    <el-dialog
        v-model="dialogVisible"
        title="发布优惠券组"
        width="450"
        :before-close="handleClose"
    >
      <el-form label-position="left" label-width="auto" style="align-items: stretch">

        <el-form-item label="优惠券类型">
          <el-select id="couponType"
                     v-model="couponType"
                     placeholder="请选择"
                     style="width: 240px"
          >
            <el-option value="FULL_REDUCTION" label="满减券"/>
            <el-option value="SPECIAL" label="蓝鲸券"/>
          </el-select>
        </el-form-item>

        <el-form-item v-if="couponType=='FULL_REDUCTION'" label="折扣底价">
          <el-input
              v-model="discountRange"
              maxlength="10"
              style="width: 240px"
              placeholder="Please input"
              show-word-limit
              type="text"
          />
        </el-form-item>
        <el-form-item v-if="couponType=='FULL_REDUCTION'" label="折扣金额">
          <el-input
              v-model="discountPrice"
              maxlength="10"
              style="width: 240px"
              placeholder="Please input"
              show-word-limit
              type="text"
          />
        </el-form-item>
        <el-form-item label="优惠券数量">
          <el-input
              v-model="couponSum"
              maxlength="10"
              style="width: 240px"
              placeholder="Please input"
              show-word-limit
              type="text"
          />
        </el-form-item>
        <el-form-item label="优惠券名">
          <el-input
              v-model="couponGroupName"
              maxlength="30"
              style="width: 240px"
              placeholder="Please input"
              show-word-limit
              type="text"
          />
        </el-form-item>
        <el-form-item label="优惠券使用期限">
          <div class="block">
            <el-date-picker
                v-model="couponDDL"
                type="date"
                placeholder="Pick a day"
                style="width: 240px"
            />
          </div>
        </el-form-item>
        <el-button @click.prevent="handleCreateCouponGroup"
                   @click="dialogVisible = false"
                   type="primary">
          发布优惠券组
        </el-button>
      </el-form>

    </el-dialog>
  </el-header>
  <el-main>
    <el-table :data="nowCouponGroupList" height="100%" style="padding-left: 10%; padding-right: 10%">
      <el-table-column prop="couponGroupName" label="券名" width="200px"/>
      <el-table-column prop="couponType" label="类型" width="100px" :formatter="getCouponTypeName"/>
      <el-table-column prop="deadline" label="有效期至" width="180px" :formatter="getTime"/>
      <el-table-column prop="couponLeft" label="剩余数量" width="100px"/>
      <el-table-column prop="storeId" label="可用商店" width="120px" :formatter="getStoreName"/>
      <el-table-column prop="couponGroupId" v-if="role==='CUSTOMER'" label="可执行操作" width="100px">
        <template #default="scope">
          <el-button type="success" size="small" >
            <el-text @click="getCoupon(scope.row.coupongroupId, userId)">领取</el-text>
          </el-button>
        </template>

      </el-table-column>


    </el-table>
  </el-main>

<!--  <br/>-->






</template>

<style scoped>

</style>

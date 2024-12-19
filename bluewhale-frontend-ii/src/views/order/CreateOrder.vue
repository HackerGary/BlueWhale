<!--Lab3新增-创建订单界面-->

<script setup lang="ts">
import {computed, onMounted, ref, unref} from 'vue'
import {createOrder, payOrder} from "../../api/order.ts";
import {router} from "../../router";
import {deleteReceiveImfo, findReceiveImfoByUserId, userInfo} from "../../api/user.ts";
import {getproductInfo} from "../../api/product.ts";
import {getCouponByUserId, getUsefulCouponByUserIdAndStoreId, previewCoupon, useCoupon} from "../../api/coupon.ts";
import {useRoute} from "vue-router";
const popoverRef = ref()
const onClickOutside = () => {
  unref(popoverRef).popperRef?.delayHide?.()
}
//用户信息
const userId = ref('')
const name = ref('')
const tel = ref('')
const address = ref('')
// 电话号码是否为空
const hasTelInput = computed(() => tel.value != '')
// 电话号码的规则
const chinaMobileRegex = /^1(3[0-9]|4[579]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[1589])\d{8}$/
const telLegal = computed(() => chinaMobileRegex.test(tel.value))
//输入框值
const type = ref('')
const quantity = ref('')
//两种提货方式
const types = [
  {
    value: 'DELIVERY',
    label: 'DELIVERY（快递送达）',
  },
  {
    value: 'PICKUP',
    label: 'PICKUP（到店自提）',
  }
]

//类型是否为空
const hasTypeInput = computed(() => type.value != '')
//收货人是否为空
const hasNameInput = computed(() => name.value != '')
const hasAddressInput = computed(() => address.value != '')
//创建按钮可用性
const createDisabled = computed(() => {
  return !(hasNameInput.value && hasTypeInput.value && hasAddressInput.value && hasTelInput.value)
})
const nowproductId = parseInt(<string>useRoute().params.productId)
const nowstoreId = parseInt(<string>useRoute().params.storeId)
const nowproduct = ref('')
const noworderId = ref('')
const nowUsedCouponId = ref('')
const nowTotalPrice = ref('')
const dialogVisible = ref(false)
const recList = ref([])

getproductInfo({
  productId: nowproductId
}).then(res => {
  nowproduct.value = res.data.result
})

//创建订单按钮触发
function handleCreateOrder() {
  createOrder({
    productId: nowproductId,
    quantity: parseInt(quantity.value),
    pricesum: nowproduct.value.price*parseInt(quantity.value),
    pickGoodType: type.value,
    orderStatusEnum: 'UNPAID',
    receiver: name.value,
    telephone: tel.value,
    address: address.value,
    userId:parseInt(userId.value),
    storeId: nowstoreId
      }
  ).then(res => {
    if (res.data.code === '000') {  //类型守卫，它检查 res.data 对象中是否存在名为 code 的属性
      ElMessage({
        message: "创建成功!",
        type: 'success',
        center: true,
      })
      noworderId.value = res.data.result
      //到商品界面
    } else if (res.data.code === '400') {
      ElMessage({
        message: res.data.msg,
        type: 'error',
        center: true,
      })
    }
  })

}



getUserInfo()

function getUserInfo() {
  userInfo().then(res => {
    userId.value = res.data.result.id
    name.value = res.data.result.name
    tel.value = res.data.result.phone
    address.value = res.data.result.address
  })

}
const couponList = ref([])

getUsefulCoupon()

function getUsefulCoupon() {
  userInfo().then(res => {
    userId.value = res.data.result.id
    getUsefulCouponByUserIdAndStoreId({
      userId: parseInt(userId.value),
      storeId: nowstoreId
    }).then(res => {
      couponList.value = res.data.result
    })
  })
}

const handleClose = () => {
  ElMessage({
    message: '已取消立即支付，请尽快前往我的订单支付'
  })
  router.back()
}

function previewUsedCoupon(price: number){
  console.log(nowUsedCouponId)
  previewCoupon({price: price, couponId: parseInt(nowUsedCouponId.value)}).then(
      res => {
        nowTotalPrice.value = res.data.result
      }
  )
}

let signQuit = ref(false)
function pay(orderId: number){
  signQuit.value = false
  if (nowUsedCouponId.value===''){
    nowUsedCouponId.value = '-1'
  }
    payOrder({
      orderId: orderId,
      couponId: parseInt(nowUsedCouponId.value)
    }).then(res => {
      if (res.data.code === '000') {  //类型守卫，它检查 res.data 对象中是否存在名为 code 的属性
        const div = document.createElement('div')
        div.innerHTML = res.data.result;
        document.body.appendChild(div);
        document.forms[0].setAttribute('target', '_blank');
        document.forms[0].submit();
        ElMessage({
          message: "支付成功",
          type: 'success',
          center: true,
        })
        // var newTab = window.open("","_blank");
        // var div = new div();
        // div.innerHTML = res.data.result;
        // newTab.document.write(div.outerHTML)
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

getReceiveInfo()

function getReceiveInfo(){
  findReceiveImfoByUserId({
    userId: sessionStorage.getItem("userId"),
  }).then(res=>{
    recList.value = res.data.result
  })
}

const handleChoose = (index: number, row) => {
  tel.value = row.telephone
  address.value = row.address
}
</script>


<template>
  <el-main style="height: 100%;">
    <el-container style="height: 100%">
      <el-aside width="40%" style="border-right: lightgrey solid 1px;padding-left: 20px">
        <div class="demo-image">
          <el-form-item>
            <el-text class="mx-1" type="primary">物品名:</el-text> &ensp;<el-tag type="primary">{{nowproduct.name}}</el-tag>
          </el-form-item>
          <el-form-item>
            <el-text class="mx-1" type="warning">评分:</el-text> &ensp;<el-tag type="warning">{{nowproduct.score}}</el-tag>
          </el-form-item>
          <el-form-item>
            <el-text class="mx-1" type="primary">价格:</el-text> &ensp;<el-tag type="primary">{{nowproduct.price}}￥</el-tag>
          </el-form-item>
          <el-form-item>
            <el-text class="mx-1" type="primary">现有库存:</el-text>&ensp; <el-tag type="primary">{{nowproduct.quantity}}</el-tag>
          </el-form-item>
          <el-form-item>
            <el-text class="mx-1" type="primary">简介:</el-text> &ensp;<el-text type="primary">{{nowproduct.introduction}}</el-text>
          </el-form-item>
        </div>
      </el-aside>
      <el-main>
        <el-form label-position="left" label-width="auto">

          <el-form-item label="购买数量">
            <el-input
                v-model.number="quantity"
                style="width: 240px"
                placeholder="请填写购买数量"
                type="text"
            />
          </el-form-item>

          <el-form-item label="总价">
            <el-text type="warning" size="large">{{nowproduct.price*parseInt(quantity)}}￥</el-text>
          </el-form-item>

          <el-form-item label="提货类型">
            <el-select v-model="type"  placeholder="Select" style="width: 240px">
              <el-option
                  v-for="item in types"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item v-if="type==='DELIVERY'" label="提货地址">
            <el-input
                v-model="address"
                style="width: 240px"
                placeholder="详细地址"
                type="text"
            />
          </el-form-item>

          <el-form-item label="收货人">
            <el-input
                v-model="name"
                style="width: 240px"
                placeholder="请填写收货人姓名"
                type="text"
            />
          </el-form-item>

          <el-form-item label="手机号码">
            <el-form-item>

              <label v-if="!telLegal" for="tel" class="error-warn">
                手机号不合法
              </label>

              <el-input
                  style="width: 240px"
                  id="tel"
                  v-model="tel" :class="{'error-warn-input' :(hasTelInput && !telLegal)}"
                  placeholder="请输入收货人手机号"/>
            </el-form-item>
          </el-form-item>

          <el-form-item>
            <el-collapse v-model="activeNames" @change="handleChange">

              <el-collapse-item title="预设收货信息" name="1">
                <el-form>
                  <el-form-item>
                    <el-table :data="recList" height="100%" style="width: 100%">
                      <el-table-column prop="telephone" label="手机号" width="200px"/>
                      <el-table-column prop="address" label="地址" width="200px"/>
                      <el-table-column label="操作" width="200px">
                        <template #default="scope">
                          <el-button
                              size="small"
                              type="primary"
                              @click="handleChoose(scope.$index, scope.row)"
                          >
                            选择
                          </el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                  </el-form-item>
                </el-form>
              </el-collapse-item>
              </el-collapse>
          </el-form-item>

          <el-button @click.prevent="handleCreateOrder"
                     :disabled="createDisabled"
                     type="primary" v-popover="popoverRef" v-click-outside="onClickOutside">
            创建订单
          </el-button>
          <el-popover
              ref="popoverRef"
              trigger="click"
              title="是否立即支付"
              virtual-triggering
              persistent
          >
            <el-button plain @click.stop="dialogVisible = true;nowTotalPrice = nowproduct.price*parseInt(quantity)">
              是
            </el-button>
            &ensp;

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
                  <el-text type="info" size="large">{{nowproduct.price*parseInt(quantity)}}￥</el-text>
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
                    />
                  </el-select>
                </el-form-item>



                <el-form-item label="现价： ">
                  &ensp;
                  <el-text type="warning" size="large">{{previewUsedCoupon(nowproduct.price*parseInt(quantity))}}{{nowTotalPrice}}￥</el-text>
                </el-form-item>
              </el-form>

              <template #footer>
                <div class="dialog-footer" id="payTab">
                  <el-button type="primary" @click="dialogVisible = false; pay(parseInt(noworderId))">
                    确认付款
                  </el-button>
                </div>
              </template>
            </el-dialog>

            <el-button @click.prevent="router.back()">否</el-button>
          </el-popover>
        </el-form>
      </el-main>

    </el-container>



  </el-main>
</template>


<style scoped>
.error-warn {
  color: red;
}

.error-warn-input {
  --el-input-focus-border-color: red;
}
</style>

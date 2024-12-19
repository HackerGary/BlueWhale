<!--Lab2新增-商品详情界面-->
<script setup lang="ts" xmlns="http://www.w3.org/1999/html">

import {ArrowLeft} from "@element-plus/icons-vue";
import {useRoute} from "vue-router";
import {ref, unref} from "vue";
import {getproductInfo,getproductImgList} from "../../api/product.ts";
import {createComment, getCommentListByProductId, getUserNameByCommentId} from "../../api/comment.ts";
import {onMounted} from "vue";
import {changeInventory} from "../../api/product.ts";
import {parseTime} from "../../utils";
import {router} from "../../router";

const visible = ref(false)
const role = sessionStorage.getItem("role")
const userstoreId = sessionStorage.getItem("storeId")
const userId = parseInt(sessionStorage.getItem('userId'))
const nowstoreId = parseInt(<string>useRoute().params.storeId)
const nowproductId = parseInt(<string>useRoute().params.productId)
const nowproduct = ref('')
const nowproductImgList = ref([])
const nowcommentList = ref([])
const change = ref('')
const commentText = ref('')
const commentId = ref()

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

//初始化获取当前页面商品信息
onMounted(() => {
  getproductInfo({
    productId: nowproductId
  }).then(res => {
    nowproduct.value = res.data.result
  })
  getproductImgList({
    productId: nowproductId
  }).then(res => {
    nowproductImgList.value = res.data.result
  })
  getCommentListByProductId({
    productId: nowproductId
  }).then(res => {
    nowcommentList.value = res.data.result
    // console.log(nowcommentList.value)
  })
})


function handleChange() {
  visible.value = false
  changeInventory({
    productId: nowproductId,
    change: parseInt(change.value)
  }).then(res => {
    if (res.data.code === '000') {  //类型守卫，它检查 res.data 对象中是否存在名为 code 的属性
      ElMessage({
        message: "库存添加成功",
        type: 'success',
        center: true,
      })
    } else if (res.data.code === '400') {
      ElMessage({
        message: "库存添加失败",
        type: 'error',
        center: true,
      })
    }
    location.reload()
  })
}

function handleComment(textStr: string,commentId:number){
  createComment({
        text:textStr,
        score:-1,
        userId:userId,
        productId:nowproductId,
        orderId:-1,
        toCommentId:commentId
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
  commentText.value = ""
}
</script>


<template>
  <el-container >
    <!--希望把商品详情的一部分内容放在这个侧边栏里，你要真不想放也没事-->
    <el-aside style="display: block; position: absolute; left: 0;top: 70px;bottom: 0" width="20%" class="page-aside" height="100%">
      <el-header height="120px" class="aside-header">
        <el-form-item></el-form-item><!--用于占位-->

        <el-form-item>
          <el-button type="primary" :icon="ArrowLeft" @click="$router.back()">
            返回
          </el-button>
        </el-form-item>


        <el-col :span="15" v-if="role === 'STAFF' && parseInt(<string>userstoreId) === nowstoreId">

          <el-popover :visible="visible" placement="top" :width="160">

            <div style="text-align: right; margin: 0">
              <el-input id="change"
                        v-model="change"
                        placeholder="请输入修改量"/>
              <el-button size="small" text @click="visible = false">取消</el-button>
              <el-button size="small" type="primary" @click="handleChange">
                确认
              </el-button>
            </div>
            <template #reference>
              <el-button type="primary" @click="visible = true">修改库存</el-button>
            </template>
          </el-popover>

        </el-col>

      </el-header>

      <el-main>

        <div class="demo-image">

          <el-form-item>
            <el-text class="mx-1" type="primary">物品名:</el-text> &ensp;<el-tag type="primary">{{nowproduct.name}}</el-tag>
          </el-form-item>
          <el-form-item>
            <el-text class="mx-1" type="warning">共有 {{nowproduct.times}} 人评分</el-text>
          </el-form-item>
          <el-form-item>
            <el-text class="mx-1" type="warning">总评分:</el-text> &ensp;<el-tag type="warning">{{nowproduct.score}}</el-tag>
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
      </el-main>

      <el-footer>
          <router-link :to="{path: '/storeDetail/'+this.$route.params.storeId+'/'+this.$route.params.productId+'/createOrder'}">
            <el-button v-if="role==='CUSTOMER'" type="primary" >
              购买
            </el-button>
          </router-link>
      </el-footer>
    </el-aside>

    <el-main style="overflow-y: scroll; position:absolute; left: 20%; right: 0;top: 70px;bottom: 0">
      <el-container>
        <el-header style="padding:20px 20px 0px 20px" width="100%" height="900px">
          <el-carousel indicator-position="outside" style="text-align: center" height="900px">
            <el-carousel-item v-for="item in nowproductImgList" :key="item" >
              <el-image style="height: 100%" :src="item"/>
            </el-carousel-item>
          </el-carousel>
        </el-header>
        <el-main>
          <br>
          <el-tag type="success" size="large" style="margin-top: 10px;font-size: 22px">
            商品评价:
          </el-tag>
          <br>
          <br>

<!--          {{nowcommentList.value}}-->

          <el-scrollbar height="800px">
            <div v-for="comment in nowcommentList">
              <div v-if="comment.toCommentId===-1">
                <p>用户&ensp;{{comment.userName}}&ensp;于&ensp;{{parseTime(comment.time)}}&ensp;评分：{{comment.score}}</p>
                <p>{{comment.text}}</p>
                <el-button plain @click="dialogVisible = true;commentId=comment.commentId">追加评论</el-button>
              </div>
              <div v-if="comment.toCommentId!==-1" style="color: blueviolet;padding-left: 100px">
                <p>用户&ensp;{{comment.userName}}&ensp;于&ensp;{{parseTime(comment.time)}}&ensp;&ensp;回复&ensp;{{comment.toUserName}}</p>
                <p>{{comment.text}}</p>
                <el-button plain @click="dialogVisible = true;commentId=comment.commentId">追加评论</el-button>
              </div>
              <el-dialog
                  v-model="dialogVisible"
                  title="Tips"
                  width="500"
                  :before-close="handleClose"
              >
                <span>请输入评论内容</span>
                <el-input
                    v-model="commentText"
                    placeholder="追评"
                    type="textarea"
                />
                <template #footer>
                  <div class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="dialogVisible = false" @click.prevent="handleComment(commentText,commentId)">
                      提交评论
                    </el-button>
                  </div>
                </template>
              </el-dialog>

<!--              <el-popover ref="popoverRef"-->
<!--                          :virtual-ref="buttonRef"-->
<!--                          trigger="click"-->
<!--                          title="请输入评价"-->
<!--                          virtual-triggering>-->
<!--                <el-input-->
<!--                    v-model="commentText"-->
<!--                    placeholder="追评"-->
<!--                    type="textarea"-->
<!--                />-->
<!--                <div></div>-->
<!--                <el-button @click.prevent="handleComment(commentText,comment.commentId)"-->
<!--                           type="primary">-->
<!--                  提交评论-->
<!--                </el-button>-->
<!--              </el-popover>-->

            </div>
          </el-scrollbar>
<!--          <el-card v-for="comment in nowcommentList" shadow="hover">-->
<!--            <template #header>-->
<!--                用户&ensp;{{comment.userName}}&ensp;于&ensp;{{parseTime(comment.time)}}&ensp;&ensp;评分：{{comment.score}}-->
<!--              <el-button ref="buttonRef" v-click-outside="onClickOutside">追加评论</el-button>-->
<!--              <el-popover ref="popoverRef"-->
<!--                          :virtual-ref="buttonRef"-->
<!--                          trigger="click"-->
<!--                          title="With title"-->
<!--                          virtual-triggering>-->
<!--                <el-input-->
<!--                    v-model="commentText"-->
<!--                    placeholder="追评"-->
<!--                    type="textarea"-->
<!--                />-->
<!--                <el-button @click.prevent="handleComment(commentText,comment.commentId)"-->
<!--                           type="primary">-->
<!--                  提交评论-->
<!--                </el-button>-->
<!--              </el-popover>-->
<!--            </template>-->
<!--                {{comment.text}}-->
<!--          </el-card>-->

        </el-main>
      </el-container>
    </el-main>

  </el-container>
</template>


<style scoped>
.page-aside {
  border-right: lightgrey solid 1px;
}
.aside-header {
  border-bottom: lightgrey solid 1px;
}
.el-carousel-item h3 {
  display: flex;
  color: #475669;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>

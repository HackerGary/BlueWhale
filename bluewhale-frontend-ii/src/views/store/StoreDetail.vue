<!--Lab2新增-商店详情界面-->
<script setup lang="ts">

import {onMounted, ref} from "vue";
import {getstoreInfo} from "../../api/store.ts";
import {useRoute} from "vue-router";
import {ArrowLeft} from "@element-plus/icons-vue";
import {getproductFirstImg, getproductImgList, getproductList} from "../../api/product.ts";
import {router} from "../../router";
import {parseType} from "../../utils";

const role = sessionStorage.getItem("role")
const userstoreId = sessionStorage.getItem("storeId")
const nowstoreId = parseInt(<string>useRoute().params.storeId)
const nowstore = ref('')
const nowproductList = ref('')
const nowproducImgList = ref([''])


//初始化获取当前页面商店信息
onMounted(() => {
  getstoreInfo({
    storeId: nowstoreId
  }).then(res => {
    nowstore.value = res.data.result
  })
  getproductList({
    storeId: nowstoreId
  }).then(res => {
    nowproductList.value = res.data.result
  })
  getproductFirstImg({
    storeId: nowstoreId
  }).then(res => {
    nowproducImgList.value = res.data.result
  })
})


function entranceProduct(id: number) {
  router.push("/storeDetail/"+nowstoreId+"/"+id)
}

</script>


<template>
  <el-container>
    <!--希望把商店详情的一部分内容放在这个侧边栏里，你要真不想放也没事-->
    <el-aside width="25%" class="page-aside">

      <el-header height=120px class="aside-header">

        <el-form-item></el-form-item><!--用于占位-->


        <el-form-item>
          <el-button type="primary" :icon="ArrowLeft" @click="$router.back()">
            返回
          </el-button>
        </el-form-item>


        <el-col :span="15" v-if="role === 'STAFF' && parseInt(<string>userstoreId) === nowstoreId">
          <router-link :to="{path: '/storeDetail/'+this.$route.params.storeId+'/createProduct'}">
            <el-button type="primary" >
            创建商品
            </el-button>
          </router-link>
        </el-col>

      </el-header>
      <el-main style="overflow: clip">
        <div class="demo-image">
          <el-image style="background-size: contain;width: 100%; height: 100%; object-fit: cover" :src="nowstore.logoUrl"/>
          <el-form-item>
          <el-text class="mx-1" type="primary">店名:</el-text>&ensp;<el-tag type="primary">{{nowstore.name}}</el-tag>
          </el-form-item>
          <el-form-item>
            <el-text class="mx-1" type="primary">简介:</el-text> <el-text type="primary" line-clamp="">{{nowstore.introduction}}</el-text>
          </el-form-item>
          <el-form-item>
            <el-text class="mx-1" type="warning">共有 {{nowstore.times}} 人评分</el-text>
            &ensp;
            <el-tag  type="warning" size="large" style="font-size: medium; padding-left: 10px">
              总评分：{{nowstore.score}}
            </el-tag>
          </el-form-item>
        </div>
      </el-main>

    </el-aside>
    <el-main style="overflow-y: scroll; position:absolute; left: 25%; right: 0;top: 70px;bottom: 0">
      <ol class="infinite-list" style="overflow: clip">
        <li v-for="product in nowproductList" class="infinite-list-item">
          <el-container>
            <el-aside style="overflow: clip; width: 200px">
              <el-image style="padding:10px;width: 180px; height: 180px" :src=nowproducImgList[nowproductList.indexOf(product)] />
            </el-aside>

            <el-container>
              <el-header style="height: 60px; padding-top: 20px">
                <el-tag type="" class="product-type" color="rgba(102, 178, 255, 0.6)">
                  {{parseType(product.type)}}
                </el-tag>
                &nbsp;
                <el-button type="primary" @click="entranceProduct(product.id)"  round>
                  {{ product.name }}
                </el-button>
              </el-header>

              <el-main style="height: 150px;padding-top: 0px">
                详情：{{product.introduction}}
              </el-main>

              <el-footer style="height: 20px; padding-bottom: 40px">
                    CNY - ¥{{product.price}}
                <el-text  type="warning" size="large" style="font-size: medium;float: right;">
                  评分：{{product.score}}
                </el-text>

              </el-footer>

            </el-container>

          </el-container>
        </li>
      </ol>
    </el-main>
<!--      <div>{{nowproductList}}</div>-->

  </el-container>
</template>


<style scoped>
.page-aside {
  border-right: lightgrey solid 1px;
}
.aside-header {
  border-bottom: lightgrey solid 1px;
}
.infinite-list {
  padding: 0px;
  margin: 0px;
  list-style: none;
}
.infinite-list .infinite-list-item {
  display: flex;
  align-items: stretch;
  justify-content: start;
  height: 200px;
  background: var(--el-color-primary-light-9);
  margin: 10px;
  color: var(--el-color-primary);
  border-radius: 1px;
}
.infinite-list .infinite-list-item + .list-item {
  margin-top: 0px;
}
.product-type {
  color: #409EFF;
}
</style>

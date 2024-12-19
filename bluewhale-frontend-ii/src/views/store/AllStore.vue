<!--Lab2新增-全部商店界面/主页-->
<script setup lang="ts">

import {onMounted} from "vue"
import {storeList} from "../../api/store.ts"
import {ref} from "vue"
import {router} from "../../router";
import {start} from "@popperjs/core";
import {parsePassword} from "../../utils";

//用户身份
const role = sessionStorage.getItem("role")
//全部商店列表
const nowstoreList = ref([])

//初始化获取所有商店列表
onMounted(() => {
  storeList().then(res => {
    nowstoreList.value = res.data.result
  })
})

function entranceStore(id: number) {
  router.push("/storeDetail/"+id)
}
</script>


<template>
  <el-main>

  <el-form>

  <el-form-item>
  <el-col :span="15" v-if="role === 'MANAGER'">
<!--仅管理员可见-->
    <router-link to="/createStore"><el-button type="primary" >创建商店</el-button></router-link>
  </el-col>
  </el-form-item>

  <el-form-item>



  </el-form-item>


  </el-form>

    <ol class="infinite-list" style="overflow: auto" start="1">

      <li v-for="store in nowstoreList" class="infinite-list-item">
        <el-container>
          <el-aside width="250px">
            <el-image style="padding:20px;width: 200px; height: 200px" :src="store.logoUrl" :fit="cover" />
          </el-aside>

          <el-container>
            <el-header style="padding: 25px">
              <el-button  type="primary" @click="entranceStore(store.storeId)"  round>{{ store.name }}</el-button>
            </el-header>

            <el-main>
              {{store.introduction}}
            </el-main>

            <el-footer>
              <el-tag  type="warning" size="large" style="font-size: medium; padding-left: 10px">
                评分：{{store.score}}
              </el-tag>
            </el-footer>

          </el-container>

        </el-container>

      </li>
    </ol>

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

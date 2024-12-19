<!--Lab5新增-搜索商品-->
<script setup lang="ts">

import {router} from "../../router";
import {computed, ref} from 'vue'
import {searchProduct} from "../../api/product.ts";
import {parseType} from "../../utils";
//用户身份

const role = sessionStorage.getItem("role")

function entranceProduct(storeId:number, id: number) {
  router.push("/storeDetail/"+storeId+"/"+id)
}

const type = ref('')
const name = ref("")
const startValue = ref("")
const endValue = ref("")
const nowProductList = ref([])

//七种商品类型
const types = [
  {
    value: 'FOOD',
    label: '食品',
  },
  {
    value: 'CLOTHES',
    label: '服饰',
  },
  {
    value: 'FURNITURE',
    label: '家具',
  },
  {
    value: 'ELECTRONICS',
    label: '电子产品',
  },
  {
    value: 'ENTERTAINMENT',
    label: '娱乐',
  },
  {
    value: 'SPORTS',
    label: '体育产品',
  },
  {
    value: 'LUXURY',
    label: '奢侈品',
  },
]

function handleSearchProduct(){
  searchProduct({
    name: name.value,
    type: type.value,
    minPrice: parseInt(startValue.value),
    maxPrice: parseInt(endValue.value)
  }).then(res=>{
    nowProductList.value = res.data.result
    console.log(res)
  })
}

const searchDisabled = computed(() => {
  return !(name.value && type.value && startValue.value && endValue.value)
})

</script>




<template>

  <el-main>
    <el-container>
      <el-aside width="20%">
        <el-form label-position="left" label-width="33%">
          <el-form-item label="商品名称">
            <el-input
                v-model="name"
                placeholder="请输入商品名称"
                type="text"
            />
          </el-form-item>

          <el-form-item label="最低价">
            <el-input
                v-model="startValue"
                placeholder="最低价"
                type="text"
            />
          </el-form-item>

          <el-form-item label="最高价">
            <el-input
                v-model="endValue"
                placeholder="最高价"
                type="text"
            />
          </el-form-item>

          <el-form-item label="商品种类">
            <el-select
                v-model="type"
                placeholder="选择"
                size="large"
            >
              <el-option
                  v-for="item in types"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-button @click.prevent="handleSearchProduct"
                       :disabled="searchDisabled"
                       type="primary">
              搜索
            </el-button>
          </el-form-item>

        </el-form>


      </el-aside>

    <el-main style="overflow-y: scroll; position:absolute; left: 20%; right: 0;top: 70px;bottom: 0">
      <ol class="infinite-list">
        <li v-for="product in nowProductList" class="infinite-list-item">
          <el-container>
            <el-aside style="overflow: clip; width: 200px">
              <el-image style="padding:10px;width: 180px; height: 180px" :src=product.productImages[0] />
            </el-aside>

            <el-container>
              <el-header style="height: 60px; padding-top: 20px">
                <el-tag type="" class="product-type" color="rgba(102, 178, 255, 0.6)">
                  {{parseType(product.type)}}
                </el-tag>
                &nbsp;
                <el-button type="primary" @click="entranceProduct(product.storeId,product.id)"  round>
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
    </el-container>





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

<!--Lab2新增-创建商品界面-->
<!--你可以选择把创建商品改成一个弹窗或其他界面的一个部分。
这样的话，就不需要有这样一个views下的创建商品文件了，因为views下的文件一般会是一个单独的界面。
但如果你觉得这一个部分的代码较多，合到其他文件里会使那个文件太长，
可以在product文件夹下创建一个components子文件夹，里面存放product模块下产生的组件界面。把这个创建商品子组件放在里面。
这个传递数据的过程可能需要用到props-->

<script setup lang="ts">
import {computed, onMounted, ref} from 'vue'
import {uploadImage} from '../../api/tools'
import {createProduct} from "../../api/product.ts";
import {router} from "../../router";
import {ArrowLeft} from "@element-plus/icons-vue";
import {useRoute} from "vue-router";

//检测用户权限
accessDect()
function accessDect() {
  const userStoreId = sessionStorage.getItem('storeId')
  if (userStoreId !== useRoute().params.storeId){
    router.push({path: "/404"})
  }
}

//输入框值
const name = ref('')
const introduction = ref('')
const type = ref('')
const price = ref('')
//七种商品类型
const types = [
  {
    value: 'FOOD',
    label: 'FOOD（食品）',
  },
  {
    value: 'CLOTHES',
    label: 'CLOTHES（服饰）',
  },
  {
    value: 'FURNITURE',
    label: 'FURNITURE（家具）',
  },
  {
    value: 'ELECTRONICS',
    label: 'ELECTRONICS（电子产品）',
  },
  {
    value: 'ENTERTAINMENT',
    label: 'ENTERTAINMENT（娱乐）',
  },
  {
    value: 'SPORTS',
    label: 'SPORTS（体育产品）',
  },
  {
    value: 'LUXURY',
    label: 'LUXURY（奢侈品）',
  },
]



//能上传多张图片的代码实现。
const imageFileList = ref([])
const logoUrlList = ref([''])
logoUrlList.value.pop();
//店名是否为空
const hasNameInput = computed(() => name.value != '')
// 店名是否为空
const hasIntroductionInput = computed(() => introduction.value != '')



function handleChange(file: any, fileList: any) {
  imageFileList.value = fileList
  let formData = new FormData()
  formData.append('file', file.raw)
  uploadImage(formData).then(res => {
    console.log(res.data.result)
    logoUrlList.value.push(res.data.result)
    console.log(JSON.stringify(logoUrlList.value))
  })
}

function uploadHttpRequest() {
  return new XMLHttpRequest()
}

//创建按钮可用性
const createDisabled = computed(() => {
  var imageNum = 0;
  for (var image in imageFileList.value){
    imageNum++;
  }
  return !(hasNameInput.value && hasIntroductionInput.value && imageNum>=2)
})

//创建商店按钮触发
function handleCreateProduct() {
  createProduct({
    name: name.value,
    type: type.value,
    introduction: introduction.value,
    storeId: Number(sessionStorage.getItem("storeId")),
    price: parseInt(price.value),
    times: 0,
    score: 5,
        logoUrlList: JSON.stringify(logoUrlList.value)
  }
  ).then(res => {
    if (res.data.code === '000') {  //类型守卫，它检查 res.data 对象中是否存在名为 code 的属性
      ElMessage({
        message: "创建成功!",
        type: 'success',
        center: true,
      })
      router.back()
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

onMounted(() => {
  accessDect()
})

</script>


<template>
  <el-main>

    <el-form>

      <el-form-item>
        <el-button type="primary" :icon="ArrowLeft" @click="$router.back()">返回</el-button>
      </el-form-item>

      <el-form-item label="物品Logo">
        <el-upload
            v-model:file-list="imageFileList"
            :on-change="handleChange"
            class="upload-demo"
            list-type="picture"
            :http-request="uploadHttpRequest"
            drag>
          <el-icon class="el-icon--upload">
            <upload-filled/>
          </el-icon>
          <div class="el-upload__text">
            将文件拖到此处或单击此处上传。至少上传两份文件。
          </div>
        </el-upload>
      </el-form-item>

      <el-form-item label="物品名称">
        <el-input
            v-model="name"
            style="width: 240px"
            maxlength="10"
            placeholder="Please input"
            show-word-limit
            type="text"
        />
      </el-form-item>

      <el-form-item label="物品类型">
        <el-select v-model="type"  placeholder="Select" style="width: 240px">
          <el-option
              v-for="item in types"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="物品价格">
        <el-input
            v-model="price"
            maxlength="10"
            style="width: 240px"
            placeholder="Please input"
            show-word-limit
            type="text"
        />
      </el-form-item>
      <el-form-item label="物品简介">
        <el-input
            v-model="introduction"
            maxlength="200"
            style="width: 240px"
            placeholder="Please input"
            show-word-limit
            type="textarea"
        />
      </el-form-item>

      <el-button @click.prevent="handleCreateProduct"
                 :disabled="createDisabled"
                 type="primary">
        创建物品
      </el-button>

    </el-form>

  </el-main>
</template>


<style scoped>

</style>

<!--Lab2新增-创建商店界面-->
<!--你可以选择把创建商店改成一个弹窗或其他界面的一个部分。
这样的话，就不需要有这样一个views下的创建商店文件了，因为views下的文件一般会是一个单独的界面。
但如果你觉得这一个部分的代码较多，合到其他文件里会使那个文件太长，
可以在store文件夹下创建一个components子文件夹，里面存放store模块下产生的组件界面。把这个创建商店子组件放在里面
这个传递数据的过程可能需要用到props-->

<script setup lang="ts">
import {computed, ref} from 'vue'
import {uploadImage} from '../../api/tools'
import {createStore} from "../../api/store.ts";
import {router} from "../../router";
import {ArrowLeft} from '@element-plus/icons-vue'

//输入框值
const name = ref('')
const introduction = ref('')


//这里为大家提供上传且仅能上传1张图片的代码实现。
const imageFileList = ref([])
const logoUrl = ref('')

//店名是否为空
const hasNameInput = computed(() => name.value != '')
// 店名是否为空
const hasIntroductionInput = computed(() => introduction.value != '')
//logo是否为空
const hasLogoInput = computed(() => logoUrl.value != '')


function handleChange(file: any, fileList: any) {
  imageFileList.value = fileList
  let formData = new FormData()
  formData.append('file', file.raw)
  uploadImage(formData).then(res => {
    logoUrl.value = res.data.result
  })
}

function handleExceed() {
  ElMessage.warning(`当前限制选择 1 个文件`);
}

function handleRemove() {
  logoUrl.value = ''
}
function uploadHttpRequest() {
  return new XMLHttpRequest()
}

//创建按钮可用性
const createDisabled = computed(() => {
  return !(hasNameInput.value && hasIntroductionInput.value && hasLogoInput.value)
})

//创建商店按钮触发
function handleCreateStore() {
  createStore({
    name: name.value,
    introduction: introduction.value,
    logoUrl: logoUrl.value
  }
  ).then(res => {
    if (res.data.code === '000') {  //类型守卫，它检查 res.data 对象中是否存在名为 code 的属性
      ElMessage({
        message: "创建成功!",
        type: 'success',
        center: true,
      })
      router.push({path: "/allStore"})
    } else if (res.data.code === '400') {
      ElMessage({
        message: res.data.msg,
        type: 'error',
        center: true,
      })
    }
  })
}
</script>


<template>
  <el-main>

    <el-form>

      <el-form-item>
        <el-button type="primary" :icon="ArrowLeft" @click="$router.back()">返回</el-button>
      </el-form-item>

      <el-form-item label="商店Logo">
        <el-upload
            v-model:file-list="imageFileList"
            :limit="1"
            :on-change="handleChange"
            :on-exceed="handleExceed"
            :on-remove="handleRemove"
            class="upload-demo"
            list-type="picture"
            :http-request="uploadHttpRequest"
            drag>
          <el-icon class="el-icon--upload">
            <upload-filled/>
          </el-icon>
          <div class="el-upload__text">
            将文件拖到此处或单击此处上传。仅允许上传一份文件。
          </div>
        </el-upload>
      </el-form-item>

      <el-form-item label="商店名称">
        <el-input
            v-model="name"
            style="width: 240px"
            maxlength="10"
            placeholder="Please input"
            show-word-limit
            type="text"
        />
      </el-form-item>

      <el-form-item label="商店简介">
      <el-input
          v-model="introduction"
          maxlength="100"
          style="width: 240px;"
          placeholder="Please input"
          show-word-limit
          autosize
          type="textarea"
      />
      </el-form-item>

      <el-button @click.prevent="handleCreateStore"
                 :disabled="createDisabled"
                 type="primary">
        创建商店
      </el-button>
    </el-form>

  </el-main>
</template>


<style scoped>

</style>

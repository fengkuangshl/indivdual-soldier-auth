<template>
  <div class="home">
    <h3>
      {{ message }}
    </h3>
    <div style="height: 687px;">
      <el-button type="primary" size="medium" @click="upload">上传</el-button>

    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import Bus from '@/components/file-uploader/config/bus'
@Component({
  components: {}
})
export default class Home extends Vue {
  message = 'welcome'

  upload(): void {
    // 打开文件选择框
    Bus.$emit('openUploader', {
      // 给服务端的额外参数
      params: {
        bizType: 'default'
      }
    })
  }
}
</script>

<style lang="less" scoped>
.home {
  background-color: rgb(255, 255, 255);
  h3 {
    padding: 10px;
  }
}
</style>

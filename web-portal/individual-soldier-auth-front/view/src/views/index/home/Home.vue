<template>
  <div class="home">
    <h3>
      {{ message }}
    </h3>
    <div style="height: 687px;">
      <KWCell :gap="15" label="滕王阁序" style="width: 300px">
        <KWText value="豫章故郡，洪都新府。星分翼轸，地接衡庐。襟三江而带五湖，控蛮荆而引瓯越。物华天宝，龙光射牛斗之墟；人杰地灵，徐孺下陈蕃之榻。雄州雾列，俊采星驰。" :row="1" />
      </KWCell>
      <el-button type="primary" size="medium" @click="upload">上传</el-button>

    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import Bus from '@/common/event-hub/event-hub'
import KWCell from '@/components/cell/Cell.vue'
import KWText from '@/components/text/Text.vue'

@Component({
  components: {
    KWCell,
    KWText
  }
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
  padding: 10px;
}
</style>

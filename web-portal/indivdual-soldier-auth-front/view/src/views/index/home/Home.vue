<template>
  <div>
    <h3>
      {{ message }}
    </h3>
    <div style="height: 687px;"></div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
@Component({
  components: {}
})
export default class Home extends Vue {
  message = 'welcome'
}
</script>

<style></style>

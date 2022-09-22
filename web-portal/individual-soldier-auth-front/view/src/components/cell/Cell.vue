<template>
  <div class="kw-cell" :flex="`justify:between align:${align}`">
    <div class="kw-cell__left" :class="type" :style="leftStyle">
      <slot name="left">{{ label }}</slot>
    </div>
    <div class="kw-cell__right" :class="type" :style="rightStyle">
      <slot>{{ value }}</slot>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
import { Style } from './interface/style'
@Component
export default class KWCell extends Vue {
  @Prop({ default: 'left', type: String })
  type!: string

  @Prop({ default: 'stretch', type: String })
  align!: string

  @Prop({ default: '', type: String })
  label!: string

  @Prop({ default: 0, type: Number })
  width!: number

  @Prop({ default: 5, type: Number })
  gap!: number

  @Prop({ default: '', type: [String, Number] })
  value!: string | number

  get leftStyle(): Style {
    const style: Style = {}
    if (this.type === 'left') {
      style.marginRight = this.gap + 'px'
      if (this.width) {
        style.width = this.width + 'px'
      }
    }
    return style
  }

  get rightStyle(): Style {
    const style: Style = {}
    if (this.type === 'right') {
      style.marginLeft = this.gap + 'px'
      if (this.width) {
        style.width = this.width + 'px'
      }
    }
    return style
  }
}
</script>

<style lang="less">
.kw-cell {
  font-size: 14px;
  line-height: 24px;
  padding: 6px 0;

  .kw-cell__left {
    flex-shrink: 0;
    &.right {
      flex: 1;
      flex-shrink: 1;
      word-break: break-word;
    }
    &.between {
      flex-shrink: 1;
    }
  }

  .kw-cell__right {
    color: #666666;
    flex-shrink: 0;
    &.left {
      flex: 1;
      flex-shrink: 1;
      word-break: break-word;
    }
    &.between {
      flex-shrink: 1;
    }
  }
}
</style>

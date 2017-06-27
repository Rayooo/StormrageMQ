<template>
  <div>
    {{aa}}
    <el-button type="primary">按钮</el-button>
  </div>
</template>

<script>
  import util from "@/util/util"
  export default {
    name:'ray',
    data () {
      return {
        aa: util.getHost() + "iahusdiaushdiashdiaushdiasuhd"
      }
    }
  }
</script>

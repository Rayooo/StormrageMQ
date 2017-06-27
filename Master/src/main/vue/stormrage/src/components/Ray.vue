<template>
  <div>
    {{aa}}
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

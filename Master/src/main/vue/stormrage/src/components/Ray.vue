<template>
  <div>
    {{aa}}
    <el-button type="primary" v-on:click="jump()" >按钮</el-button>
  </div>
</template>

<script>
  import util from "@/util/util"
  import {router} from "../main"
  export default {
    name:'ray',
    data () {
      return {
        aa: util.getHost() + "iahusdiaushdiashdiaushdiasuhd"
      }
    },
    methods: {
        jump:function () {
          router.push({ name: 'HomePage', params: { userId: 123 }});
        }
    }
  }
</script>

<template>
  <div>
    <div id="logo">
      <img width="30%" src="../../static/logo.png"/>
      <div id="name">
        Stormrage MQ
      </div>
    </div>
    <div>
    </div>
    <div id="buttons">
      <el-button type="primary" id="login" v-on:click="login()">登录</el-button>
    </div>
  </div>
</template>

<script>
  import ElButton from "../../node_modules/element-ui/packages/button/src/button";
  import {router} from '../main'
  export default {
    components: {ElButton},
    name: 'homePage',
    data () {
      return {
        msg: 'Welcome to Your Vue.js App'
      }
    },
    methods:{
      login: function () {
        router.push({ name: 'myRay', params: { userId: 123 }});
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  #logo {
    text-align: center;
  }

  #name {
    font-size: 25px;
  }

  #login {
    width: 10%;
  }

  #buttons{
    margin-top: 20px;
    text-align: center;
  }
</style>

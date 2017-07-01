<template>
  <div id="register">
    <el-input v-model="userName" placeholder="请输入用户名"></el-input>
    <el-input v-model="password" placeholder="请输入密码"></el-input>
    <el-button type="primary" @click="register()" >注册</el-button>
  </div>
</template>

<script>
  import ElInput from "../../node_modules/element-ui/packages/input/src/input";
  import ElButton from "../../node_modules/element-ui/packages/button/src/button";
  import util from "@/util/util"
  import {Vue} from '../main'

  console.log(Vue);
  export default {
    components: {
      ElButton,
      ElInput},
    name: "Register",
    data() {
      return {
        userName:"",
        password:""
      }
    },
    methods: {
      register(){
        console.log(this.userName);
        console.log(this.password);
        const param = {userName: this.userName, password: this.password};
        Vue.http.post(util.getHost()+"userAccount/register", param).then((response)=>{
            console.log(response);
        })
      }
    }
  }
</script>

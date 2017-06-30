<template>
  <div id="homePage">
    <div id="logo">
      <img width="30%" src="../../static/logo.png"/>
      <div id="name">
        Stormrage MQ
      </div>
    </div>


    <div id="inputs" style="margin-top: 50px;margin-bottom: 10px">
      <div style="width: 30%;margin-left: 35%" >
        <transition name="el-fade-in">
          <div v-show="showInputs" class="transition-box">
            <el-input  v-model="userName" placeholder="请输入用户名"></el-input>
            <el-input  type="password" v-model="password" placeholder="请输入密码" style="margin-top: 10px"></el-input>
          </div>
        </transition>
      </div>
    </div>

    <div id="buttons" v-bind:style="buttonStyle">
      <el-button  type="primary" id="login" v-on:click="login()">登录</el-button>
    </div>
  </div>
</template>

<script>
  import ElButton from "../../node_modules/element-ui/packages/button/src/button";
  import {router} from '../main'
  import ElRow from "element-ui/packages/row/src/row";

  export default {
    components: {
      ElRow,
      ElButton},
    name: 'homePage',
    data () {
      return {
        buttonTop:20,
        showInputs:false,
        msg: 'Welcome to Your Vue.js App',
        buttonStyle: {'margin-top': this.buttonTop + 'px'},
        userName:'',
        password:''
      }
    },
    methods:{
      login: function () {
        if(!this.showInputs){
          const length = 153;
          const move = setInterval(() => {
            this.buttonTop += 1;
            this.buttonStyle = {'margin-top': this.buttonTop + 'px'};
            if (this.buttonTop === length) {
              clearInterval(move);
              this.showInputs = true;
              this.buttonStyle = {'margin-top': 20 + 'px'};
            }
          }, 5);
        }
        else{
          router.push({ name: 'Ray', params: { userId: 123 }});
        }
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

  #homePage {
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
  }
</style>

<template>
    <div id="homePage">
        <div id="logo">
            <img width="30%" src="../../static/logo.png"/>
            <div id="name">
                Stormrage MQ
            </div>
        </div>


        <div id="inputs" style="margin-top: 50px;margin-bottom: 10px">
            <div style="width: 30%;margin-left: 35%">
                <transition name="el-fade-in">
                    <div v-show="showInputs" class="transition-box">
                        <el-input v-model="userName" placeholder="请输入用户名"></el-input>
                        <el-input type="password" v-model="password" placeholder="请输入密码"
                                  style="margin-top: 10px" @keyup.enter.native="login()"></el-input>
                    </div>
                </transition>
            </div>
        </div>

        <div id="buttons" v-bind:style="buttonStyle">
            <el-button type="primary" id="login"  v-on:click="login()" :loading="loading">登录</el-button>
        </div>
    </div>
</template>

<script>
    import ElButton from "../../node_modules/element-ui/packages/button/src/button";
    import {router} from '../main'
    import ElRow from "element-ui/packages/row/src/row";
    import {Vue} from "../main"
    import Global from "./Global.vue"
    import App from "../App.vue"

    export default {
        components: {
            ElRow,
            ElButton
        },
        name: 'homePage',
        data () {
            return {
                buttonTop: 20,
                showInputs: false,
                buttonStyle: {'margin-top': this.buttonTop + 'px'},
                userName: '',
                password: '',
                loading: false
            }
        },
        methods: {
            login: function () {
                //第一次嗯下
                if (!this.showInputs) {
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
                else {
                    //第二次嗯下登录
                    const param = {userName:this.userName, password:this.password};
                    this.loading = true;
                    Global.post("userAccount/login", param, (response) => {
                        this.loading = false;
                        if(response.body.code === 0){
                            Global.userInfo = response.body.result;
                            Global.setSessionStorage("user",response.body.result);
                            App.userInfo = Global.userInfo;
//                            window.location.reload(false);
                            router.app.$emit("loginMessage");
                            router.push({name: 'Admin'});
                        }else{
                            this.$message.error(response.body.message);
                        }
                    }, () => {
                        this.loading = false;
                        this.$message.error("服务器异常");
                    });

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

    #buttons {
        margin-top: 20px;
    }
</style>

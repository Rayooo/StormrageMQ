<template>
    <div id="register">
        <el-input v-model="userName" placeholder="请输入用户名"></el-input>
        <el-input v-model="password" placeholder="请输入密码"></el-input>
        <el-button type="primary" @click="register()">注册</el-button>
    </div>
</template>

<script>
    import ElInput from "../../node_modules/element-ui/packages/input/src/input";
    import ElButton from "../../node_modules/element-ui/packages/button/src/button";
    import Global from "../components/Global.vue"

    export default {
        components: {
            ElButton,
            ElInput
        },
        name: "Register",
        data() {
            return {
                userName: "",
                password: ""
            }
        },
        methods: {
            register(){
                const param = {userName: this.userName, password: this.password};
                Global.post("userAccount/register", param, (response) => {
                    //console.log(response);
                })
            }
        }
    }
</script>

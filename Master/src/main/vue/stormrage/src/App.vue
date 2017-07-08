<template>
    <div id="app">
        <el-menu :default-active="activeIndex2" class="el-menu-demo" mode="horizontal" @select="handleSelect"
                 v-show="isShowMenuBar" :router="true">
            <el-menu-item index="1" :route='{name: "Admin"}' :disabled="false">Stormrage MQ</el-menu-item>
            <el-menu-item index="2" :route='{name: "Exchanger"}' :disabled="false">交换器</el-menu-item>
            <el-menu-item index="3" :route='{name: "Queue"}' :disabled="false">队列</el-menu-item>
            <el-menu-item index="4" :route='{name: "Message"}' :disabled="false">消息</el-menu-item>

            <el-submenu index="5" style="float: right;" >
                <template slot="title" ><span id="userName">{{userName}}</span></template>
                <el-menu-item index="5-1" :route="{}" :disabled="false">用户信息</el-menu-item>
                <el-menu-item index="5-2" :route="{}" :disabled="false" @click="logout()">退出</el-menu-item>
            </el-submenu>

        </el-menu>

        <router-view></router-view>

    </div>
</template>

<script>
    import {router} from "./main"
    import ElMenu from "../node_modules/element-ui/packages/menu/src/menu";
    import ElMenuItem from "../node_modules/element-ui/packages/menu/src/menu-item";
    import ElIcon from "../node_modules/element-ui/packages/icon/src/icon";
    import Global from "./components/Global.vue"
    import ElSubmenu from "../node_modules/element-ui/packages/menu/src/submenu";
    export default  {
        components: {
            ElSubmenu,
            ElIcon,
            ElMenuItem,
            ElMenu
        },
        name: 'app',
        data() {
            return {
                isShowMenuBar: true,
                activeIndex2: '1',
                isLogin:Global.isLogin(),
                userName: Global.userInfo.userName
            }
        },
        mounted: function () {
            router.app.$on("loginMessage",function () {
                document.getElementById("userName").innerHTML = Global.userInfo.userName;
            });
            this.isShowMenuBar = window.location.href.split("#")[1] !== "/";
        },
        methods: {
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
            },
            go(viewName){
                router.push({name: viewName})
            },
            logout(){
                Global.logout();
            }
        }
    }


</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        color: #2c3e50;
    }
</style>

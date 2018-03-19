<template>
    <section>
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect"
                 :router="true">
            <el-menu-item index="1" :route='{name: "Admin"}' :disabled="false">Stormrage MQ</el-menu-item>
            <el-menu-item index="2" :route='{name: "Exchanger"}' :disabled="false">交换器</el-menu-item>
            <el-menu-item index="3" :route='{name: "Queue"}' :disabled="false">队列</el-menu-item>
            <el-menu-item index="4" :route='{name: "Message"}' :disabled="false">消息</el-menu-item>

            <el-submenu index="5" style="float: right;" >
                <template slot="title" ><span id="userName">{{userInfo.userName}}</span></template>
                <el-menu-item index="5-1" :route="{name: 'UserInfo'}" :disabled="false">用户信息</el-menu-item>
                <el-menu-item index="5-2" :route="{}" :disabled="false" @click="logout()">退出</el-menu-item>
            </el-submenu>

        </el-menu>
    </section>
</template>

<script>

    import router from "../router"
    import Global from "./Global.vue"
    import {mapState, mapGetters} from "vuex"

    export default {
        name: "navigation",
        props:['fatherComponent'],
        data() {
            return {
                activeIndex: ''
            }
        },
        methods: {
            handleSelect(key, keyPath) {
//                console.log(key, keyPath);
            },
            go(viewName){
                router.push({name: viewName})
            },
            logout(){
                Global.logout();
            }
        },
        computed: {
            ...mapState([
                 "userInfo", //映射 this.userInfo 为store.state.userInfo
                 "token"
            ]),
            ...mapGetters([
                "getUserInfo"
            ])
        },
        mounted() {
            this.msg = Global.userInfo;
            if(this.fatherComponent === "exchanger"){
                this.activeIndex = '2';
            }
            else if(this.fatherComponent === "queue"){
                this.activeIndex = '3';
            }
            else if(this.fatherComponent === "message"){
                this.activeIndex = '4';
            }
            else if(this.fatherComponent === "admin") {
                this.activeIndex = '1';
            }
        }

    }


</script>

<template>
    <div id="app">
        <el-menu :default-active="activeIndex2" class="el-menu-demo" mode="horizontal" @select="handleSelect"
                 v-show="isShowMenuBar">
            <el-menu-item index="2">Ray</el-menu-item>
            <el-submenu index="3">
                <template slot="title">我的工作台</template>
                <el-menu-item index="3-1">选项1</el-menu-item>
                <el-menu-item index="3-2">选项2</el-menu-item>
                <el-menu-item index="3-3">选项3</el-menu-item>
            </el-submenu>
            <el-menu-item index="4">Another</el-menu-item>
        </el-menu>

        <router-view></router-view>

    </div>
</template>

<script>
    import {router} from "./main"
    import ElMenu from "../node_modules/element-ui/packages/menu/src/menu";
    import ElMenuItem from "../node_modules/element-ui/packages/menu/src/menu-item";
    import ElIcon from "../node_modules/element-ui/packages/icon/src/icon";
    export default {
        components: {
            ElIcon,
            ElMenuItem,
            ElMenu
        },
        name: 'app',
        data() {
            return {
                isShowMenuBar: true,
                activeIndex2: '1'
            };
        },
        mounted: function () {
            this.isShowMenuBar = window.location.href.split("#")[1] !== "/";
        },
        methods: {
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
            },
            go(viewName){
                router.push({name: viewName})
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

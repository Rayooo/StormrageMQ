// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from "./store/index"
import ElementUI from "element-ui"
import Resource from "vue-resource"
import "element-ui/lib/theme-default/index.css"
import Global from "./components/Global.vue"


Vue.use(ElementUI);
Vue.use(Resource);


import ECharts from 'vue-echarts/components/ECharts'
// import ECharts modules manually to reduce bundle size
import 'echarts/lib/chart/bar'
import 'echarts/lib/chart/line'
import 'echarts/lib/component/tooltip'
import 'echarts/lib/component/title'
// register component to use
Vue.component('chart', ECharts)


Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
    el: '#app',
    store,
    router,
    template: '<App/>',
    components: {App},
    mounted() {
        Global.post("/constants/getExcludePathPatterns", {}, (response) => {
            Global.setUnCheckUrl(response.body.result);
        });
        const info = store.getters.getUserInfo;
    }
});

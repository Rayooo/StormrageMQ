// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import Router from 'vue-router'
import Resource from 'vue-resource'
import PageNotFound from '@/components/PageNotFound'

Vue.config.productionTip = false;

Vue.use(Resource);
Vue.use(Router);
Vue.use(ElementUI);

function view(name) {
    return function (resolve) {
        require(['@/components/' + name + '.vue'], resolve);
    }
}

//路由
const router = new Router({
    routes: [
        {
            path: "/",
            name: "HomePage",
            component: view("HomePage")
        },
        {
            path: "/register",
            name: "Register",
            component: view("Register")
        },
        {
            path: "/admin",
            name: "Admin",
            component: view("Admin")
        },
        {
            path: "/exchanger",
            name: "Exchanger",
            component: view("exchanger/Exchanger")
        },
        {
            path: "/message",
            name: "Message",
            component: view("message/Message")
        },
        {
            path: "/queue",
            name: "Queue",
            component: view("queue/Queue")
        },

        {path: "*", component: PageNotFound}
    ]
});


const vm = new Vue({
    el: '#app',
    router,
    template: '<App ref="app"/>',
    components: {App}
});


router.beforeEach((to, from, next) => {
    vm.$refs.app.isShowMenuBar = to.path !== "/";
    next();
});




export {router, vm, Vue};

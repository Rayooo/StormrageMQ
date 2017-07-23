import Vue from 'vue'
import Router from 'vue-router'


Vue.use(Router);

function view(name) {
    return function (resolve) {
        require(['@/page/' + name ], resolve);
    }
}

export default new Router({
    routes: [
        {
            path: '/',
            name: 'Login',
            component: view("Login.vue")
        },
        {
            path: "/register",
            name: "Register",
            component: view("Register.vue")
        },
        {
            path: "/exchanger",
            name: "Exchanger",
            component: view("exchanger/Exchanger.vue")
        },
        {
            path: "/message",
            name: "Message",
            component: view("message/Message.vue")
        },
        {
            path: "/queue",
            name: "Queue",
            component: view("queue/Queue.vue")
        },
        {
            path: "/admin",
            name: "Admin",
            component: view("Admin.vue")
        },
        {path: "*", component: view("PageNotFound.vue")}
    ]
})

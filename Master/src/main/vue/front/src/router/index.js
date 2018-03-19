import Vue from 'vue'
import Router from 'vue-router'
import store from "../store/index"
Vue.use(Router);

function view(name) {
    return function (resolve) {
        require(['@/page/' + name ], resolve);
    }
}

function isLogin() {
   let user = JSON.parse(sessionStorage.getItem("user"));
   if(!user){
       return false;
   }
   else{
       return !!user.loginToken;
   }
}

function checkUrl() {
   if(!isLogin()){
       let path = window.location.href.split("#")[1];
       if(unLoginUrl.indexOf(path) === -1){        //找不到
           window.location.href = "/";
       }
   }
}

let unLoginUrl = ["/", "/register"];


let router = new Router({
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
        {
            path: "/userInfo",
            name: "UserInfo",
            component: view("user/UserInfo.vue")
        },
        {path: "/error", component: view("Error.vue")},
        {path: "*", component: view("PageNotFound.vue")}
    ]
});

router.beforeEach((to, from, next) => {
    checkUrl();
    next();
});

export default router;


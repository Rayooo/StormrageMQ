import Vue from 'vue'
import Router from 'vue-router'
import PageNotFound from '@/components/PageNotFound'

function view(name) {
  return function(resolve) {
    require(['@/components/' + name + '.vue'], resolve);
  }
}

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HomePage',
      component: view("HomePage")
    },
    {
      path: '/ray',
      name: 'myRay',
      component: view("Ray")
    },
    {
      path: '/another',
      name: "hhh",
      component: view("Another")
    },

    { path: "*", component: PageNotFound }
  ]
})

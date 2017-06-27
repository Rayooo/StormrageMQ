import Vue from 'vue'
import Router from 'vue-router'
import HomePage from '@/components/HomePage'
import Ray from '@/components/Ray'
import Another from '@/components/Another'
import PageNotFound from '@/components/PageNotFound'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HomePage',
      component: HomePage
    },
    {
      path: '/ray',
      name: 'myRay',
      component: Ray
    },
    {
      path: '/another',
      name: "hhh",
      component: Another
    },

    { path: "*", component: PageNotFound }
  ]
})

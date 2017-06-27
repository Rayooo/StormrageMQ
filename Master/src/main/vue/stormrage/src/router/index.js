import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Ray from '@/components/Ray'
import Another from '@/components/Another'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
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
    }
  ]
})

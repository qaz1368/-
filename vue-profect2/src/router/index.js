import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'ApplicationView',
    component: () => import('../views/ApplicationView.vue'),
    meta: {
      title: '申请',
    }
  },
];

const router = new VueRouter({
  routes
});

export default router;

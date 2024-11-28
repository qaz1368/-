import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import request from "@/util/request";


Vue.config.productionTip = false

Vue.use(ElementUI, { size: "mini" });

Vue.prototype.request=request
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

router.beforeEach((to, from, next) => {
  /* 路由发生变化修改页面title */
  console.log(to,from,next);
  if (to.meta.title) {
    document.title = to.meta.title;
    next();
  }

});
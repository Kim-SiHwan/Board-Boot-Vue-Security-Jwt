import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import router from './routes/index';store.js
import {store} from "../store";


Vue.config.productionTip = false

new Vue({
  vuetify,
  router,
  store :store,
  render: h => h(App)
}).$mount('#app')

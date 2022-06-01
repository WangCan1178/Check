import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import installElementPlus from './plugins/element'
import './assets/css/icon.css'
import axios from "axios";
const app = createApp(App)
installElementPlus(app)
app
    .use(store)
    .use(router)
    .mount('#app')
app.config.globalProperties.$axios = axios
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'

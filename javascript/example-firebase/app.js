// Setup Firebase
import * as firebase from "firebase/app";

const firebaseConfig = {
  apiKey: "AIzaSyABCTBVgSZBfThJGYFyzMV65hKMxoNlFho",
  authDomain: "reactivex-demo.firebaseapp.com",
  databaseURL: "https://reactivex-demo.firebaseio.com",
  projectId: "reactivex-demo",
  storageBucket: "reactivex-demo.appspot.com",
  messagingSenderId: "32458207114",
  appId: "1:32458207114:web:4b1949d4973c53d4cfa273"
};

firebase.initializeApp(firebaseConfig);

// Setup Vue
import VueRouter from 'vue-router';
import Vue from 'vue';
import App from './App.vue';

import ArticleDetailsPage from './pages/ArticleDetailsPage';
import ArticleListPage from './pages/ArticleListPage';

const routes = [
  { path: '/', component: ArticleListPage },
  { path: '/articles/:id', component: ArticleDetailsPage }
]

const router = new VueRouter({
  routes
})

Vue.use(VueRouter)
new Vue({ router: router, render: createElement => createElement(App) }).$mount('#app');

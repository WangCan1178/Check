import {createRouter, createWebHashHistory} from "vue-router";
import Home from "../views/Home.vue";

const routes = [
    {
        path: '/',
        redirect: '/dashboard'
    }, {
        path: "/",
        name: "Home",
        component: Home,
        children: [
            {
                path: "/dashboard",
                name: "dashboard",
                meta: {
                    title: '系统首页'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/Dashboard.vue")
            }, {
                path: "/add",
                name: "addGroup",
                meta: {
                    title: '加入的群组'
                }, component: () => import ( /* webpackChunkName: "dashboard" */ "../views/addGroup.vue")
            },{
                path:"add/:id(\\d+)",
                name:"AGroup",
                meta:{
                    title:"群组详情",
                    noCache: false
                },
                component: () => import("../views/AGroup.vue")
            }, {
                path: "/manage",
                name: "manageGroup",
                meta: {
                    title: '管理的群组'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/manageGroup.vue")
            },{
                path:"manage/:id(\\d+)",
                name:"groupMa",
                meta:{
                    title:"群组详情"
                },
                component: () => import("../views/MGroup.vue")
            },{
                path:"task/:id(\\d+)",
                name:"task",
                meta:{
                    title:"任务详情"
                },
                component: () => import("../views/Task.vue")
            }, {
                path: '/user',
                name: 'user',
                meta: {
                    title: '个人中心'
                },
                component: () => import (/* webpackChunkName: "user" */ '../views/User.vue')
            },
            {
                path: "/message",
                name: "message",
                meta: {
                    title: '消息中心'
                },
                component: () => import ( /* webpackChunkName: "tabs" */ "../views/Message.vue")
            },
        ]
    }, {
        path: "/login",
        name: "Login",
        meta: {
            title: '登录'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../views/Login.vue")
    },{
        path: "/register",
        name: "Register",
        meta: {
            title: '注册'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../views/Register.vue")
    },{
        path: "/next",
        name: "RegisterNext",
        meta: {
            title: '完善信息'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../views/Next.vue")
    },{
        path: "/forget",
        name: "Forget",
        meta: {
            title: '忘记密码'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../views/Forget.vue")
    },{
        path: "/forgetnext",
        name: "ForgetNext",
        meta: {
            title: '重置密码'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../views/forgetnext.vue")
    }
];

const router = createRouter({
    history: createWebHashHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title}`;
    const role = localStorage.getItem('ms_username');
    if (!role && to.path !== '/login' && to.path!== '/register' && to.path !== '/forget' && to.path !== "/next" && to.path !== "/forgetnext") {
        next('/login');
    } else if (to.meta.permission) {
        // 如果是管理员权限则可进入，这里只是简单的模拟管理员权限而已
        role === 'admin'
            ? next()
            : next('/403');
    } else {
        next();
    }
});

export default router;
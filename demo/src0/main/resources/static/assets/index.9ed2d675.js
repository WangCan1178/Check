import {
    r as e,
    o as t,
    c as a,
    p as s,
    a as o,
    u as n,
    b as l,
    d as i,
    e as r,
    f as d,
    g as c,
    h as m,
    t as u,
    w as p,
    i as h,
    F as g,
    j as _,
    k as f,
    l as b,
    T as v,
    K as x,
    m as k,
    n as L,
    q as T,
    s as E,
    v as y,
    _ as I,
    x as P,
    R as w,
    y as C
} from "./vendor.9c82358b.js";

const R = {
    name: "App", computed: {
        key() {
            return this.$route.path + Math.random()
        }
    }
};
let A;
R.render = function (s, o, n, l, i, r) {
    const d = e("router-view");
    return t(), a(d, {key: r.key})
};
const j = {}, O = function (e, t) {
    if (!t) return e();
    if (void 0 === A) {
        const e = document.createElement("link").relList;
        A = e && e.supports && e.supports("modulepreload") ? "modulepreload" : "preload"
    }
    return Promise.all(t.map((e => {
        if (e in j) return;
        j[e] = !0;
        const t = e.endsWith(".css"), a = t ? '[rel="stylesheet"]' : "";
        if (document.querySelector(`link[href="${e}"]${a}`)) return;
        const s = document.createElement("link");
        return s.rel = t ? "stylesheet" : A, t || (s.as = "script", s.crossOrigin = ""), s.href = e, document.head.appendChild(s), t ? new Promise(((e, t) => {
            s.addEventListener("load", e), s.addEventListener("error", t)
        })) : void 0
    }))).then((() => e()))
};
var D = "./assets/img.cf1cb86c.jpg";
const V = {
    setup() {
        const e = localStorage.getItem("ms_username"), t = n(), a = l((() => t.state.collapse)), s = () => {
            t.commit("handleCollapse", !a.value)
        };
        i((() => {
            document.body.clientWidth < 1500 && s()
        }));
        const o = r();
        return {
            username: e, message: 2, collapse: a, collapseChage: s, handleCommand: e => {
                "loginout" == e ? (localStorage.removeItem("ms_username"), o.push("/login")) : "user" == e ? o.push("/user") : "deleteUser" == e && (localStorage.removeItem("ms_username"), o.push("/login"))
            }
        }
    }
}, $ = p();
s("data-v-12d71992");
const G = {class: "header"}, S = {key: 0, class: "el-icon-s-fold"}, F = {key: 1, class: "el-icon-s-unfold"},
    M = d("div", {class: "logo"}, "check查客", -1), N = {class: "header-right"}, q = {class: "header-user-con"},
    z = {class: "btn-bell"}, U = d("i", {class: "el-icon-bell"}, null, -1), H = {key: 0, class: "btn-bell-badge"},
    W = d("div", {class: "user-avator"}, [d("img", {src: D})], -1), K = {class: "el-dropdown-link"},
    B = d("i", {class: "el-icon-caret-bottom"}, null, -1), J = m("个人中心"), Q = m("退出登录"), X = m("注销账号");
o();
const Y = $(((s, o, n, l, i, r) => {
    const p = e("router-link"), h = e("el-tooltip"), g = e("el-dropdown-item"), _ = e("el-dropdown-menu"),
        f = e("el-dropdown");
    return t(), a("div", G, [d("div", {
        class: "collapse-btn",
        onClick: o[1] || (o[1] = (...e) => l.collapseChage && l.collapseChage(...e))
    }, [l.collapse ? (t(), a("i", F)) : (t(), a("i", S))]), M, d("div", N, [d("div", q, [d("div", z, [d(h, {
        effect: "dark",
        content: l.message ? `有${l.message}条未读消息` : "消息中心",
        placement: "bottom"
    }, {
        default: $((() => [d(p, {to: "/message"}, {default: $((() => [U])), _: 1})])),
        _: 1
    }, 8, ["content"]), l.message ? (t(), a("span", H)) : c("", !0)]), W, d(f, {
        class: "user-name",
        trigger: "click",
        onCommand: l.handleCommand
    }, {
        dropdown: $((() => [d(_, null, {
            default: $((() => [d(g, {command: "user"}, {
                default: $((() => [J])),
                _: 1
            }), d(g, {divided: "", command: "loginout"}, {default: $((() => [Q])), _: 1}), d(g, {
                divided: "",
                command: "deleteUser"
            }, {default: $((() => [X])), _: 1})])), _: 1
        })])), default: $((() => [d("span", K, [m(u(l.username) + " ", 1), B])])), _: 1
    }, 8, ["onCommand"])])])])
}));
V.render = Y, V.__scopeId = "data-v-12d71992";
const Z = {
    setup() {
        const e = h(), t = l((() => e.path)), a = n();
        return {
            items: [{icon: "el-icon-lx-home", index: "/dashboard", title: "系统首页"}, {
                icon: "el-icon-lx-group",
                index: "/add",
                title: "我加入的群组"
            }, {icon: "el-icon-lx-addressbook", index: "/manage", title: "我管理的群组"}],
            onRoutes: t,
            collapse: l((() => a.state.collapse))
        }
    }
}, ee = p();
s("data-v-0395d9bc");
const te = {class: "sidebar"};
o();
const ae = ee(((s, o, n, l, i, r) => {
    const c = e("el-menu-item"), p = e("el-submenu"), h = e("el-menu");
    return t(), a("div", te, [d(h, {
        class: "sidebar-el-menu",
        "default-active": l.onRoutes,
        collapse: l.collapse,
        "background-color": "#324157",
        "text-color": "#bfcbd9",
        "active-text-color": "#20a0ff",
        "unique-opened": "",
        router: ""
    }, {
        default: ee((() => [(t(!0), a(g, null, _(l.items, (e => (t(), a(g, null, [e.subs ? (t(), a(p, {
            index: e.index,
            key: e.index
        }, {
            title: ee((() => [d("i", {class: e.icon}, null, 2), d("span", null, u(e.title), 1)])),
            default: ee((() => [(t(!0), a(g, null, _(e.subs, (e => (t(), a(g, null, [e.subs ? (t(), a(p, {
                index: e.index,
                key: e.index
            }, {
                title: ee((() => [m(u(e.title), 1)])),
                default: ee((() => [(t(!0), a(g, null, _(e.subs, ((e, s) => (t(), a(c, {
                    key: s,
                    index: e.index
                }, {default: ee((() => [m(u(e.title), 1)])), _: 2}, 1032, ["index"])))), 128))])),
                _: 2
            }, 1032, ["index"])) : (t(), a(c, {index: e.index, key: e.index}, {
                default: ee((() => [m(u(e.title), 1)])),
                _: 2
            }, 1032, ["index"]))], 64)))), 256))])),
            _: 2
        }, 1032, ["index"])) : (t(), a(c, {index: e.index, key: e.index}, {
            title: ee((() => [m(u(e.title), 1)])),
            default: ee((() => [d("i", {class: e.icon}, null, 2)])),
            _: 2
        }, 1032, ["index"]))], 64)))), 256))])), _: 1
    }, 8, ["default-active", "collapse"])])
}));
Z.render = ae, Z.__scopeId = "data-v-0395d9bc";
const se = {
        setup() {
            const e = h(), t = r(), a = n(), s = l((() => a.state.tagsList)), o = l((() => s.value.length > 0)), i = e => {
                s.value.some((t => t.path === e.fullPath)) || (s.value.length >= 8 && a.commit("delTagsItem", {index: 0}), a.commit("setTagsItem", {
                    name: e.name,
                    title: e.meta.title,
                    path: e.fullPath
                }))
            };
            i(e), f((e => {
                i(e)
            }));
            return {
                isActive: t => t === e.fullPath, tagsList: s, showTags: o, closeTags: o => {
                    const n = s.value[o];
                    a.commit("delTagsItem", {index: o});
                    const l = s.value[o] ? s.value[o] : s.value[o - 1];
                    l ? n.path === e.fullPath && t.push(l.path) : t.push("/")
                }, handleTags: o => {
                    "other" === o ? (() => {
                        const t = s.value.filter((t => t.path === e.fullPath));
                        a.commit("closeTagsOther", t)
                    })() : (a.commit("clearTags"), t.push("/"))
                }
            }
        }
    }, oe = {key: 0, class: "tags"}, ne = d("i", {class: "el-icon-close"}, null, -1), le = {class: "tags-close-box"},
    ie = m(" 标签选项 "), re = d("i", {class: "el-icon-arrow-down el-icon--right"}, null, -1), de = m("关闭其他"),
    ce = m("关闭所有");
se.render = function (s, o, n, l, i, r) {
    const p = e("router-link"), h = e("el-button"), f = e("el-dropdown-item"), v = e("el-dropdown-menu"),
        x = e("el-dropdown");
    return l.showTags ? (t(), a("div", oe, [d("ul", null, [(t(!0), a(g, null, _(l.tagsList, ((e, s) => (t(), a("li", {
        class: ["tags-li", {active: l.isActive(e.path)}],
        key: s
    }, [d(p, {to: e.path, class: "tags-li-title"}, {
        default: b((() => [m(u(e.title), 1)])),
        _: 2
    }, 1032, ["to"]), d("span", {
        class: "tags-li-icon",
        onClick: e => l.closeTags(s)
    }, [ne], 8, ["onClick"])], 2)))), 128))]), d("div", le, [d(x, {onCommand: l.handleTags}, {
        dropdown: b((() => [d(v, {size: "small"}, {
            default: b((() => [d(f, {command: "other"}, {
                default: b((() => [de])),
                _: 1
            }), d(f, {command: "all"}, {default: b((() => [ce])), _: 1})])), _: 1
        })])), default: b((() => [d(h, {size: "mini", type: "primary"}, {default: b((() => [ie, re])), _: 1})])), _: 1
    }, 8, ["onCommand"])])])) : c("", !0)
};
const me = {
    components: {vHeader: V, vSidebar: Z, vTags: se}, setup() {
        const e = n();
        return {tagsList: l((() => e.state.tagsList.map((e => e.name)))), collapse: l((() => e.state.collapse))}
    }
}, ue = {class: "about"}, pe = {class: "content"};
me.render = function (s, o, n, l, i, r) {
    const c = e("v-header"), m = e("v-sidebar"), u = e("v-tags"), p = e("router-view");
    return t(), a("div", ue, [d(c), d(m), d("div", {class: ["content-box", {"content-collapse": l.collapse}]}, [d(u), d("div", pe, [d(p, null, {
        default: b((({Component: e}) => [d(v, {
            name: "move",
            mode: "out-in"
        }, {
            default: b((() => [(t(), a(x, {include: l.tagsList}, [(t(), a(k(e)))], 1032, ["include"]))])),
            _: 2
        }, 1024)])), _: 1
    })])], 2)])
};
const he = [{path: "/", redirect: "/dashboard"}, {
    path: "/",
    name: "Home",
    component: me,
    children: [{
        path: "/dashboard",
        name: "dashboard",
        meta: {title: "系统首页"},
        component: () => O((() => import("./Dashboard.2aa1dce2.js")), ["./assets/Dashboard.2aa1dce2.js", "./assets/Dashboard.d2e4143f.css", "./assets/vue-schart.e1182e2b.js", "./assets/vendor.9c82358b.js"])
    }, {
        path: "/add",
        name: "addGroup",
        meta: {title: "加入的群组"},
        component: () => O((() => import("./addGroup.400172d4.js")), ["./assets/addGroup.400172d4.js", "./assets/addGroup.65a2a561.css", "./assets/vendor.9c82358b.js"])
    }, {
        path: "add/:id(\\d+)",
        name: "AGroup",
        meta: {title: "群组详情", noCache: !1},
        component: () => O((() => import("./AGroup.3d6d583b.js")), ["./assets/AGroup.3d6d583b.js", "./assets/AGroup.25cf801d.css", "./assets/vendor.9c82358b.js"])
    }, {
        path: "/manage",
        name: "manageGroup",
        meta: {title: "管理的群组"},
        component: () => O((() => import("./manageGroup.4fad21b7.js")), ["./assets/manageGroup.4fad21b7.js", "./assets/manageGroup.ae0102fd.css", "./assets/vendor.9c82358b.js"])
    }, {
        path: "manage/:id(\\d+)",
        name: "groupMa",
        meta: {title: "群组详情"},
        component: () => O((() => import("./MGroup.4dcb2622.js")), ["./assets/MGroup.4dcb2622.js", "./assets/MGroup.feed8644.css", "./assets/vendor.9c82358b.js"])
    }, {
        path: "task/:id(\\d+)",
        name: "task",
        meta: {title: "任务详情"},
        component: () => O((() => import("./Task.a5445003.js")), ["./assets/Task.a5445003.js", "./assets/Task.22d3ef3f.css", "./assets/vendor.9c82358b.js", "./assets/vue-schart.e1182e2b.js"])
    }, {
        path: "/user",
        name: "user",
        meta: {title: "个人中心"},
        component: () => O((() => import("./User.9b427256.js")), ["./assets/User.9b427256.js", "./assets/User.af88b9e6.css", "./assets/vendor.9c82358b.js"])
    }, {
        path: "/message",
        name: "message",
        meta: {title: "消息中心"},
        component: () => O((() => import("./Message.31dafdfe.js")), ["./assets/Message.31dafdfe.js", "./assets/Message.874c9530.css", "./assets/vendor.9c82358b.js"])
    }]
}, {
    path: "/login",
    name: "Login",
    meta: {title: "登录"},
    component: () => O((() => import("./Login.8e61a087.js")), ["./assets/Login.8e61a087.js", "./assets/Login.310aab74.css", "./assets/vendor.9c82358b.js"])
}, {
    path: "/register",
    name: "Register",
    meta: {title: "注册"},
    component: () => O((() => import("./Register.c2a23f83.js")), ["./assets/Register.c2a23f83.js", "./assets/Register.14cde4a0.css", "./assets/vendor.9c82358b.js"])
}, {
    path: "/next",
    name: "RegisterNext",
    meta: {title: "完善信息"},
    component: () => O((() => import("./Next.ebf9296b.js")), ["./assets/Next.ebf9296b.js", "./assets/Next.8ed97719.css", "./assets/vendor.9c82358b.js"])
}, {
    path: "/forget",
    name: "Forget",
    meta: {title: "忘记密码"},
    component: () => O((() => import("./Forget.a35d73a5.js")), ["./assets/Forget.a35d73a5.js", "./assets/Forget.b284447c.css", "./assets/vendor.9c82358b.js"])
}, {
    path: "/forgetnext",
    name: "ForgetNext",
    meta: {title: "重置密码"},
    component: () => O((() => import("./forgetnext.2ce790a7.js")), ["./assets/forgetnext.2ce790a7.js", "./assets/forgetnext.2c7089bc.css", "./assets/vendor.9c82358b.js"])
}], ge = L({history: T(), routes: he});
ge.beforeEach(((e, t, a) => {
    document.title = `${e.meta.title}`;
    const s = localStorage.getItem("ms_username");
    s || "/login" === e.path || "/register" === e.path || "/forget" === e.path || "/next" === e.path || "/forgetnext" === e.path ? e.meta.permission ? "admin" === s ? a() : a("/403") : a() : a("/login")
}));
var _e = E({
    state: {tagsList: [], collapse: !1}, mutations: {
        delTagsItem(e, t) {
            e.tagsList.splice(t.index, 1)
        }, setTagsItem(e, t) {
            e.tagsList.push(t)
        }, clearTags(e) {
            e.tagsList = []
        }, closeTagsOther(e, t) {
            e.tagsList = t
        }, closeCurrentTag(e, t) {
            for (let a = 0, s = e.tagsList.length; a < s; a++) {
                if (e.tagsList[a].path === t.$route.fullPath) {
                    a < s - 1 ? t.$router.push(e.tagsList[a + 1].path) : a > 0 ? t.$router.push(e.tagsList[a - 1].path) : t.$router.push("/"), e.tagsList.splice(a, 1);
                    break
                }
            }
        }, handleCollapse(e, t) {
            e.collapse = t
        }
    }, actions: {}, modules: {}
});
const fe = y({
    locale: I.name,
    fallbackLocale: P.name,
    messages: {
        "zh-cn": {
            i18n: {
                breadcrumb: "国际化产品",
                tips: "通过切换语言按钮，来改变当前内容的语言。",
                btn: "切换英文",
                title1: "常用用法",
                p1: "要是你把你的秘密告诉了风，那就别怪风把它带给树。",
                p2: "没有什么比信念更能支撑我们度过艰难的时光了。",
                p3: "只要能把自己的事做好，并让自己快乐，你就领先于大多数人了。"
            }
        },
        en: {
            i18n: {
                breadcrumb: "International Products",
                tips: "Click on the button to change the current language. ",
                btn: "Switch Chinese",
                title1: "Common usage",
                p1: "If you reveal your secrets to the wind you should not blame the wind for  revealing them to the trees.",
                p2: "Nothing can help us endure dark times better than our faith. ",
                p3: "If you can do what you do best and be happy, you're further along in life  than most people."
            }
        }
    }
});
const be = C(R);
var ve;
(ve = be).use(w, {locale: I}), ve.use(fe), be.use(_e).use(ge).mount("#app");
export {D as a};

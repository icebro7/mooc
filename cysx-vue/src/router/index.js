import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

let routes = [
    { name: "login", path: "/login", component: () => import("@/views/LoginView.vue") },
    { name: "enroll", path: "/enroll", component: () => import("@/views/EnrollView.vue") },
    {
        name: "main",
        path: "/main",
        component: () => import("@/views/MainView.vue"),
        children: [
            { name: "course", path: "/course", component: () => import("@/views/CourseView.vue") },
            { name: "person", path: "/person", component: () => import("@/views/PersonView.vue") },
            { name: "chapter", path: "/chapter", component: () => import("@/views/ChapterView.vue") }
        ]
    }
];

let router = new VueRouter({
    routes
});

router.beforeEach((to, from, next) => {
    // 白名单
    let list = ['/login', '/enroll', '/'];
    if (list.indexOf(to.path) !== -1) {
        next(); // 如果在白名单中，直接放行
    } else {
        // 获取本地数据
        let user = localStorage.getItem("cysx_token");
        if (user != null) {
            next(); // 如果用户已登录，放行
        } else {
            // 如果用户未登录，且当前路由不是 /login，才跳转到 /login
            if (to.path !== '/login') {
                alert("请先登录!");
                next('/login'); // 使用 next('/login') 而不是 router.push("/login")
            } else {
                next(); // 如果已经在 /login，直接放行
            }
        }
    }
});

export default router;
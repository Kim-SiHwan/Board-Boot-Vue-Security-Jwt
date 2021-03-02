import Vue from 'vue';
import Router from 'vue-router';
import Join from "@/components/Join";
import Board from "@/components/Board";
import BoardDetail from "@/components/BoardDetail";
import BoardWrite from "@/components/BoardWrite";
import Login from "@/components/Login";
import ErrorPage from "@/components/ErrorPage";

Vue.use(Router); //vue 라우터 사용

export default new Router({ //라우터 연결
    routes: [
        {
            path: '/',
            redirect: '/board'
        },
        {
            path: '/login',
            component: Login
        },
        {
            path: '/join',
            component: Join
        },
        {
            path: '/board',
            component: Board
        },
        {
            path: '/detail',
            component: BoardDetail
        },
        {
            path: '/write',
            component: BoardWrite
        },
        {
            path: '/error',
            component: ErrorPage
        }


    ]
})
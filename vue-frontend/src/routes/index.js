import Vue from 'vue';
import Router from 'vue-router';
import Join from "@/components/Join";
import Sample from "@/components/Sample";
import Board from "@/components/Board";
import BoardDetail from "@/components/BoardDetail";
import BoardWrite from "@/components/BoardWrite";

Vue.use(Router); //vue 라우터 사용

export default new Router({ //라우터 연결
    routes: [
        {
            path:'/join',
            component:Join
        },
        {
            path:'/sample',
            component: Sample
        },
        {
            path:'/board',
            component: Board
        },
        {
            path:'/detail',
            component: BoardDetail
        },
        {
            path: '/write',
            component: BoardWrite
        }



    ]
})
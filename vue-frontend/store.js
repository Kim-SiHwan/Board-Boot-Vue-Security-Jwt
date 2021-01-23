import Vue from "vue";
import Vuex from "vuex";
import member_api from "@/apis/member_api";
import board_api from "@/apis/board_api";
Vue.use(Vuex);

export const store = new Vuex.Store({
    // ...

    state:{
      token:'',
      username:'',
      boardList:[],
      board:[]
    },
    getters:{
      isAuthenticated(state){
          console.log(state.username)
          return !!state.token;
      },
      getBoardList(state){
          return state.boardList;
      },
      getBoard(state){
          console.log("?상태 ")
          console.log(state.board.title);
          return state.board;
      }
    },
    mutations:{
        setTokenInLocal(state,payload){
            console.log("setToken")
            console.log(state)
            console.log(payload);
            state.token=payload.token;
            state.username=payload.username;
            localStorage.setItem("access_token","Bearer "+payload.token);
        },
        deleteTokenInLocal(state){
            state.token="";
            state.username="";
            localStorage.removeItem("access_token");
        },
        startBoardList(state,payload){
            console.log(state);
            console.log(payload);
            state.boardList=payload.data;
        },
        getBoard(state,payload){
            console.log("payload : "+payload);
            state.board=payload;
        },


    },
    actions:{
        async REQUEST_JOIN(context,member){
            try{
                const response = await member_api.requestJoin(member);
                console.log(response.data);
                console.log(context);
                return response;
            }catch (e) {
                console.log("실패했습니다.")
                console.log(e.response.data.message);
            }
        },
        async REQUEST_LOGIN(context,member){
            try{
                const response = await member_api.requestLogin(member);
                console.log(response.data.token);
                store.commit('setTokenInLocal',response.data);
                console.log(context);

                return response;
            }catch (e) {
                console.log("실패했습니다.")
            }
        },
        async REQUEST_LOGOUT(){
            try{
                store.commit('deleteTokenInLocal');
            }catch (e) {
                console.log("로그아웃 실패")
            }
        },
        async REQUEST_BOARD_LIST(){
            try{
                const response= await board_api.getBoards();
                store.commit('startBoardList',response);
            }catch (e) {
                console.log("불러오기 실패")
            }
        },
        async REQUEST_BOARD(context,boardId){
            console.log("getBOARD :"+context+" "+boardId)
            try{
                const response = await board_api.getBoard(boardId);
                store.commit('getBoard',response);
            }catch (e) {
                console.log("불러오기 실패")
            }
        },
        async REQUEST_CREATE_BOARD(context,board){
            try{
                await board_api.createBoard(board);
            }catch (e) {
                console.log("글 작성 실패");
            }
        }
    }

});

import Vue from "vue";
import Vuex from "vuex";
import router from "@/routes/index"
import member_api from "@/apis/member_api";
import board_api from "@/apis/board_api";
import reply_api from "@/apis/reply_api";
import like_api from "@/apis/like_api";
Vue.use(Vuex);

export const store = new Vuex.Store({
    // ...

    state:{
      token:'',
      username:'',
      boardList:[],
      board:[],
      replies:[],
      replyContent:'',
      keyword:'default',
      snackbar:{open:false, text:'',timeout:2500,color:'error'}
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
          return state.board;
      }
    },
    mutations:{
        setKeyword(state,payload){
          state.keyword=payload;
        },
        setTokenInLocal(state,payload){
            console.log("setToken")
            console.log(state)
            console.log(payload);
            state.token=payload.token;
            state.username=payload.username;
            state.likeFlag=false;
            localStorage.setItem("access_token","Bearer "+payload.token);
            router.push('/board');
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
        setSnackBar(state,info){
            state.snackbar.open=true;
            state.snackbar.text=info.msg
            state.snackbar.color=info.color;

        },
        getRepliesByBoardId(state,payload){
            console.log(payload);
            state.replies=payload.data;
        },
        setReplyContent(state,payload){
            state.replyContent=payload.data;
        }


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
                store.commit('setSnackBar',
                    {msg:member.username+'님 반갑습니다.', color:'success'}
                    );
                return response;
            }catch (e) {
                store.commit('setSnackBar',
                    {msg:'비밀번호 혹은 아이디를 확인해주세요.', color:'error'}
                );
            }
        },
        async REQUEST_LOGOUT(){
            store.commit('deleteTokenInLocal');
            store.commit('setSnackBar',
                {msg:'로그아웃 되었습니다.', color:'info'}
            );

        },
        async REQUEST_BOARD_LIST(context,payload){
            try{
                store.commit('setKeyword',payload);
                const response= await board_api.getBoards(payload);
                store.commit('startBoardList',response);
            }catch (e) {
                console.log("불러오기 실패")
            }
        },
        async REQUEST_BEST_BOARDS(){
            try{
                const response= await board_api.getBests();
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
                store.commit('setSnackBar',
                    {msg:'게시글이 등록되었습니다.', color:'success'}
                );
            }catch (e) {
                console.log("글 작성 실패");
            }
        },
        async REQUEST_GET_REPLIES(context,boardId){
            try{
                const response = await reply_api.getReplies(boardId);
                console.log("REPLY : "+response.data);
                store.commit('getRepliesByBoardId',response);
            }catch (e){
                console.log("댓글 로딩 실패")
            }
        },
        async REQUEST_CREATE_REPLY(context,payload){
            try{
                const response = await reply_api.createReply(payload.boardId,payload.reply);
                store.commit('getRepliesByBoardId',response);
            }catch (e) {
                store.commit('setSnackBar',
                    {msg:'로그인에 실패했습니다..', color:'warning'}
                );
                console.log("댓글 작성 실패")
            }
        },
        async REQUEST_DELETE_BOARD(context,payload){
            try{
                await board_api.deleteBoard(payload);
                store.commit('setSnackBar',
                    {msg:'게시글이 삭제되었습니다.', color:'success'}
                );
                router.push('/board');
            }catch (e) {
                store.commit('setSnackBar',
                    {msg:'삭제에 실패하였습니다.', color:'error'}
                );
            }
        },
        async REQUEST_UPDATE_BOARD(context,payload){
            try{
                console.log("PATYLSOCASD" ,payload.id);
                await board_api.updateBoard(payload);
                store.commit('setSnackBar',
                    {msg:'게시글이 수정되었습니다.', color:'success'});

            }catch (e) {
                store.commit('setSnackBar',
                    {msg:'수정에 실패하였습니다.', color:'error'}
                );
            }
        },
        async REQUEST_DELETE_REPLY(context,payload){
            try{
                const response= await reply_api.deleteReply(payload);
                store.commit('getRepliesByBoardId',response);
            }catch (e) {
                store.commit('setSnackBar',
                    {msg:'댓글 삭제를 실패하였습니다.', color:'error'}
                );
            }
        },
        async REQUEST_REPLY_FOR_UPDATE(context,payload){
            try{
                const response = await reply_api.getReply(payload);
                store.commit('setReplyContent',response);
            }catch (e) {
                store.commit('setSnackBar',
                    {msg:'댓글 가져오기를 실패하였습니다.', color:'error'}
                );
            }
        },
        async REQUEST_UPDATE_REPLY(context,payload){
            try{
                const response= await reply_api.updateReply(payload);
                store.commit('getRepliesByBoardId',response);
                store.commit('setSnackBar',
                    {msg:'댓글이 수정되었습니다.', color:'success'}
                );
            }catch (e) {
                store.commit('setSnackBar',
                    {msg:'댓글 수정을 실패하였습니다.', color:'error'}
                );
            }
        },
        async REQUEST_LIKE(context,payload){
            try{
                const response = await like_api.pushLike(payload);
                console.log(response);
                store.commit('setSnackBar',
                    {msg:response.data,color:'info'});
            }catch (e) {
                store.commit('setSnackBar',
                    {msg:'추천을 실패했습니다.',color:'error'});
            }
        }

    }

});

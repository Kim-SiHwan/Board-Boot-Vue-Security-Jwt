import Vue from "vue";
import Vuex from "vuex";
import router from "@/routes"
import member_api from "@/apis/member_api";
import board_api from "@/apis/board_api";
import reply_api from "@/apis/reply_api";
import like_api from "@/apis/like_api";

Vue.use(Vuex);

export const store = new Vuex.Store({
    // ...
    state: {
        token: '',
        authenticated: false,
        username: '',
        keyword: '',
        boardList: [],
        bestBoardList: [],
        board: '',
        replyList: [],
        snackbar: {open: false, text: '', timeout: 2500, color: 'error'}


    },

    getters: {
        isAuthenticated(state) {
            return !!(state.token && state.authenticated && state.username);
        },
        getBoardList(state) {
            return state.boardList;
        },
        getBoard(state) {
            return state.board;
        },
        getUsername(state) {
            return state.username;
        },
        getToken(state) {
            return state.token;
        }
    },
    mutations: {
        LOGIN(state, payload) {
            localStorage.setItem('access_token', 'Bearer ' + payload.token);
            localStorage.setItem('username', payload.username);
            state.token = localStorage.getItem('access_token');
            state.username = localStorage.getItem('username');
            state.authenticated = true;
        },
        LOGOUT(state) {
            localStorage.removeItem('access_token');
            localStorage.removeItem('username');
            state.token = '';
            state.username = '';
            state.authenticated = false;
        },
        SET_BOARD_LIST(state, payload) {
            state.boardList = payload;
        },
        SET_BEST_BOARD_LIST(state, payload) {
            state.bestBoardList = payload;
        },
        SET_BOARD(state, payload) {
            state.board = payload;
        },
        SET_KEYWORD(state, payload) {
            state.keyword = payload;
        },
        SET_REPLY_LIST(state, payload) {
            state.replyList = payload;
        },

        setKeyword(state, payload) {
            state.keyword = payload;
        },
        setTokenInLocal(state, payload) {
            console.log("setToken")
            console.log(state)
            console.log(payload);
            state.token = payload.token;
            state.username = payload.username;
            state.likeFlag = false;
            localStorage.setItem("access_token", "Bearer " + payload.token);
            router.push('/board');
        },
        deleteTokenInLocal(state) {
            state.token = "";
            state.username = "";
            localStorage.removeItem("access_token");
        },
        startBoardList(state, payload) {
            console.log(state);
            console.log(payload);
            state.boardList = payload.data;
        },
        getBoard(state, payload) {
            console.log("payload : " + payload);
            state.board = payload;
        },
        SET_SNACK_BAR(state, payload) {
            state.snackbar.open = true;
            state.snackbar.text = payload.msg;
            state.snackbar.color = payload.color;
        },
        setSnackBar(state, info) {
            state.snackbar.open = true;
            state.snackbar.text = info.msg
            state.snackbar.color = info.color;

        },
        getRepliesByBoardId(state, payload) {
            console.log(payload);
            state.replies = payload.data;
        },
        setReplyContent(state, payload) {
            state.replyContent = payload.data;
        }


    },
    actions: {
        async REQUEST_JOIN(context, payload) {
            const response = await member_api.requestJoin(payload);
            if (response) {
                context.commit('SET_SNACK_BAR', {
                    msg: '정상적으로 가입 되었습니다.', color: 'success'
                });
            }
        },
        async REQUEST_LOGIN(context, payload) {
            const response = await member_api.requestLogin(payload);
            if (response) {
                context.commit('LOGIN', response.data);
                context.commit('SET_SNACK_BAR', {
                    msg: response.data.username + '님 환영합니다.', color: 'success'
                })
            }
        },
        async REQUEST_LOGOUT(context) {
            context.commit('LOGOUT')
            context.commit('SET_SNACK_BAR', {
                msg: '로그아웃 되었습니다.', color: 'success'
            })
        },
        async REQUEST_BOARD_LIST(context, payload) {
            const response = await board_api.getAllBoardsByKeyword(payload);
            console.log(payload + "~~");
            if (response) {
                context.commit('SET_KEYWORD', payload);
                context.commit('SET_BOARD_LIST', response.data);
            }
        },
        async REQUEST_BEST_BOARDS(context) {
            const response = await board_api.getBests();
            console.log(response.data);
            if (response) {
                context.commit('SET_BOARD_LIST', response.data);
            }
        },

        async REQUEST_BOARD(context, payload) {
            const response = await board_api.getBoard(payload);
            console.log(payload);
            console.log(response.data);
            if (response) {
                context.commit('SET_BOARD', response.data);
            }
        },
        async REQUEST_CREATE_BOARD(context, payload) {
            const response = await board_api.createBoard(payload);
            if (response) {
                context.commit('SET_SNACK_BAR', {
                    msg: '게시글을 등록했습니다.', color: 'info'
                })
                router.replace({path: '/board'});
            }
        },
        async REQUEST_DELETE_BOARD(context, payload) {
            const response = await board_api.deleteBoard(payload);
            if (response) {
                context.commit('SET_SNACK_BAR', {
                    msg: '게시글을 삭제했습니다.', color: 'info'
                })
            }
        },
        async REQUEST_UPDATE_BOARD(context, payload) {
            const response = await board_api.updateBoard(payload);
            if (response) {
                const boardResponse = await board_api.getBoard(payload.boardId);
                console.log(boardResponse.data);
                context.commit('SET_BOARD', boardResponse.data);
                context.commit('SET_SNACK_BAR', {
                    msg: '게시글을 수정했습니다.', color: 'info'
                })
            }
        },
        async REQUEST_GET_REPLIES(context, payload) {
            const response = await reply_api.getReplies(payload);
            console.log(response.data);
            if (response) {
                store.commit('SET_REPLY_LIST', response.data);
            }
        },
        async REQUEST_CREATE_REPLY(context, payload) {
            console.log(payload);
            const response = await reply_api.createReply(payload);
            if (response) {
                const replyResponse = await reply_api.getReplies(payload.boardId);
                context.commit('SET_REPLY_LIST', replyResponse.data);
                context.commit('SET_SNACK_BAR', {
                    msg: '댓글이 추가되었습니다.', color: 'info'
                })
            }

        },

        async REQUEST_DELETE_REPLY(context, payload) {
            const response = await reply_api.deleteReply(payload.replyId);
            if (response) {
                const replyResponse = await reply_api.getReplies(payload.boardId);
                context.commit('SET_REPLY_LIST', replyResponse.data);
                context.commit('SET_SNACK_BAR', {
                    msg: '댓글이 삭제되었습니다.', color: 'info'
                })
            }
        },

        async REQUEST_UPDATE_REPLY(context, payload) {
            const response = await reply_api.updateReply(payload);
            console.log(payload);
            if (response) {
                const replyResponse = await reply_api.getReplies(payload.boardId);
                context.commit('SET_REPLY_LIST', replyResponse.data);
                context.commit('SET_SNACK_BAR', {
                    msg: '댓글이 수정되었습니다.', color: 'info'
                })
            }
        },
        async REQUEST_LIKE(context, payload) {
            const response = await like_api.pushLike(payload);
            if (response) {
                context.commit('SET_SNACK_BAR', {
                    msg: response.data, color: 'info'
                });
            }

        }

    }

});

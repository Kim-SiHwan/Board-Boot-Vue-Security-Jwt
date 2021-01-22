import Vue from "vue";
import Vuex from "vuex";
import member from "./src/store/member"

Vue.use(Vuex);

export const store = new Vuex.Store({
    // ...
    modules:{
        memberStore : member
    }/*,
    state:{
      token: localStorage.getItem("access_token")
    },
    getters:{
      isAuthenticated(state){
          return !!state.token;
      }
    },
    mutations:{
        setTokenInLocal(state,payload){
            console.log(payload);
            state.token=payload;
            localStorage.setItem("access_token","Bearer "+payload);
        },
        deleteTokenInLocal(state){
            state.token="";
            localStorage.removeItem("access_token");
        }
    }*/

});

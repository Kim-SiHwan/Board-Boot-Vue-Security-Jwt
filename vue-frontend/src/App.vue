<template>
  <v-app >

    <v-app-bar
        absolute
        color="gray"
        dark
    >

      <v-toolbar-title>
        <router-link :to="{path:'/board'}"
                     style="color: white; text-decoration: none">
          Kim's Board
        </router-link>
      </v-toolbar-title>

      <v-spacer></v-spacer>
      <v-spacer></v-spacer>

      <v-btn  class="pr-15" v-if="!$store.getters.isAuthenticated">
        <router-link to="/login"
                     style="color: white; text-decoration: none">로그인</router-link>
      </v-btn>

      <v-btn  class="pr-15 pt-4" v-else @click="logout" >
        <p style="color: white; text-decoration: none">로그아웃</p>
      </v-btn>
      <v-btn >
        <router-link to="/join"
                     style="color: white; text-decoration: none">회원가입</router-link>
      </v-btn>
      <v-btn class="pr-15 pt-4">
        <router-link :to="{path:'/board',query:{best:'Y'}}"
                     style="color: white; text-decoration: none">
          인기게시판
        </router-link>
      </v-btn>


    </v-app-bar>

    <v-container style="width: 1200px; padding-top: 120px">
      <v-snackbar
          v-model="$store.state.snackbar.open"
          :color="$store.state.snackbar.color"
          :timeout="$store.state.snackbar.timeout">
        {{$store.state.snackbar.text}}
      </v-snackbar>
      <router-view></router-view>

    </v-container>

  </v-app>
</template>

<script>


export default {
  name: 'App',

  components: {
  },

  data: () => ({
    //
  }),
  methods:{
    logout(){
      this.$store.dispatch('REQUEST_LOGOUT');
    },
    bests(){
      this.$store.dispatch('REQUEST_BEST_BOARDS');
    }
  }
};
</script>

<template>
  <v-app>

    <v-app-bar
        absolute
        color="gray"
        dark
    >

      <v-toolbar-title>
        <router-link :to="{path:'/board'}"
                     style="color: white; text-decoration: none">
          <v-btn @click="origin">Kim's Board</v-btn>
        </router-link>
      </v-toolbar-title>

      <v-spacer></v-spacer>
      <v-spacer></v-spacer>
      <v-btn v-if="!$store.getters.isAuthenticated" class="mr-15">
        <router-link style="color: white; text-decoration: none"
                     to="/login">로그인
        </router-link>
      </v-btn>

      <v-btn v-else class="mr-15 pt-4" @click="logout">
        <p style="color: white; text-decoration: none">로그아웃</p>
      </v-btn>
      <router-link style="color: white; text-decoration: none"
                   to="/join">
        <v-btn>
          회원가입
        </v-btn>

      </router-link>

      <router-link :to="{path:'/board',query:{best:'Y'}}"
                   style="color: white; text-decoration: none">
        <v-btn class="ml-15"
               @click="bests">
          인기게시판
        </v-btn>

      </router-link>


    </v-app-bar>

    <v-snackbar
        v-model="$store.state.snackbar.open"
        :color="$store.state.snackbar.color"
        :timeout="$store.state.snackbar.timeout">
      {{ $store.state.snackbar.text }}
    </v-snackbar>

    <router-view style="margin-top: 70px"></router-view>


  </v-app>
</template>

<script>


export default {
  name: 'App',

  components: {},


  data: () => ({
    //
  }),
  methods: {
    logout() {
      this.$store.dispatch('REQUEST_LOGOUT');
    },
    bests() {
      this.$store.dispatch('REQUEST_BEST_BOARDS');
    },
    origin() {
      this.$store.dispatch('REQUEST_BOARD_LIST', '');
    }
  }
};
</script>

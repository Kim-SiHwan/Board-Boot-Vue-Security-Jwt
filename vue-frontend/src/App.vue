<template>
  <v-app >
<!--
    <v-app-bar
        absolute
        color="gray"
        dark
    >
      <v-app-bar-nav-icon></v-app-bar-nav-icon>

      <v-toolbar-title>Title</v-toolbar-title>

      <v-spacer></v-spacer>
      <router-link to="/join">      <v-btn icon>
        회원가입
      </v-btn></router-link>
      <router-link to="/sample">      <v-btn icon>
        로그인
      </v-btn></router-link>



      <v-btn icon>
        <v-icon>mdi-magnify</v-icon>
      </v-btn>

      <v-btn icon>
        <v-icon>mdi-heart</v-icon>
      </v-btn>

      <v-btn icon>
        <v-icon>mdi-dots-vertical</v-icon>
      </v-btn>
    </v-app-bar>-->

    <v-container style="width: 1200px">
      <router-link to="/join">go Board</router-link>
      <router-link to="/sample">go SAMPLE</router-link>

      <router-link to="/board">go Board</router-link>
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
};
</script>

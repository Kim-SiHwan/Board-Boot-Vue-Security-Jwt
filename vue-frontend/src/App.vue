<template>
  <v-app>
    <h1>??</h1>
    <router-link to="/join">go Join</router-link>
    <router-link to="/sample">go Sample</router-link>
    <router-link to="/board">go Board</router-link>
    <v-snackbar
      v-model="$store.state.snackbar.open"
      :color="$store.state.snackbar.color"
      :timeout="$store.state.snackbar.timeout">
      {{$store.state.snackbar.text}}
    </v-snackbar>
    <router-view></router-view>

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

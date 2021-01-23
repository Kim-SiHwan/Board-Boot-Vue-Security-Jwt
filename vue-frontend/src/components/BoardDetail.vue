<template>
  <v-app>
    <v-container>
      {{$route.query.boardId}}
      {{board.data}}
    </v-container>
  </v-app>

</template>

<script>
export default {
  name: "BoardDetail",
  data(){
    return{

    }
  },
  computed:{
    board(){
      return this.$store.state.board;
    }
  },
  created() {
    this.$store.dispatch('REQUEST_BOARD',this.$route.query.boardId);
  }


}
</script>

<style scoped>

</style>
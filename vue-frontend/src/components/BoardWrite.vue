<template>
  <v-app>
    <v-container>
      <v-text-field
          v-model="board.title"
      >

      </v-text-field>
      <v-textarea
        v-model="board.content">

      </v-textarea>

      <v-btn @click="createBoard">글쓰기</v-btn>
    </v-container>
  </v-app>

</template>

<script>
export default {
  name: "BoardWrite",
  data(){
    return{
      board:{
        username:'',
        title:'',
        content:''
      },
      boardId:''
    }
  },
  methods:{
    createBoard(){
      this.$store.dispatch('REQUEST_CREATE_BOARD',this.board);
      this.$router.replace({path:'/board'});
    }
  },
  created() {
    this.board.username= this.$store.state.username;
  }
}
</script>

<style scoped>

</style>
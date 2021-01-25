<template>
  <v-app>
    <v-container>
      <v-text-field
          v-model="board.title"
          :rules="rules.titleRules"

          label="제목"
      >

      </v-text-field>
      <v-textarea
          :rules="rules.contentRules"
          v-model="board.content"
          label="내용"
          no-resize

          outlined
          rows="30"
      >

      </v-textarea>
      <v-textarea
          :rules="rules.contentRules"
          v-model="board.content">

      </v-textarea>

      <v-btn @click="createBoard">글쓰기</v-btn>

    </v-container>
  </v-app>

</template>

<script>
export default {
  name: "BoardWrite",
  data() {
    return {
      board: {
        username: '',
        title: '',
        content: ''
      },
      boardId: '',
      rules: {
        titleRules: [],
        contentRules: []
      }
    }
  },
  methods: {
    createBoard() {
      this.$store.dispatch('REQUEST_CREATE_BOARD', this.board);
      this.$router.replace({path: '/board'});
    },
    setRules() {

      this.rules.titleRules = [
        v => !!v || '제목을 입력해주세요.',
        v => v.length <= 20 || '20자 이내로 작성해주세요.',
      ],
          this.rules.contentRules = [
            v => !!v || '내용을 입력해주세요.',
            v => v.length <= 500 || '500자 이내로 작성해주세요.',

          ]
    }
  },

  created() {
    this.board.username = this.$store.state.username;
    this.setRules();
  }
}
</script>

<style scoped>

</style>
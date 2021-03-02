<template>
  <v-app>
    <v-container>
      <v-card
          min-height="1000">

        <v-card-title v-if="!update.flag">{{ board.title }}<small>({{ board.replyCount }})</small></v-card-title>
        <v-text-field
            v-if="update.flag"
            v-model="update.updateTitle"
            label="수정할 제목을 입력하세요."
            v-bind:placeholder="board.title">

        </v-text-field>
        <v-card-text v-if="board">
          작성자 : {{ board.username }}<br>
          조회수 : {{ board.read }}<br>
          작성일 : {{ board.createDate.substring(0, 10) }}
        </v-card-text>
        <v-divider class="mx-4"></v-divider>
        <v-card-subtitle v-if="!update.flag" class="font-weight-bold">
          {{ board.content }}
        </v-card-subtitle>
        <v-textarea
            v-if="update.flag"
            v-model="update.updateContent"
            label="수정할 내용을 입력하세요."
            outlined
            rows="24"
            v-bind:placeholder="board.content">

        </v-textarea>

      </v-card>

      <div v-if="this.$store.getters.isAuthenticated && (this.username == board.username || this.username=='admin')"
           class="float-right">
        <v-btn
            v-if="update.flag"
            color="success"
            @click="updateBoardSubmit"
        >
          수정
        </v-btn>
        <v-btn
            v-if="!update.flag"
            color="primary"
            @click="updateBoard"
        >
          수정
        </v-btn>
        <v-btn
            v-else
            color="warning"
            @click="updateBoard">
          취소
        </v-btn>
        <v-btn
            color="error"
            @click="deleteBoard"
        >
          삭제
        </v-btn>
      </div>

      <br><br><br>
      <v-btn
          class="ma-2"
          color="blue lighten-2"
          icon
          text
          @click="pushLike"
      >
        <v-icon>mdi-thumb-up</v-icon>
      </v-btn>
      {{ board.likeCount }}
      <hr>
      <reply></reply>
    </v-container>
  </v-app>

</template>

<script>

import Reply from "@/components/Reply";

export default {
  name: "BoardDetail",
  components: {
    'reply': Reply
  },

  data() {
    return {
      boardId: '',
      update: {
        flag: false,
        boardId: '',
        updateTitle: '',
        updateContent: ''
      },
      likeDto: ''


    }
  },
  methods: {

    updateBoard() {
      this.update.flag = !this.update.flag;
    },
    deleteBoard() {
      this.$store.dispatch('REQUEST_DELETE_BOARD', this.boardId);
    },
    updateBoardSubmit() {
      this.update.boardId = this.boardId;
      this.$store.dispatch('REQUEST_UPDATE_BOARD', this.update);
      this.update.flag = false;

    },
    pushLike() {
      if (this.username === '') {
        this.$store.commit('setSnackBar',
            {msg: '로그인이 필요한 서비스입니다.', color: 'warning'}
        );
        return false;
      }
      this.likeDto = {
        boardId: this.boardId,
        username: this.$store.state.username
      }
      this.$store.dispatch('REQUEST_LIKE', this.likeDto);
    }

  },
  computed: {
    board() {
      return this.$store.state.board;
    },
    username() {
      return this.$store.state.username;
    }

  },
  created() {
    this.update.id = this.$route.query.boardId;
    this.boardId = this.$route.query.boardId;
    this.$store.dispatch('REQUEST_BOARD', this.boardId);

  }

}
</script>

<style scoped>

</style>
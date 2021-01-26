<template>
  <v-app>
    <v-container style="width: 1200px">

      <v-card
          min-height="1000">

        <v-card-title v-if="!update.flag">{{ board.data.title }}<small>({{ board.data.replyCount }})</small></v-card-title>
        <v-text-field
          v-if="update.flag"
          label="수정할 제목을 입력하세요."
          v-bind:placeholder="board.data.title"
          v-model="update.updateTitle">

        </v-text-field>
        <v-card-text>
          작성자 : {{ board.data.username }}<br>
          조회수 : {{ board.data.read }}<br>
          작성일 : {{ board.data.createDate.substring(0, 10) }}
        </v-card-text>
        <v-divider class="mx-4"></v-divider>
        <v-card-subtitle class="font-weight-bold" v-if="!update.flag">
          {{ board.data.content }}
        </v-card-subtitle>
        <v-textarea
            v-if="update.flag"
            outlined
            rows="24"
            label="수정할 내용을 입력하세요."
            v-bind:placeholder="board.data.content"
            v-model="update.updateContent">

        </v-textarea>

      </v-card>

      <div class="float-right" v-if="this.$store.getters.isAuthenticated && (this.$store.state.username == board.data.username || this.$store.state.username=='admin')">
        <v-btn
          v-if="update.flag"
          @click="updateBoardSubmit"
          color="success"
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
          text
          icon
          color="blue lighten-2"
          @click="pushLike"
      >
        <v-icon>mdi-thumb-up</v-icon>
      </v-btn>
      {{board.data.likeCount}}
      <hr>
      <reply></reply>
    </v-container>
  </v-app>

</template>

<script>

import Reply from "@/components/Reply";

export default {
  name: "BoardDetail",
  components:{
    'reply':Reply
  },

  data() {
    return {
      update:{
        flag:false,
        id:'',
        updateTitle:'',
        updateContent:''
      },
      likeDto:''


    }
  },
  methods: {

    updateBoard(){
      this.update.flag = !this.update.flag;
    },
    deleteBoard(){
      this.$store.dispatch('REQUEST_DELETE_BOARD',this.$route.query.boardId);
    },
    updateBoardSubmit(){
      this.$store.dispatch('REQUEST_UPDATE_BOARD',this.update);
      this.updateBoard();
      this.update.updateContent='';
      this.update.updateTitle='';
      this.$store.dispatch('REQUEST_BOARD',this.$route.query.boardId);

    },
    pushLike(){
      if(this.$store.state.username==''){
        this.$store.commit('setSnackBar',
            {msg:'로그인이 필요한 서비스입니다.', color:'warning'}
        );
        return false;
      }
      this.likeDto={
        boardId: this.$route.query.boardId,
        username: this.$store.state.username
      }
      this.$store.dispatch('REQUEST_LIKE',this.likeDto);
    }

  },
  computed: {
    board() {
      return this.$store.state.board;
    },

  },
  created() {
    this.update.id=this.$route.query.boardId;
    this.$store.dispatch('REQUEST_BOARD', this.$route.query.boardId);

  }

}
</script>

<style scoped>

</style>
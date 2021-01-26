<template>
  <v-app>
    <v-container>

      <v-data-table
          id="replyDiv"
          :headers="headers"
          :items="replies"
          :items-per-page="itemsPerPage"
          :page.sync="page"
          class="elevation-1"
          no-data-text="첫 댓글을 작성해보세요!"
          @page-count="pageCount= $event">

        <template v-slot:item="replies">
          <tr>

            <td width="500">

              <v-textarea
                  no-resize
                  outlined
                  class="mt-5"
                  readonly="readonly"
                  rows="4"
                  v-bind:value="replies.item.content"
                  v-bind:ref="replies.item.id"
              >
              </v-textarea>
            </td>
            <td width="100">{{ replies.item.username }}</td>
            <td width="100">{{ replies.item.createDate.substring(0, 10) }}</td>
            <td width="100">

              <v-icon

                  small
                  class="mr-2"
                  color="blue"
                  @click="updateReplyForm(replies.item.id,replies.item.username)">
                mdi-pencil
              </v-icon>
              <v-icon
                  small
                  color="red"
                  @click="deleteReply(replies.item.id,replies.item.username)"
              >
                mdi-delete
              </v-icon>
            </td>
          </tr>
        </template>

      </v-data-table>
      <v-pagination
          v-model="page"
          :length="pageCount">

      </v-pagination>

      <v-textarea
          v-model="reply.content"
          label="Content"
          no-resize
          outlined
          rows="3"
          v-on:keyup.enter="addReply">

      </v-textarea>


      <v-btn
          @click="addReply"
          color="primary"
          class="float-right">
        <v-icon dark>
          mdi-pencil
        </v-icon>
      </v-btn>


      <v-row justify="center">
        <v-dialog v-model="dialog" persistent max-width="450">
          <v-card>
            <v-card-title class="headline">댓글 수정</v-card-title>
            <v-textarea
                style="width: 90%; margin-left: 17px"
                v-model="updateReply.replyUpdateContent"
                label="수정할 내용을 입력해주세요."
                no-resize
                outlined
                rows="4"
                v-bind:placeholder="replyContent.content"
                >

            </v-textarea>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="updateReplySubmit(replyContent.id)">수정하기</v-btn>
              <v-btn color="red darken-1" text @click="dialog = false">취소하기</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-row>
    </v-container>
  </v-app>

</template>

<script>
export default {
  name: "Reply",
  data(){
    return{
      ids:'',
      dialog: false,
      page: 1,
      pageCount: 0,
      itemsPerPage: 12,
      reply: {
        username: '',
        content: ''
      },
      updateReply:{
        flag:false,
        boardId:'',
        replyUpdateContent: ''
      },
      headers: [
        {
          text: '내용',
          align: 'start',
          sortable: false
        },
        {
          text: '글쓴이'
        },
        {
          text: '작성일'
        },
        {
          text: '수정/삭제'
        }
      ]
    }
  },
  methods:{
    addReply() {
      if(this.reply.username==''){
        this.$store.commit('setSnackBar',
            {msg:'로그인이 필요한 서비스입니다.', color:'warning'}
        );
        return false;
      }
      this.$store.dispatch('REQUEST_CREATE_REPLY', {
        boardId: this.$route.query.boardId,
        reply: this.reply
      });
      this.reply.content=''
      window.location.href='#replyDiv';
    },
    updateReplyForm(replyId,username){
      if(this.reply.username != username){
        this.$store.commit('setSnackBar',
            {msg:'작성자만 수정할 수 있습니다.', color:'error'}
        );
        return false;
      }

      this.dialog=true;
      this.ids={
        id:replyId,
        boardId:this.$route.query.boardId
      }
      this.$store.dispatch('REQUEST_REPLY_FOR_UPDATE',this.ids);
      console.log(replyId);
    },
    updateReplySubmit(replyId){
      this.updateReply={
        id:replyId,
        boardId:this.$route.query.boardId,
        replyUpdateContent:this.updateReply.replyUpdateContent
      }
      this.$store.dispatch('REQUEST_UPDATE_REPLY',this.updateReply);
      this.dialog=false;
      this.updateReply.replyUpdateContent='';

    },
    deleteReply(replyId,username){
      if(this.reply.username=="admin" || this.reply.username == username){
        this.ids={
          id:replyId,
          boardId:this.$route.query.boardId
        }
        this.$store.dispatch('REQUEST_DELETE_REPLY',this.ids);
      }else{
        this.$store.commit('setSnackBar',
            {msg:'작성자만 삭제할 수 있습니다.', color:'error'}
        );
        return false;
      }

    },

  },
  computed:{
    replies() {
      return this.$store.state.replies;
    },
    replyContent(){
      return this.$store.state.replyContent;
    }
  },
  created() {
    this.reply.username = this.$store.state.username;
    this.$store.dispatch('REQUEST_GET_REPLIES', this.$route.query.boardId)

  }
}
</script>

<style scoped>

</style>
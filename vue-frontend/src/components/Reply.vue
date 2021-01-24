<template>
  <v-app>
    <v-container>
      <v-data-table
          :headers="headers"
          :items="replies"
          :items-per-page="itemsPerPage"
          :page.sync="page"
          class="elevation-1"
          no-data-text="첫 댓글을 작성해보세요!"
          @page-count="pageCount= $event"

      >

        <template v-slot:item="replies">
          <tr>
            <td width="500">

              <v-textarea
                  no-resize
                  outlined
                  readonly="readonly"
                  rows="4"
                  style="overflow: auto"
                  v-bind:value="replies.item.content"
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
                  @click="updateReplyForm(replies.item.id)">
                mdi-pencil
              </v-icon>
              <v-icon
                  small
                  color="red"
                  @click="deleteReply(replies.item.id)"
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
      >

      </v-textarea>



      <button @click="addReply">addReply</button>

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
      itemsPerPage: 3,
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
      this.$store.dispatch('REQUEST_CREATE_REPLY', {
        boardId: this.$route.query.boardId,
        reply: this.reply
      });
    },
    updateReplyForm(){
      this.updateReply.flag=true;
    },
    updateReplySubmit(replyId){
      this.updateReply={
        id:replyId,
        updateContent:this.updateReply.replyUpdateContent
      }
      this.$store.dispatch('REQUEST_UPDATE_REPLY',this.updateReply);
    },
    deleteReply(replyId){
      this.ids={
        id:replyId,
        boardId:this.$route.query.boardId
      }
      this.$store.dispatch('REQUEST_DELETE_REPLY',this.ids);
    },

  },
  computed:{
    replies() {
      return this.$store.state.replies;
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
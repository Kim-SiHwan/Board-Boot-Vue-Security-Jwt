<template>
  <v-app>
    <v-container>
      <p v-if="replyList">{{ replyList.length }}개의 댓글이 달렸습니다</p>

      <v-data-table
          id="replyDiv"
          :headers="headers"
          :items="replyList"
          :items-per-page="itemsPerPage"
          :page.sync="page"
          class="elevation-1"
          hide-default-footer
          hide-default-header
          no-data-text="첫 댓글을 작성해보세요!"
          @page-count="pageCount= $event">

        <template v-slot:item="replyList">
          <tr>

            <td width="500">

              <v-textarea
                  v-bind:ref="replyList.item.id"
                  class="mt-5"
                  no-resize
                  outlined
                  readonly="readonly"
                  rows="4"
                  v-bind:value="replyList.item.content"
              >
              </v-textarea>
            </td>
            <td width="100">{{ replyList.item.username }}</td>
            <td width="100">{{ replyList.item.createDate.substring(0, 10) }}</td>
            <td width="100">

              <v-icon

                  class="mr-2"
                  color="blue"
                  small
                  @click="updateReplyForm({
                      commentId : replyList.item.id,
                      boardId : boardId,
                      originReplyContent : replyList.item.content ,
                      username : replyList.item.username

                  })">
                mdi-pencil
              </v-icon>
              <v-icon
                  color="red"
                  small
                  @click="deleteReply(replyList.item.id,replyList.item.username)"
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
          v-model="replyForm.content"
          label="Content"
          no-resize
          outlined
          rows="3"
          v-on:keyup.enter="addReply"
      >

      </v-textarea>


      <v-btn
          class="float-right"
          color="primary"
          @click="addReply">
        <v-icon dark>
          mdi-pencil
        </v-icon>
      </v-btn>


      <v-row justify="center">
        <v-dialog v-model="dialog" max-width="450" persistent>
          <v-card>
            <v-card-title class="headline">댓글 수정</v-card-title>
            <v-textarea
                v-model="updateForm.content"
                label="수정할 내용을 입력해주세요."
                no-resize
                outlined
                rows="4"
                style="width: 90%; margin-left: 17px"
                v-bind:placeholder="updateForm.originReplyContent">

            </v-textarea>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="updateReplySubmit">수정하기</v-btn>
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
  data() {
    return {
      ids: '',
      dialog: false,
      boardId: '',
      page: 1,
      pageCount: 0,
      itemsPerPage: 12,
      replyForm: {
        boardId: '',
        username: '',
        content: ''
      },
      updateForm: {
        boardId: '',
        replyId: '',
        originReplyContent: '',
        username: '',
        content: ''
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
  methods: {
    addReply() {
      if (!this.$store.getters.isAuthenticated) {
        this.$store.commit('SET_SNACK_BAR',
            {msg: '로그인이 필요한 서비스입니다.', color: 'warning'}
        );
        return false;
      }
      this.replyForm = {
        boardId: this.boardId,
        username: this.username,
        content: this.replyForm.content
      }
      this.$store.dispatch('REQUEST_CREATE_REPLY', this.replyForm);
      this.replyForm.content = ''
    },
    updateReplyForm(updateData) {
      if (updateData.username !== this.username) {
        this.$store.commit('SET_SNACK_BAR',
            {msg: '작성자만 수정할 수 있습니다.', color: 'error'}
        );
        return false;
      }
      this.updateForm = {
        replyId: updateData.commentId,
        boardId: updateData.boardId,
        username: updateData.username,
        originReplyContent: updateData.originReplyContent
      };
      this.dialog = true;

    },
    updateReplySubmit() {

      this.$store.dispatch('REQUEST_UPDATE_REPLY', this.updateForm);
      this.dialog = false;
      this.updateForm = ''

    },
    deleteReply(replyId, username) {
      if (this.username !== username) {
        this.$store.commit('SET_SNACK_BAR',
            {msg: '작성자만 삭제할 수 있습니다.', color: 'error'}
        );
        return false;
      }
      this.idList = {
        replyId: replyId,
        boardId: this.boardId
      }
      this.$store.dispatch('REQUEST_DELETE_REPLY', this.idList);

    },

  },
  computed: {
    replyList() {
      return this.$store.state.replyList;
    },
    username() {
      return this.$store.state.username;
    }

  },
  created() {
    this.boardId = this.$route.query.boardId;
    this.$store.dispatch('REQUEST_GET_REPLIES', this.boardId)
    console.log(this.replyList);
  }
}
</script>

<style scoped>

</style>
<template>
  <v-app>
    <v-container>
      <v-data-table
          :headers="headers"
          :items="boardList"
          :items-per-page="itemsPerPage"
          :page.sync="page"
          class="elevation-1"
          hide-default-footer
          @page-count="pageCount= $event"
      >
        <template v-slot:item="boardList">
          <tr>
            <td width="750">
              <router-link :to="{path:'/detail',query:{boardId:boardList.item.id}}"
                           style="color: black; text-decoration: none">{{ boardList.item.title }}
              </router-link>
              <small>({{ boardList.item.replyCount }})</small></td>

            <td width="200">{{ boardList.item.username }}</td>
            <td align="center" width="150">{{ boardList.item.likeCount }}</td>
            <td align="center" width="150">{{ boardList.item.read }}</td>
            <td v-if="boardList.item.createDate" width="200">{{ boardList.item.createDate.substring(0, 10) }}</td>
          </tr>
        </template>

      </v-data-table>
      <v-pagination
          v-model="page"
          :length="pageCount">

      </v-pagination>

      <router-link v-if="$store.getters.isAuthenticated" to="/write">
        <v-btn
            class="float-right"
            color="primary">
          <v-icon dark>
            mdi-pencil
          </v-icon>
        </v-btn>
      </router-link>

      <v-chip
          v-if="keyword!==''"
          close
          color="blue"
          text-color="white"
          @click:close="removeFilter">
        {{ keyword }}
      </v-chip>

      <v-text-field
          v-model="searchKeyword"

          label="검색어 입력"
          prepend-icon="mdi-magnify"
          v-on:keyup.enter="searchBoard">

      </v-text-field>
    </v-container>

  </v-app>
</template>

<script>
export default {
  name: "Board",
  data() {
    return {
      page: 1,
      pageCount: 0,
      itemsPerPage: 20,
      searchKeyword: '',
      headers: [
        {
          text: '제목',
          align: 'start',
          sortable: false
        },
        {
          text: '글쓴이'
        },
        {
          text: '추천수'
        },
        {
          text: '조회수'
        },
        {
          text: '작성일'
        }
      ]
    }
  },
  methods: {

    removeFilter() {
      this.$store.dispatch('REQUEST_BOARD_LIST', '');
    },
    getBoards() {
      this.$store.dispatch('REQUEST_BOARD_LIST', this.keyword);
    },
    getBestBoards() {
      this.$store.dispatch('REQUEST_BEST_BOARDS');
    },
    searchBoard() {
      this.$store.dispatch('REQUEST_BOARD_LIST', this.searchKeyword);
    }
  },
  created() {
    if (this.$route.query.best) {
      this.getBestBoards();
      return;
    }
    this.getBoards();
  },
  computed: {
    boardList() {
      return this.$store.state.boardList;
    },
    keyword() {
      return this.$store.state.keyword;
    },

  }
}
</script>

<style scoped>

</style>
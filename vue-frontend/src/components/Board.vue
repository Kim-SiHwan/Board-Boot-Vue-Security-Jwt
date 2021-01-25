<template>
  <v-app>
    <v-container style="width: 1200px">
      <v-data-table
          :headers="headers"
          :items="boardList"
          :page.sync="page"
          :items-per-page="itemsPerPage"
          @page-count="pageCount= $event"
          class="elevation-1"
      >
        <template v-slot:item="boardList">
          <tr>
            <td width="800" ><router-link :to="{path:'/detail',query:{boardId:boardList.item.id}}" style="color: black; text-decoration: none">{{boardList.item.title}}</router-link> <small>({{boardList.item.replyCount}})</small></td>

            <td width="200">{{boardList.item.username}}</td>
            <td width="150" align="center">{{boardList.item.read}}</td>
            <td width="200">{{boardList.item.createDate.substring(0,10)}}</td>
          </tr>
        </template>

      </v-data-table>
      <v-pagination
          v-model="page"
          :length="pageCount">

      </v-pagination>

      <router-link to="/write" v-if="$store.getters.isAuthenticated">
        <v-btn
            color="primary"
            class="float-right">
          <v-icon dark>
            mdi-pencil
          </v-icon>
        </v-btn>
      </router-link>

    </v-container>

  </v-app>
</template>

<script>
export default {
  name: "Board",
  data(){
    return{
      page:1,
      pageCount:0,
      itemsPerPage:20,

      headers:[
        {
          text:'제목',
          align:'start',
          sortable:false
        },
        {
          text:'글쓴이'
        },
        {
          text:'조회수'
        },
        {
          text:'작성일'
        }
      ]
    }
  },
  created() {
    this.$store.dispatch('REQUEST_BOARD_LIST')
  },
  computed:{
    boardList(){
      return this.$store.state.boardList;
    }
  }
}
</script>

<style scoped>

</style>
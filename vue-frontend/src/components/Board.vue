<template>
  <v-app>
    <v-container>
      <v-data-table
          :headers="headers"
          :items="boardList"
          class="elevation-1"
      >
        <template v-slot:item="boardList">
          <tr>
            <td width="300"><router-link :to="{path:'/detail',query:{boardId:boardList.item.id}}">{{boardList.item.title}}</router-link></td>

            <td>{{boardList.item.username}}</td>
            <td width="100" align="center">{{boardList.item.read}}</td>
            <td>{{boardList.item.createDate.substring(0,10)}}</td>
          </tr>
        </template>

      </v-data-table>

      <button @click="getBoard">asdd</button>
      <router-link to="/write" v-if="$store.getters.isAuthenticated">
        <v-btn
            color="primary">
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
  methods:{
    getBoard(){
      console.log(this.$store.getters.getBoardList);
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
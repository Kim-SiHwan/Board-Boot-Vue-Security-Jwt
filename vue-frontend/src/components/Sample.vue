<template>
  <v-app>
    <v-container>
      <button @click="serverCheck">Btn</button>

      <v-text-field v-model="username"></v-text-field>
      <v-text-field v-model="password"></v-text-field>

      <button @click="login">Login</button><br>
      <button @click="logout">Logout</button><br>

      <button @click="forAdmin">onlyAdmin</button><br>
      <button @click="forAll">onlyAll</button><br>

      <button @click="showToken">showToken</button><br>
      <button @click="checkAuth">checkAuth</button>

    </v-container>

  </v-app>

</template>

<script>
import axios from 'axios';


export default {
  name: "Sample",
  data(){
    return{
      username:'',
      password:'',
      loginDto:''
    }
  },
  methods:{
    serverCheck(){
      axios.get('/api/test').then(res=>{
        console.log(res.data);
      })
    },
    login(){
      this.loginDto={
        username:this.username,
        password:this.password
      }

      axios.post('http://localhost:8080/api/authenticate',this.loginDto)
          .then(res=>{
            localStorage.setItem("access_token","Bearer "+res.data.token);
            console.log(res.data.token);
          })

    },
    logout(){
      this.$store.commit('deleteTokenInLocal');
    },
    checkAuth(){

    }
    ,
    forAdmin(){
      axios.request({
        method:'GET',
        headers : {'Authorization' : localStorage.getItem("access_token")},
        url: 'http://localhost:8080/api/user/kim'

      }).then(res=>{
        console.log(res);
      })
    },
    forAll(){

      axios.request({
        method:'GET',
        headers : {'Authorization' : localStorage.getItem("access_token")},
        url: 'http://localhost:8080/api/user'

      }).then(res=>{
        console.log(res);
      })

    },
    showToken(){
      console.log(localStorage.getItem("access_token"));
    }
  }
}
</script>

<style scoped>

</style>
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
        username:"user",
        password:"user"
      }

      this.$store.dispatch('REQUEST_LOGIN',this.loginDto);

    },
    logout(){
      this.$store.dispatch('REQUEST_LOGOUT');
      this.$store.commit('setSnackBar',{msg:'로그아웃 완료', color:'success'});

    },
    checkAuth(){
      console.log(this.$store.getters.isAuthenticated);

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
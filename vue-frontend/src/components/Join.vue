<template>
  <v-app>
    <v-container>
      <v-row align="center" class="fill-height" justify="center">
        <div class=" elevation-15" style="width: 600px; height: 600px">
          <h3 class="text-center pt-15 py-3 black--text">
            회 원 가 입
          </h3>

          <v-form ref="form" class="pa-15 text-center">

            <v-text-field v-model="member.username" :rules="rules.nameRules" class=" pl-3 pr-3" label="Name"
                          prepend-icon="mdi-account" required type="text">
            </v-text-field>

            <v-text-field v-model="member.password" :rules="rules.passwordRules" class="pt-10 pl-3 pr-3" label="Password"
                          prepend-icon="mdi-lock" required type="password">
            </v-text-field>

            <v-text-field :rules="checkPasswordRules" class="pt-10 pl-3 pr-3" label="Re-enter password"
                          prepend-icon="mdi-lock"
                          required type="password"
                          @keyup.enter="requestJoin">
            </v-text-field>
            <v-btn
                color="primary"
                depressed
                large
                @click="requestJoin">
              가입하기
            </v-btn>
          </v-form>
        </div>
      </v-row>
    </v-container>
  </v-app>
</template>

<script>
export default {
  name: "Join",
  data() {
    return {
      member: {
        username: '',
        password: '',
        nickname: ''
      },
      rules: {
        emailRules: [],
        nameRules: [],
        passwordRules: []

      },

      checkPasswordRules: [
        v => v === this.member.password || '비밀번호가 다릅니다.'
      ]
    }
  },
  methods: {
    requestJoin() {
      this.$store.dispatch('REQUEST_JOIN', this.member);
    },
    setRules() {

      this.rules.passwordRules = [
        v => !!v || '비밀번호를 입력해주세요.',
        v => v.length <= 10 || '10자 이내로 작성해주세요.',
        v => v.search(/\s/) === -1 || '공백을 제거해주세요.'
      ],
          this.rules.nameRules = [
            v => !!v || '이름을 입력해주세요.',
            v => v.length <= 10 || '10자 이내로 작성해주세요.',
            v => v.search(/\s/) === -1 || '공백을 제거해주세요.'

          ]
    }
  },
  created() {
    this.setRules();
  }
}
</script>

<style scoped>

</style>
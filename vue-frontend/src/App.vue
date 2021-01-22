<template>
  <v-app>
    <sample></sample>

  </v-app>
</template>

<script>

import Sample from "@/components/Sample";

export default {
  name: 'App',

  components: {
    'sample':Sample
  },

  data: () => ({
    //
  }),
};
</script>

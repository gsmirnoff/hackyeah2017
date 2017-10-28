<template>
  <div id="root">
    <header-component></header-component>
    <main>
      <title-component></title-component>
      <techs></techs>
    </main>
    <footer-component></footer-component>
  </div>
</template>

<script>
import Header from './Header.vue';
import Title from './Title.vue';
import Techs from './techs/Techs.vue';
import Footer from './Footer.vue';

import Vue from 'vue'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'bootstrap/dist/css/bootstrap.css'

Vue.use(BootstrapVue);

export default {
  name: 'Main',
  components: {
    'header-component': Header,
    'title-component': Title,
    'techs': Techs,
    'footer-component': Footer
  }
};
</script>

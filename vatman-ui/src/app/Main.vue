<template>
  <div id="root">
    <header-component></header-component>
    <main>
      <techs></techs>
    </main>
    <footer-component></footer-component>
  </div>
</template>

<script>
import Header from './Header.vue';
import Techs from './techs/Techs.vue';
import Footer from './Footer.vue';

export default {
  name: 'Main',
  components: {
    'header-component': Header,
    'techs': Techs,
    'footer-component': Footer
  }
};
</script>

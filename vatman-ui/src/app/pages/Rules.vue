<template>
  <div id="root">
    <header-component></header-component>
    <main>
      rules page
    </main>
    <footer-component></footer-component>
  </div>
</template>

<script>
  import Header from '../Header.vue';
  import Footer from '../Footer.vue';

  export default {
    name: 'Rules',
    components: {
      'header-component': Header,
      'footer-component': Footer
    }
  };
</script>

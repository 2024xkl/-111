<template>
  <div id="app" >
    <router-view/>

  </div>
</template>

<script>
// 引入必要的组件
export default {
  name: 'App',
  components: {

    // 不需要额外的组件了，因为现在是通过路由渲染页面
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial , sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: -9px;
}
</style>

<template>
  <el-menu 
    text-color="#ffffff"
    active-text-color="#409EFF"
    style="height:100vh;background-color: #1a2b3c"
    default-active="/Home"
    router
  >
      <el-menu-item index="/Home">
        <i class="el-icon-s-home"></i>
        <span>首页</span>
      </el-menu-item>

      <el-menu-item :index="'/'+item.menuClick" v-for="(item,i) in menu" :key="i">
        <i :class="item.menuIcon"></i>
        <span slot="title">{{item.menuName}}</span>
      </el-menu-item>
  </el-menu>
</template>

<script>
export default {
    name: "Aside",
    data(){
      return{}
    },
    computed:{
      "menu":{
        get(){
          return this.$store.state.menu
        }
      }
    }
}
</script>

<style scoped>
.el-menu-item {
  transition: all 0.3s;
}

.el-menu-item:hover {
  background-color: #2c3e50 !important;
}

.el-menu-item.is-active {
  background-color: #2c3e50 !important;
  border-right: 3px solid #409EFF;
}

.el-menu-item i {
  color: #ffffff;
  margin-right: 5px;
}
</style>
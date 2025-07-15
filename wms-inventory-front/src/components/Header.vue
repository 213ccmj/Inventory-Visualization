<template>
  <div style="display: flex;line-height: 60px">
    <div style="flex: 1;display: flex; font-size: 30px;justify-items: center">
      <img src="../assets/logo1.png" alt="" width="40px" height="40px" style="padding: 10px 10px 10px 10px">
      <span style="color: #ffffff;font-size: 30px">库存管理系统</span>
    </div>
    <el-dropdown style="flex: 1; text-align: right;color: #ffffff;padding-right: 20px">
      <span style="cursor: pointer; padding: 10px;">{{user.name}}</span>
        <i class="el-icon-arrow-down" ></i>
        <el-dropdown-menu>
          <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
          <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
    name: "Header",
    data(){     
      return{
        user: JSON.parse(sessionStorage.getItem('CurUser')),
        
      }
    },
    methods:{
      toUser(){
        console.log("to_User")
        this.$router.push("/UserCenter")
      },
      logout(){
        console.log("logout")

        this.$confirm('确定退出登录？','提示',{
          confirmButtonText: '确定',
          type: 'warning',
          center: true,
        }).then(()=>{
          this.$message({
            type: 'success',
            message: '退出登录成功'
          })
          this.$router.push("/")
          sessionStorage.clear()
        }).catch(()=>{
          this.$message({
            type: 'info',
            message: '已取消'
          })
        }) 
      },
      
    },
    
    created(){
      this.$router.push("/Home")
    }
}
</script>

<style>
.el-header{
  padding: 0px;
  background-color: #1a2b3c;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12);
}

.el-dropdown-menu {
  background-color: #2c3e50;
}

.el-dropdown-menu__item {
  color: #ffffff;
}

.el-dropdown-menu__item:hover {
  background-color: #3a4f63;
  color: #409EFF;
}
</style>
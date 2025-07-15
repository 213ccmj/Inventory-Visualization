<template>
  <div class="login-container">
    <div class="login-box">
      <!-- 左侧图片区域 -->
      <div class="login-left">
        <img src="../assets/login-devices.png" alt="设备" class="devices-img"/>
      </div>
      <!-- 右侧登录表单区域 -->
      <div class="login-right">
        <h2 class="welcome-text">欢迎来到库存管理系统</h2>
        <el-form ref="loginForm" :model="loginForm" class="login-form">
          <el-form-item prop="num">
            <el-input v-model="loginForm.num" placeholder="请输入账号"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input type="password" v-model="loginForm.password" placeholder="请输入密码" @keyup.enter.native="login(loginForm)"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="login(loginForm)" class="login-button">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name:"Login",
  data() {
    return {
      loginForm: {
        num: '',
        password: ''
      }
    };
  },

  methods: {
    login(loginForm) {
      this.$axios.get(this.$httpUrl+'/user/login?num='+loginForm.num+"&password="+loginForm.password).then(res=>res.data).then(res=>{
           
            if(res.code==200){
                sessionStorage.setItem("CurUser",JSON.stringify(res.data.user))
                this.$store.commit("setMenu",res.data.menu)
                this.$router.replace('/index');
            }else{
              this.$message.error('账号或密码错误');
            } 
        })
    },
  },
};
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(45deg, #ff6b6b, #4ecdc4, #45b7d1, #96c93d);
  background-size: 400% 400%;
  position: relative;
  overflow: hidden;
  animation: gradientAnimation 15s ease infinite;
}

/* 添加背景动画效果 */
.login-container::before {
  content: "";
  position: absolute;
  width: 200%;
  height: 200%;
  background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
  animation: gradientBG 20s ease infinite;
  opacity: 0.15;
}

@keyframes gradientAnimation {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}

@keyframes gradientBG {
  0% {
    transform: translateX(-50%) translateY(-50%) rotate(0deg);
  }
  50% {
    transform: translateX(-50%) translateY(-50%) rotate(180deg);
  }
  100% {
    transform: translateX(-50%) translateY(-50%) rotate(360deg);
  }
}

.login-box {
  width: 750px;
  height: 420px;
  display: flex;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
  position: relative;
  z-index: 1;
  backdrop-filter: blur(10px);
  background: rgba(255, 255, 255, 0.9);
}

.login-left {
  flex: 1;
  background: linear-gradient(135deg, #8b9fe8 0%, #6c7ee1 100%);
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.devices-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.login-right {
  width: 300px;
  background: #fff;
  padding: 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.welcome-text {
  font-size: 20px;
  color: #333;
  margin-bottom: 40px;
  font-weight: 500;
  text-align: center;
}

.login-form {
  width: 100%;
  max-width: 260px;
}

.login-form .el-input input {
  height: 40px;
  border-radius: 4px;
}

.login-button {
  width: 100%;
  height: 40px;
  background: #6c7ee1;
  border: none;
  margin-top: 20px;
  font-size: 16px;
}

.login-button:hover {
  background: #8b9fe8;
}

/* 覆盖 Element UI 的默认样式 */
.el-form-item {
  margin-bottom: 20px;
}

.el-input__inner {
  border: 1px solid #e4e7ed;
}

.el-input__inner:focus {
  border-color: #6c7ee1;
}
</style>

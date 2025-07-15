<template>
<div class="user-center-container">
  <div class="user-center-header">
    <h1>个人中心</h1>
  </div>

  <div class="user-center-content">
    <el-card class="user-info-card" shadow="hover">
      <el-descriptions title='个人信息' :column="2" border>
        <el-descriptions-item>
          <template slot="label">
              <i class="el-icon-s-custom"></i>
              <span class="label-text">账号</span>
          </template>
          <span class="info-text">{{user.num}}</span>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              <span class="label-text">电话</span>
          </template>
          <span class="info-text">{{user.phone}}</span>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
              <i class="el-icon-user"></i>
              <span class="label-text">年龄</span>
          </template>
          <span class="info-text">{{user.age}}</span>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
              <i class="el-icon-user-solid"></i>
              <span class="label-text">性别</span>
          </template>
          <el-tag :type="user.sex == '1' ? 'primary' : 'danger'" effect="dark" class="custom-tag">
              <i :class="user.sex==1?'el-icon-male':'el-icon-female'"></i>
              {{user.sex==1?"男":"女"}}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
              <i class="el-icon-s-check"></i>
              <span class="label-text">角色</span>
          </template>
          <el-tag type="success" effect="dark" class="custom-tag">
            {{user.grade==0?"超级管理员":(user.grade==1?"管理员":"员工")}}
          </el-tag>
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
              <i class="el-icon-edit"></i>
              <span class="label-text">操作</span>
          </template>
          <el-button type="primary" @click="modify" class="modify-btn">
            <i class="el-icon-edit"></i> 修改信息
          </el-button>
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>

  <el-dialog
    title="修改个人信息"
    :visible.sync="centerDialogVisible"
    width="40%"
    center
    class="custom-dialog">
    <el-form ref="form" :rules="rules" :model="form" label-width="80px" class="custom-form">
      <el-form-item label="账号" prop="num">
        <el-input v-model="form.num" readonly></el-input>
      </el-form-item>
      
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" type="password"></el-input>
      </el-form-item>

      <el-form-item label="名字" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>

      <el-form-item label="年龄" prop="age">
        <el-input v-model="form.age"></el-input>
      </el-form-item>

      <el-form-item label="性别">
        <el-radio-group v-model="form.sex">
          <el-radio label="1">男</el-radio>
          <el-radio label="0">女</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="电话" prop="phone">
        <el-input v-model="form.phone"></el-input>
      </el-form-item>
    </el-form>  
    <span slot="footer" class="dialog-footer">
      <el-button @click="centerDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="doMod">确 定</el-button>
    </span>
  </el-dialog>
</div>

</template>

<script>
export default {
    name:'Home',
  data() {
    let checkAge = (rule, value, callback) => {
        if(value>150){
          callback(new Error('年龄输入过大'));
        }else{
          callback();
        }
      };
    return {
      user: {},
      centerDialogVisible: false,
        form:{
          id:'',
          num:'',
          name:'',
          password:'',
          age:'',
          phone:'',
          sex:0,
          grade:'2'
        },
        rules: {
          name: [
            { required: true, message: '请输入名字', trigger: 'blur' },
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur' }
          ],
          age: [
            { required: true, message: '请输入年龄', trigger: 'blur' },
            { min: 1, max: 3, message: '长度在 1 到 3 个字符', trigger: 'blur' },
            {pattern: /^([1-9][0-9]*){1,3}$/,message: '年龄必须为正整数字', trigger: "blur"},
            {validator: checkAge,trigger: 'blur'}
          ],
          phone: [
            { required: true, message: '手机号不能为空', trigger: 'blur' },
            { pattern:/^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: '请输入正确的手机号码', trigger: 'blur' }
          ],
        }
    };
  },
  computed:{

  },
  methods:{
    init(){
      this.user = JSON.parse(sessionStorage.getItem('CurUser'))
    },
    modify(){
        this.centerDialogVisible = true;
        this.$nextTick(()=>{
          this.form.id = this.user.id;
          this.form.num = this.user.num;
          this.form.name = this.user.name;
          this.form.password = this.user.password;
          this.form.age = this.user.age+'';
          this.form.sex = this.user.sex+'';
          this.form.phone = this.user.phone;
          this.form.grade = this.user.grade;
        })
      },
      doMod(){

        this.$refs.form.validate((valid) => {
          if (valid) {
            this.$axios.post(this.$httpUrl+'/user/mod',this.form).then(res=>res.data).then(res=>{
           
            if(res.code==200){
              this.$message({
                message: '操作成功',
                type: 'success'
              });
              sessionStorage.setItem("CurUser",JSON.stringify(this.form))
              this.centerDialogVisible = false;
              this.init()
            }else{
              this.$message.error('操作失败');
            } 
        })
          } else {
            console.log('error submit!!');
            return false;
          }
        });

      },
  },
  beforeMount(){
      this.init();
    }
};
</script>

<style>
.user-center-container {
  padding: 40px;
  background-color: #fff;
  min-height: 85vh;
}

.user-center-header {
  text-align: center;
  margin-bottom: 40px;
}

.user-center-header h1 {
  font-size: 36px;
  color: #303133;
  margin-bottom: 10px;
}

.welcome-text {
  font-size: 18px;
  color: #606266;
}

.user-center-content {
  max-width: 1000px;
  margin: 0 auto;
}

.user-info-card {
  border-radius: 8px;
  transition: all 0.3s;
}

.user-info-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.label-text {
  margin-left: 8px;
  font-weight: 500;
}

.info-text {
  font-size: 16px;
  color: #606266;
}

.custom-tag {
  font-size: 14px;
  border-radius: 4px;
}

.modify-btn {
  padding: 12px 24px;
  font-size: 14px;
  border-radius: 4px;
  transition: all 0.3s;
}

.modify-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.custom-dialog {
  border-radius: 8px;
}

.custom-form {
  padding: 20px;
}

.custom-form .el-form-item {
  margin-bottom: 25px;
}

.custom-form .el-input {
  width: 100%;
}

.el-descriptions {
  padding: 20px;
}

.el-descriptions-item__label {
  font-weight: 500;
  color: #303133;
}

.el-descriptions-item__content {
  color: #606266;
}
</style>

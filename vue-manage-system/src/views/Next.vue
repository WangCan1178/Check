<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">完善信息</div>
            <el-form :model="User" :rules="rules" label-width="0px" class="ms-content">
                <el-form-item prop="username">
                    <el-input v-model="User.username" placeholder="请输入昵称">
                        <template #prepend>
                            <i class="el-icon-user"></i>
                        </template>
                    </el-input>
                </el-form-item>
                <p style="font-size: 14px;color: #909399">请输入6-16位数字字母组合</p>
                <el-form-item prop="password">
                    <el-input v-model="User.password" placeholder="请输入密码" type="password">
                        <template #prepend>
                            <i class="el-icon-lock"></i>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password2">
                    <el-input v-model="User.password2" placeholder="请再次确认密码" type="password" :disabled="pCheck">
                        <template #prepend>
                            <i class="el-icon-unlock"></i>
                        </template>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submit()" :disabled="btn">注册</el-button>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
    import {ref, reactive,getCurrentInstance} from "vue"
    import {useRouter} from "vue-router"
    import router from "../router";
    import {ElMessage} from "element-plus";
    export default {
        name: "Next",
        data(){
            return{
                User:{
                    username:"",
                    password:"",
                    password2:"",
                },
                btn:true,
                nCheck:true,
                pCheck:true,
                pCheck2: true,
                rules:{
                    username:[
                        {
                            required: true,
                            validator: this.nameCheck,
                            trigger: "blur"
                        },
                    ],
                    password:[
                        {
                            required: true,
                            validator: this.pswCheck,
                            trigger: "blur"
                        },
                    ],
                    password2: [
                        {
                            required: true,
                            validator:this.passwordCheck,
                            trigger:"blur",
                        },
                    ],
                }
            }
        },
        computed:{
            buttonStatus(){
                let nC = this.nCheck;
                let pC = this.pCheck;
                let pC2 = this.pCheck2;
                return{
                    nC,pC,pC2
                }
            }
        },
        watch:{
            buttonStatus(value) {
                if (!value.nC && !value.pC && !value.pC2){
                    this.btn = false;
                }
            }
        },
        methods:{
            submit(){
                //TODO 存入数据库 以及callback
                this.$axios.post(
                    "http://localhost:9000/register",
                    {
                        userid:sessionStorage.getItem("phone"),
                        name:this.User.username,
                        password:this.User.password
                    }
                ).then(function (response) {
                    if(response.data === "failed"){
                        ElMessage.error("注册失败");
                    }else {
                        ElMessage.success("注册成功");
                        router.push("/login");
                    }
                }).catch(function (err) {
                    console.log(err)
                })
            },
            nameCheck(rule,value,callback){
                if (value.length<1){
                    this.nCheck= true;
                    return callback(new Error("昵称不能为空"));
                } else if(value.toString().length > 10){
                    this.nCheck= true;
                    return callback(new Error("昵称不能超过10位"));
                }else {
                    this.nCheck= false;
                    callback();
                }
            },
            pswCheck(rule,value,callback){
                const reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
                if (value.length < 6){
                    this.pCheck = true;
                    return callback(new Error("密码不能少于6位"));
                }else if (value.length > 16){
                    this.pCheck = true;
                    return callback(new Error("密码不能超过16位"));
                }else if (!reg.test(value)){
                    this.pCheck = true;
                    return callback(new Error("密码输入有误，请检查格式是否正确"));
                }else {
                    this.pCheck = false;
                    callback();
                }
            },
            passwordCheck(rule,value,callback){
                if (value.length < 1){
                    this.pCheck2 = true;
                    return callback(new Error("重复密码不能为空"));
                }else if (value !== this.User.password){
                    this.pCheck2 = true;
                    return callback(new Error("两次输入密码不一致"));
                }else {
                    this.pCheck2 = false;
                    return callback()
                }
            },
        }
    }
</script>

<style scoped>
    .login-wrap {
        position: relative;
        width: 100%;
        height: 100%;
        background-size: 100%;
    }
    .ms-title {
        width: 100%;
        line-height: 50px;
        text-align: center;
        font-size: 20px;
        color: black;
        border-bottom: 1px solid #ddd;
    }
    .ms-login {
        position: absolute;
        left: 50%;
        top: 50%;
        width: 350px;
        margin: -190px 0 0 -175px;
        border-radius: 5px;
        background: rgba(255, 255, 255, 1);
        overflow: hidden;
    }
    .ms-content {
        padding: 30px 30px;
    }
    .login-btn {
        text-align: center;
    }
    .login-btn button {
        width: 100%;
        height: 36px;
        margin-bottom: 10px;
    }
</style>
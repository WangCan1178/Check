<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">忘记密码</div>
            <el-form :model="pswFind" :rules="rules" label-width="0px" class="ms-content">
                <el-form-item prop="phone">
                    <el-input v-model="pswFind.phone" placeholder="请输入电话">
                        <template #prepend style="padding: 0 10px">
                            <i class="el-icon-mobile-phone" style="font-size: 20px;"></i>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="verification">
                    <el-input v-model="pswFind.verification" placeholder="请输入验证码">
                        <template #prepend>
                            <i class="el-icon-message" style="font-size: 20px"></i>
                        </template>
                        <template #append>
                            <el-button @click="verCodeClick" style="background-color: #409EFF;color:white">{{clickVerCode ? verSecond+'s' : '获取验证码'}}</el-button>
                        </template>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                        <el-button type="primary" @click="next()">下一步</el-button>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
    import {reactive,ref} from "vue"
    import router from "../router";
    import {ElMessage} from "element-plus";
    export default {
        name: "Forget",
        data(){
            return{
                pswFind: {
                    phone:"",
                    verification:"",
                    verification:"",
                },
                clickVerCode: false,
                verSecond: 60,
                timer: null,
                rules:{
                    phone: [{
                        required: true,
                        validator: this.telCheck,
                        trigger:"blur"
                    }],
                    verification: [{
                        required: true,
                        validator: this.code,
                        trigger: "blur",
                    }]
                },

            }
        },
        methods:{
            next(){
                this.$axios.get(
                    "http://localhost:9000/phone",{
                        params:{
                            phone: this.pswFind.phone
                        }
                    }).then((response)=> {
                        //alert(response.data)
                    if(response.data === ""){
                        ElMessage.info("该号码不存在，请注册");
                        router.push("/login");
                    }else {
                        //alert(this.pswFind.phone)
                        sessionStorage.setItem("phone",this.pswFind.phone);
                        router.push("/forgetnext");
                    }
                }).catch(function (err) {
                    console.log(err);
                });
            },
            code(rule,value,callback){
                if(value === ""){
                    return callback(new Error("请输入验证码"));
                } else if (value.length !== 4){
                    return callback(new Error("请输入正确4位验证码"));
                }else{
                    callback()
                }
            },
            telCheck(rule,value,callback){
                var  reg = /^1[3-9]\d{9}$/;
                if (value === ""){
                    return callback(new Error("请输入手机密码"));
                } else if (value.toString().length !== 11){
                    return callback(new Error("手机号码必须为11位"));
                }else if (!reg.test(value)){
                    return callback(new Error("请输入有效的手机号码"));
                }else {
                    callback();
                }
            },
            clearTimer(){
                if(this.timer){
                    clearInterval(this.timer);
                    this.timer = null
                }
            },
            verCodeClick(){
                if(this.clickVerCode){
                    return
                }
                //TODO 验证码获取代码
                this.clickVerCode = true;
                this.timer = setInterval(()=>{
                    if(this.verSecond == 1){
                        this.clickVerCode = false;
                        this.verSecond = 60;
                        clearTimer()
                    }
                    this.verSecond -= 1;
                }, 1 * 1000);
            }
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
<template>
    <div>
        <transition name="el-fade-in" @click="back = !back">
            <img src="../assets/img/QQ图片20220607153231.png" v-show="back" style="width: 100%;height: 100%;background-size: 100%;">
        </transition>
    </div>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">check查客核查小帮手</div>
            <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content" >
                <el-form-item prop="username">
                    <el-input v-model="param.username" placeholder="请输入电话">
                        <template #prepend>
                            <el-button icon="el-icon-mobile"></el-button>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" placeholder="password" v-model="param.password">
                        <template #prepend>
                            <el-button icon="el-icon-lock"></el-button>
                        </template>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm()">登录</el-button>
                </div>
                <router-link to="/register">
                    <el-button style="font-size: 15px" type="text">点击注册</el-button>
                </router-link>
                <router-link to="/forget">
                    <el-button style="font-size: 15px;float: right" type="text">忘记密码</el-button>
                </router-link>
            </el-form>
        </div>
    </div>
</template>

<script>
import { ref, reactive,getCurrentInstance } from "vue";
import { useStore } from "vuex";
import { ElMessage } from "element-plus";
import requests from "../assets/requests";
import {qs} from "qs";
import router from "../router";

export default {
    data(){
        return{
            back:true,
            param : {
                username: "",
                password: "",
            },
            rules:{
                username: [
                    {
                        required: true,
                        validator:this.telCheck,
                        trigger: "blur",
                    },
                ],
                password: [
                    { required: true, message: "请输入密码", trigger: "blur" },
                ],
            },
            isVal:false,
            that:getCurrentInstance()
        }
    },
    setup() {
        const store = useStore();
        store.commit("clearTags");
    },
    methods:{
        submitForm() {
            if(!this.isVal){
                this.$message.error("手机号格式不正确");
                return
            }
            this.$axios.get(
                "http://localhost:9000/login",{
                    params: {
                            username: this.param.username,
                            password: this.param.password
                        }
                })
                .then(function (response) {
                    console.log(response.data);
                    if(response.data.name == null){
                        ElMessage.error("用户名或密码不正确，登录失败")
                    }else{
                        ElMessage.success("登录成功");
                        localStorage.setItem("ms_username", response.data.name);
                        localStorage.setItem("userId",response.data.userid);
                        router.push('/')
                    }
                }).catch(function (error) {
                    ElMessage.error("出错了！");
                    console.log(error);
                })
        },
        telCheck  (rule,value,callback)  {
            var  reg = /^1[3-9]\d{9}$/;
            if (value === ""){
                this.isVal = false;
                this.isVal = false;
                return callback(new Error("请输入手机号码"));
            } else if (value.toString().length !== 11){
                this.isVal = false;
                return callback(new Error("手机号码必须为11位"));
            }else if (!reg.test(value)){
                this.isVal = false;
                return callback(new Error("请输入有效的手机号码"));
            }else {
                this.isVal = true;
                callback();
            }

        }
    }
};
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
.login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #fff;
}

/*.fade-leave-active {*/
/*    animation: bounce-out .3s;*/
/*}*/
/*@keyframes bounce-out {*/
/*    0% {*/
/*        transform: translate3d(0, 0, 0);*/
/*    }*/
/*    25% {*/
/*        transform: translate3d(25%, 0, 0);*/
/*    }*/
/*    50% {*/
/*        transform: translate3d(50%, 0, 0);*/
/*    }*/
/*    75% {*/
/*        transform: translate3d(75%, 0, 0);*/
/*    }*/
/*    100% {*/
/*        transform: translate3d(100%, 0, 0);*/
/*    }*/
/*}*/


</style>
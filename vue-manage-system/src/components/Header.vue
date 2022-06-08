<template>
    <div class="header">
        <!-- 折叠按钮 -->
        <div class="collapse-btn" @click="collapseChage">
            <i v-if="!collapse" class="el-icon-s-fold"></i>
            <i v-else class="el-icon-s-unfold"></i>
        </div>
        <img src="public/icon.ico" style="height: 40px;width: 40px;float: left;margin-top: 15px">
        <div class="logo">check查客</div>
        <div class="header-right">
            <div class="header-user-con">
                <!-- 消息中心 -->
                <div class="btn-bell">
                    <el-tooltip effect="dark" :content="message?`有${message}条未读消息`:`消息中心`" placement="bottom">
                        <router-link to="/message">
                            <i class="el-icon-bell"></i>
                        </router-link>
                    </el-tooltip>
                    <span class="btn-bell-badge" v-if="message"></span>
                </div>
                <!-- 用户头像 -->
                <div class="user-avator">
                    <!--  TODO 用户头像转换-->
                    <img src="../assets/img/img.jpg" />
                </div>
                <!-- 用户名下拉菜单 -->
                <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        {{username}}
                        <i class="el-icon-caret-bottom"></i>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="user">个人中心</el-dropdown-item>
<!--                            <el-dropdown-item divided command="changestyle">切换主题</el-dropdown-item>-->
                            <el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
                            <el-dropdown-item divided command="deleteUser">注销账号</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </div>
        </div>
    </div>
<!--    <el-dialog title="切换主题" v-model="styleVisible">-->
<!--        <template>-->
<!--            <component is="ThemePicker"></component>-->
<!--        </template>-->
<!--&lt;!&ndash;        <el-color-picker v-model="color"></el-color-picker>&ndash;&gt;-->
<!--&lt;!&ndash;        <template #footer>&ndash;&gt;-->
<!--&lt;!&ndash;                <span class="dialog-footer">&ndash;&gt;-->
<!--&lt;!&ndash;                    <el-button @click="styleVisible = false">取 消</el-button>&ndash;&gt;-->
<!--&lt;!&ndash;                    <el-button type="primary" @click="alter">切换</el-button>&ndash;&gt;-->
<!--&lt;!&ndash;                </span>&ndash;&gt;-->
<!--&lt;!&ndash;        </template>&ndash;&gt;-->
<!--    </el-dialog>-->
</template>
<script>
import { computed, onMounted,reactive,ref } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
//import themePicker from "./ThemePicker.vue";

export default {
    inject:['reload'],
    // components:{
    //     themePicker
    // },
    data(){
        return{
            message:0,
            username:localStorage.getItem("ms_username"),
            store:useStore(),
            router:useRouter(),
            collapse:true
        }
    },
    methods:{
        collapseChage(){
            // 侧边栏折叠
            this.store.commit("handleCollapse", !this.collapse);
        },
        handleCommand(command){
            if (command == "loginout") {
                localStorage.removeItem("ms_username");
                this.router.push("/login");
            } else if (command == "user") {
                this.router.push("/user");
            }else if (command == "deleteUser"){
                //TODO 删除账号
                localStorage.removeItem("ms_username");
                this.router.push("/login");}
        },
    },
    mounted(){
        if (document.body.clientWidth < 1500) {
            this.collapseChage();
        }
        this.$axios.get(
            "http://localhost:9000/getMess",{
                params:{
                    userid:localStorage.getItem("userId")
                }
            }
        ).then((response) => {
            if (response.data !== null){
                for (let i=0;i<response.data.length;i++){
                    if (response.data[i].isread === 0){
                        this.message = this.message+1
                    }
                }
                console.log(this.message)
            }
        }).catch((err) => {
            this.$message.error("出错了！");
            console.log(err);
        });
    },
    computed:{
        collapse(){this.collapse = this.store.state.collapse}
    },
    watch:{
        '$route'(){
            this.reload()
        }
    },
};
</script>
<style scoped>
.header {
    position: relative;
    box-sizing: border-box;
    width: 100%;
    height: 70px;
    font-size: 22px;
    color: #fff;
}
.collapse-btn {
    float: left;
    padding: 0 21px;
    cursor: pointer;
    line-height: 70px;
}
.header .logo {
    float: left;
    width: 250px;
    line-height: 70px;
}
.header-right {
    float: right;
    padding-right: 50px;
}
.header-user-con {
    display: flex;
    height: 70px;
    align-items: center;
}
.btn-fullscreen {
    transform: rotate(45deg);
    margin-right: 5px;
    font-size: 24px;
}
.btn-bell,
.btn-fullscreen {
    position: relative;
    width: 30px;
    height: 30px;
    text-align: center;
    border-radius: 15px;
    cursor: pointer;
}
.btn-bell-badge {
    position: absolute;
    right: 0;
    top: -2px;
    width: 8px;
    height: 8px;
    border-radius: 4px;
    background: #f56c6c;
    color: #fff;
}
.btn-bell .el-icon-bell {
    color: #fff;
}
.user-name {
    margin-left: 10px;
}
.user-avator {
    margin-left: 20px;
}
.user-avator img {
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 50%;
}
.el-dropdown-link {
    color: #fff;
    cursor: pointer;
}
.el-dropdown-menu__item {
    text-align: center;
}
</style>

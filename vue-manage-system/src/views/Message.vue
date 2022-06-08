<template>
    <div class="">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-copy"></i> 消息中心</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-tabs v-model="message">
                <el-tab-pane :label="`未读消息(${state.unread.length})`" name="first">
                    <el-table :data="state.unread" :show-header="false" style="width: 100%">
                        <el-table-column>
                            <template #default="scope">
                                <router-link :to="'/add/'+scope.row.groupid">
                                    <span class="message-title">{{scope.row.title}}</span>
                                </router-link>
                            </template>
                        </el-table-column>
                        <el-table-column prop="date" width="180"></el-table-column>
                        <el-table-column width="120" align="center">
                            <template #default="scope">
                                <el-button size="small" @click="handleRead(scope.$index)">标为已读</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="handle-row">
                        <!-- 全部标为已读方法-->
                        <el-button type="primary" @click="handleReadAll()">全部标为已读</el-button>
                    </div>
                </el-tab-pane>
                <el-tab-pane :label="`已读消息(${state.read.length})`" name="second">
                    <template v-if="message === 'second'">
                        <el-table :data="state.read" :show-header="false" style="width: 100%">
                            <el-table-column>
                                <template #default="scope">
                                    <router-link :to="'/add/'+scope.row.groupid">
                                        <span class="message-title" style="color: black">{{scope.row.title}}</span>
                                    </router-link>
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" width="180"></el-table-column>
                            <el-table-column width="100" align="center">
                                <template #default="scope">
                                    <el-button size="small" @click="handleUnread(scope.$index)">标为未读</el-button>
                                </template>
                            </el-table-column>
                            <el-table-column width="100" align="center">
                                <template #default="scope">
                                    <el-button size="small" type="danger" @click="handleDel(scope.$index)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <div class="handle-row">
                            <el-button @click="handleUnreadAll()">全部标为未读</el-button>
                            <el-button type="danger" @click="handleDelAll()">删除全部</el-button>
                        </div>
                    </template>
                </el-tab-pane>
                <el-tab-pane :label="`回收站(${state.recycle.length})`" name="third">
                    <template v-if="message === 'third'">
                        <el-table :data="state.recycle" :show-header="false" style="width: 100%">
                            <el-table-column>
                                <template #default="scope">
                                    <span class="message-title" style="color: black">{{scope.row.title}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" width="150"></el-table-column>
                            <el-table-column width="100" align="center">
                                <template #default="scope">
                                    <el-button size="small" @click="handleRestore(scope.$index)">还原</el-button>
                                </template>
                            </el-table-column>
                            <el-table-column width="100" align="center">
                                <template #default="scope">
                                    <el-button size="small" type="danger" @click="deleteMes(scope.$index)">彻底删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <div class="handle-row">
                            <el-button type="danger" @click="handleRestoreAll()">清空回收站</el-button>
                        </div>
                    </template>
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script>
import { ref, reactive } from "vue";
export default {
    name: "tabs",
    data(){
        return{
            message:"first",
            state:{
                unread:[],
                read:[],
                recycle: [],
            }
        }
    },
    mounted() {
        this.$axios.get(
            "http://localhost:9000/getMess",{
                params:{
                    userid:localStorage.getItem("userId")
                }
            }
        ).then((response) => {
            if (response.data !== null){
                for (let i=0;i<response.data.length;i++){
                    let message = {
                        mesid:0,
                        isread:-1,
                        groupid:0,
                        title:"",
                        date:""
                    }
                    message.mesid = response.data[i].mesid;
                    message.groupid = response.data[i].groupid;
                    message.title = "您的任务\""+response.data[i].title+"\"还未完成，点击跳转完成";
                    message.date = response.data[i].time;
                    if (response.data[i].isread === 0){
                        this.state.unread.push(message)
                    }else if (response.data[i].isread === 1){
                        this.state.read.push(message)
                    }else {
                        this.state.recycle.push(message)
                    }
                }
            }
        }).catch((err) => {
            this.$message.error("出错了！");
            console.log(err);
        });
    },
    methods:{
        handleRead(index){
            const item = this.state.unread.splice(index, 1);    //删除数组的值
            console.log(item[0].mesid);
            this.state.read = item.concat(this.state.read);       //合并数组
            this.$axios.get(
                "http://localhost:9000/alterRead",{
                    params:{
                        mesid:item[0].mesid,
                        isread:1
                    }
                }
            ).then((response) => {

            }).catch((err) => {
                console.log(err);
            });
        },
        handleDel(index){
            const item = this.state.read.splice(index, 1);
            this.state.recycle = item.concat(this.state.recycle);
            this.$axios.get(
                "http://localhost:9000/alterRead",{
                    params:{
                        mesid:item[0].mesid,
                        isread:2
                    }
                }
            ).then((response) => {

            }).catch((err) => {
                console.log(err);
            });
        },
        handleUnread(index){
            const item = this.state.read.splice(index, 1);
            this.state.unread = item.concat(this.state.unread);
            this.$axios.get(
                "http://localhost:9000/alterRead",{
                    params:{
                        mesid:item[0].mesid,
                        isread:0
                    }
                }
            ).then((response) => {

            }).catch((err) => {
                console.log(err);
            });
        },
        handleRestore(index){
            const item = this.state.recycle.splice(index, 1);
            this.state.read = item.concat(this.state.read);
            this.$axios.get(
                "http://localhost:9000/alterRead",{
                    params:{
                        mesid:item[0].mesid,
                        isread:1
                    }
                }
            ).then((response) => {

            }).catch((err) => {
                console.log(err);
            });
        },
        deleteMes(index){
            const item = this.state.recycle.splice(index, 1);
            this.$axios.get(
                "http://localhost:9000/deleteMes",{
                    params:{
                        mesid:item[0].mesid,
                    }
                }
            ).then((response) => {

            }).catch((err) => {
                console.log(err);
            });
        },
        handleReadAll(){
            while(this.state.unread.length>0) {
                this.handleRead();
            }
        },
        handleDelAll(){
            while(this.state.read.length>0){
                this.handleDel();
            }
        },
        handleUnreadAll(){
            while(this.state.read.length>0){
                this.handleUnread();
            }
        },
        handleRestoreAll(){
            while(this.state.recycle.length>0){
                this.deleteMes();
            }
        }
    },
};
</script>

<style>
.message-title {
    cursor: pointer;
}
.handle-row {
    margin-top: 30px;
}
</style>


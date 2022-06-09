<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span class="box-title">我加入的群组</span>
                <el-button style="float: right; font-size: 16px;padding-right:30px" type="text" @click="addGroup()">加入群组</el-button>
            </div>
            <el-table :show-header="false" :data="addList" style="width: 100%" stripe highlight-current-row>
                <el-table-column width="10">
                    <template #default="scope">
                        <span style="display: none">{{ scope.row.groupid}}</span>
                    </template>
                </el-table-column>
                <el-table-column>
                    <template #default="scope">
                        <span style="font-size: 16px">{{ scope.row.name }}</span>
                    </template>
                </el-table-column>
                <el-table-column width="150" align="center">
                    <template #default="scope">
                        <router-link :to="'/add/'+scope.row.groupid">
                            <el-button type="primary" size="small" style="width: 100%">
                                进入
                            </el-button>
                        </router-link>
                    </template>
                </el-table-column >
                <el-table-column width="150" align="center">
                    <template #default="scope">
                        <el-button type="danger" size="small" icon="el-icon-delete" style="width: 100%" @click="quit(scope.$index,scope.row)">
                            退出
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <!-- todo 分页功能 -->
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handlecurrentChange"
                    :current-page="currentPage"
                    layout="total,prev, pager, next, jumper"
                    :total="totalCount"
                    :hide-on-single-page="false"
            ></el-pagination>
        </el-card>

    </div>
</template>

<script>
    import {reactive} from "vue"
    import router from "../router";
    import { ElMessage } from "element-plus";
    export default {
        name: "addGroup",
        inject:['reload'],
        data(){
            return{
                addList:[],
                group:{
                    groupid: "",
                    name : "",
                },
                currentPage:1,
                totalCount:1,
            }
        },
        mounted(){
            this.$axios.get(
                "http://localhost:9000/add/getAddGroup",{
                    params:{
                        id:localStorage.getItem("userId"),
                    }
                }
            ).then((response) => {
                for(let i=0;i<response.data.length;i++){
                    //console.log(response.data[i]);
                    this.addList.push(response.data[i]);
                }
                this.totalCount = response.data.length
            }).catch((err) => {
                this.$message.error("出错了！");
                console.log(err);
            })
        },
        methods:{
            handleSizeChange(val){
                this.pageSize = val;
                this.currentPage = 1
            },
            handlecurrentChange(val){
                this.currentPage = val
            },
            quit(index,row){
                //退出群组
                let idx = -1;
                idx = index;
                Object.keys(this.group).forEach( (item) =>{
                    this.group[item] = row[item];
                });
                console.log(this.group)
                this.$axios.post(
                    "http://localhost:9000/add/delAddGroup",{
                        userid:localStorage.getItem("userId"),
                        groupid: this.group.groupid
                    }
                ).then((response)=>{
                    console.log(response.data)
                    if(response.data === "success"){
                        ElMessage.success("成功退出");
                        this.reload();
                    }
                }).catch((err) => {
                    this.$message.error("出错了！");
                    console.log(err)
                })
            },
            addGroup(){
                this.$prompt('请输入要加入的群组id','加入群组',{
                    confirmButtonText:"确定",
                    cancelButtonText:"取消",
                    inputPattern: /[0-9]*[0-9]$/,
                    inputErrorMessage: "群组id格式不正确"
                }).then(({ value }) => {
                    //添加群组
                    this.$axios.post(
                        'http://localhost:9000/add/addGroup',{
                            userid:localStorage.getItem("userId"),
                            memname:localStorage.getItem("ms_username"),
                            groupid:value
                        }
                    ).then((response)=>{
                        if (response.data === "success"){
                            this.$message({
                                type: "success",
                                message: "加入群组成功"
                            });
                            this.reload()
                        }else {
                            ElMessage.error("该群组不存在或已加入，请重新输入")
                        }
                    }).catch((err)=>{
                            ElMessage.error("出错了");
                            console.log(err);
                    });
                }).catch( () => {
                    this.$message.error("加入群组失败");
                })
            }
        }
    };
</script>

<style scoped>
    .box-title{
        font-size: 18px;
    }

    .text {
        font-size: 15px;
    }

    .item {
        margin-bottom: 18px;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }
    .clearfix:after {
        clear: both
    }

    .box-card {
        width: 100%;
    }
</style>
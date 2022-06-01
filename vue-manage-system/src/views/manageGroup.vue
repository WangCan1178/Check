<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span class="box-title">我管理的群组</span>
                <el-button style="float: right;font-size: 16px;padding-right: 30px" type="text" @click="createGroup()">创建群组</el-button>
            </div>
            <el-table :show-header="false" :data="manList" style="width: 100%" stripe highlight-current-row>
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
                        <router-link :to="'/manage/'+scope.row.groupid">
                            <el-button type="primary" size="small" style="width: 100%" >
                                进入
                            </el-button>
                        </router-link>
                    </template>
                </el-table-column >
                <el-table-column width="150" align="center">
                    <template #default="scope">
                        <el-button type="danger" size="small" icon="el-icon-delete" style="width: 100%" @click="delGroup(scope.$index,scope.row)">
                            解散
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination></el-pagination>
        </el-card>
    </div>
</template>

<script>
    import {reactive} from "vue"
    import {ElMessage} from "element-plus"
    import qs from "qs"

    export default {
        name: "manageGroup",
        inject:['reload'],
        data(){
            return{
                manList: [],
                group:{
                    groupid: "",
                    name : "",
                },
            }
        },
        mounted(){
            this.$axios.get(
                "http://localhost:9000/manage/getManGroup",{
                    params:{
                        id:localStorage.getItem("userId"),
                    }
                }
            ).then((response) => {
                for(let i=0;i<response.data.length;i++){
                    this.manList.push(response.data[i]);
                }
            }).catch((err) => {
                this.$message.error("出错了！");
                console.log(err);
            })
        },
        methods:{
            createGroup(){
                this.$prompt('请输入创建的群组名称','创建群组',{
                    confirmButtonText:"确定",
                    cancelButtonText:"取消",
                }).then(({value}) =>{
                    //加入数据库并重载页面
                    this.$axios.get(
                        'http://localhost:9000/manage/addGroup', {
                            params:{
                                userid:localStorage.getItem("userId"),
                                groupname:value,
                                memname:localStorage.getItem("ms_username")
                            }
                        }
                    ).then((response)=>{
                        if (response.data === "success"){
                            this.$message({
                                type: "success",
                                message: "创建群组成功"
                            });
                            this.reload()
                        }
                    }).catch((err)=>{
                        ElMessage.error("出错了");
                        console.log(err);
                    });
                }).catch((err)=>{
                    console.log(err)
                })
            },
            delGroup(index, row){
                let idx = -1;
                //删除群组
                idx = index;
                Object.keys(this.group).forEach((item) => {
                    this.group[item] = row[item];
                });
                console.log(this.group)
                this.$axios.post(
                    "http://localhost:9000/manage/delGroup",{
                        groupid: this.group.groupid,
                    }
                ).then((response)=>{
                    if(response.data === "success"){
                        ElMessage.success("成功解散群组");
                        this.reload();
                    }
                }).catch((err) => {
                    this.$message.error("出错了！");
                    console.log(err)
                })
            }
        }
    }
</script>

<style scoped>

</style>
<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="8">
                <el-card style="margin-bottom: 20px" shadow="hover" >
                    <div class="user-info">
                        <div class="user-info-cont">
                            <div>
                                <span style="font-size: 25px;color: #222;margin-bottom: 5px">{{task.taskname}}</span>
                                <el-button type="danger" @click="finish()" size="mini" style="float: right; " :class="{'isTfinish': task.isFinish}">结束</el-button>
                            </div>
                        </div>
                    </div>
                    <div class="user-info-list">
                        开始时间：
                        <span>{{task.starttime}}</span>
                    </div>
                    <div class="user-info-list">
                        结束时间：
                        <span>{{task.endtime}}</span>
                    </div>
                </el-card>
                <el-card shadow="hover">
                    <template #header>
                        <div class="clearfix">
                            <span style="font-size: 18px">统计结果</span>
                            <el-button style="float: right; padding: 3px 0" type="text" @click="">导出图表</el-button>
                        </div>
                    </template>
                    <schart class="schart" canvasId="pie" :options="options3"></schart>
                </el-card>
            </el-col>
            <el-col :span="16">
                <el-card shadow="hover">
                    <template #header>
                        <div class="clearfix">
                            <span style="font-size: 18px">完成情况</span>
                            <el-button style="float: right; padding: 3px 0" type="text" @click="notice()">通知未完成成员</el-button>
                            <el-button style="float: right; padding: 3px 0;margin-right: 15px" type="text" @click="">导出excel</el-button>
                        </div>
                    </template>
                    <el-table :show-header="false" :data="memberList" style="width: 100%">
                        <el-table-column width="1">
                            <template #default="scopeTask">
                                <span style="display: none">{{scopeTask.row.id}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column width="1">
                            <template #default="scopeTask" >
                                <el-checkbox v-model="scopeTask.row.isFinish" style="display: none"></el-checkbox>
                            </template>
                        </el-table-column>
                        <el-table-column >
                            <template #default="scopeTask">
                                <div style="font-size: 16px" :class="{'isfinish': scopeTask.row.isPass,}">{{scopeTask.row.name}}</div>
                            </template>
                        </el-table-column>
                        <el-table-column width="150" align="center">
                            <template #default="scopeTask">
                                <el-button type="primary" size="small" style="width: 100%" @click="taskC(scopeTask.$index,scopeTask.row)" :disabled="scopeTask.row.isFinish">
                                    查看
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
        </el-row>

        <el-dialog :title="memberTitle" v-model="isedit" >
            <el-form >
                <el-image :src="photo.pUrl">
                    <div slot="error" class="image-slot">
                        <i class="el-icon-picture-outline"></i>
                    </div>
                </el-image>
                <div style="font-size: 16px;margin-top: 10px">
                    识别结果：
                        <span v-show="!edit1">{{photo.pResult}}</span>
                        <el-select type="text" v-model="photo.pResult" v-show="edit1" style="width: 150px">
                            <el-option label="未通过" value="未通过"></el-option>
                            <el-option label="已通过" value="已通过"></el-option>
                        </el-select>
                        <span style="margin-left: 20px" >
                            <el-button type="primary" :icon="{'el-icon-edit':!edit1,'el-icon-check':edit1}" circle size="mini" @click="edit1 = !edit1" ></el-button>
                        </span>
                </div>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="isedit = false">取 消</el-button>
                    <el-button type="primary" @click="sub()">提交</el-button>
                </span>
            </template>
        </el-dialog>

    </div>
</template>

<script>
    import {reactive, ref, getCurrentInstance} from "vue"
    import {useRoute,useRouter} from "vue-router"
    import Schart from "vue-schart";
    import router from "../router";
    import {ElMessage} from "element-plus";
    import {compareTime} from "./compareTime";

    export default {
        name: "Task",
        path:"/task/:id",
        inject:['reload'],
        components:{
            Schart
        },
        data(){
            return{
                options3: {
                    type: "pie",
                    legend: {
                        position: "left",
                    },
                    bgColor: "#fbfbfb",
                    labels: [
                        "已通过",
                        "未通过",
                        "未填写",
                    ],
                    datasets: [
                        {
                            data: [0,0,0],
                        },
                    ],
                },
                route:useRoute(),
                task:{
                    taskname: "",
                    starttime: "",
                    endtime:"",
                    isFinish: false
                },
                memberList: [],
                isedit:false,
                memberTitle:"",
                photo:{
                    pUrl:"",
                    pResult:"",
                    pid:""
                },
                picture:{
                    id:"",
                    name:"",
                    isFinish: false,
                    isPass: false,
                },
                edit1:false,
            }
        },
        mounted(){
            //alert(this.route.params.id);
            //根据id修改任務信息()
            this.$axios.get(
                "http://localhost:9000/task/getTaskInfo",{
                    params:{
                        id:this.route.params.id,
                    }
                }
            ).then((response) => {
                console.log(response.data)
                if (response.data !== null){
                    this.task.taskname = response.data.title;
                    this.task.description = response.data.description;
                    this.task.starttime = response.data.publishtime;
                    this.task.endtime = response.data.endtime;
                    //与当前时间对比修改isFinish
                    if(compareTime(response.data.endtime)){
                        this.task.isFinish = false
                    }
                    else {
                        this.task.isFinish = true
                    }
                }
            }).catch((err) => {
                this.$message.error("出错了！");
                console.log(err);
            });
            //  修改结果数据
            this.$axios.get(
                "http://localhost:9000/pic/getPicUnPass",{
                    params:{
                        id:this.route.params.id,
                    }
                }
            ).then((response) => {
                this.options3.datasets[0].data[1] = response.data.length
            }).catch((err) => {
                this.$message.error("出错了！");
                console.log(err);
            });
            this.$axios.get(
                "http://localhost:9000/pic/getPicUnFinish",{
                    params:{
                        id:this.route.params.id,
                    }
                }
            ).then((response) => {
                console.log(this.options3.datasets[0].data[0])
                this.options3.datasets[0].data[2] = response.data.length
            }).catch((err) => {
                this.$message.error("出错了！");
                console.log(err);
            });
            this.$axios.get(
                "http://localhost:9000/pic/getPicPass",{
                    params:{
                        id:this.route.params.id,
                    }
                }
            ).then((response) => {
                this.options3.datasets[0].data[0] = response.data.length
            }).catch((err) => {
                this.$message.error("出错了！");
                console.log(err);
            });
            this.$axios.get(
                "http://localhost:9000/pic/getPic",{
                    params:{
                        id:this.route.params.id,
                    }
                }
            ).then((response) => {
                for(let i=0;i<response.data.length;i++){
                    var pic = {
                        id:response.data[i].picid,
                        name:response.data[i].userid,
                        isFinish: false,
                        isPass: false,
                    }
                    if (response.data[i].result === "2"){
                        pic.isFinish = true
                    }
                    if (response.data[i].result === "1"){
                        pic.isPass = true
                    }
                    this.memberList.push(pic)
                }
            }).catch((err) => {
                this.$message.error("出错了！");
                console.log(err);
            });

        },
        watch:{
            '$route'() {
                this.reload()
            }

        },
        methods:{
            taskC(index,row){
                //获取图片和结果
                this.isedit = true;
                let idx = -1;
                //删除群组
                idx = index;
                Object.keys(this.picture).forEach((item) => {
                    this.picture[item] = row[item];
                });
                this.$axios.get(
                    "http://localhost:9000/pic/getPicD",{
                        params:{
                            id:this.picture.id,
                        }
                    }
                ).then((response) => {
                    this.photo.pid = response.data.picid;
                    this.photo.pUrl = response.data.photo;
                    if(response.data.result === "0"){
                        this.photo.pResult = "未通过"
                    }else if (response.data.result === "1"){
                        this.photo.pResult = "已通过"
                    }
                }).catch((err) => {
                    this.$message.error("出错了！");
                    console.log(err);
                });
            },
            sub(){
                //保存结果
                this.$axios.get(
                    "http://localhost:9000/pic/alterR", {
                        params:{
                            picid:this.photo.pid,
                            result:(this.photo.pResult === "未通过")?"0":"1",
                        }
                    }
                ).then((response) => {
                    if (response.data === "success"){
                        ElMessage.success("成功修改结果");
                        this.isedit = false;
                        this.reload()
                    }else {
                        ElMessage.error("修改结果失败");
                        this.isedit = false;
                    }
                }).catch((err) => {
                    this.$message.error("出错了！");
                    console.log(err);
                });
            },
            notice(){
            //TODO 通知函数
            },
            FormatTime(time){
                var now=new Date(time);
                var year=now.getFullYear();
                var month=now.getMonth()+1;
                var date=now.getDate();
                var hour=now.getHours();
                var minute=now.getMinutes();
                var second=now.getSeconds();
                return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
            },
            finish(){
                this.$confirm('是否现在结束该任务?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post(
                        "http://localhost:9000/task/endTask", {
                                taskid:this.route.params.id,
                                endtime:this.FormatTime(Date.now()),
                        }
                    ).then((response) => {
                        if (response.data === "success"){
                            ElMessage.success("任务已结束");
                            this.reload()
                        }else {
                            ElMessage.error("结束任务失败");
                        }
                    }).catch((err) => {
                        this.$message.error("出错了！");
                        console.log(err);
                    });
                }).catch(() => {
                });
            }
        }
    }

</script>

<style scoped>

    .clearfix:before,
    .clearfix:after {

        content: "";
    }
    .clearfix:after {
        clear: both
    }

    .schart {
        height: 300px;
    }
    .user-info {
        display: flex;
        align-items: center;
        padding-bottom: 10px;
        border-bottom: 2px solid #ccc;
        margin-bottom: 5px;
    }
    .user-info-cont {
        flex: 1;
        font-size: 15px;
        color: #999;
    }
    .user-info-list {
        font-size: 14px;
        color: #999;
        line-height: 25px;
    }
    .isfinish{
        text-decoration: line-through;
        color: #999;
    }
    .isTfinish{
        display: none;
    }

</style>
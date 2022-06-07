<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span class="box-title">{{ groupName }}</span>
                <span class="text" style="margin-left: 50px">群组id：{{ groupID }}</span>
                <el-button style="float: right;font-size: 16px;" type="text" @click="altername()">修改群内名称</el-button>
                <el-button style="float: right; font-size: 16px;text-align: center;padding-right: 30px" type="text" @click="member()">查看成员</el-button>
                <el-button style="float: right; font-size: 16px;padding-right:30px;text-align: center" type="text" @click="addMan()">添加管理员</el-button>
            </div>
            <el-table :show-header="false" :data="taskList" style="width: 100%" highlight-current-row>
                <el-table-column width="10">
                    <template #default="scope2">
                        <span style="display: none">{{scope2.row.taskid}}</span>
                    </template>
                </el-table-column>
                <el-table-column >
                    <template #default="scope2">
                        <div style="font-size: 16px" >{{scope2.row.title}}</div>
                    </template>
                </el-table-column>
                <el-table-column width="150" align="center">
                    <template #default="scope2">
                        <router-link :to="'/task/'+scope2.row.taskid">
                            <el-button type="primary" size="small" style="width: 100%">
                                查看
                            </el-button>
                        </router-link>
                    </template>
                </el-table-column>
            </el-table>
            <!--todo 分页-->
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handlecurrentChange"
                    :current-page="currentPage"
                    layout="total,prev, pager, next, jumper"
                    :total="totalCount"
                    :hide-on-single-page="false"
            ></el-pagination>
        </el-card>

        <el-dialog title="成员列表" v-model="memberVisible">
            <el-table :data="memberList" :show-header="false" highlight-current-row>
                <el-table-column width="10">
                    <template #default="scope3">
                        <span style="display: none">{{ scope3.row.userid}}</span>
                    </template>
                </el-table-column>
                    <el-table-column>
                        <template #default="scope3">
                            <span style="font-size: 16px">{{scope3.row.memname}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column width="150" align="center">
                        <template #default="scope3">
                            <el-button type="danger" size="small" style="width: 100%" @click="delMem(scope3.$index,scope3.row)">
                                删除
                            </el-button>
                        </template>
                    </el-table-column>
            </el-table>
        </el-dialog>

        <el-button type="primary" icon="el-icon-plus" circle class="button" @click="addTask()"></el-button>

        <el-dialog title="创建任务" v-model="taskVisible" width="30%">
            <el-form :model="taskInfo" :rules="rule">
                <el-form-item label="任务名称" prop="name">
                    <el-input v-model="taskInfo.name"></el-input>
                </el-form-item>
                <el-form-item label="任务模板" prop="mode">
                    <el-select v-model="taskInfo.mode" placeholder="请选择模板" style="width: 100%">
                        <el-option label="健康码" value="1"></el-option>
                        <el-option label="行程码" value="2"></el-option>
                        <el-option label="其他" value="3"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="识别文字"  v-if="taskInfo.mode == '3' " prop="text">
                    <el-input v-model="taskInfo.text" placeholder="请输入所需文字"></el-input>
                </el-form-item>
                <el-form-item label="开始时间" prop="time">
                        <el-date-picker
                                v-model="taskInfo.time"
                                type="daterange"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                                style="width: 100%"
                                :disabled-date="disabledDate"
                        ></el-date-picker>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit()">立即创建</el-button>
                    <el-button>取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="修改名称" v-model="nameVisible">
            <el-form >
                <div style="font-size: 16px;margin-bottom: 20px" >
                    <span v-show="!edit3">{{memName}}</span>
                    <el-input type="text" v-model="memName" v-show="edit3" style="width: 150px"></el-input>
                    <span style="margin-left: 20px" >
                            <el-button type="primary" :icon="{'el-icon-edit':!edit3,'el-icon-check':edit3}" circle size="mini" @click="edit3 = !edit3" ></el-button>
                        </span>
                </div>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="nameVisible = false">取 消</el-button>
                    <el-button type="primary" @click="alter">提交</el-button>
                </span>
            </template>
        </el-dialog>

    </div>
</template>

<script>
    import {getCurrentInstance, reactive, ref} from "vue";
    import {useRoute, useRouter} from "vue-router";
    import {ElMessage} from "element-plus";

    export default {
        name: "MGroup",
        path:"/manage/:id",
        inject:['reload'],
        data(){
            return{
                memberVisible:false,
                route:useRoute(),
                groupName:"",
                groupID:"",
                taskList:[],
                memberList: [],
                Mem:{
                    userid:"",
                    memname:"",
                },
                taskVisible:false,
                taskInfo:{
                    name:"",
                    mode:"",
                    time:"",
                    text:""
                },
                nameVisible:false,
                memName:"",
                edit3:false,
                rule:{
                    name:[{
                        required:true,
                        trigger:"blur",
                        message: "请输入任务名称"
                    }],
                    mode:[{
                        required:true,
                        trigger:"blur",
                        message: "请选择任务模式"
                    }],
                    text:[{
                        required:true,
                        trigger:"blur",
                        message: "请输入所需识别文字"
                    }],
                    time:[{
                        required:true,
                        trigger:"blur",
                        message: "请输入任务时间"
                    }],
                },
                currentPage:1,
                totalCount:1,
            }
        },
        mounted(){
            this.groupID=this.route.params.id;
            //任务列表
            //根据id查姓名修改groupName
            this.$axios.get(
                "http://localhost:9000/manage/getName",{
                    params:{
                        id:this.route.params.id,
                    }
                }
            ).then((response) => {
                if (response.data !== null){
                    this.groupName = response.data
                }
            }).catch((err) => {
                this.$message.error("出错了！");
                console.log(err);
            });
            this.$axios.get(
                "http://localhost:9000/manage/getTask",{
                    params:{
                        id:this.route.params.id,
                    }
                }
            ).then((response) => {
                if (response.data !== null){
                    this.taskList = []
                    for(let i=0;i<response.data.length;i++){
                        this.taskList.push(response.data[i])
                    }
                    this.totalCount = response.data.length
                }
            }).catch((err) => {
                this.$message.error("出错了！");
                console.log(err);
            });
        },
        watch:{
            '$route'(){
                this.reload()
            }
        },
        methods: {
            handleSizeChange(val){
                this.pageSize = val;
                this.currentPage = 1
            },
            handlecurrentChange(val){
                this.currentPage = val
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
            altername(){
                this.$axios.get(
                    "http://localhost:9000/manage/altername",{
                        params:{
                            userid:localStorage.getItem("userId"),
                            id:this.route.params.id,
                        }
                    }
                ).then((response) => {
                    this.memName = response.data
                }).catch((err) => {
                    this.$message.error("出错了！");
                    console.log(err);
                });
                this.nameVisible = true
            },
            alter(){
                if (this.memName === ""){
                    ElMessage.error("群内昵称不能为空");
                    return
                }else {
                    this.$axios.post(
                        "http://localhost:9000/manage/alter",{
                            memname:this.memName,
                            groupid:this.route.params.id,
                            userid:localStorage.getItem("userId")
                        }
                    ).then((response) => {
                        if (response.data === "success"){
                            ElMessage.success("成功修改名称");
                        }else {
                            ElMessage.error("修改名称出错");
                        }
                    }).catch((err) => {
                        this.$message.error("出错了！");
                        console.log(err);
                    });
                }
            },
            addTask(){
                this.taskVisible = true
            },
            member(){
                //成员列表
                this.$axios.get(
                    "http://localhost:9000/manage/getMember",{
                        params:{
                            id:this.route.params.id,
                        }
                    }
                ).then((response) => {
                    if (response.data !== null){
                        this.memberList = []
                        for(let i=0;i<response.data.length;i++){
                            this.memberList.push(response.data[i])
                        }
                    }
                }).catch((err) => {
                    this.$message.error("出错了！");
                    console.log(err);
                });
                this.memberVisible = true
            },
            addMan(){
                this.$prompt('请输入要添加管理员的id(电话)','创建管理员',{
                    confirmButtonText:"确定",
                    cancelButtonText:"取消",
                    inputPattern: /^1[3-9]\d{9}$/,
                    inputErrorMessage: "管理员id格式不正确"
                }).then(({ value }) => {
                    //添加管理员
                    this.$axios.post(
                        'http://localhost:9000/manage/addManager',{
                            userid:value,
                            groupid:this.route.params.id
                        }
                    ).then((response)=>{
                        if (response.data === "success"){
                            this.$message({
                                type: "success",
                                message: "添加管理员成功"
                            });
                            this.reload()
                        }
                    }).catch((err)=>{
                        ElMessage.error("出错了");
                        console.log(err);
                    });
                }).catch( () => {
                    this.$message.error("添加管理员失败");
                })
            },
            onSubmit(){
                // 创建任务
                var description = "";
                if(this.taskInfo.mode === "3"){
                    description = this.taskInfo.text
                }else{
                    description = this.taskInfo.mode
                }
                this.$axios.post("http://localhost:9000/task/insertTaskJKM",{
                    title: this.taskInfo.name,
                    description: description,
                    groupid:this.route.params.id,
                    publishtime:this.FormatTime(this.taskInfo.time[0]),
                    endtime:this.FormatTime(this.taskInfo.time[1]),
                }).then((response)=>{
                    if(response.data === "success"){
                        this.$message.success("创建任务成功");
                        this.reload()
                    }
                }).catch((err)=>{
                    this.$message.error("出错了！");
                    console.log(err)
                })
            },
            delMem(index,row){
                let idx = -1;
                //根据成员id删除成员
                idx = index;
                Object.keys(this.Mem).forEach( (item) =>{
                    this.Mem[item] = row[item];
                });
                this.$axios.post(
                    "http://localhost:9000/manage/delMember",{
                        userid:this.Mem.userid,
                        groupid: this.route.params.id
                    }
                ).then((response)=>{
                    if(response.data === "success"){
                        ElMessage.success("成功删除");
                        this.reload();
                    }
                }).catch((err) => {
                    this.$message.error("出错了！");
                    console.log(err)
                })
            },
            disabledDate(time) {
                return time.getTime() <= Date.now();
            }
        }
    }
</script>

<style scoped>
    .box-title{
        font-size: 18px;
        margin-bottom: 20px;
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

    .isfinish{
        text-decoration: line-through;
        color: #999;
    }

    .button{
        position:absolute;
        right:30px;
        bottom:30px;
        width: 50px;
        height: 50px;
    }

</style>
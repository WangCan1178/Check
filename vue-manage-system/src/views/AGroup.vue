<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span class="box-title">{{ groupName }}</span>
                <el-button style="float: right;font-size: 16px;padding-right: 30px" type="text" @click="altername()">修改群内名称</el-button>
            </div>
            <el-table :show-header="false" :data="taskList" style="width: 100%" highlight-current-row>
                <el-table-column width="10">
                    <template #default="scope1">
                        <span style="display: none">{{scope1.row.id}}</span>
                    </template>
                </el-table-column>
                <el-table-column width="10">
                    <template #default="scope1" >
                        <el-checkbox v-model="scope1.row.isFinish" style="display: none"></el-checkbox>
                    </template>
                </el-table-column>
                <el-table-column >
                    <template #default="scope1">
                        <div style="font-size: 16px" :class="{'isfinish': scope1.row.isFinish,}">{{scope1.row.title}}</div>
                    </template>
                </el-table-column>
                <el-table-column width="150" align="center">
                    <template #default="scope1">
                            <el-button type="primary" size="small" style="width: 100%" @click="write(scope1.$index,scope1.row)" icon="el-icon-lx-edit " :disabled="scope1.row.isEnd">
                                填写
                            </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <!--todo 分页-->
        </el-card>

        <el-dialog :title="dialogtitle" v-model="editTaskVisible">
                <div style="font-size: 16px;margin-bottom: 20px" >{{task.description}}</div>
                <el-form >
                    <!--TODO 修改链接 略缩图-->
                    <!--https://www.mocky.io/v2/5185415ba171ea3a00704eed/posts/ -->
                    <el-upload class="upload-demo" drag  multiple
                               accept="image/png,image/jpg,image/jpeg"
                               :file-list="fileLists"
                               :on-preview="handlePreview"
                               :on-change="handleChange"
                               :on-remove="handleRemove"
                               ref="upload"
                               list-type="picture"
                               :auto-upload="false"
                               :limit="1"
                    >
<!--                        <img v-if="photourl" :src="photourl">-->
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                        <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
                    </el-upload>
                </el-form>
<!--            <div>识别结果：已通过</div>-->
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editTaskVisible = false">取 消</el-button>
                    <el-button type="primary" @click="submit">提交</el-button>
                </span>
            </template>
        </el-dialog>

        <el-dialog title="修改名称" v-model="nameVisible">
            <el-form >
                <div style="font-size: 16px;margin-bottom: 20px" >
                    <span v-show="!edit2">{{memName}}</span>
                    <el-input type="text" v-model="memName" v-show="edit2" style="width: 150px"></el-input>
                    <span style="margin-left: 20px" >
                            <el-button type="primary" :icon="{'el-icon-edit':!edit2,'el-icon-check':edit2}" circle size="mini" @click="edit2 = !edit2" ></el-button>
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
    import {reactive, ref, getCurrentInstance} from "vue"
    import {useRoute,useRouter} from "vue-router"
    import {ElMessage} from "element-plus"
    export default {
        name: "Agroup",
        path:"/add/:id",
        inject:['reload'],
        data(){
            return{
                resData:{
                    userId: localStorage.getItem("userid")
                },
                route:useRoute(),
                groupName: "",
                taskList: [],
                fileList: {
                    name:"",
                    url:"",
                },
                editTaskVisible:false,
                dialogtitle: "",
                task:{
                    id:"",
                    title:"",
                    description:"",
                    isFinish: false
                },
                photoU: "",
                nameVisible:false,
                memName:"",
                edit2:false,
            }
        },
        mounted(){
            //获取群组名字和任务列表
            this.$axios.get(
                "http://localhost:9000/add/getName",{
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
                "http://localhost:9000/add/getTask",{
                    params:{
                        id:this.route.params.id,
                        userid:localStorage.getItem("userId")
                    }
                }
            ).then((response) => {
                if (response.data !== null){
                    let task = {
                        id:0,
                        title:"",
                        isFinish:false,
                        isEnd:false
                    };
                    for (let i=0;i<response.data.length;i++){
                        task.id = response.data[i].taskid;
                        task.title = response.data[i].title;
                        if(response.data[i].ifend === 1){
                            task.isEnd = true
                        }
                        this.taskList.push(task);
                    }
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
            write(index,row){
                let idx = -1;
                //TODO 获取任务信息
                //TODO 有没有提交过的任务可能先查询图片进行一个显示
                idx = index;
                Object.keys(this.task).forEach( (item) =>{
                    this.task[item] = row[item];
                });
                this.dialogtitle = this.task.name;
                this.taskdes = this.task.description;
                this.editTaskVisible= true;
            },
            handleRemove(file, fileList) {

            },
            submit(res){
                const formData = new FormData();
                formData.append("file",)
                this.$refs.upload.submit();
                this.$refs.upload.clearFiles();
            },
            submitSuccess(res,file,fileList){
                this.$message({
                    message:"上传成功",
                    type:"success"
                });
                //this.editTaskVisible = false
            },
            submitPhoto(file){
                //TODO 提交代码
                this.editTaskVisible = false
            },
            altername(){
                this.$axios.get(
                    "http://localhost:9000/add/altername",{
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
                        "http://localhost:9000/add/alter",{
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
            }
        }
    }
</script>

<style scoped>
    .box-title{
        font-size: 18px;
        margin-bottom: 10px;
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

    .myDialogClass{
        padding: 0;
    }

    .isfinish{
        text-decoration: line-through;
        color: #999;
    }

</style>
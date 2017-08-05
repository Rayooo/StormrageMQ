<template>
    <section id="queue">
        <navigation father-component="queue"></navigation>

        <div style="margin-top: 20px;margin-bottom: 20px" >
            <el-button type="info" @click="dialogFormVisible = true">添加队列</el-button>
        </div>

        <div v-if="tableData.length < 1" style="text-align: center">您还没有创建队列</div>

        <h4 v-if="tableData.length > 0" style="text-align: center">队列(Queue) 列表</h4>

        <el-table v-if="tableData.length > 0" :data="tableData" stripe style="width: 100%"  >
            <el-table-column prop="name" label="名称" ></el-table-column>

            <el-table-column prop="addressList" label="监听的消费者" ></el-table-column>

            <el-table-column label="创建时间">
                <template scope="scope">
                    <el-icon name="time"></el-icon>
                    <span style="margin-left: 10px">{{scope.row.createTimeFormat}}</span>
                </template>
            </el-table-column>

            <el-table-column label="操作">
                <template scope="scope">
                    <el-button size="small" type="primary" @click="handleEdit(scope.row)">修改</el-button>
                    <el-button size="small" type="danger" @click="handleDelete(scope.row.id, scope.row.name)">删除</el-button>
                </template>
            </el-table-column>

        </el-table>



        <el-dialog title="添加队列" :visible.sync="dialogFormVisible">
            <el-form :model="form">
                <el-form-item label="队列名称(使用类似xxx.xxx.xx的格式)" >
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="addQueue()">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="修改队列" :visible.sync="dialogChangeFormVisible">
            <el-form :model="form">
                <el-form-item label="队列名称(使用类似xxx.xxx.xx的格式)" >
                    <el-input v-model="changeForm.name"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogChangeFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="changeQueue()">确 定</el-button>
            </div>
        </el-dialog>

    </section>
</template>

<script>
    import Global from "@/components/Global.vue"
    import Navigation from "../../components/Navigation.vue";
    import moment from "moment"

    export default {
        components: {Navigation},
        name:"Queue",
        data() {
            return {
                dialogFormVisible: false,
                tableData:[],
                form:{
                    name: ""
                },
                changeForm:{},
                dialogChangeFormVisible:false
            }
        },
        mounted() {
            this.getQueueList()
        },
        methods:{
            addQueue(){
                if(!this.form.name){
                    this.$message.warning("请填写队列名称");
                    return;
                }
                Global.post("/queue/addQueue", this.form, (res)=>{
                    this.dialogFormVisible = false;
                    if(res.body.code === 0){
                        this.$message.success("添加成功");
                        this.form = {name: ""};
                        this.getQueueList();
                    }
                    else{
                        this.$message.error(res.body.message);
                    }
                },() => {
                    this.dialogFormVisible = false;
                    this.$message.error("服务器异常");
                })
            },
            getQueueList(){
                Global.post("/queue/getQueueList", {}, (res)=>{
                    if(res.body.code === 0){
                        this.tableData = res.body.result;
                        for(let i = 0; i < this.tableData.length; ++i){
                            this.tableData[i].createTimeFormat = moment(this.tableData[i].createTime).format("YYYY-MM-DD HH:mm:ss");
                        }
                    }
                })
            },
            handleEdit(data){
                this.changeForm = data;
                this.dialogChangeFormVisible = true;
            },
            handleDelete(id, name){
                this.$confirm('此操作将删除' + name + ', 是否继续?', '提示', {
                    confirmButtonText: '删除',
                    cancelButtonText: '取消',
                    type: 'warning',
                    confirmButtonClass: "el-button--danger"
                }).then(() => {
                    Global.post("/queue/deleteQueue", {id:id}, (res) => {
                        if(res.body.code === 0){
                            this.$message.success("删除成功");
                            this.getQueueList();
                        }
                        else{
                            this.$message.error(res.body.message);
                        }
                    }, () => {
                        this.$message.error("服务器异常");
                    })
                }).catch(()=>{});
            },
            changeQueue(){
                if(!this.changeForm.name){
                    this.$message.warning("请填写队列名称");
                    return;
                }

                Global.post("/queue/changeQueue",this.changeForm, (res)=>{
                    this.dialogChangeFormVisible = false;
                    if(res.body.code === 0){
                        this.$message.success("修改成功");
                        this.changeForm = {};
                    }
                    else{
                        this.$message.error(res.body.message);
                    }
                    this.getQueueList();
                }, ()=>{
                    this.dialogChangeFormVisible = false;
                    this.$message.error("服务器异常");
                    this.getQueueList();
                })

            }
        }
    }
</script>

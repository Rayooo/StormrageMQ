<template>
    <section id="queue">
        <navigation father-component="queue"></navigation>

        <div style="margin-top: 20px;margin-bottom: 20px" >
            <el-button type="info" @click="dialogFormVisible = true">添加队列</el-button>
        </div>

        <div v-if="tableData.length < 1" style="text-align: center">您还没有创建队列</div>





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

    </section>
</template>

<script>
    import Global from "@/components/Global.vue"
    import Navigation from "../../components/Navigation.vue";

    export default {
        components: {Navigation},
        name:"Queue",
        data() {
            return {
                dialogFormVisible: false,
                tableData:[],
                form:{
                    name: ""
                }
            }
        },
        mounted() {
            this.msg = "这是队列";
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
                    }
                    else{
                        this.$message.error(res.body.message);
                    }
                },() => {
                    this.dialogFormVisible = false;
                    this.$message.error("服务器异常");
                })
            }
        }
    }
</script>

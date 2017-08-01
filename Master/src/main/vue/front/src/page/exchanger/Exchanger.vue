<template>
    <section id="exchanger">
        <navigation father-component="exchanger"></navigation>

        <div style="margin-top: 20px;margin-bottom: 20px" >
            <el-button type="info" @click="dialogFormVisible = true">添加交换器</el-button>
        </div>

        <div v-if="tableData.length < 1" style="text-align: center">您还没有创建交换器</div>

        <h4 v-if="tableData.length > 0" style="text-align: center">交换器列表</h4>
        <el-table v-if="tableData.length > 0" :data="tableData" stripe style="width: 100%" >
            <el-table-column prop="name" label="名称" ></el-table-column>
            <el-table-column prop="type" label="类型" >
                <template scope="scope">
                    <span v-if="scope.row.type == '1'">Direct交换器</span>
                    <span v-if="scope.row.type == '2'">Fanout交换器</span>
                    <span v-if="scope.row.type == '3'">Topic交换器</span>
                </template>
            </el-table-column>
            <el-table-column prop="content" label="内容"></el-table-column>
            <el-table-column label="创建时间">
                <template scope="scope">
                    <el-icon name="time"></el-icon>
                    <span style="margin-left: 10px">{{scope.row.createTimeFormat}}</span>
                </template>
            </el-table-column>

            <el-table-column label="操作">
                <template scope="scope">
                    <el-button size="small" type="primary" @click="handleEdit(scope.row.id, scope.row.name)">修改</el-button>
                    <el-button size="small" type="danger" @click="handleDelete(scope.row.id, scope.row.name)">删除</el-button>
                </template>
            </el-table-column>

        </el-table>


        <el-dialog title="添加交换器" :visible.sync="dialogFormVisible">
            <el-form :model="form">
                <el-form-item label="交换器名称" >
                    <el-input v-model="form.name" auto-complete="off"></el-input>
                </el-form-item>

                <el-form-item label="交换器类型" >
                    <el-select v-model="form.type" placeholder="请选择交换器类型">
                        <el-option v-for="exchangerType in exchangerTypeList" :label=exchangerType.describe :value=exchangerType.type :key=exchangerType.type></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="请输入Routing Key" v-if="form.type == 1">
                    <el-input v-model="form.content"></el-input>
                </el-form-item>

                <el-form-item label="请依次输入MQ的名称，以英文逗号分割" v-if="form.type == 2">
                    <el-input v-model="form.content"></el-input>
                </el-form-item>

                <el-form-item label="请输入匹配MQ的名称的正则表达式" v-if="form.type == 3">
                    <el-input v-model="form.content"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="addExchanger()">确 定</el-button>
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
        name:"Exchanger",
        data() {
            return {
                tableData: [],
                dialogFormVisible: false,
                form: {
                    name: "",
                    type: "",
                    content: ""
                },
                exchangerTypeList:[]
            }
        },
        mounted() {
            Global.post("/exchanger/listExchangerTypes", {}, (response)=> {
                this.exchangerTypeList = response.body.result;
            });
            this.getExchangerList()



        },
        methods: {
            addExchanger(){
                if(!this.form.type || !this.form.name || !this.form.content){
                    this.$message.warning("请填写完所有的内容");
                    return;
                }
                Global.post("/exchanger/addExchanger", this.form, (response) => {
                    this.dialogFormVisible = false;
                    if(response.body.code === 0){
                        this.$message.success("添加成功");
                        this.form = {name: "", type: "", content: ""}
                        this.getExchangerList()
                    }
                    else{
                        this.$message.error(response.body.message);
                    }
                }, ()=>{
                    this.dialogFormVisible = false;
                    this.$message.error("服务器异常");
                })
            },
            getExchangerList(){
                Global.post("/exchanger/getExchangerList", {}, (res) => {
                    if(res.body.code === 0){
                        this.tableData = res.body.result;
                        for(let i = 0; i < this.tableData.length; ++i){
                            this.tableData[i].createTimeFormat = moment(this.tableData[i].createTime).format("YYYY-MM-DD HH:mm:ss");
                        }
                    }
                });
            },
            handleDelete(id, name){
                this.$confirm('此操作将删除' + name + ', 是否继续?', '提示', {
                    confirmButtonText: '删除',
                    cancelButtonText: '取消',
                    type: 'warning',
                    confirmButtonClass: "el-button--danger"
                }).then(() => {
                    Global.post("/exchanger/deleteExchanger", {id:id}, (res) => {
                        if(res.body.code === 0){
                            this.$message.success("删除成功");
                            this.getExchangerList();
                        }
                        else{
                            this.message.error(res.body.message);
                        }
                    }, () => {
                        this.$message.error("服务器异常");
                    })
                }).catch(()=>{});
            }
        }

    }
</script>

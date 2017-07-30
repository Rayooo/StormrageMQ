<template>
    <section id="exchanger">
        <navigation father-component="exchanger"></navigation>

        <div style="margin-top: 20px;margin-bottom: 20px" >
            <el-button @click="dialogFormVisible = true">添加交换器</el-button>
        </div>

        <el-table
            :data="tableData"
            stripe
            style="width: 100%">
            <el-table-column
                prop="date"
                label="日期"
                width="180">
            </el-table-column>
            <el-table-column
                prop="name"
                label="姓名"
                width="180">
            </el-table-column>
            <el-table-column
                prop="address"
                label="地址">
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
                <el-button type="primary" @click="dialogFormVisible = false;addExchanger()">确 定</el-button>
            </div>
        </el-dialog>

    </section>
</template>

<script>
    import Global from "@/components/Global.vue"
    import Navigation from "../../components/Navigation.vue";

    export default {
        components: {Navigation},
        name:"Exchanger",
        data() {
            return {
                tableData: [{
                    date: '2016-05-02',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1518 弄'
                }, {
                    date: '2016-05-04',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1517 弄'
                }, {
                    date: '2016-05-01',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1519 弄'
                }, {
                    date: '2016-05-03',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1516 弄'
                }],

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
            }, ()=>{})
        },
        methods: {
            addExchanger(){
                Global.post("/exchanger/addExchanger", this.form, (response) => {
                    if(response.body.code === 0){
                        this.$message.success("添加成功");
                    }
                    else{
                        this.$message.error(response.body.message);
                    }
                }, ()=>{
                    this.$message.error("服务器异常");
                })
            }
        }
    }
</script>

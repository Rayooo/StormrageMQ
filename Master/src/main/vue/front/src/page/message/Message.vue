<template>
    <section id="message">
        <navigation father-component="message"></navigation>

        <div v-if="tableData.length < 1" style="text-align: center">暂时还没有消息</div>

        <h4 v-if="tableData.length > 0" style="text-align: center">消息(Message) 列表</h4>

        <el-table v-if="tableData.length > 0" :data="tableData" stripe style="width: 100%" v-on:row-click="rowClick">

            <el-table-column prop="uuid" label="UUID" ></el-table-column>

            <el-table-column prop="type" label="消息类型" >
                <template scope="scope">
                    <span v-if="scope.row.type == '1'">普通消息</span>
                    <span v-if="scope.row.type == '2'">重要消息</span>
                    <span v-if="scope.row.type == '0'">认证消息</span>
                </template>
            </el-table-column>

            <el-table-column prop="content" label="内容" >
                <template scope="scope">
                    <span v-if="scope.row.type == '1' || scope.row.type == '2'">{{scope.row.content}}</span>
                    <span v-if="scope.row.type == '0'">/</span>
                </template>
            </el-table-column>

            <el-table-column prop="exchangerName" label="交换器名称" >
                <template scope="scope">
                    <span v-if="scope.row.type == '1' || scope.row.type == '2'">{{scope.row.exchangerName}}</span>
                    <span v-if="scope.row.type == '0'">/</span>
                </template>
            </el-table-column>

            <el-table-column label="创建时间">
                <template scope="scope">
                    <el-icon name="time"></el-icon>
                    <span style="margin-left: 10px">{{scope.row.createTimeFormat}}</span>
                </template>
            </el-table-column>


        </el-table>

        <div class="block" style="text-align: center" >
            <el-pagination
                layout="prev, pager, next"
                v-on:current-change = "currentPage"
                :total="totalPage">
            </el-pagination>
        </div>

        <el-dialog title="详细信息" :visible.sync="messageDetailVisible">
            <el-form :model="messageDetail">
                <!--<el-form-item label="交换器名称:" >-->
                    <!--<span>{{exchangerDetail.name}}</span>-->
                <!--</el-form-item>-->

                <!--<el-form-item label="匹配到的队列:" >-->
                    <!--<br/>-->
                    <!--<ul>-->
                        <!--<li v-for="d in exchangerDetail.queueList">-->
                            <!--{{ d }}-->
                        <!--</li>-->
                    <!--</ul>-->
                <!--</el-form-item>-->


            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="messageDetailVisible = false">确 定</el-button>
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
        name:"Message",
        data() {
            return {
                tableData:[],
                totalPage:0,
                messageDetailVisible: false,
                messageDetail: {}
            }
        },
        mounted() {
            Global.post("/message/getMessageList", {pageIndex : 1}, (res)=>{
                if(res.body.code === 0){
                    console.log(res.body);
                    this.totalPage = res.body.result.totalRecords;
                    this.tableData = res.body.result.pageData;
                    for(let i = 0; i < this.tableData.length; ++i){
                        this.tableData[i].createTimeFormat = moment(this.tableData[i].createTime).format("YYYY-MM-DD HH:mm:ss");
                    }
                }
            })

        },
        methods:{
            currentPage(currentPage){
                Global.post("/message/getMessageList", {pageIndex : currentPage}, (res)=>{
                    if(res.body.code === 0){
                        console.log(res.body);
                        this.totalPage = res.body.result.totalRecords;
                        this.tableData = res.body.result.pageData;
                        for(let i = 0; i < this.tableData.length; ++i){
                            this.tableData[i].createTimeFormat = moment(this.tableData[i].createTime).format("YYYY-MM-DD HH:mm:ss");
                        }
                    }
                })
            },
            rowClick(row, event, column){
                console.log(row);
                //展开row的详细信息
                this.messageDetailVisible = true
            }

        }

    }
</script>

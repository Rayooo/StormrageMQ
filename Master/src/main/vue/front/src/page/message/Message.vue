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

        <el-dialog title="详细信息" :visible.sync="messageDetailVisible" >
            <el-form :model="messageDetail">
                <el-form-item style="margin-bottom: 0">
                    <span v-if="messageDetail.type == '1'"><el-tag type="info" >普通消息</el-tag></span>
                    <span v-if="messageDetail.type == '2'"><el-tag type="danger" >重要消息</el-tag></span>
                    <span v-if="messageDetail.type == '0'"><el-tag type="warning">认证消息</el-tag></span>
                </el-form-item>


                <div v-if="messageDetail.type == '0'">
                    <el-form-item  label="uuid:" style="margin-bottom: 0">
                        <span>{{messageDetail.uuid}}</span>
                    </el-form-item>

                    <el-form-item  label="类型:" style="margin-bottom: 0">
                        <span v-if="messageDetail.clientType == '1'">生产者</span>
                        <span v-if="messageDetail.clientType == '2'">消费者</span>
                    </el-form-item>

                    <el-form-item  label="服务器名称:" style="margin-bottom: 0">
                        <span>{{messageDetail.clientName}}</span>
                    </el-form-item>

                    <el-form-item  label="时间:" style="margin-bottom: 0">
                        <span>{{messageDetail.createTimeFormat}}</span>
                    </el-form-item>
                    <el-form-item  label="用户名:" style="margin-bottom: 0">
                        <span>{{messageDetail.userName}}</span>
                    </el-form-item>
                </div>


                <div v-if="messageDetail.type == '1' || messageDetail.type == '2'">
                    <el-form-item  label="uuid:" style="margin-bottom: 0">
                        <span>{{messageDetail.uuid}}</span>
                    </el-form-item>

                    <el-form-item  label="消息内容:" style="margin-bottom: 0">
                        <span>{{messageDetail.content}}</span>
                    </el-form-item>

                    <el-form-item  label="投递的交换器:" style="margin-bottom: 0">
                        <span>{{messageDetail.exchangerName}}</span>
                    </el-form-item>

                    <el-form-item  label="时间:" style="margin-bottom: 0">
                        <span>{{messageDetail.createTimeFormat}}</span>
                    </el-form-item>


                    <div v-for="o in queueMessageList" style="margin-bottom: 5px">
                        <el-card class="box-card">
                            <div v-if="o.received === true && o.sending === true">
                                <el-tag type="success">发送成功</el-tag>
                            </div>

                            <div v-if="o.received === false && o.sending === true">
                                <el-tag type="warning">发送中，消费者未接收</el-tag>
                            </div>

                            <div v-if="o.received === false && o.sending === false">
                                <el-tag type="info">未发送</el-tag>
                            </div>

                            <el-form-item  label="id:" style="margin-bottom: 0">
                                <span>{{o.id}}</span>
                            </el-form-item>

                            <el-form-item  label="队列名称:" style="margin-bottom: 0">
                                <span>{{o.queueName}}</span>
                            </el-form-item>

                            <el-form-item  label="消费者名称:" style="margin-bottom: 0">
                                <span>{{o.consumerName}}</span>
                            </el-form-item>

                        </el-card>
                    </div>



                </div>



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
                messageDetail: {},
                queueMessageList:[]
            }
        },
        mounted() {
            Global.post("/message/getMessageList", {pageIndex : 1}, (res)=>{
                if(res.body.code === 0){
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
                this.messageDetail = row;
                console.log(row)
                Global.post("/message/getQueueMessageList", {uuid: row.uuid}, (res)=>{
                    if(res.body.code === 0){
                        console.log(res.body)
                        this.queueMessageList = res.body.result;
                        console.log(this.queueMessageList)
                        //展开row的详细信息
                        this.messageDetailVisible = true
                    }
                })




            }

        }

    }
</script>

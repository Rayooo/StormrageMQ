<template>
    <section id="admin">
        <navigation father-component="admin"></navigation>

        <div style="text-align: center;margin-top: 20px">
            <chart :options="polar" style="height: 500px;width: 80%;margin-left: 10%"></chart>
        </div>

    </section>
</template>

<style>
</style>

<script>
    import Global from "../components/Global.vue"
    import Navigation from "../components/Navigation.vue";
    import ECharts from 'vue-echarts/components/ECharts'
    import moment from "moment"

    export default {
        components: {Navigation, ECharts},
        name:"Admin",
        data: function () {
            return {
                polar: {
                    title: {
                        text: '每日消息数量折线图'
                    },
                    tooltip:{
                        trigger: 'axis',
                        axisPointer: {
                            type: 'line',
                            label: {
                                backgroundColor: '#6a7985'
                            }
                        }
                    },
                    legend: {
                        data:['消息数量', '队列剩余消息数量', '队列已发送消息数量']
                    },
                    xAxis: {
                        type: 'category',
                        data: [],
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        name: '消息数量',
                        data: [],
                        type: 'line',
                        smooth: true
                    }, {
                        name: '队列剩余消息数量',
                        data: [],
                        type: 'line',
                        smooth: true
                    }, {
                        name: '队列已发送消息数量',
                        data: [],
                        type: 'line',
                        smooth: true
                    },],
                    color:['#c23531', '#61a0a8',  '#ca8622', '#bda29a','#6e7074', '#546570', '#c4ccd3']

                }

            }
        },
        mounted() {
            this.getMessageCount();
            setInterval(this.getMessageCount, 5000);
        },
        methods:{
            getMessageCount(){
                Global.post("/statistic/messageCount", {statisticsType: "EVERY_DAY_MESSAGE_COUNT"}, (res)=>{
                    if(res.body.code === 0){
                        var list = res.body.result;
                        var xData = [];
                        var sData = [];
                        for(var i = 0; i < list.length; i++) {
                            xData.push(moment(list[i].statisticsTime).format("YYYY-MM-DD"));
                            sData.push(list[i].count);
                        }
                        this.polar.xAxis.data = xData;
                        this.polar.series[0].data = sData;
                    }
                });

                Global.post("/statistic/messageCount", {statisticsType: "EVERY_DAY_QUEUE_MESSAGE_UNSEND"}, (res)=>{
                    if(res.body.code === 0){
                        var list = res.body.result;
                        var xData = [];
                        var sData = [];
                        for(var i = 0; i < list.length; i++) {
                            xData.push(moment(list[i].statisticsTime).format("YYYY-MM-DD"));
                            sData.push(list[i].count);
                        }
                        this.polar.series[1].data = sData;
                    }
                })

                Global.post("/statistic/messageCount", {statisticsType: "EVERY_DAY_QUEUE_MESSAGE_SEND"}, (res)=>{
                    if(res.body.code === 0){
                        var list = res.body.result;
                        var xData = [];
                        var sData = [];
                        for(var i = 0; i < list.length; i++) {
                            xData.push(moment(list[i].statisticsTime).format("YYYY-MM-DD"));
                            sData.push(list[i].count);
                        }
                        this.polar.series[2].data = sData;
                    }
                })

            },
        }
    }
</script>

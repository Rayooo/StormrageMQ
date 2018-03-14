<template>
    <section id="admin">
        <navigation></navigation>

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
                    tooltip:{},
                    legend: {
                        data:['消息数量']
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
                        type: 'line'
                    }]

                }

            }
        },
        mounted() {
            this.getMessageCount()
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

                        console.log(xData)
                        console.log(sData)

                    }
                })
            },
        }
    }
</script>

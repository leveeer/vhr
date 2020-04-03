<template>

    <div>
    <div id="depNum" :style="{width: '1000px', height: '300px'}"></div>
    <div id="degree" :style="{width: '1000px', height: '300px', marginTop:'30px', }"></div>
    </div>
</template>

<script>

    export default {
        name: "StaAll",
        data() {
            return {
                depNumOption:{
                    color: ['#3398DB'],
                    title: {
                        text: '各部门人数统计柱状图',
                        left:'center',
                    },
                    tooltip: {},
                    legend: {
                        data:['该部门人数']
                    },
                    xAxis: {
                        data: [],
                        axisLabel: {
                            interval:0,
                            rotate:45, //代表逆时针旋转45度
                            textStyle: {
                                fontSize: 12,//字体大小
                            }
                        }
                    },
                    yAxis: {},
                    series: [{
                        name: '人数',
                        type: 'bar',
                        barWidth : 30,
                        data: []
                    }]
                },
                degreeOption:{
                    color: ['#3398DB'],
                    title: {
                        text: '公司各学历人数统计折线图',
                        left:'center',
                    },
                    tooltip: {},
                    legend: {
                        data:['该学历人数']
                    },
                    xAxis: {
                        data: [],
                        axisLabel: {
                            interval:0,
                            //rotate:45, //代表逆时针旋转45度
                            textStyle: {
                                fontSize: 12,//字体大小
                            }
                        }
                    },
                    yAxis: {},
                    series: [{
                        name: '人数',
                        type: 'line',
                        //barWidth : 30,
                        data: []
                    }]
                },
            }
        },
        mounted() {
            //加载柱状图
            this.getRequest('/employee/statistics/deps').then(resp => {
                if (resp) {
                    const deps = [];
                    const num = [];
                    for (const i in resp) {
                        deps.push(resp[i].name);
                        num.push(resp[i].num);
                    }
                    this.depNumOption.xAxis.data = deps;
                    this.depNumOption.series[0].data = num;
                    //console.log(this.option.xAxis.data);
                    //console.log(this.option.series[0].data);
                    //alert(this.option.xAxis.data);
                    const depDom = document.getElementById('depNum');
                    const depNumChart = this.echarts.init(depDom);
                    // 绘制图表
                    depNumChart.setOption(this.depNumOption);
                } else {
                    this.$message({
                        type: "error",
                        message: "数据加载失败",
                    })
                }
            })

            this.getRequest('/employee/statistics/degree').then(resp => {
                if (resp) {
                    const degrees = [];
                    const num = [];
                    for (const i in resp) {
                        degrees.push(resp[i].tiptopDegree);
                        num.push(resp[i].num);
                    }
                    this.degreeOption.xAxis.data = degrees;
                    this.degreeOption.series[0].data = num;
                    //console.log(this.option.xAxis.data);
                    //console.log(this.option.series[0].data);
                    //alert(this.option.xAxis.data);
                    const degreeDom = document.getElementById('degree');
                    const degreeChart = this.echarts.init(degreeDom);
                    // 绘制图表
                    degreeChart.setOption(this.degreeOption);
                } else {
                    this.$message({
                        type: "error",
                        message: "数据加载失败",
                    })
                }
            })

        },

        methods : {

        }
    }
</script>

<style scoped>

</style>
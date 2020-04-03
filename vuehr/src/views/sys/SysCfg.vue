<template>
    <div>
        <el-divider><h2>系统详细信息</h2></el-divider>
        <div style="">
                <el-row :gutter="12">
                    <el-col :span="8" style="">
                        <el-card shadow="hover" class="box-card">
                            <div slot="header" style="text-align: center" class="clearfix">
                                <span>CPU信息</span>
                            </div>
                            <div style="text-align: center;font-size: 16px">
                                <div>核心数: <el-tag>{{serverInfo.cpu.cpuNum}}个</el-tag></div>
                                <div>用户使用率: <el-tag>{{serverInfo.cpu.used + '%'}}</el-tag></div>
                                <div>系统使用率: <el-tag>{{serverInfo.cpu.sys + '%'}}</el-tag></div>
                                <div>当前空闲率: <el-tag>{{serverInfo.cpu.free + '%'}}</el-tag></div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card shadow="hover" class="box-card">
                            <div slot="header" style="text-align: center" class="clearfix">
                                <span>内存信息</span>
                            </div>
                            <div style="text-align: center;font-size: 16px">
                                <div>总内存: <el-tag>{{serverInfo.mem.total + 'G'}}</el-tag></div>
                                <div>已用内存: <el-tag>{{serverInfo.mem.used + 'G'}}</el-tag></div>
                                <div>剩余内存: <el-tag>{{serverInfo.mem.free + 'G'}}</el-tag></div>
                                <div>使用率: <el-tag>{{serverInfo.mem.usage + '%'}}</el-tag></div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card shadow="hover" class="box-card">
                            <div slot="header" style="text-align: center" class="clearfix">
                                <span>JVM信息</span>
                            </div>
                            <div style="text-align: center;font-size: 16px">
                                <div>总内存: <el-tag>{{serverInfo.jvm.total + 'M'}}</el-tag></div>
                                <div>已用内存: <el-tag>{{serverInfo.jvm.used + 'M'}}</el-tag></div>
                                <div>剩余内存: <el-tag>{{serverInfo.jvm.free + 'M'}}</el-tag></div>
                                <div>使用率: <el-tag>{{serverInfo.jvm.usage + '%'}}</el-tag></div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
            </div>
        <div style="">
                <el-row :gutter="12">
                    <el-col :span="12" style="">
                        <el-card shadow="hover" class="box-card">
                            <div slot="header" style="text-align: center" class="clearfix">
                                <span>服务器信息</span>
                            </div>
                            <div style="text-align: center;font-size: 16px">
                                <div>服务器名称: <el-tag>{{serverInfo.sys.computerName}}</el-tag></div>
                                <div>操作系统: <el-tag>{{serverInfo.sys.osName}}</el-tag></div>
                                <div>服务器IP: <el-tag>{{serverInfo.sys.computerIp}}</el-tag></div>
                                <div>系统架构: <el-tag>{{serverInfo.sys.osArch}}</el-tag></div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="12">
                        <el-card shadow="hover" class="box-card">
                            <div slot="header" style="text-align: center" class="clearfix">
                                <span>Java虚拟机信息</span>
                            </div>
                            <div style="text-align: center;font-size: 16px">
                                <div>Java名称: <el-tag>{{serverInfo.jvm.name}}</el-tag></div>
                                <div>Java版本: <el-tag>{{serverInfo.jvm.version}}</el-tag></div>
                                <div>启动时间: <el-tag>{{serverInfo.jvm.startTime}}</el-tag></div>
                                <div>运行时长: <el-tag>{{serverInfo.jvm.runTime}}</el-tag></div>
                                <div>安装路径: <el-tag>{{serverInfo.jvm.home}}</el-tag></div>
                                <div>项目路径: <el-tag>{{serverInfo.sys.userDir}}</el-tag></div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
            </div>
        <el-divider style="margin-top: 20px"><h3>磁盘状态</h3></el-divider>
        <!--<div>
            <h4 style="text-align: center">系统状态仪表盘</h4>
            <div id="serverChart"></div>
        </div>-->
        <template>
            <el-table
                    :data="sysFiles"
                    border
                    style="width: 100%">
                <el-table-column
                        prop="dirName"
                        label="盘符路径"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="sysTypeName"
                        label="文件系统"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="typeName"
                        width="180"
                        label="盘符类型">
                </el-table-column>
                <el-table-column
                        prop="total"
                        label="总大小"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="free"
                        label="可用大小"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="used"
                        label="已用大小"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="usage"
                        label="已用百分比"
                        width="140">
                </el-table-column>
            </el-table>
        </template>
    </div>
</template>

<script>
    export default {
        name: "SysCfg",
        data() {
            return {
                serverInfo: {},
                sysFiles:[],
                /*option: {
                    backgroundColor: 'rgba(255,255,255,0)',
                    tooltip: {
                        formatter: "{a} <br/>{c} {b}"
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            dataView: {show: false, readOnly: true},
                            restore: {show: false},
                            saveAsImage: {show: false}
                        }
                    },
                    grid: {
                        backgroundColor: '#fff',
                        width: "20%", //图例宽度
                        height: "40%", //图例高度
                    },
                    series: [
                        {
                            name: '内存使用率',
                            type: 'gauge',
                            z: 3,
                            min: 0,
                            max: 100,
                            splitNumber: 10,
                            radius: '50%',
                            axisLine: {            // 坐标轴线
                                lineStyle: {       // 属性lineStyle控制线条样式
                                    width: 10
                                }
                            },
                            axisLabel: {
                                textStyle: {
                                    fontSize: 14,
                                }
                            },

                            axisTick: {            // 坐标轴小标记
                                length: 15,        // 属性length控制线长
                                lineStyle: {       // 属性lineStyle控制线条样式
                                    color: 'auto'
                                },

                            },
                            splitLine: {           // 分隔线
                                length: 20,         // 属性length控制线长
                                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                                    color: 'auto'
                                }
                            },
                            title: {
                                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                    fontWeight: 'bolder',
                                    fontSize: 10,
                                    fontStyle: 'italic'
                                }
                            },
                            detail: {
                                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                    fontWeight: 'bolder',
                                    fontSize: 20,
                                }
                            },
                            data: [{value: 0, name: '内存使用率（%）'}]
                        },
                        {
                            name: 'CPU使用率',
                            type: 'gauge',
                            center: ['22%', '55%'],    // 默认全局居中
                            radius: '40%',
                            min: 0,
                            max: 100,
                            endAngle: 45,
                            splitNumber: 10,
                            axisLine: {            // 坐标轴线
                                lineStyle: {       // 属性lineStyle控制线条样式
                                    width: 8
                                }
                            },

                            axisLabel: {
                                textStyle: {
                                    fontSize: 8,
                                }
                            },
                            axisTick: {            // 坐标轴小标记
                                length: 12,        // 属性length控制线长
                                lineStyle: {       // 属性lineStyle控制线条样式
                                    color: 'auto'
                                }
                            },
                            splitLine: {           // 分隔线
                                length: 20,         // 属性length控制线长
                                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                                    color: 'auto'
                                }
                            },
                            pointer: {
                                width: 5
                            },
                            title: {
                                offsetCenter: [0, '-30%'],       // x, y，单位px
                                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                    fontWeight: 'bolder',
                                    fontSize: 8,
                                    fontStyle: 'italic'
                                }
                            },
                            detail: {
                                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                    fontWeight: 'bolder',
                                    fontSize: 16
                                }
                            },
                            data: [{value: 50, name: 'CPU使用率（%）'}]
                        },
                        {
                            name: 'JVM使用率',
                            type: 'gauge',
                            center: ['79%', '55%'],    // 默认全局居中
                            radius: '40%',
                            min: 0,
                            max: 100,
                            startAngle: 140,
                            endAngle: -45,
                            splitNumber: 10,
                            axisLine: {            // 坐标轴线
                                lineStyle: {       // 属性lineStyle控制线条样式
                                    width: 8
                                }
                            },
                            axisLabel: {
                                textStyle: {
                                    fontSize: 8,
                                }
                            },
                            axisTick: {            // 坐标轴小标记
                                length: 12,        // 属性length控制线长
                                lineStyle: {       // 属性lineStyle控制线条样式
                                    color: 'auto'
                                }
                            },
                            splitLine: {           // 分隔线
                                length: 20,         // 属性length控制线长
                                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                                    color: 'auto'
                                }
                            },
                            pointer: {
                                width: 5
                            },
                            title: {
                                offsetCenter: [0, '-30%'],       // x, y，单位px
                                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                    fontWeight: 'bolder',
                                    fontSize: 8,
                                    fontStyle: 'italic'
                                }
                            },
                            detail: {
                                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                    fontWeight: 'bolder',
                                    fontSize: 16,
                                }
                            },
                            data: [{value: 86.87, name: 'JVM使用率（%）'}]
                        }
                    ]
                },*/

            }
        },

        methods: {
            getServerInfo() {
                const serverChartDom = document.getElementById('serverChart');
                const serverChart = this.echarts.init(serverChartDom);
                // 绘制图表
                let url = "/employee/server/";
                this.getRequest(url).then(resp => {
                    if (resp) {
                        this.option.series[0].data[0].value = resp[0].toFixed(2);
                        this.option.series[1].data[0].value = resp[1].toFixed(2);
                        this.option.series[2].data[0].value = resp[2].toFixed(2);
                        console.log(this.option.series[0].data[0]);
                        console.log(this.option.series[1].data[0]);
                        console.log(this.option.series[2].data[0]);
                        serverChart.setOption(this.option, true);
                    }
                })

            },

            getAllInfo() {
                this.getRequest("/employee/server/getAllInfo/").then(resp => {
                    if (resp) {
                        this.serverInfo = resp;
                        //alert(resp.sysFiles);
                        //alert(resp.sysFiles.dirName)
                        this.sysFiles = resp.sysFiles;
                        //alert(this.sysFiles);
                    }
                })
            }

        },

        mounted() {
            this.getAllInfo();
            //this.getServerInfo();
            //setInterval(this.getServerInfo,5000);
        }

    }
</script>

<style>
    #serverChart{
        width:1000px;
        height: 300px;
       /* margin-left: 100px;
        margin-top: -110px*/
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
        height: 230px;
    }
</style>
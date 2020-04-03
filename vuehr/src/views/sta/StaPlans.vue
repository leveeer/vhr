<template>
    <div>
        <!--人事信息统计-->
        <div>
            <el-calendar id="calendar" first-day-of-week="7" >
                <!-- 这里使用的是 2.5 slot 语法，对于新项目请使用 2.6 slot 语法-->
                <template
                        slot="dateCell"
                        slot-scope="{date, data}">
                    <p id="day"  @click="addPlan(data)">
                        {{ data.day.split('-').slice(2).join('-') }}<br>
                        <span style="color: #f4f4f4;background-color:#e30007 ">{{dealMyDate(data.day)}}</span>
                    </p>
                </template>
            </el-calendar>
        </div>
        <!--添加员工培训信息-->
        <el-dialog
                :title="title"
                :visible.sync="dialogVisible"
                width="50%">
            <div>
                <el-form :model="plan" :rules="rules" ref="planForm">
                    <el-row>
                        <el-col :span="24">
                            <el-form-item label="计划内容:" prop="plan">
                                <el-input size="mini"
                                          type="textarea"
                                          autosize
                                          style="width: 350px"
                                          placeholder="请输入计划内容"
                                          v-model="plan.plan"
                                          prefix-icon="el-icon-edit">
                                </el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="24">
                            <el-form-item label="日期:" prop="planDate">
                                <el-input size="mini"
                                          style="width: 150px"
                                          v-model="plan.planDate"
                                          readonly
                                          prefix-icon="el-icon-edit">
                                </el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="24">
                            <el-form-item label="添加人:" prop="adduser">
                                <el-input size="mini"
                                          style="width: 150px"
                                          v-model="plan.adduser"
                                          readonly
                                          prefix-icon="el-icon-edit">
                                </el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="24">
                            <el-form-item label="备注:" prop="remark">
                                <el-input size="mini"
                                          type="textarea"
                                          autosize
                                          style="width: 350px"
                                          placeholder="备注(选填)"
                                          v-model="plan.remark"
                                          prefix-icon="el-icon-edit">
                                </el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doAddPlan">确 定</el-button>
    </span>
        </el-dialog>
    </div>



</template>

<script>
    export default {
        name: "StaPlans",
        data() {
            return {
                title: '',
                dialogVisible: false,
                value: new Date(),
                date:"",
                plans:[],
                userName: "",
                plan: {
                    planDate: "",
                    plan: "",
                    adduser:"",
                    remark: ""
                },
                resDate: [],

                rules: {
                    plan: [{required: true, message: '请输入计划内容', trigger: 'blur'}],
                    remark: [{required: false, message: '', trigger: 'blur'}],
                }
            }
        },

        mounted() {
            this.getAllPlan();
        },

        methods: {

            emptyPlan() {
                this.plan = {
                    planDate: "",
                    plan: "",
                    adduser:"",
                    remark: ""
                }
            },

            addPlan(data) {
                //alert(data.day);
                this.emptyPlan();
                this.title = '添加计划安排';
                this.dialogVisible = true;
                const user = JSON.parse(window.sessionStorage.getItem("user"));
                this.plan.adduser = user.name;
                this.plan.planDate = data.day;
                //alert(user.name);
            },

            doAddPlan() {
                //alert("添加");
                this.$refs['planForm'].validate(valid => {
                    if (valid) {
                        //alert("55555555555");
                        this.postRequest("/employee/staplans/", this.plan).then(resp => {
                            if (resp) {
                                this.dialogVisible = false;
                                this.getAllPlan();
                            }
                        })
                    }
                });
            },

            dealMyDate(v) {
                console.log(v)
                let len = this.resDate.length
                let res = ""
                for (let i = 0; i < len; i++) {
                    if (this.resDate[i].planDate == v) {
                        res = this.resDate[i].plan
                        break
                    }
                }
                return res
            },

            getAllPlan(){
                this.getRequest("/employee/staplans/").then(resp => {
                    if (resp) {
                        this.resDate = resp.data;
                    }
                });
            }

        }
    }
</script>

<style scoped>

    .el-calendar{
        text-align: center;
        font-size: 12px;
        width: 1000px;
        height: 400px;
    }

    .is-selected {
        color: #fa271f;
    }

</style>
<template>
    <div>
        <div>
            <div style="display: flex;justify-content: space-between">
                <div>
                    <el-input placeholder="请输入员工名进行搜索，可以直接回车搜索..." prefix-icon="el-icon-search"
                              clearable
                              @clear="initPerTrain"
                              style="width: 350px;margin-right: 10px" v-model="keyword"
                              @keydown.enter.native="initPerTrain"></el-input>
                    <el-button icon="el-icon-search" type="primary" @click="initPerTrain">
                        搜索
                    </el-button>
                </div>
                <div>
                    <el-button type="primary" icon="el-icon-plus" @click="showAddPerTrainView">
                        添加员工培训信息
                    </el-button>
                </div>
            </div>
        </div>
        <div style="margin-top: 10px">
            <el-table
                    :data="trains"
                    stripe
                    border
                    v-loading="loading"
                    element-loading-text="正在加载..."
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="employee.name"
                        fixed
                        align="left"
                        label="姓名"
                        width="90">
                </el-table-column>
                <el-table-column
                        prop="department.name"
                        fixed
                        width="100"
                        align="left"
                        label="所属部门">
                </el-table-column>
                <el-table-column
                        prop="employee.email"
                        align="left"
                        label="邮箱"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="employee.phone"
                        width="100"
                        align="left"
                        label="电话号码">
                </el-table-column>
                <el-table-column
                        prop="traindate"
                        label="培训日期"
                        align="left"
                        width="185">
                </el-table-column>
                <el-table-column
                        prop="traincontent"
                        label="培训内容"
                        align="left"
                        width="300">
                </el-table-column>
                <el-table-column
                        prop="remark"
                        label="备注"
                        align="left"
                        width="300">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="200"
                        label="操作">
                    <template slot-scope="scope">
                        <!--<el-button @click="showEditPerecView(scope.row)" style="padding: 3px" size="mini">编辑</el-button>-->
                        <!--<el-button @click="showAdvEmpView(scope.row)" style="padding: 3px" size="mini" type="primary">查看奖罚详情</el-button>-->
                        <el-button @click="deletePerTrain(scope.row)" style="padding: 3px" size="mini" type="danger">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="display: flex;justify-content: flex-end">
                <el-pagination
                        background
                        @current-change="currentChange"
                        @size-change="sizeChange"
                        layout="sizes, prev, pager, next, jumper, ->, total, slot"
                        :total="total">
                </el-pagination>
            </div>
        </div>

        <!--添加员工培训信息-->
        <el-dialog
                :title="title"
                :visible.sync="dialogVisible"
                width="70%">
            <div>
                <el-form :model="train" :rules="rules" ref="trainForm">
                    <el-row>
                        <el-col :span="8">
                            <el-form-item label="员工编号:" prop="eid">
                                <el-input size="mini"
                                          :maxlength="9"
                                          style="width: 150px"
                                          prefix-icon="el-icon-edit"
                                          v-model.number="train.eid"
                                          placeholder="请输入员工编号"
                                          @change="getEmpInfo(train.eid)"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="8" v-show="showName">
                            <el-form-item label="员工姓名:">
                                <el-input size="mini"
                                          style="width: 150px"
                                          prefix-icon="el-icon-edit"
                                          v-model="ename" readonly></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8" v-show="showDepartment">
                            <el-form-item label="所在部门:">
                                <el-input size="mini"
                                          style="width: 150px"
                                          prefix-icon="el-icon-edit"
                                          v-model="dname" readonly></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24">
                            <el-form-item label="培训日期:" prop="traindate">
                                <el-date-picker
                                        v-model="train.traindate"
                                        size="mini"
                                        type="date"
                                        value-format="yyyy-MM-dd"
                                        style="width: 130px;"
                                        placeholder="培训日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24">
                            <el-form-item label="培训内容:" prop="traincontent">
                                <el-input
                                        type="textarea"
                                        autosize
                                        v-model="train.traincontent"
                                        prefix-icon="el-icon-edit"
                                        style="width: 330px;"
                                        placeholder="请输入培训内容"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24">
                            <el-form-item label="备    注:" prop="remark">
                                <el-input
                                        type="textarea"
                                        autosize
                                        v-model="train.remark"
                                        prefix-icon="el-icon-edit"
                                        style="width: 330px;"
                                        placeholder="备注(选填)"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doAddPerTrain">确 定</el-button>
    </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "PerTrain",
        data() {
            return {
                showName: false,
                showDepartment:false,
                ename:"",
                dname:"",
                title: '',
                importDataBtnText: '导入数据',
                importDataBtnIcon: 'el-icon-upload2',
                importDataDisabled: false,
                trains: [],
                loading: false,
                popVisible: false,
                popVisible2: false,
                dialogVisible: false,
                total: 0,
                page: 1,
                keyword: '',
                size: 10,
                train: {
                    eid: null,
                    name: "",
                    email: "laowang@qq.com",
                    phone: "18565558897",
                    departmentId: null,
                    traindate: "2020-01-01",
                    traincontent: "",
                    remark: ""
                },
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                rules: {
                    eid: [{required: true, message: '请输入员工编号', trigger: 'blur'},
                        {type: 'number', message: '员工编号必须为数字值', trigger: 'blur'}],
                    traindate: [{required: true, message: '请输入培训日期', trigger: 'blur'}],
                    traincontent: [{required: true, message: '请输入培训内容', trigger: 'blur'}],
                    remark: [{required: false, message: '', trigger: 'blur'}],
                }
            }
        },
        mounted() {
            this.initPerTrain();
        },
        methods: {

            getEmpInfo(id){
                //alert("值改变了");
                //alert(id);
                let url = "/employee/pertrain/"+ id;
                this.getRequest(url).then(resp =>{
                    if (resp) {
                        this.ename = resp.name;
                        this.dname = resp.department.name;
                        this.showName = true;
                        this.showDepartment = true;

                    }else {
                        this.$message({
                            type:"error",
                            message:"该员工不存在！",
                        })
                        this.showName = false;
                        this.showDepartment = false;
                    }
                })
            },

            showAddPerTrainView() {
                this.emptyTrain();
                this.title = '添加员工培训信息';
                this.showName = false;
                this.showDepartment = false;
                this.dialogVisible = true;
            },

            emptyTrain() {
                this.train = {
                    eid: null,
                    departmentId: null,
                    traindate: "2020-01-01",
                    traincontent: "",
                    remark: ""
                }
            },


            sizeChange(currentSize) {
                this.size = currentSize;
                this.initPerTrain();
            },
            currentChange(currentPage) {
                this.page = currentPage;
                this.initPerTrain();
            },

            doAddPerTrain() {
                //alert("添加");
                this.$refs['trainForm'].validate(valid => {
                    if (this.showName==true && valid) {
                        //alert("55555555555");
                        this.postRequest("/employee/pertrain/", this.train).then(resp => {
                            if (resp) {
                                this.dialogVisible = false;
                                this.initPerTrain();
                            }
                        })
                    }else{
                        if (!valid){
                            this.$message({
                                type:"error",
                                message:"信息不完整！请重新确认",
                            })
                        }else {
                            this.$message({
                                type:"error",
                                message:"该员工不存在，请重新确认",
                            })
                        }
                    }
                });
            },

            deletePerTrain(data) {
                alert(data.employee.name);
                this.$confirm('此操作将永久删除【' + data.employee.name + '】的该条培训记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/employee/pertrain/" + data.id).then(resp => {
                        if (resp) {
                            this.initPerTrain();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },



            initPerTrain(type) {
                this.loading = true;
                let url = '/employee/pertrain/?page=' + this.page + '&size=' + this.size;
                if (type) {
                    url += "&name=" + this.keyword;
                }
                this.getRequest(url).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.trains = resp.data;
                        this.total = resp.total;
                    }
                });
            }
        }
    }
</script>

<style>
    /* 可以设置不同的进入和离开动画 */
    /* 设置持续时间和动画函数 */
    .slide-fade-enter-active {
        transition: all .8s ease;
    }

    .slide-fade-leave-active {
        transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
    }

    .slide-fade-enter, .slide-fade-leave-to
        /* .slide-fade-leave-active for below version 2.1.8 */
    {
        transform: translateX(10px);
        opacity: 0;
    }
</style>
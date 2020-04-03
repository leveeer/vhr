<template>
    <div>
        <div>
            <div style="display: flex;justify-content: space-between">
                <div>
                    <el-input placeholder="请输入员工名进行搜索，可以直接回车搜索..." prefix-icon="el-icon-search"
                              clearable
                              @clear="initPers"
                              style="width: 350px;margin-right: 10px" v-model="keyword"
                              @keydown.enter.native="initPers"></el-input>
                    <el-button icon="el-icon-search" type="primary" @click="initPers">
                        搜索
                    </el-button>
                </div>
                <div>
                    <el-button type="primary" icon="el-icon-plus" @click="showAddPerecView">
                        添加奖罚信息
                    </el-button>
                </div>
            </div>
        </div>
        <div style="margin-top: 10px">
            <el-table
                    :data="pers"
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
                        prop="ecdate"
                        label="奖罚日期"
                        align="left"
                        width="185">
                </el-table-column>
                <el-table-column
                        prop="ecreason"
                        label="奖罚原因"
                        align="left"
                        width="285">
                </el-table-column>
                <el-table-column
                        prop="ecpoint"
                        label="奖罚分"
                        align="left"
                        width="85">
                </el-table-column>
                <el-table-column
                        prop="ectypeStr"
                        label="奖罚类型"
                        align="left"
                        width="85">
                </el-table-column>
                <el-table-column
                        prop="remark"
                        label="备注"
                        align="left"
                        width="285">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="200"
                        label="操作">
                    <template slot-scope="scope">
                        <el-button @click="showEditPerecView(scope.row)" style="padding: 3px" size="mini">编辑</el-button>
                        <!--<el-button @click="showAdvEmpView(scope.row)" style="padding: 3px" size="mini" type="primary">查看奖罚详情</el-button>-->
                        <el-button @click="deletePerec(scope.row)" style="padding: 3px" size="mini" type="danger">删除
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
        <el-dialog
                :title="title"
                :visible.sync="dialogVisible"
                width="80%">
            <div>
                <el-form :model="per" :rules="rules" ref="perForm">
                    <el-row>
                        <el-col :span="12" v-show="showEid">
                            <el-form-item label="员工编号:" prop="eid">
                                <el-input size="mini"
                                          :maxlength="9"
                                          style="width: 150px"
                                          prefix-icon="el-icon-edit"
                                          v-model.number="per.eid"
                                          placeholder="请输入员工编号"
                                          @change="getEmpInfo(per.eid)"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="12" v-show="showName">
                            <el-form-item label="员工姓名:" prop="employee.name">
                                <el-input size="mini"
                                          style="width: 150px"
                                          prefix-icon="el-icon-edit"
                                          v-model="name" readonly></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="奖罚日期:" prop="ecdate">
                                <el-date-picker
                                        v-model="per.ecdate"
                                        size="mini"
                                        type="date"
                                        value-format="yyyy-MM-dd"
                                        style="width: 130px;"
                                        placeholder="奖罚日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24">
                            <el-form-item label="奖罚原因:" prop="ecreason">
                                <el-input
                                        type="textarea"
                                        autosize
                                        v-model="per.ecreason"
                                        prefix-icon="el-icon-edit"
                                        style="width: 330px;"
                                        placeholder="请输入奖罚原因"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="奖 罚 分:" prop="ecpoint">
                                <el-input
                                        v-model.number="per.ecpoint"
                                        size="mini"
                                        :maxlength="3"
                                        prefix-icon="el-icon-edit"
                                        style="width: 130px;"
                                        placeholder="请输入奖罚分"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="奖罚类型:" prop="ectype">
                                <el-radio-group v-model="per.ectype">
                                    <el-radio :label="0">奖</el-radio>
                                    <el-radio :label="1">罚</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24">
                            <el-form-item label="备    注:" prop="remark">
                                <el-input
                                        type="textarea"
                                        autosize
                                        v-model="per.remark"
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
    <el-button type="primary" @click="doAddPerec">确 定</el-button>
  </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "PerEc",
        data() {
            return {
                showName: false,
                showEid:true,
                name:"",
                valid:true,
                title: '',
                importDataBtnText: '导入数据',
                importDataBtnIcon: 'el-icon-upload2',
                importDataDisabled: false,
                pers: [],
                loading: false,
                popVisible: false,
                popVisible2: false,
                dialogVisible: false,
                total: 0,
                page: 1,
                keyword: '',
                size: 10,
                per: {
                    eid:null,
                    name: "",
                    ecdate: "",
                    ecreason: "",
                    ecpoint: null,
                    ectype: 0,
                    remark:""
                },
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                rules: {
                    eid: [{required: true, message: '请输入员工编号', trigger: 'blur'},
                        {type: 'number', message: '员工编号必须为数字值', trigger: 'blur'}],
                    ecdate:[{required: true, message: '请输入奖罚日期',trigger: 'blur'}],
                    ecreason: [{required: true, message: '请输入奖罚原因', trigger: 'blur'}],
                    ecpoint: [{required: true, message: '请输入奖罚分', trigger: 'blur'},
                                {type: 'number', message: '奖罚分必须为数字值', trigger: 'blur'}],
                    ectype: [{required: true, message: '请输入奖罚类型', trigger: 'blur'}],
                    remark: [{required: false, message: '请输入奖罚分', trigger: 'blur'}],
                }
            }
        },
        mounted() {
            this.initPers();
        },
        methods: {
            validateEidLength(rule,value,callback){
                if(value.length < 12){
                    callback();
                }else{
                    callback(new Error("员工编号有误！"))
                }
            },

            getEmpInfo(id){
                //alert("值改变了");
                //alert(id);
                let url = "/employee/perec/"+ id;
                this.getRequest(url).then(resp =>{
                    if (resp) {
                        this.showName = true;
                        this.name = resp.name;
                    }else {
                        this.$message({
                            type:"error",
                            message:"该员工不存在！",
                        })
                        this.showName = false;
                    }
                })
            },

            emptyPer() {
                this.per = {
                    eid:null,
                    name: "张三",
                    ecdate: "",
                    ecreason: "",
                    ecpoint: null,
                    ectype: null,
                    remark:""
                }
            },

            showEditPerecView(data) {
                this.title = '编辑奖罚信息';
                this.showEid = false;
                this.showName = true;
                this.name = data.employee.name;
                this.per = data;
                //alert(this.per.ectype);
                //console.log(data);
                this.dialogVisible = true;
            },

            showAddPerecView() {
                this.emptyPer();
                this.title = '添加员工奖罚';
                this.showEid = true;
                this.showName = false;
                this.dialogVisible = true;
            },

            doAddPerec() {
                if(this.per.id) {
                    //alert("更新");
                    this.$refs['perForm'].validate(valid => {
                        if (valid) {
                            this.putRequest("/employee/perec/", this.per).then(resp => {
                                if (resp) {
                                    this.dialogVisible = false;
                                    this.initPers();
                                }
                            })
                        }
                    });
                } else {
                    //alert("添加");
                    this.$refs['perForm'].validate(valid => {
                        if (this.showName==true && valid) {
                            //alert("55555555555");
                            this.postRequest("/employee/perec/", this.per).then(resp => {
                                if (resp) {
                                    this.dialogVisible = false;
                                    this.initPers();
                                }
                            })
                        }else{

                            if(!valid) {
                                this.$message({
                                    type: "error",
                                    message: "信息不完整！请重新确认",
                                })
                            }else {
                                this.$message({
                                    type: "error",
                                    message: "该员工不存在，请重新确认",
                                })
                            }
                        }
                    });
                }
            },

            deletePerec(data) {
                alert(data.employee.name);
                this.$confirm('此操作将永久删除【' + data.employee.name + '】的该条奖罚记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/employee/perec/" + data.id).then(resp => {
                        if (resp) {
                            this.initPers();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },

            sizeChange(currentSize) {
                this.size = currentSize;
                this.initPers();
            },
            currentChange(currentPage) {
                this.page = currentPage;
                this.initPers();
            },
            initPers(type) {
                this.loading = true;
                let url = '/employee/perec/?page=' + this.page + '&size=' + this.size;
                if (type) {
                    url += "&name=" + this.keyword;
                }
                this.getRequest(url).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.pers = resp.data;
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
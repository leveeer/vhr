<template>
    <div>
        <div>
            <div style="display: flex;justify-content: space-between">
                <div>
                    <el-button type="primary" icon="el-icon-download" @click="backUpDataSource">
                        备份数据库
                    </el-button>
                </div>
            </div>
        </div>
        <div style="margin-top: 10px">
            <el-table
                    :data="datas"
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
                        prop="filename"
                        fixed
                        align="left"
                        label="文件名"
                        width="290">
                </el-table-column>
                <el-table-column
                        prop="backupuser"
                        width="130"
                        align="left"
                        label="备份者">
                </el-table-column>
                <el-table-column
                        prop="backupdate"
                        label="备份日期"
                        align="left"
                        width="130">
                </el-table-column>
                <el-table-column
                        prop="fileurl"
                        label="存放路径"
                        align="left"
                        width="285">
                </el-table-column>
                <el-table-column
                        prop="remark"
                        label="备注"
                        align="left"
                        width="285">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="150"
                        label="操作">
                    <template slot-scope="scope">
                        <!--<el-button @click="showEditPerecView(scope.row)" style="padding: 3px" size="mini">编辑</el-button>-->
                        <!--<el-button @click="showAdvEmpView(scope.row)" style="padding: 3px" size="mini" type="primary">查看奖罚详情</el-button>-->
                        <el-button @click="doRestore(scope.row)" style="padding: 3px" size="mini" type="success">还原
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
        <!--添加备份信息-->
        <el-dialog
                :title="title"
                :visible.sync="dialogVisible"
                width="50%">
            <div>
                <el-form :model="dataInfo" :rules="rules" ref="dataInfoForm">
                    <el-row>
                        <el-col :span="24">
                            <el-form-item label="备份文件名:" prop="filename">
                                <el-input size="mini"
                                          style="width: 270px"
                                          placeholder="请输入备份文件名"
                                          v-model="dataInfo.filename"
                                          prefix-icon="el-icon-edit">
                                </el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="24">
                            <el-form-item label="备份日期:" prop="backupdate">
                                <el-input size="mini"
                                          style="width: 150px"
                                          readonly
                                          v-model="dataInfo.backupdate"
                                          prefix-icon="el-icon-edit">
                                </el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="24">
                            <el-form-item label="备份者:" prop="backupuser">
                                <el-input size="mini"
                                          style="width: 150px"
                                          v-model="dataInfo.backupuser"
                                          readonly
                                          prefix-icon="el-icon-edit">
                                </el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="24">
                            <el-form-item label="保存路径:" prop="fileurl">
                                <el-input size="mini"
                                          style="width: 300px"
                                          v-model="dataInfo.fileurl"
                                          placeholder="请输入备份文件保存路径"
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
                                          v-model="dataInfo.remark"
                                          prefix-icon="el-icon-edit">
                                </el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doBackUpDataSource">确 定</el-button>
    </span>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "SysData",
        data() {
            return {
                datas: [],
                loading: false,
                total: 0,
                page: 1,
                size: 10,
                title: '',
                isShow:false,
                dialogVisible: false,
                dataInfo:{
                    id:null,
                    filename:"",
                    backudate:"",
                    backupuser:"",
                    fileurl:"",
                    remark:"",
                },

                rules:{
                    filename: [{required: true, message: '请输入备份文件名', trigger: 'blur'}],
                    fileurl: [{required: true, message: '请输入备份文件保存路径', trigger: 'blur'}],
                    remark: [{required: false, message: '', trigger: 'blur'}],
                }

            }
        },
        mounted() {
            this.initData();
        },
        methods: {

            sizeChange(currentSize) {
                this.size = currentSize;
                this.initPerTrain();
            },
            currentChange(currentPage) {
                this.page = currentPage;
                this.initPerTrain();
            },

            backUpDataSource(){
                this.emptyDataInfo();
                this.title = '添加数据库备份';
                this.dialogVisible = true;
                const user = JSON.parse(window.sessionStorage.getItem("user"));
                this.dataInfo.backupuser = user.name;
                const date = new Date();
                const year = date.getFullYear();
                /* 在日期格式中，月份是从0开始的，因此要加0
                 * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
                 * */
                const month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
                const day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
                //console.log(date);
                this.dataInfo.backupdate = year+"-"+month+"-"+day;
                //alert(this.dataInfo.backupDate)
            },

            emptyDataInfo(){
                this.dataInfo={
                    filename:"",
                    backupdate:"",
                    backupuser:"",
                    fileurl:"",
                    remark:"",
                }
            },

            doBackUpDataSource() {
                this.$refs['dataInfoForm'].validate(valid => {
                    if (valid) {
                        alert(this.dataInfo)
                        alert("55555555555");
                        this.postRequest("/employee/sysdata/", this.dataInfo).then(resp => {
                            if (resp) {
                                this.dialogVisible = false;
                                this.initData();
                            }
                        })
                    }
                });
            },


            initData() {
                this.loading = true;
                let url = '/employee/sysdata/?page=' + this.page + '&size=' + this.size;
                this.getRequest(url).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.datas = resp.data;
                        this.total = resp.total;
                    }
                });
            },

            doRestore(data){
                alert(data.fileurl);
                this.$confirm('此操作将使数据库恢复为【' + data.filename + '】的版本, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let url = '/employee/sysdata/doRestore/?fileurl=' + data.fileurl;
                    this.postRequest(url).then(resp => {
                        if (resp) {
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消'
                    });
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
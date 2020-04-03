<template>
    <div>
        <div>
            <div style="display: flex;justify-content: space-between">
                <div>
                    <el-input placeholder="请输入员工名进行搜索，可以直接回车搜索..." prefix-icon="el-icon-search"
                              clearable
                              @clear="initAttend"
                              style="width: 350px;margin-right: 10px" v-model="keyword"
                              @keydown.enter.native="initAttend"></el-input>
                    <el-button icon="el-icon-search" type="primary" @click="initAttend">
                        搜索
                    </el-button>
                </div>
            </div>
        </div>
        <div style="margin-top: 10px">
            <el-table
                    :data="attendances"
                    stripe
                    border
                    v-loading="loading"
                    element-loading-text="正在加载..."
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    style="width: 100%">
                <el-table-column
                        prop="username"
                        align="left"
                        label="姓名"
                        width="200">
                </el-table-column>
                <el-table-column
                        prop="clickDate"
                        width="200"
                        align="left"
                        label="考勤日期">
                </el-table-column>
                <el-table-column
                        prop="firstClickTime"
                        align="left"
                        label="第一次打卡"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="lastClickTime"
                        width="180"
                        align="left"
                        label="最后一次打卡">
                </el-table-column>
                <el-table-column
                        label="工作时长"
                        align="left"
                        width="185">
                    <template slot-scope="scope">
                        <el-tag effect="dark" type="info">{{scope.row.workHours}}小时</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        width="210"
                        align="left"
                        :filters="[{ text: '早退', value: 0 }, { text: '正常', value: 1 }, { text: '加班', value: 2 }]"
                        filter-placement="bottom-end"
                        :filter-method="filterTag"
                        label="状态">
                    <template slot-scope="scope">
                            <el-tag effect="dark" :type="scope.row.status | statusFilter">
                                {{scope.row.status | formatStatus}}
                            </el-tag>
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
    </div>
</template>

<script>
    export default {
        name: "StaAttend",
        data() {
            return {

                loading: false,
                total: 0,
                page: 1,
                keyword: '',
                size: 10,
                attendances:[],
            }
        },

        methods:{

            filterTag(value, row) {
                return row.status === value;
            },


            sizeChange(currentSize) {
                this.size = currentSize;
                this.initAttend();
            },
            currentChange(currentPage) {
                this.page = currentPage;
                this.initAttend();
            },

            initAttend(type){
                this.loading = true;
                let url = '/employee/attendance/?page=' + this.page + '&size=' + this.size;
                if (type) {
                    url += "&name=" + this.keyword;
                }
                this.getRequest(url).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.attendances = resp.data;
                        this.total = resp.total;
                    }
                });
            }
        },

        mounted() {
            this.initAttend();
        },


        filters: {
            //tag类型
            statusFilter(status) {
                const statusMap  = {
                    0: 'warning',
                    1: 'primary',
                    2: 'success'
                };
                return statusMap[status]
            },
            //颜色名称
            formatStatus(status) {
                const statusMap = {
                    0: '早退',
                    1: '正常',
                    2: '加班'
                };
                return statusMap[status]
            }


        },
    }
</script>

<style>

    .el-table td, .el-table th {
        text-align: center;
    }

</style>
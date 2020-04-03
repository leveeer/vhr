<template>
    <div>
    <el-table
            :data="oplogs"
            style="width: 100%"
            height="500">
        <el-table-column
                fixed
                prop="username"
                label="操作者"
                width="100">
            <template slot-scope="scope">
                <el-tag effect="dark">{{scope.row.username}}</el-tag>
            </template>
        </el-table-column>
        <el-table-column
                fixed
                prop="operation"
                label="操作内容"
                width="150">
        </el-table-column>
        <el-table-column
                prop="visitTime"
                label="访问时间"
                width="150">
        </el-table-column>
        <el-table-column
                prop="ip"
                label="IP地址"
                width="150">
        </el-table-column>
        <el-table-column
                prop="executionTime"
                label="访问时长"
                width="80">
            <template slot-scope="scope">
                <el-tag effect="dark" type="success">{{scope.row.executionTime}}ms</el-tag>
            </template>
        </el-table-column>

        <el-table-column
                prop="method"
                label="访问方法"
                width="450">
        </el-table-column>
        <el-table-column
                prop="params"
                label="参数"
                width="4000">
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
</template>

<script>
    export default {
        name: "SysLog",
        data() {
            return {

                total: 0,
                page: 1,
                size: 10,
                loading: false,
                oplogs: [],

            }
        },

        methods:{
            sizeChange(currentSize) {
                this.size = currentSize;
                this.getAllLogs();
            },
            currentChange(currentPage) {
                this.page = currentPage;
                this.getAllLogs();
            },

            getAllLogs(){
                this.loading = true;
                let url = '/employee/syslog/?page=' + this.page + '&size=' + this.size;
                this.getRequest(url).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.oplogs = resp.data;
                        this.total = resp.total;
                    }
                });
            }
        },

        mounted() {
            this.getAllLogs();
        }
    }
</script>

<style scoped>

</style>
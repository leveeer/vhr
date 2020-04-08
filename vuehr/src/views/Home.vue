<template>
    <div>
        <el-container>
            <el-header class="homeHeader">
                <div class="title">人事管理系统</div>
                <div>
                    <el-button icon="el-icon-s-flag" type="text" style="margin-right: 10px;color: #ffffff;" @click="punchClock"></el-button>
                    <el-button icon="el-icon-message-solid" type="text" style="margin-right: 8px;color: #ffffff;" size="normal" @click="goChat"></el-button>
                    <el-dropdown class="userInfo" @command="commandHandler">
                      <span class="el-dropdown-link" style="color: #ffffff">
                        {{user.name}}<i><img :src="user.userface" alt=""></i>
                      </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
                            <el-dropdown-item command="setting">设置</el-dropdown-item>
                            <el-dropdown-item command="logout" divided>注销登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </el-header>
            <el-container id="v-content" :style="{minHeight: Height+'px'}">
                <el-aside class="aside" width="200px" style="font-family: 华光黑体_CNKI">
                    <el-menu default-active="2" collapse-transition router :default-active="$route.path"
                             unique-opened class="el-menu-vertical-demo" background-color="#334157"
                             text-color="#fff" active-text-color="#ffd04b">
                        <el-submenu :index="index+''" v-for="(item,index) in routes" v-if="!item.hidden" :key="index">
                            <template slot="title">
                                <i style="color: #fafafa;margin-right: 5px" :class="item.iconCls"></i>
                                <span>{{item.name}}</span>
                            </template>
                            <el-menu-item :index="child.path" v-for="(child,indexj) in item.children" :key="indexj">
                                {{child.name}}
                            </el-menu-item>
                        </el-submenu>
                    </el-menu>
                </el-aside>
                <el-main>
                    <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path!='/home'">
                        <el-breadcrumb-item :to="{ path: '/home' }" style="font-family: 华光黑体_CNKI">首页</el-breadcrumb-item>
                        <el-breadcrumb-item style="font-family: 华光黑体_CNKI">{{this.$router.currentRoute.name}}</el-breadcrumb-item>
                    </el-breadcrumb>
                    <div class="homeWelcome" v-if="this.$router.currentRoute.path=='/home'">
                        <div style="margin-top: 120px;text-align:center;font-size: 70px;color: #f4f4f4">{{dateFormat(new Date())}}</div>
                        <div style="font-size: 70px;color: #f4f4f4;text-align: center" v-text="date"></div>
                        <div style="font-size: 50px;text-align:center;color: #f4f4f4;font-family: 'JetBrains Mono Medium'">welcome come to vhr</div>
                    </div>
                    <router-view class="homeRouterView"/>
                </el-main>
            </el-container>
            <el-footer class="homeFooter" style="height: 60px">
                <div style="color: #fafafa;margin-left: 600px">Copyright © 2020 江西农业大学软件学院.All rights reserved.</div>
            </el-footer>
        </el-container>
    </div>
</template>

<script>
    export default {
        name: "Home",
        data() {
            return {
                //user: JSON.parse(window.sessionStorage.getItem("user")),
                attendance:{
                    clickTime:"",
                    username:"",
                    clickDate:"",
                },
                date: null,
                Height: 0

            }
        },
        computed: {
            routes() {
                return this.$store.state.routes;
            },

            user(){
                return this.$store.state.currentHr;
            }
        },

        mounted() {
            let _this = this; // 声明一个变量指向Vue实例this，保证作用域一致
            this.timer = setInterval(() => {
                _this.date = this.dateFormatTime(new Date()); // 修改数据date
            }, 1000);

            //动态设置内容高度 让footer始终居底   header+footer的高度是100
            this.Height = document.documentElement.clientHeight - 120;
            //监听浏览器窗口变化　
            window.onresize = ()=> {this.Height = document.documentElement.clientHeight - 120}
        },

        beforeDestroy() {
            if (this.timer) {
                clearInterval(this.timer); // 在Vue实例销毁前，清除定时器
            }
        },

        methods: {

            dateFormatWithTime(time) {
                const date = new Date(time);
                const year = date.getFullYear();
                /* 在日期格式中，月份是从0开始的，因此要加0
                 * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
                 * */
                const month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
                const day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
                const hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
                const minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
                const seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
                // 拼接
                return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
            },

            dateFormatTime(time) {
                const date = new Date(time);
                /* 在日期格式中，月份是从0开始的，因此要加0
                 * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
                 * */
                const hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
                const minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
                const seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
                // 拼接
                return hours+":"+minutes+":"+seconds;
            },

            dateFormat(time) {
                const date = new Date(time);
                const year = date.getFullYear();
                /* 在日期格式中，月份是从0开始的，因此要加0
                 * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
                 * */
                const month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
                const day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
                // 拼接
                return year+"-"+month+"-"+day;
            },

            punchClock(){
                this.attendance.clickTime = this.dateFormatWithTime(new Date());
                this.attendance.clickDate = this.dateFormat(new Date());
                this.attendance.username = this.user.name;
                this.postRequest("/employee/attendance/", this.attendance).then(resp => {
                    if (resp) {
                    }
                })
            },

            goChat() {
                this.$router.push("/chat");
            },
            commandHandler(cmd) {
                if (cmd == 'logout') {
                    this.$confirm('此操作将注销登录, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.getRequest("/logout");
                        window.sessionStorage.removeItem("user")
                        this.$store.commit('initRoutes', []);
                        this.$router.replace("/");
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消操作'
                        });
                    });
                }else if (cmd == 'userinfo'){
                    this.$router.push('/hrinfo');
                }
            }
        }
    }
</script>

<style scoped>

    .aside {
        display: block;
        position: relative;
        overflow-y: hidden;
        overflow-x: hidden;
        background-color: #334157 !important;
    }

    .el-footer.homeFooter{
        background-color: #334157;
        display: flex;
        align-items: center;
        justify-content: space-between;
        box-sizing: border-box;
        /*margin-top: 20px;*/
    }

    .el-menu {
        border: none;
    }

    [v-cloak]{
        display: none;
    }

    .homeRouterView {
        margin-top: 10px;
    }

    .homeWelcome {
        /*text-align: center;
        font-size: 30px;
        font-family: "PingFang SC";
        color: #409eff;
        padding-top: 50px;*/
        background-size: 100% 100%;
        background: url('../static/images/背景2.jpg') center center repeat;
        height: 82%;
        position: fixed;
        width: 100%;
        margin-left: -21px;
        margin-top: -20px;
    }

    .home-container {
        margin-top: 10px;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-around;
    }

    .homeHeader {
        background-color: #334157;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 0px 15px;
        box-sizing: border-box;
    }

    .homeHeader .title {
        font-size: 30px;
        font-family: "华光黑体_CNKI";
        color: #ffffff
    }

    .homeHeader .userInfo {
        cursor: pointer;
    }

    .el-dropdown-link img {
        width: 48px;
        height: 48px;
        border-radius: 24px;
        margin-left: 8px;
    }

    .el-dropdown-link {
        display: flex;
        align-items: center;
    }


</style>
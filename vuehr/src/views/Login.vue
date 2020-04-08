<template>
    <div class="login">
        <el-form
                :rules="rules"
                ref="loginForm"
                v-loading="loading"
                element-loading-text="正在登录..."
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)"
                :model="loginForm"
                class="loginContainer">
            <h3 class="loginTitle">系统登录</h3>
            <hr>
            <el-form-item prop="username" label="用户名：">
                <el-input size="normal" type="text" v-model="loginForm.username" auto-complete="off"
                          placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password" label="密 码：">
                <el-input size="normal" type="password" v-model="loginForm.password" auto-complete="off"
                          placeholder="请输入密码" @keydown.enter.native="submitLogin"></el-input>
            </el-form-item>
            <el-checkbox size="normal" class="loginRemember" v-model="checked">记住密码</el-checkbox>
            <el-button size="normal" type="primary" style="width: 100%;" @click="submitLogin">登录</el-button>
        </el-form>
    </div>
</template>

<script>
    import { setCookie, getCookie, delCookie } from '../utils/util'
    export default {
        name: "Login",
        data() {
            return {
                loading: false,
                loginForm: {
                    username: null,
                    password: null
                },
                checked: false,
                rules: {
                    username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                    password: [{required: true, message: '请输入密码', trigger: 'blur'}]
                }
            }
        },

        created() {
            // 获取存在本地的用户名密码
            this.getuserpwd()
        },

        methods: {
            getuserpwd() {
                if (getCookie('user') !== '' && getCookie('pwd') !== '') {
                    this.loginForm.username = getCookie('user')
                    this.loginForm.password = getCookie('pwd')
                    this.rememberpwd = true
                }
            },

            submitLogin() {
                this.$refs.loginForm.validate((valid) => {
                    if (valid) {
                        this.loading = true;
                        this.postKeyValueRequest('/doLogin', this.loginForm).then(resp => {
                            this.loading = false;
                            if (resp) {
                                if (this.checked) {
                                    //保存帐号到cookie，有效期7天
                                    setCookie('user', this.loginForm.username, 7)
                                    //保存密码到cookie，有效期7天
                                    setCookie('pwd', this.loginForm.password, 7)
                                 } else {
                                   delCookie('user');
                                   delCookie('pwd')
                                }

                                this.$store.commit('INIT_CURRENTHR', resp.obj);
                                window.sessionStorage.setItem("user", JSON.stringify(resp.obj));
                                let path = this.$route.query.redirect;
                                this.$router.replace((path == '/' || path == undefined) ? '/home' : path);
                            }
                        })
                    } else {
                        this.$message.error('请输入所有字段');
                        return false;
                    }
                });
            }
        }
    }
</script>

<style>
    .login{
        background-size: 100% 100%;
        background: url('../static/images/背景3.jpg') center center repeat;
        height: 100%;
        position: fixed;
        width: 100%
    }

    .loginContainer {
        border-radius: 15px;
        background-clip: padding-box;
        margin: 180px auto;
        width: 350px;
        padding: 15px 35px 15px 35px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
        background: linear-gradient(-160deg, rgba(19, 38, 170, 0.8) 0%, rgba(196, 196, 196, 0.8) 50%, rgba(48, 52, 116, 0.8) 100%) no-repeat;
    }
    .loginContainer .el-form-item__label{
        color: white;
    }

    .loginTitle {
        margin: 15px auto 20px auto;
        text-align: center;
        font-family: 华光黑体_CNKI;
        font-size: 20px;
        color: #fafafa;
    }

    .loginRemember {
        text-align: left;
        margin: 0px 0px 15px 0px;
        color: #fafafa;
    }

    .loginRemember .el-checkbox__label{
        color: #fafafa;
    }


</style>
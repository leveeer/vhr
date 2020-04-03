<template>
    <div id="uesrtext">
        <textarea placeholder="按 Ctrl + Enter 发送" v-model="content" v-on:keyup="addMessage"></textarea>
        <div class="sendMsg">
            <el-tooltip content="按Ctrl+Enter发送" placement="bottom" effect="light">
                <el-button @click="sendMessage">发送</el-button>
            </el-tooltip>
        </div>
    </div>
</template>

<script>
    import {mapState} from 'vuex'

    export default {
        name: 'uesrtext',
        data() {
            return {
                content: ''
            }
        },
        computed: mapState([
            'sessions',
            'currentSession'
        ]),
        methods: {
            addMessage(e) {
                if (e.ctrlKey && e.keyCode === 13 ) {
                    if (this.content.split(" ").join("").length !== 0){
                        let msgObj = new Object();
                        msgObj.to = this.currentSession.username;
                        msgObj.content = this.content;
                        this.$store.state.stomp.send('/ws/chat', {}, JSON.stringify(msgObj));
                        this.$store.commit('addMessage', msgObj);
                        this.content = '';
                    }else {
                        this.$message.error('不能发送空白消息')
                    }
                }
            },

            sendMessage(e) {
                if (this.content.split(" ").join("").length !== 0){
                    let msgObj = new Object();
                    msgObj.to = this.currentSession.username;
                    msgObj.content = this.content;
                    this.$store.state.stomp.send('/ws/chat', {}, JSON.stringify(msgObj));
                    this.$store.commit('addMessage', msgObj);
                    this.content = '';
                }else {
                    this.$message.error('不能发送空白消息')
                }

            }
        }
    }
</script>

<style lang="scss" scoped>
    #uesrtext {
        position: absolute;
        bottom: 0;
        right: 0;
        width: 100%;
        height: 30%;
        border-top: solid 1px #DDD;

        > textarea {
            padding: 10px;
            width: 100%;
            height: 60%;
            border: none;
            outline: none;
        }

        > div {
            background-color: #ffffff;
            height: 90%;
            margin-top: -10px;

            > button{
                float: right;
                margin-top: 10px;
                margin-right: 10px;
                background-color: #DDD;
            }
        }
    }


</style>

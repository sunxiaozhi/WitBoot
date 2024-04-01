<!-- <script>
export default {
    data() {
        return {
            username: "",
            password: ""

        }
    },

    methods: {
        alertUsername() {
            alert(this.username);
            alert(this.password);
        }
    },
}
</script> -->

<script setup lang="ts">
import { ref } from 'vue';
import { login } from '@/api/user';
import { setAccessToken } from '@/utils/auth';

const username = ref('');
const password = ref('');

function userLogin() {
    console.log(`用户名：${username.value},密码：${password.value}`);
    if (username.value.length == 0 || password.value.length == 0) {
        alert("请填写用户名或密码");
    }

    login({ "username": username.value, "password": password.value }).then((res: any) => {
        console.log(res)

        let accessToken = res.accessToken;
        setAccessToken(accessToken);
    })
}
</script>

<template>
    <div class="login">
        <h1 class="login-title">Login</h1>
        <!-- <h1>{{ username }}</h1>
        <h1>{{ password }}</h1> -->

        <div class="login-item">
            <span class="login-filed-tip">用户名</span>
            <input class="login-field" type="text" v-model="username">
        </div>
        <div class="login-item">
            <span class="login-filed-tip">密码</span>
            <input class="login-field" type="password" v-model="password">
        </div>
        <div class="login-item">
            <button class="login-button" @click="userLogin">登录</button>
        </div>
    </div>
</template>

<style>
body {
    background-color: #F1F1F1;
}

.login {
    min-height: 50vh;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    background-color: #FFF;
    border-radius: 50px;
}

.login-title {
    font-weight: 500;
    color: hsla(160, 100%, 37%, 1);
    margin-bottom: 25px;
}

.login-item {
    display: flex;
    height: 35px;
    justify-content: center;

    margin-bottom: 15px;
}

.login-filed-tip {
    display: flex;
    flex: 0 0 60px;
    justify-content: center;
    align-items: center;

    background-color: hsla(160, 100%, 37%, 1);
    border: solid 1px hsla(160, 100%, 37%, 1);
    color: #fff;

    border-radius: 6px 0 0 6px;
}

.login-field {
    border: solid 1px #AAABD3;
    border-radius: 0 6px 6px 0;
}

.login-button {
    font-size: 20px;
    border: solid 1px #AAABD3;
    border-radius: 6px;

    color: #FFF;
    background-color: hsla(160, 100%, 37%, 1);

    width: 100px;
}
</style>
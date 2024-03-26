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

const username = ref('');
const password = ref('');

function alertUsername() {
    alert(`用户名：${username.value},密码：${password.value}`);
}
</script>

<template>
    <div class="login">
        <h1>WitBoot Login</h1>
        <!-- <h1>{{ username }}</h1>
        <h1>{{ password }}</h1> -->

        <div class="username">
            <input placeholder="用户名" type="text" v-model="username">
        </div>
        <div class="password">
            <input placeholder="密码" type="password" v-model="password">
        </div>
        <div class="login-button">
            <button @click="alertUsername">登录</button>
        </div>
    </div>
</template>

<style>
@media (min-width: 1024px) {
    .login {
        min-height: 100vh;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
    }

    .password {
        margin-top: 10px;
        margin-bottom: 10px;
    }

}
</style>
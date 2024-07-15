<script setup lang="ts">
import { login } from '@/api/user';
import { setAccessToken } from '@/utils/auth';
import { useRouter } from "vue-router";
import HelloWorld from '@/components/HelloWorld.vue'
import { reactive } from 'vue'

// do not use same name with ref
const form = reactive({
    username: '',
    password: '',
})

const router = useRouter();

function userLogin() {
    console.log(`用户名：${form.username},密码：${form.password}`);
    if (form.username.length == 0 || form.password.length == 0) {
        alert("请填写用户名或密码");
    }

    login({ "username": form.username, "password": form.password }).then((res: any) => {
        console.log(res)

        let accessToken = res.accessToken;
        setAccessToken(accessToken);

        router.push('/user')
    })
}
</script>

<template>
    <el-row>
        <el-col :span="16">
            <header>
                <img alt="Vue logo" class="logo" src="@/assets/logo.svg" width="125" height="125" />

                <div class="wrapper">
                    <HelloWorld msg="WitBoot" />
                </div>
            </header>
        </el-col>

        <el-col :span="8">
            <el-form :model="form" label-width="auto" style="max-width: 600px">
                <el-form-item label="用户名">
                    <el-input v-model="form.username" />
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="form.password" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="userLogin">登录</el-button>
                </el-form-item>
            </el-form>

        </el-col>
    </el-row>
</template>

<style scoped>
header {
    line-height: 1.5;
    max-height: 100vh;
}

.logo {
    display: block;
    margin: 0 auto 2rem;
}

nav {
    width: 100%;
    font-size: 12px;
    text-align: center;
    margin-top: 2rem;
}

nav a.router-link-exact-active {
    color: var(--color-text);
}

nav a.router-link-exact-active:hover {
    background-color: transparent;
}

nav a {
    display: inline-block;
    padding: 0 1rem;
    border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
    border: 0;
}

@media (min-width: 1024px) {
    header {
        display: flex;
        place-items: center;
        padding-right: calc(var(--section-gap) / 2);
    }

    .logo {
        margin: 0 2rem 0 0;
    }

    header .wrapper {
        display: flex;
        place-items: flex-start;
        flex-wrap: wrap;
    }

    nav {
        text-align: left;
        margin-left: -1rem;
        font-size: 1rem;

        padding: 1rem 0;
        margin-top: 1rem;
    }
}
</style>
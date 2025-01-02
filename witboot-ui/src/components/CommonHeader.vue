<template>
    <el-header>
        <div class="header-left">
            <el-icon :size="size" :color="color" @click="changeCollapse()">
                <Menu />
            </el-icon>
        </div>
        <div class="header-right">
            <el-dropdown placement="bottom-end">
                <el-icon :size="size" :color="color" @click="changeCollapse()">
                    <User />
                </el-icon>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item>个人信息</el-dropdown-item>
                        <el-dropdown-item>退出</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </el-header>
</template>

<script setup lang="ts">
import { Menu, User } from '@element-plus/icons-vue'

const size = '40px'
const color = '#fff'

const changeCollapse = () => {
    console.log('changeCollapse')
}
</script>

<style lang="scss" scoped>
.el-header {
    display: flex;
    justify-content: space-between;
    align-items: center;

    background-color: #00adb5;
}
</style>
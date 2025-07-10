<template>
  <el-tabs
    v-model="menuTabsStore.activeTab"
    type="card"
    editable
    class="menu-tabs"
    @edit="menuTabsStore.setActiveTab($event)"
    @tabRemove="menuTabsStore.removeTab($event)"
  >
    <el-tab-pane
      v-for="item in menuTabsStore.tabs"
      :key="item.path"
      :label="item.title"
      :name="item.path"
    >
    </el-tab-pane>
  </el-tabs>
</template>

<script lang="ts" setup>

import { useMenuTabsStore } from '@/stores/menuTabsStore'

const menuTabsStore  = useMenuTabsStore ()
</script>

<style>
</style>

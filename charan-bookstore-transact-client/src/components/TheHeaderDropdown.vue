<script setup lang="ts">
import { useCategoryStore } from '@/stores/category';
const categoryStore = useCategoryStore();
</script>

<style scoped>
/*.header-dropdown {
  position: relative;
}

.categories-button {
  margin-top: 0.25em;
  background-color: var(--primary-color-dark);
}

.header-dropdown ul {
  display: none;
  background-color: white;
  color: black;
}

.header-dropdown li {
  padding: 0.25em 0.5em;
}

.header-dropdown a {
  color: black;
  text-decoration: none;
}

.header-dropdown li:hover {
  background: lightgray;
}

.header-dropdown:hover ul {
  position: absolute;
  z-index: 1;
  display: block;
  min-width: 8em;
  cursor: pointer;
}*/

.header-dropdown {
    position: relative;
    width: auto;
    transition: all 5s;
}

.categories-button {
    background-color: var(--primary-color-dark);
    color: black;
    margin-top: 0.25em;
}

.header-dropdown ul {
    position: absolute;
    right: 0;
    background-color: white;
    color: black;
    display: none;
}

.header-dropdown li {
    padding: 0.25em 0.5em;
}

.header-dropdown a {
    color: black;
    text-decoration: none;
}

.header-dropdown li:hover {
    background: lightpink;
}

.header-dropdown:hover ul {
    display: block;
    position: absolute;
    min-width: 8em;
    z-index: 1;
    cursor: pointer;
}
</style>

<template>
  <div class="header-dropdown">
    
        <i style = "position:relative; left: 25px;" class="fa-solid fa-caret-down fa-1x"></i>
        <button class="button categories-button" style="padding: 0.5em 0.8em;"><strong>&nbsp;&nbsp;&nbsp;  CATEGORIES</strong></button>
    
    <ul>
      <li v-for="category in categoryStore.categoryList" :key="category.categoryId">
        <router-link :to="'/category/' + category.name">
          {{ category.name }}
        </router-link>
      </li>
    </ul>
  </div>
</template>

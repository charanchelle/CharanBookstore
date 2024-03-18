<script setup lang="ts">
import { useCategoryStore } from '@/stores/category';
const categoryStore = useCategoryStore();
</script>

<style scoped>
/*.category-buttons {
  display: flex;
  flex-direction: row;
  background-color: var(--neutral-color);
  text-align: center;
}

.button.category-button.router-link-active {
  background-color: var(--primary-color);
}

.button.category-button {
  background-color: var(--neutral-color);
}

.button.category-button:hover,
.button.category-button:active {
  background-color: var(--primary-color);
}*/

.category-buttons {
    display: flex;
    flex-direction: row-reverse;
    text-align: center;
    background-color: var(--neutral-color);
    font-family: Verdana, Geneva, Tahoma, sans-serif;
}

.button.selected-category-button {
    background-color: var(--primary-color);
}

.button.unselected-category-button,
.button.unselected-category-button:visited {
    background-color: var(--neutral-color);
}

.button.unselected-category-button:hover,
.button.unselected-category-button:active {
    background-color: var(--primary-color);
}

.button.category-button {
    background-color: var(--neutral-color);
}

.button.category-button.router-link-active {
    background-color: var(--primary-color);
}

.button.category-button:hover,
.button.category-button:active {
    background-color: var(--primary-color);
}
</style>

<template>
  <nav class="category-nav">
    <ul class="category-buttons">
      <li v-for="category in categoryStore.categoryList" :key="category.categoryId">
        <router-link
          :to="'/category/' + category.name"
          class="button category-button" v-if="category.categoryId === 1001" style="position: relative; left: -700px;"
        >
            {{ category.name }}
        </router-link>

          <router-link
                  :to="'/category/' + category.name"
                  class="button category-button" v-if="category.categoryId === 1002" style="position: relative; left: -450px;"
            >
              {{ category.name }}
          </router-link>

          <router-link
                  :to="'/category/' + category.name"
                  class="button category-button" v-if="category.categoryId === 1003" style="position: relative; left: -200px;"
          >
              {{ category.name }}
          </router-link>

          <router-link
                  :to="'/category/' + category.name"
                  class="button category-button" v-if="category.categoryId === 1004" style="position: relative; left: 50px;"
          >
              {{ category.name }}
          </router-link>

      </li>
    </ul>
  </nav>
</template>

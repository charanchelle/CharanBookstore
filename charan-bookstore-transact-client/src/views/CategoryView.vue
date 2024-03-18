<script setup lang="ts">
import { useCategoryStore } from "@/stores/category";
import TheCategoryNav from "@/components/TheCategoryNav.vue";
import TheCategoryBookList from "@/components/TheCategoryBookList.vue";
import {useRoute} from "vue-router";
import {useBookStore} from "@/stores/book";
import {watch} from "vue";
import router from "@/router";

const route = useRoute();
const categoryStore = useCategoryStore();

const bookStore = useBookStore();

/* watch(
  () => route.params.name,
  (newName) => {
    bookStore.fetchBooks(newName as string);
  },
  { immediate: true }
); */

watch(
  () => route.params.name,
  async (newName) => {
    try {
      await bookStore.fetchBooks(newName as string)
      categoryStore.updateSelectedCategoryName(newName as string)
    } catch (e) {
      await router.push('/not-found')
    }
  },
  { immediate: true }
)

</script>

<style scoped></style>

<template>
 <div>
  <the-category-nav></the-category-nav>
  <the-category-book-list></the-category-book-list>
 </div>
</template>
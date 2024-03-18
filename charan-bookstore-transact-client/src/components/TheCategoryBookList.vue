<script setup lang="ts">

import { useBookStore } from "@/stores/book";
import CategoryBookListItem from "@/components/CategoryBookListItem.vue";
import { watch } from "vue";
import { useRoute } from "vue-router";
import { useCategoryStore } from "@/stores/category";

const bookStore = useBookStore();
const categoryStore = useCategoryStore();
const route = useRoute();
// const firstBookId = bookStore.bookList[0].bookId;

watch(
  () => route.params.name,
  () => {
    categoryStore.updateSelectedCategoryName(String(route.params.name));
  },
  {
    immediate: true,
  }
);
</script>

<style scoped>
ul {
    display: flex;
    /* width: 900px; */
    flex-direction: row;
    flex-wrap: wrap;
    padding: 1em;
    gap: 1em;
    position: relative;
    left: 235px;
}
</style>

<template>
    <section style="background-color: #BBDDEC;">
        <!-- <ul>
            <template v-for="book in props.bookList" :key="book.bookId">
                <category-book-list-item :book="book" v-if="book.bookId < firstBookId + 2"></category-book-list-item>
            </template>
        </ul>        
        <ul>
            <template v-for="book in props.bookList" :key="book.bookId">
                <category-book-list-item :book="book" v-if="book.bookId < firstBookId + 4 && book.bookId >= firstBookId + 2"></category-book-list-item>
            </template>
        </ul>
        <ul>
            <template v-for="book in props.bookList" :key="book.bookId">
                <category-book-list-item :book="book" v-if="book.bookId >= firstBookId + 4"></category-book-list-item>
            </template>
        </ul> -->

        <ul v-for="rowCount in Math.ceil(bookStore.bookList.length/2)">
            <template v-for="book in bookStore.bookList" :key="book.bookId">
                <category-book-list-item :book="book" v-if="book.bookId < bookStore.bookList[0].bookId + (rowCount*2) && book.bookId >= bookStore.bookList[0].bookId + (rowCount-1)*2"></category-book-list-item>
            </template>
        </ul>

    </section>
</template>

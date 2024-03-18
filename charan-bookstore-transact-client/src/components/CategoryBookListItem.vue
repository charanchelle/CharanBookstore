<script setup lang="ts">
import type { BookItem } from "@/types";
import { useCartStore } from "@/stores/cart";

const cartStore = useCartStore();
const props = defineProps<{
  book: BookItem;
}>();
const bookImageFileName = function (book: BookItem): string {
  let name = book.title.toLowerCase();
  name = name.replace(/ /g, "-");
  name = name.replace(/'/g, "");
  return `${name}.jpg`;
};

const bookImagePrefix = `${import.meta.env.BASE_URL}/book-images/`;

</script>
<style scoped>
.button {
  width: 180px;
}

.button:hover {
  font-style: italic;
}
.book-box {
    display: flex;
    flex-direction: row;
    /* flex-wrap: wrap; */
    background-color: var(--card-background-color);
    padding: 1em;
    gap: 0.25em;
    height: 230px;
}

.book-title {
    font-weight: bold;
    gap: 0.25em;
    padding: 0.2em;
}

.book-author {
    font-style: italic;
    gap: 0.25em;
    padding: 0.2em;
}

.book-price {
    gap: 0.25em;
    padding: 0.2em;
}

.book-box1 {
    display: flex;
    width: 150px;
    flex-direction: column;
    background-color: var(--card-background-color);
    padding: 1em;
    gap: 0.25em;
    position:relative; left:0px; top:-10px;
}
</style>

<template>
  <li class="book-box" style="width: 330px;">
    <div class="book-image">
      <img
        :src="bookImagePrefix + bookImageFileName(props.book)"
        :alt="book.title"
      />
      <button v-if="book.isPublic" class="button" style="background-color: #177550; position:relative; top:-40px; left: 15px; padding: 0.35em 0px; width: 90px"><strong>Read Now</strong></button>
    </div>
      <div class="book-box1">
          <div class="book-title">{{ book.title }}</div>
          <div class="book-author">by <strong>{{ book.author }}</strong></div>
          <div class="book-price">${{ (book.price * 100 / 100).toFixed(2) }}</div>
          <button class="button" @click="cartStore.addToCart(book)" style="position:relative; top:22px; background-color: #854000; padding: 0.5em 0px; width: 100px">Add to Cart</button>
          <!-- <button v-if="book.isPublic" class="button" style="background-color: #177550; position:relative; top:25px; left:-125px; padding: 0.35em 0px; width: 90px"><strong>Read Now</strong></button> -->
      </div>
  </li>
</template>

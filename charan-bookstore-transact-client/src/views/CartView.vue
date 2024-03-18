<script setup lang="ts">
import CartTable from "@/components/CartTable.vue";
import { useCartStore } from "@/stores/cart";
const cartStore = useCartStore();
const clearCart = function () {
  cartStore.clearCart();
};
</script>
<style scoped>
h1 {
  margin: 1em auto;
  text-align: center;
}
</style>

<template>
  <!-- <h1>Cart Page</h1> -->
  <cart-table></cart-table>
</template>
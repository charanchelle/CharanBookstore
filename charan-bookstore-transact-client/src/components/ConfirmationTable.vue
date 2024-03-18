<script setup lang="ts">
//import { getBookImageUrl } from "@/utils";
import { useCartStore } from "@/stores/cart";
const cartStore = useCartStore();
//import { asDollarsAndCents } from "@/utils";

import { useOrderDetailsStore } from "@/stores/orderDetails";
import { BookItem, OrderDetails } from "@/types";
import { integer } from "@vuelidate/validators";

const orderDetailsStore = useOrderDetailsStore();
const orderDetails: OrderDetails = orderDetailsStore.orderDetails;

// A helper function - optional to use
const bookAt = function (orderDetails: OrderDetails, index: number): BookItem {
  return orderDetails.books[index];
};
function getBookPrice(bookId: number) {
  for (let book of orderDetails.books) {
    if (book.bookId === bookId) {
      return book.price;
    }
  }
}
</script>



<style scoped>
table {
  border: 1px black solid;
  width: fit-content;
  margin-top: 1em;
  margin-bottom: 1em;
}

td {
  display: table-cell;
  padding: 0.5em 0.5em;
  background-color: white;
  vertical-align: middle;
}


td:nth-child(1) {
  text-align: left;
}

td:nth-child(2) {
  text-align: center;
}

td:nth-child(3) {
  text-align: right;
}
</style>


<template>
  <table>
    <tr
      v-for="(item, index) in orderDetails.lineItems"
      :key="item.bookId"
    >
      <td>
        {{ orderDetails.books[index].title }}&nbsp; (x&nbsp;{{ item.quantity }})
      </td>
      <td style="background-color: white; text-align: right;">${{ (orderDetails.books[index].price * item.quantity).toFixed(2) }}</td>
    </tr>
    <tr>
      <td class="total-display" style="background-color: lightgray;">Sub-total</td>
      <td style="background-color: lightgray; text-align: right;">${{ (orderDetails.order.amount - 5).toFixed(2) }}</td>
    </tr>
    <tr>
      <td class="total-display" style="background-color: lightgray;">Surcharge</td>
      <td style="background-color: lightgray; text-align: right;">$5.00</td>
    </tr>
    <tr>
      <td class="total-display" style="background-color: lightgray;">Total</td>
      <td style="background-color: lightgray; text-align: right;">${{ (orderDetails.order.amount).toFixed(2) }}</td>
    </tr>
  </table>
</template>


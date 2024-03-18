<script setup lang="ts">
import type { BookItem } from "@/types";
import { useCartStore } from "@/stores/cart";
import { useCategoryStore } from "@/stores/category";
import { ShoppingCartItem } from "@/models/ShoppingCart";
const cartStore = useCartStore();
const categoryStore = useCategoryStore();

const bookImageFileName = function (book: BookItem): string {
  let name = book.title.toLowerCase();
  name = name.replace(/ /g, "-");
  name = name.replace(/'/g, "");
  return `${name}.jpg`;
};
// const bookImagePrefix = `${import.meta.env.BASE_URL}/assets/book-images`;
const bookImagePrefix = `${import.meta.env.BASE_URL}/book-images/`;

const updateCart = function (book: BookItem, quantity: number) {
  cartStore.updateBookQuantity(book, quantity);
};

const getSubTotal = function () {
  let subTotal = 0;
  for (let item of cartStore.cart.items) {
    subTotal += item.book.price * item.quantity;
  }
  return subTotal;
};

const getCartContent = function () {
  let singularOrPlural = "items";
  if (cartStore.count == 1) {
    singularOrPlural = "item";
  }

  let content =
    "Your shopping cart contains " +
    cartStore.count +
    " " +
    singularOrPlural

  return content;
};

/* function calculate_total(book: BookItem) {
  var total = 0;
  for (ShoppingCartItem item in useCartStore().cart.items) {
    var subtotal = (item.book.price * 100 / 100).toFixed(2) * item.quantity;
    total += subtotal;
  }
  return total.toFixed(2);
} */
</script>

<style scoped>
.button {
  width: 180px;
}

.button:hover {
  font-style: italic;
}

.cart-table {
  display: grid;
  grid-template-columns: max-content minmax(10em, 17em) repeat(3, max-content);
  row-gap: 1em;
  width: fit-content;
  margin: 0 auto;
  background-color: aliceblue;
}

ul {
  display: contents;
}

ul > li {
  display: contents;
}

.table-heading {
  background-color: #333;
  color: white;
}

.table-heading > * {
  background-color: #333;
  padding: 0.5em;
}

.heading-book {
  grid-column: 1 / 2;
  text-align: center;
}

.heading-name {
  grid-column: 2 / 3;
  text-align: center;
}

.heading-price {
  grid-column: 3 / 4;
  text-align: right;
}

.heading-quantity {
  grid-column: 4 / 5;
  text-align: center;
}

.heading-subtotal {
  text-align: right;
  grid-column: -2 / -1;
}

.cart-book-image {
  padding: 0 1em;
}

.cart-book-image > * {
  margin-left: auto;
  margin-right: 0;
}

img {
  display: block;
  width: 100px;
  height: auto;
}

.rect {
  background-color: var(--primary-color-dark);
}

.narrow-rect {
  width: 75px;
  height: 100px;
}

.square {
  width: 100px;
  height: 100px;
}

.wide-rect {
  width: 125px;
  height: 100px;
}

.cart-book-price {
  padding-left: 1em;
  text-align: right;
}

.cart-book-quantity {
  padding-left: 1em;
  padding-right: 1em;
}

.cart-book-subtotal {
  text-align: right;
  padding-left: 1em;
  padding-right: 1em;
}

/* Row separators in the table */

.line-sep {
  display: block;
  height: 2px;
  background-color: gray;
  grid-column: 1 / -1;
}

/* Increment/decrement buttons */

.icon-button {
    background: none;
  border: none;
  cursor: pointer;
}

.inc-button {
  font-size: 1.125rem;
  color: var(--primary-color);
  margin-right: 0.25em;
}

.inc-button:hover {
  color: var(--primary-color-dark);
}

.dec-button {
  font-size: 1.125rem;
  color: grey;
}

.dec-button:hover {
  color: lightgrey;
}

/* Chevron buttons */

.dec-arrow-button,
.inc-arrow-button {
  font-size: 1rem;
  color: var(--primary-color);
}

.dec-arrow-button:hover,
.inc-arrow-button:hover {
  color: var(--primary-color-dark);
}

input[type="number"] {
  width: 4em;
}

select {
  background-color: var(--primary-color);
  color: white;
  border: 2px solid var(--primary-color-dark);
  border-radius: 3px;
}
</style>

<template>
    <div style="background-color: #BBDDEC;">
  <br>  <h3 v-if="cartStore.count > 0" style="text-align: center;">{{ getCartContent() }}.</h3> <br>
  <br>  <h3 v-if="cartStore.count == 0" style="text-align: center;"><br><br><br><br><br><br><br>Your shopping cart is empty.</h3> <br>
  <div v-if="cartStore.count > 0" class="cart-table" style="background-color: navajowhite;">
    <ul>
      <li class="table-heading">
        <div class="heading-book" style="font-family: Verdana, Geneva, Tahoma, sans-serif;"><strong>Book</strong></div>
        <div class="heading-name" style="font-family: Verdana, Geneva, Tahoma, sans-serif;"><strong>Name</strong></div>
        <div class="heading-price" style="font-family: Verdana, Geneva, Tahoma, sans-serif;"><strong>Price</strong></div>
        <div class="heading-quantity" style="font-family: Verdana, Geneva, Tahoma, sans-serif;"><strong>Quantity</strong></div>
        <div class="heading-subtotal" style="font-family: Verdana, Geneva, Tahoma, sans-serif;"><strong>Amount</strong></div>
      </li>

      <template v-for="item in cartStore.cart.items" :key="item.book.bookId">
        <li>
          <div class="cart-book-image">
            <img
              :src="`${bookImagePrefix}${bookImageFileName(item.book)}`"
              :alt="item.book.title"
              width="100px"
              height="auto"
            />
          </div>
          <div class="cart-book-title" style="text-align: center;">{{ item.book.title }}</div>
          <div class="cart-book-price" style="text-align: right;">
            ${{ (item.book.price * 100 / 100).toFixed(2) }}
          </div>
          <div class="cart-book-quantity" style="text-align: center;">
            <span class="quantity" style="position:relative; left: 30px; top: -2px;">{{ item.quantity }}</span
            >&nbsp;
            <button
              class="icon-button inc-button"
              @click="updateCart(item.book, item.quantity + 1)"
              style="position:relative; left: 32px;"
            >
              <i class="fas fa-plus-circle"></i>
            </button>
            <button
              class="icon-button dec-button"
              @click="updateCart(item.book, item.quantity - 1)"
              style="position:relative; left: 22px;"
            >
              <i class="fas fa-minus-circle"></i>
            </button>
          </div>
          <div class="cart-book-subtotal">${{ (item.quantity * item.book.price * 100 / 100).toFixed(2) }}</div>
        </li>
        <li class="line-sep"></li>
      </template>
      
      <p v-if="cartStore.count > 0" style="position:relative; right:-520px; top: -7px;"> Sub-total:</p>
      <b v-if="cartStore.count > 0" style="position:relative; left:476px; top: -5px;"> ${{ getSubTotal().toFixed(2) }}</b>




      <!-- <li>
        <div class="cart-book-image">
          <div class="rect narrow-rect"></div>
        </div>
        <div class="cart-book-title">Book Title</div>
        <div class="cart-book-price">$2.99</div>
        <div class="cart-book-quantity">
          <span class="quantity">2</span>&nbsp;
          <button
            class="icon-button inc-button"
          >
            <i class="fas fa-plus-circle"></i>
          </button>
          <button
            class="icon-button dec-button"
          >
            <i class="fas fa-minus-circle"></i>
          </button>
        </div>
        <div class="cart-book-subtotal">$3.98</div>
      </li>
      <li class="line-sep"></li>
      <li>
        <div class="cart-book-image">
          <img :src="`${bookImagePrefix}/dune.jpeg`" alt="Dune" />
        </div>
        <div class="cart-book-title">Book Title</div>
        <div class="cart-book-price">$2.99</div>
        <div class="cart-book-quantity">
          <button
            class="icon-button dec-arrow-button"
          >
            <i class="fas fa-chevron-left"></i>
          </button>
          <span class="quantity">&nbsp;&nbsp;2&nbsp;&nbsp;</span>
          <button
            class="icon-button inc-arrow-button"
          >
            <i class="fas fa-chevron-right"></i>
          </button>
        </div>
        <div class="cart-book-subtotal">$3.98</div>
      </li>
      <li class="line-sep"></li>
      <li>
        <div class="cart-book-image">
          <div class="rect square"></div>
        </div>
        <div class="cart-book-title">
          A Very Long Book Title That Goes On and On As Though the Author Were
          Very Impressed with Themself
        </div>
        <div class="cart-book-price">$43.50</div>
        <div class="cart-book-quantity">
          <input type="number" value="12" min="0" max="20" />
        </div>
        <div class="cart-book-subtotal">$522.00</div>
      </li>
      <li class="line-sep"></li>
      <li>
        <div class="cart-book-image">
          <div class="rect wide-rect"></div>
        </div>
        <div class="cart-book-title">Book Title</div>
        <div class="cart-book-price">$2.99</div>
        <div class="cart-book-quantity">
          <select id="quantity">
            <option value="0">Qty: 0</option>
            <option value="1">Qty: 1</option>
            <option value="2">Qty: 2</option>
            <option value="3">Qty: 3</option>
            <option value="4">Qty: 4</option>
            <option value="5">Qty: 5</option>
            <option value="6">Qty: 6</option>
            <option value="7">Qty: 7</option>
            <option value="8">Qty: 8</option>
            <option value="9">Qty: 9</option>
            <option value="10">Qty: 10</option>
          </select>
        </div>
        <div class="cart-book-subtotal">$3.98</div>
      </li> -->
    </ul>
  </div> 
  <br>
  <b v-if="cartStore.count == 0">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
  <router-link :to="'/category/' + categoryStore.selectedCategoryName">
    <button class="button categories-button" style="padding: 0.5em 0.8em; margin-top: 0.25em; position:relative; right:-350px; top: -5px; background-color: white; color: black; text-align: center;"><strong>Continue Shopping</strong></button>  
  </router-link>
  <router-link to="/checkout">
    <button class="button categories-button" v-if="cartStore.count > 0" style="padding: 0.5em 0.8em; margin-top: 0.25em; position:relative; right:-420px; top: -5px; color: white; border-color: black; background-color: #0072A5;"><strong>Proceed to Checkout</strong></button>
  </router-link>
  <br><br>
  <button class="button categories-button" v-if="cartStore.count > 0" style="padding: 0.5em 0.8em; margin-top: 0.25em; position:relative; right:-500px; top: -5px; background-color: white; color: black; text-align: center;" @click="cartStore.clearCart()"><strong>Clear Cart</strong></button>
  <br><br>
  <b v-if="cartStore.count == 0"><br><br><br><br><br><br><br><br></b>
</div>
</template>


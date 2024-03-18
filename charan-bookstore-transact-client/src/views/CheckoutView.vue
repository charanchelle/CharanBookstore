<script setup lang="ts">
import { reactive, ref } from "vue";
import useVuelidate from "@vuelidate/core";
import { helpers, maxLength, minLength, required, email } from "@vuelidate/validators";
import { useCartStore } from "@/stores/cart";
import { useCategoryStore } from "@/stores/category";
import { isCreditCard, isMobilePhone } from "@/validators";
import CheckoutFieldError from "@/components/CheckoutFieldError.vue";
import router from "@/router";
import type { OrderDetails, ServerErrorResponse } from "@/types";
import { useOrderDetailsStore } from "@/stores/orderDetails";

const cartStore = useCartStore();
const cart = cartStore.cart;
const categoryStore = useCategoryStore();
const orderDetailsStore = useOrderDetailsStore();

const months: string[] = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

const defaultServerErrorMessage = "An unexpected error occurred, please try again."
const serverErrorMessage = ref(defaultServerErrorMessage);

let years: number[] = [];

let selectedYear = new Date().getFullYear();
for (let i = 0; i < 15; i++) {
  years.push(selectedYear + i);
}

let selectedMonth = new Date().getMonth() + 1;

const form = reactive({
  name: "",
  address: "",
  phone: "",
  email: "",
  ccNumber: "",
  ccExpiryMonth: new Date().getMonth() + 1,
  ccExpiryYear: new Date().getFullYear(),
  checkoutStatus: "",
});

const rules = {
  name: {
    required: helpers.withMessage("Please provide a name.", required),
    minLength: helpers.withMessage(
      "Name must have at least 4 letters.",
      minLength(4)
    ),
    maxLength: helpers.withMessage(
      "Name can have at most 45 letters.",
      maxLength(45)
    ),
  },
  address: {
    required: helpers.withMessage("Please provide an address.", required),
    minLength: helpers.withMessage(
      "Address must have at least 4 letters.",
      minLength(4)
    ),
    maxLength: helpers.withMessage(
      "Address can have at most 45 letters.",
      maxLength(45)
    ),
  },
  phone: {
    required: helpers.withMessage('Please provide a phone number.', required),
    phone: helpers.withMessage('Please provide a valid phone number.', isMobilePhone),
  },
  email: {
    required: helpers.withMessage("Please provide an email.", required),
    email,
  },
  ccNumber: {
    required: helpers.withMessage(
      "Please provide a credit card number.",
      required
    ),
    ccNumber: helpers.withMessage(
      "Please provide a valid credit card number.",
      isCreditCard
    ),
  }
};
const v$ = useVuelidate(rules, form);

async function submitOrder() {
  console.log("Submit order");
  const isFormCorrect = await v$.value.$validate();
  if (!isFormCorrect) {
    form.checkoutStatus = "ERROR";
  } else {
    try {
      form.checkoutStatus = "PENDING";
      serverErrorMessage.value = defaultServerErrorMessage;

      const placeOrderResponse: OrderDetails | ServerErrorResponse =
        await cartStore
          .placeOrder({
            name: form.name,
            address: form.address,
            phone: form.phone,
            email: form.email,
            ccNumber: form.ccNumber,
            ccExpiryMonth: selectedMonth,
            ccExpiryYear: selectedYear,
          })

      if ("error" in placeOrderResponse) {
        form.checkoutStatus = "SERVER_ERROR";
        serverErrorMessage.value = (placeOrderResponse as ServerErrorResponse).message
        console.log("Error placing order", placeOrderResponse);
      } else {
        form.checkoutStatus = "OK";
        orderDetailsStore.setOrderDetails(placeOrderResponse as OrderDetails);
        await router.push({name: "confirmation-view"});
      }

    } catch (e) {
      form.checkoutStatus = "SERVER_ERROR";
      serverErrorMessage.value = defaultServerErrorMessage;
      console.log("Error placing order", e);
    }
  }
}

/* NOTE: For example yearFrom(0) == <current_year> */
function yearFrom(index: number) {
  return new Date().getFullYear() + index;
}
</script>

<style scoped>
/* TODO: Adapt these styles to your page */
.button {
  width: 180px;
}

.button:hover {
  font-style: italic;
}

.checkout-page {
  background: rgba(105, 100, 100, 0.5);
  color: black
}
.checkout-page-body {
  display: flex;
  padding: 1em;
}

form {
  display: flex;
  flex-direction: column;
}

form > div {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 0.5em;
}

form > div > input,
form > div > select {
  background-color: var(--card-background-color);;
  margin-left: 0.5em;
}

form > .error {
  color: red;
  text-align: right;
  font-style: italic;
  font-size: 15px; 
  position: relative; 
  top:-10px
}

.checkoutStatusBox {
  margin: 1em;
  padding: 1em;
  text-align: center;
}
</style>

<template>
  <div class="checkout-page" style="background-color: #BBDDEC;">
    <!-- TODO: Add an HTML section to display when checking out with an empty cart -->

    <br>
    <h3 style="text-align: center;">Checkout</h3>

    <section v-if="cart.empty">
      <br><br><br><br><br><br><br><br><br>
      <h3 style="color: black; text-align: center;"> Please add atleast an item to your cart to checkout..! </h3>
      <br><br><br>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <router-link :to="'/category/' + categoryStore.selectedCategoryName">
        <button class="button categories-button" style="padding: 0.5em 0.8em; margin-top: 0.25em; position:relative; right:-350px; top: -5px; background-color: white; color: black; text-align: center;"><strong>Continue Shopping</strong></button>  
      </router-link>
      <br><br><br><br><br><br><br><br><br><br><br><br>
    </section>

    <section class="checkout-page-body" v-if="!cart.empty">
      <form @submit.prevent="submitOrder">
        <div>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <label for="name">Name</label>
          <input
            type="text"
            style="border: solid black 2px;"
            size="20"
            id="name"
            name="name"
            v-model.lazy="v$.name.$model"
          />
        </div>
        <CheckoutFieldError 
        v-bind:field-name="v$.name" />
        <!-- TODO: Add address input and validation messages -->

        <div>
          <label for="address">Address</label>
          <input
            type="text"
            size="20"
            id="address"
            name="address"
            style="border: solid black 2px;"
            v-model.lazy="v$.address.$model"
          />
        </div>
        <CheckoutFieldError 
        v-bind:field-name="v$.address" />

        <div>
          <label for="phone">Phone</label>
          <input
            class="textField"
            type="text"
            size="20"
            id="phone"
            name="phone"
            style="border: solid black 2px;"
            v-model.lazy="v$.phone.$model"
          />
        </div>
        <CheckoutFieldError 
        v-bind:field-name="v$.phone" />
        <!-- TODO: Add phone validation message(s) -->

        <div>
          <label for="email">Email</label>
          <input type="text" size="20" id="email" name="email" v-model.lazy="v$.email.$model" style="border: solid black 2px;"/>
        </div>
        <!-- TODO: Add email validation message(s) -->
        <CheckoutFieldError 
        v-bind:field-name="v$.email" />

        <div>
          <label for="ccNumber">Credit card</label>
          <input type="text" size="20" id="ccNumber" name="ccNumber" v-model.lazy="v$.ccNumber.$model" style="border: solid black 2px;" />
        </div>
        <!-- TODO: Add credit card validation message(s) -->
        <CheckoutFieldError 
        v-bind:field-name="v$.ccNumber" />
        

        <div>
          <label>Exp Date</label> 
          <select v-model="selectedMonth" style="border: solid black 2px; width: 120px;">
            <option
              v-for="(month, index) in months"
              :key="index"
              :value="index + 1"
            >
              {{ month }} ({{ index + 1 }})
            </option>
          </select>
          <select v-model="selectedYear" style="border: solid black 2px;">
            <option v-for="(year, index) in years" :key="index" :value="year">
              {{ year }}
            </option>
          </select>
        </div>

        <!-- TODO (style): Use a single label for both month and date and put the on the same line. -->
        <!-- TODO (style): For example: Exp Date {Month} {Year}, with space between month/year selectors. -->
        <br>
        <div class="submit-button-row">
          <input
          type="submit"
          name="submit"
          class="button"
          style="word-wrap:break-word;padding: 0.5em 0.8em; margin-top: 0.25em; position:relative; right:20px; top: -5px; color: white; border-color: black; background-color: #0072A5;"
          :disabled="form.checkoutStatus === 'PENDING'"
          value="Complete Purchase"
        />
        </div>
        

        <section class="checkout-details" style="position: absolute; right: 600px; top: 240px;">
          <br>
        <span style="position: relative; right: -210px;">Your credit card will be charged <b> ${{ ((cart.subtotal + cart.surcharge/100)).toFixed(2) }} </b><br></span>
        <span style="position: relative; right: -260px;">(<b>${{ cart.subtotal.toFixed(2) }}</b> + <b>${{ (cart.surcharge/100).toFixed(2) }}</b> shipping)</span>
      </section>
        <!-- TODO (style): The submit button should not take up the entire width of the form. -->
        <!-- TODO (style): The submit button should be styled consistent with your own site. -->
      </form>
      <!-- TODO (style): Fix error message placement so they nearer to the correct fields. -->
      <!-- TODO (style): HINT: Use another <div> and label, input, and error, and use flexbox to style. -->

      

      <section v-show="form.checkoutStatus !== ''" class="checkoutStatusBox" style="font-style: italic; position: relative; left: 100px; top: 30px;">
        <br><br><br><br><br>
        <div v-if="form.checkoutStatus === 'ERROR'" style="font-style: italic; position: relative; left: 20px; top: 30px;">
          Error: Please fix the problems above and try again.
        </div>

        <div v-else-if="form.checkoutStatus === 'PENDING'" style="font-style: italic;">Processing...</div>

        <div v-else-if="form.checkoutStatus === 'OK'" style="font-style: italic;">Order placed...</div>

        <div v-else style="font-style: italic;">{{ serverErrorMessage }}</div>
      </section>
    </section>
  </div>
</template>

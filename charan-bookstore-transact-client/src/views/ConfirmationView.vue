<script setup lang="ts">
import { useCategoryStore } from "@/stores/category";
import ConfirmationTable from "@/components/ConfirmationTable.vue";
import { useOrderDetailsStore } from "@/stores/orderDetails";
import { computed } from "vue";

const categoryStore = useCategoryStore();
const orderDetailsStore = useOrderDetailsStore();
const orderDetails = orderDetailsStore.orderDetails;

const orderDate = computed(function () {
  let date = new Date(orderDetails.order.dateCreated);
  return date.toLocaleTimeString();
});

function getCCExpYear() {
  return new Date(orderDetails.customer.ccExpDate).getFullYear();
}

function getCCExpMonth() {
  let month = new Date(orderDetails.customer.ccExpDate).getMonth();
  if (month + 1 < 10) {
    return "0" + (month + 1);
  } else {
    return (month + 1);
  }
}

function getSecuredCardNumber() {
  return orderDetails.customer.ccNumber.substring(
    orderDetails.customer.ccNumber.length - 4
  );
}
</script>

<style scoped>

.button {
  width: 180px;
}
.button:hover {
  font-style: italic;
}

#confirmationView {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin-top: 1rem 0;
  padding: 1rem;
  border-bottom: 2.5px solid black;
}
.confirmationContent {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  padding: 2rem;
}
.check-mark {
  width: 8rem;
}
.confirm-tagline {
  font-size: 1.5rem;
  margin: 1rem;
}
.customer-details {
  display: flex;
  width: 100%;
  flex-direction: column;
}

</style>

<template>
    <div style="background-color: #BBDDEC;">
        

    <div v-if="orderDetailsStore.hasOrderDetails()" class="confirmationContent">
      <div class="confirm-tagline" style="text-align: center;"><b>Your Order is Confirmed</b></div>
      <div class="">
        Confirmation number #{{ orderDetails.order.confirmationNumber }}
      </div>
      <div>
        Time: {{ new Date(orderDetails.order.dateCreated).toLocaleString() }}
      </div>
      <confirmation-table> </confirmation-table>


      <div class="confirmationContent" style="text-align: center;">
        <h3> Customer Details: </h3>
        <ul style="text-align: left; position:relative; left: 85px; top: 10px;">
          <li><u>Name</u>: {{ orderDetails.customer.customerName }}</li>
          <li><u>Address</u>: {{ orderDetails.customer.address }}</li>
          <li><u>Email</u>: {{ orderDetails.customer.email }}</li>
          <li><u>Mobile</u>: {{ orderDetails.customer.phone }}</li>
          <li>
            <u>Card details</u>: **** **** **** {{ getSecuredCardNumber() }} ({{
              getCCExpYear()
            }}
            /
            {{ getCCExpMonth() }})
          </li>
        </ul>
      </div>
    </div>
    <div v-else>
      <br><br><br><br><br><br><br>
      <h3 style="text-align: center;"> No order is placed..! </h3>
      <br><br><br><br><br>
    
    </div>


        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <router-link :to="'/category/' + categoryStore.selectedCategoryName">
            <button class="button categories-button" style="padding: 0.5em 0.8em; margin-top: 0.25em; position:relative; right:-350px; top: -5px; background-color: white; color: black; text-align: center;"><strong>Continue Shopping</strong></button>  
        </router-link>
        <br> <br><br>
    </div>
</template>


import { defineStore } from 'pinia'
import type { OrderDetails } from '@/types'

const ORDER_DETAILS_KEY = 'OrderDetails'

export const useOrderDetailsStore = defineStore('OrderDetailsStore', {
  state: () => {
    let orderDetailsState = {
        orderDetails: {} as OrderDetails
    }
    let orderDetailsString = sessionStorage.getItem(ORDER_DETAILS_KEY)
    if (orderDetailsString !== null) {
      let OrderDetailsFromSession: OrderDetails;
      OrderDetailsFromSession = JSON.parse(orderDetailsString) as OrderDetails
      orderDetailsState.orderDetails = OrderDetailsFromSession
    }
    return orderDetailsState;
  },

  actions: {
    clearOrderDetails() {
      this.orderDetails = {} as OrderDetails;
      sessionStorage.setItem(ORDER_DETAILS_KEY, JSON.stringify(this.orderDetails))
    },
    setOrderDetails(orderDetails: OrderDetails) {
        this.orderDetails = orderDetails;
        sessionStorage.setItem(ORDER_DETAILS_KEY, JSON.stringify(this.orderDetails))
      },
    hasOrderDetails() {
        /* if(this.orderDetails.order.orderId === null) return false;
        return true; */
        return sessionStorage.getItem(ORDER_DETAILS_KEY) != null
      },
  }
})

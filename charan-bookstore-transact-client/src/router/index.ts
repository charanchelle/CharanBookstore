import { createRouter, createWebHistory } from "vue-router";
// @ts-ignore
import HomeView from "../views/HomeView.vue";
// @ts-ignore
import CategoryView from "@/views/CategoryView.vue";
import CartView from '@/views/CartView.vue'
import CheckoutView from "@/views/CheckoutView.vue";
import ConfirmationView from "@/views/ConfirmationView.vue";
import PageNotFoundView from "@/views/PageNotFoundView.vue"
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/category/:name",
      name: "category-view",
      component: CategoryView,
    },
    {
      path: "/cart",
      name: "cart-view",
      component: CartView,
    },
    {
      path: "/checkout",
      name: "checkout-view",
      component: CheckoutView,
    },
    {
      path: "/confirmation",
      name: "confirmation-view",
      component: ConfirmationView,
    },
    {
      path: "/home",
      name: "home1",
      component: HomeView,
    },
    {
      path: "/index.html",
      name: "home2",
      component: HomeView,
    },
    {
      path: "/:catchAll(.*)",
      name: "404",
      component: PageNotFoundView,
    },
  ],
});

export default router;

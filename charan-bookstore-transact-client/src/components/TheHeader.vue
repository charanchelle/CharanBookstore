<script setup lang="ts">
import TheHeaderDropdown from "@/components/TheHeaderDropdown.vue";
import { useCartStore } from "@/stores/cart";
import { useRouter } from "vue-router";
const cartStore = useCartStore()
const router = useRouter()

</script>

<style scoped>
header {
    background: var(--secondary-background-color);
    display: flex;
    justify-content: space-between;
    padding: 1em;
    gap: 1em;
}

header > section {
    flex-basis: 100%
}

.bookstore-logo {
    text-align: left;
}

.title-and-search-bar {
    text-align: center;
}

.header-dropdown-and-cart {
    text-align: right;
}

.text-logo {
    font-family: var(--title-font-family);
    width: 445px;
    height: 70px;
    font-size: 50px;
    text-align: center;
    color: white;
}

.search-bar {
    padding: 0.5em;
    border-radius: 5px;
    width: 300px;
    height: 35px;
    border: solid black 2px;
}

.fa-magnifying-glass {
    width: 35px;
    height: 35px;
    text-align: center;
}

.fa-cart-shopping{
    width: 50px;
    height: 35px;
    padding: 0.3em;
    text-align: center;
}

.fa-circle{
    width: 50px;
    height: 35px;
    text-align: right;
}

@media (max-width: 600px) {
    header {
        flex-direction: column;
    }
}
</style>

<template>
    <header class="container" style="height: 150px;">
        <section class="bookstore-logo">


                <router-link to="/">
                <img
                        src="/site-images/book_logo.png"
                        alt="Charan Bookstore Logo"
                        width="100"
                        height="100"
                        style = "position:relative; left:20px; top: -3px;"
                />
                </router-link>
        </section>
        <section class="title-and-search-bar">
            <h1 class="text-logo" style="font-family: 'Merriweather';">Charan Bookstore</h1>
            <form action="category.html">
                <input type="text" class="search-bar" />
                <i style = "cursor: pointer;" class="fa-solid fa-magnifying-glass fa-lg"></i>
            </form>
        </section>
        <section class="header-dropdown-and-cart">

            <span style = "cursor: pointer;" class="fa-layers fa-fw">
              <i style = "position:relative; left: -10px; font-size: 3em;" class="fa-sharp fa-solid fa-cart-shopping fa-2x" @click="router.push({ path: '/cart' })"></i>
              <span style = "position:relative; left:-23px; top:-18px; font-size: 1.5em; font-weight:900" class="fa-layers-text fa-inverse" data-fa-transform="grow-6 down-0" @click="router.push({ path: '/cart' })">{{ cartStore.count }}</span>
            </span>

            <span style = "cursor: pointer;" class="fa-layers fa-fw">
              <i style="position:relative; left: 17px; font-size: 3em;" class="fa-solid fa-circle fa-2x"></i>
              <span style = "position:relative; left:-23px; top:-10px; font-size: 1.5em; font-weight:900" class="fa-layers-text fa-inverse" data-fa-transform="grow-6 down-0">CC</span>
            </span><br><br>
      <the-header-dropdown>

      </the-header-dropdown>
    </section>
  </header>
</template>

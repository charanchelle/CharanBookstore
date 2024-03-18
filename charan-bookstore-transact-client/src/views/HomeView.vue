<script setup lang="ts">
import TheHomeCategoryList from "@/components/TheHomeCategoryList.vue";
</script>

<style scoped>
.home-page {
  display: flex;
}

.welcome-text {
  width: 20em;
}

.button {
  width: 200px;
  transition-duration: 2s;
}

.button:hover {
  font-style: italic;
}
</style>
<template>
<!--  <div class="home-page" >
    <section class="welcome-text flow-content container dark-background">
      <h2>Welcome to Another Bookstore</h2>
      <p>
        Another Bookstore is a client-side application written using Vue
        components.
      </p>
      <p>
        It includes a home page and a category page. Both pages have the same
        header and footer.
      </p>
      <p>
        You can navigate to the category page by choosing any category from the
        dropdown menu in the header. You can navigate to the home page by
        clicking on the logo in the header.
      </p>
      <p>Take some time and explore the code.</p>
    </section>
    <section class="category-images container">
      <the-home-category-list></the-home-category-list>
    </section>
  </div>-->

    <div class="home-page1" style="background-color: #BBDDEC;">
        <section class="body" style="text-align: center;">
            <br> <h1> Welcome to My Bookstore </h1> <br>
            <router-link to="/category/Romance/">
              <button class="button categories-button" style="font-size: large; font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif; color: white; border-color: black; background-color: #0072A5;"><strong>SHOP FOR BOOKS</strong></button>
            </router-link>
            <p style="font-size: large; font-family: 'Merriweather';"> <br /> Why not try something from <strong>Philosophy</strong>? <br /> </p>
        </section>
        <section class="category-images container"><br>
            <the-home-category-list></the-home-category-list>
        </section>
    </div>
</template>

<script setup lang="ts">
import type { CategoryItem } from "@/types";
const categoryImageFileName = function (category: CategoryItem): string {
  let name = category.name.toLowerCase();
  name = name.replace(/ /g, "-");
  name = name.replace(/'/g, "");
  return `${name}.jpg`;
};
</script>

<style scoped>
ul {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;

  gap: 1em;
}

li {
  text-align: center;
  cursor: pointer;
}

li div {
  margin-bottom: -2em;
  padding: 0.5em 0;
  background: rgba(0, 0, 0, 0.5); /* last # is percent opacity */
  color: white;
  transform: translateY(-2.25em);
}

.row {
    display: flex;
}

</style>

<template>
  <ul>
<!--    <li v-for="category in categoryList" :key="category.categoryId">
      <router-link :to="'../category/' + category.name">
        <img
          :src="'category-images/' + categoryImageFileName(category)"
          :alt="category.name + ' category'"
          :width="170"
          :height="170"
        />
        <div>{{ category.name }}</div>
      </router-link>
    </li>-->

      <div class="row">
          <div class="column">
            <router-link to="/category/Romance/">
              <img
                      src="/category-images/atlas-shrugged.jpg"
                      alt="AtlasShrugged"
                      width="171"
                      height="170"
                      style = "position:relative; left:230px; top: -10px;"
              />
            </router-link>
          </div>
          <router-link to="/category/Romance/">
            <div class="column">
              <img
                      src="/category-images/wisdom-of-life.jpg"
                      alt="WisdomOfLife"
                      width="180"
                      height="180"
                      style = "position:relative; left:270px; top:-10px;"
              />
          </div>
          </router-link>
          <router-link to="/category/Romance/">
            <div class="column">
              <img
                      src="/category-images/thus-spoke-zarathustra.jpg"
                      alt="ThusSpokeZarathusra"
                      width="188"
                      height="188"
                      style = "position:relative; left:310px; top:-10px;"
              />
          </div>
          </router-link>
      </div>
  </ul>
</template>

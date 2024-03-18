<script setup lang="ts">
//import {defineProps} from "vue";
import type {BaseValidation} from "@vuelidate/core";

const props = defineProps<{
  fieldName: BaseValidation;
}>();
</script>

<style scoped>
form > .error {
	color: red;
	text-align: right;
  font-style: italic;
  font-size: 15px; 
  position: relative; 
  top:-10px
}
</style>

<template>
  <template v-if="fieldName.$error">
    <span
      class="error"
      >{{ fieldName.$errors[0].$message }}</span
    >
  </template>
</template>

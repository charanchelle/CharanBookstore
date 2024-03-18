import { defineStore } from "pinia";
import type { CategoryItem } from "@/types";
import { apiUrl } from "@/api";

export const useCategoryStore = defineStore("category", {
    state: () => ({
      categoryList: [] as CategoryItem[],
      selectedCategoryName: "Romance"
    }),
    actions: {
      async fetchCategories(): Promise<void> { 
        const response = await fetch(`${apiUrl}/categories/`)
        const data = await response.json()
        this.categoryList = data as CategoryItem[]
       },
       updateSelectedCategoryName(name: string) {
        this.selectedCategoryName = name;
      }
    },
    // getters
  });
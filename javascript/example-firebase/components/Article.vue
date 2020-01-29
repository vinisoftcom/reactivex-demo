<template>
  <div>
    <router-link v-if="article" :to="'/articles/' + article.article_id.id">
      <div class="max-w-sm m-4 rounded overflow-hidden shadow-lg">
        <img class="w-full h-48" :src="article.cover" alt="Sunset in the mountains">
        <div class="px-6 py-4">
          <div class="font-bold text-xl mb-2">{{ article.title }}</div>
          <p class="text-gray-700 text-base">{{ article.preview }}</p>
        </div>
        <div class="px-6 py-4">
          <span v-bind:key="tag" v-for="tag in article.tags" class="inline-block bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700 mr-2">#{{ tag }}</span>
        </div>
      </div>
    </router-link>
  </div>
</template>
<script>
export default {

  props: ['article'],

}
</script>
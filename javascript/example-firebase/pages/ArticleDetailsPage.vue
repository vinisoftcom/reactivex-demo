<template>
  <div class="container max-w-xl mx-auto pb-10">
    <div v-if="article" class="mt-6">
      <h1 class="text-3xl">{{ article.title }}</h1>

      <div class="mt-6" v-if="article.tags.length > 0">
        <span v-bind:key="tag" v-for="tag in article.tags" class="inline-block bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700 mr-2">#{{ tag }}</span>
      </div>

      <p class="mt-6">{{ article.content }}</p>

      <div v-if="author" class="md:flex mt-10 shadow bg-white rounded-lg p-6">
        <img class="h-16 w-16 md:h-24 md:w-24 rounded-full mx-auto md:mx-0 md:mr-6" :src="author.avatar">
        <div class="text-center md:text-left">
          <h2 class="text-lg">{{ author.name }}</h2>
          <div class="text-blue-800 font-bold">{{ author.role }}</div>
          <div class="text-gray-600">{{ author.email }}</div>
          <div class="text-gray-600">joined {{ author.joined }}</div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Articles from '../articles';
import Authors from '../authors';
import { of, combineLatest } from 'rxjs';
import { flatMap, first } from 'rxjs/operators';

export default {
  data() {
    return {
      article: null,
      author: null,
      isLoading: true,
      error: null,

      subscriptions: [],
    }
  },

  mounted() {
    const articleId = this.$route.params.id;

    const subscription = Articles.findById(articleId).pipe(
      flatMap(article => combineLatest(
          of(article), 
          Authors.findById(article.author.id)
        )
      ),
    ).subscribe(
      ([ article, author ]) => {
        this.author = author;
        this.article = article;
        this.isLoading = false;
      },
      error => {
        this.error = error;
        this.isLoading = false;
      }
    );

    this.subscriptions.push(subscription);
  },

  beforeDestroy() {
    this.subscriptions.forEach(subscription => {
      subscription.unsubscribe();
    });
  },
}
</script>
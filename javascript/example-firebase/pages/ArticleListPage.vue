<template>
  <div class="flex flex-wrap justify-center">
    <Article v-bind:key="article.article_id.id" v-for="article in articles" :article="article"></Article>
  </div>
</template>
<script>
import Article from '../components/Article';
import Articles from '../articles';

export default {
  components: { Article },

  data() {
    return {
      error: null,
      articles: [],
      isLoading: true,

      subscriptions: [],
    }
  },

  mounted() {
    const subscription = Articles.featured().subscribe(
      articles => {
        this.articles = articles;
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
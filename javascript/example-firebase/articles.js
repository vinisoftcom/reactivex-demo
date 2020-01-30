import * as firebase from "firebase/app";
import "firebase/firestore";

import { from } from 'rxjs';
import { flatMap, map, toArray, shareReplay } from 'rxjs/operators';
import { fromRef } from './utils';

class ArticleStore {

  constructor() {
    this.$featured = null;

    this.articles = {};
  }

  /**
   * Retrieve article by ID.
   */
  findById(id) {
    if (!this.articles.hasOwnProperty(id)) {
      const documentRef = firebase.firestore()
        .doc(`articles/${id}`);

      this.articles[id] = fromRef(documentRef).pipe(
        map(snap => snap.data())
      );
    }

    return this.articles[id];
  }

  /**
   * Retrieve featured articles.
   */
  featured() {
    if (!this.$featured) {
      const collectionRef = firebase.firestore()
        .collection('featured_articles');

      this.$featured = fromRef(collectionRef).pipe(
        map(snap => snap.docs.map(doc => doc.data())),
      );
    }

    return this.$featured;
  }

}

const Articles = new ArticleStore();
export default Articles;
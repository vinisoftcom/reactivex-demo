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
        flatMap(
          snap => from(snap.docs).pipe(
            map(doc => doc.data()),
            toArray()
          ),
        ),
        shareReplay({
          bufferSize: 1,
          refCount: true
        }),
      );
    }

    return this.$featured;
  }

}

const Articles = new ArticleStore();
export default Articles;
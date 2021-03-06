import * as firebase from "firebase/app";
import "firebase/firestore";

import { map, shareReplay } from 'rxjs/operators';
import { fromRef } from './utils';

class AuthorsStore {

  constructor() {
    this.authors = {};
  }

  /**
   * Find author by ID.
   */
  findById(id) {
    if (!this.authors.hasOwnProperty(id)) {
      const documentRef = firebase.firestore()
        .doc(`authors/${id}`);

      this.authors[id] = fromRef(documentRef).pipe(
        map(snap => snap.data()),
      );
    }

    return this.authors[id];
  }
  
}

const Authors = new AuthorsStore();
export default Authors;
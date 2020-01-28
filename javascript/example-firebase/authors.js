import * as firebase from "firebase/app";
import "firebase/firestore";

import { map, shareReplay } from 'rxjs/operators';
import { fromRef } from './utils';

class AuthorsStore {

  constructor() {
    this.authors = {};
  }

  findById(id) {
    if (!this.authors.hasOwnProperty(id)) {
      const documentRef = firebase.firestore()
        .doc(`authors/${id}`);

      this.authors[id] = fromRef(documentRef).pipe(
        map(snap => snap.data()),
        shareReplay({
          bufferSize: 1,
          refCount: true
        }),
      );
    }

    return this.authors[id];
  }

}

const Authors = new AuthorsStore();
export default Authors;
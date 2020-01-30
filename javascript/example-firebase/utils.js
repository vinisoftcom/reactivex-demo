import { Observable } from 'rxjs';

export function fromRef(ref) {
  return new Observable(e => {
    // console.log('CONSUMER SUBSCRIBED, CALLING ref.onSnapshot')
    const unsubscribe = ref.onSnapshot(value => {
      // console.log('onSnapshot called');
      e.next(value);
    }, error => {
      // console.log('onSnapshot:error called');
      e.error(error);
    });

    return () => {
      // console.log('CONSUMER UNSUBSCRIBED')
      unsubscribe();
    }
  });
}
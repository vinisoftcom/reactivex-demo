import { Observable } from 'rxjs';

export function fromRef(ref) {
  return new Observable(e => {
    console.log(`Consumer subscribed to ${ref.path}`)
    const unsubscribe = ref.onSnapshot(value => {
      console.log('Got new value from firestore, passing through emmiter…');
      e.next(value);
    }, error => {
      console.log('Oplaa, got error from firestore.');
      e.error(error);
    });

    return () => {
      console.log(`Consumer unsubscribed from ${ref.path}`);
      unsubscribe();
    }
  });
}
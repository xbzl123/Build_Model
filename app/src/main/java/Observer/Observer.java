package Observer;

public interface Observer<T> {
    void onUpdate(Observable<T> observable,T data);
}

package iterator;

import java.util.ArrayList;
import java.util.List;

public class IteratImp implements Iterat {
    private List<Object> list = new ArrayList<>();

    int cursor = 0;

    public IteratImp(List<Object> list){
        this.list = list;
    }

    @Override
    public Object next() {
        if(hasNext()){
            cursor++;
        return list.get(cursor);
        }
        return null;
    }

    @Override
    public Object first() {
        if(list.size() > 0 ){
        return list.get(0);
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        if(list.size()-cursor <= 1){
            return false;
        }
        return true;
    }
}

package iterator;

import java.util.ArrayList;
import java.util.List;

public class AggregateImp implements Aggregate{
    private List<Object> list = new ArrayList<>();

    @Override
    public void addObject(Object object) {
        list.add(object);
    }

    @Override
    public Iterat getIterator() {
        return new IteratImp(list);
    }
}

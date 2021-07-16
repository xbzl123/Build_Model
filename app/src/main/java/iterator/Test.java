package iterator;

public class Test {
    public static void main(String[] args){
        Aggregate aggregateImp = new AggregateImp();
        aggregateImp.addObject("111111");
        aggregateImp.addObject("222222");
        aggregateImp.addObject("333333");
        aggregateImp.addObject("444444");
        Iterat iterat = aggregateImp.getIterator();
        System.out.println(iterat.first());
        while (iterat.hasNext()){
            System.out.println(iterat.next());
        }
    }
}

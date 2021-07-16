package composite;

public class Test {

    public static void display(Branch branch){
        for (TreePoint b:branch.getChildList()){
            if(b instanceof Leaf){
                b.getName();
            }else{
                display((Branch) b);
            }
        }
    }

    public static void main(String[] args){
        Branch root = new Branch();
        Branch b1 = new Branch();
        Branch b2 = new Branch();
//        root.getName();
        root.add(b1);
        root.add(b2);

        Leaf l1 = new Leaf();
        Leaf l2 = new Leaf();
        Leaf l3 = new Leaf();

        b1.add(l1);
        b1.add(l2);
        b1.add(l3);
        display(b1);
    }
}

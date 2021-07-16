package composite;

import java.util.ArrayList;
import java.util.List;

public class Branch extends TreePoint{
    List list = new ArrayList<>();

    public void add(TreePoint treePoint){
        if(treePoint instanceof Leaf){
            System.out.println("add leaf");
        }else{
            System.out.println("add branch");
        }
        list.add(treePoint);
    }

    public void remove(TreePoint treePoint){
        list.remove(treePoint);
    }

    public List<TreePoint> getChildList() {
        return this.list;
    }

    @Override
    public void getName() {
        System.out.println("Branch");
    }
}

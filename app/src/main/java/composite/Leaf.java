package composite;

import java.util.ArrayList;
import java.util.List;

public class Leaf extends TreePoint{
    List list = new ArrayList<>();

    @Override
    public void getName() {
        System.out.println("Leaf");
    }
}

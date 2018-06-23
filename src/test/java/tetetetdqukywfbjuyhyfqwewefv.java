import org.junit.Test;
import report.spring.utils.CollectionsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class tetetetdqukywfbjuyhyfqwewefv {
    @Test
    public void name() {
        Map<Integer,Map<Integer,String>> root = new HashMap<>();
       Map<Integer,String> inner1 = new HashMap<>();
        inner1.put(1,"A1");
        inner1.put(2,"B1");
        Map<Integer,String> inner2 = new HashMap<>();
        inner2.put(1,"A2");
        inner2.put(2,"B2");

        root.put(1,inner1);
        root.put(1,inner2);
        root.get(1).get(1);



        String[][] m = {{"A1","B2"},{"A2","B2"}};

    }

    @Test
    public void ss() {
        List<Integer> i = new ArrayList<>();
        List<Integer> j = new ArrayList<>();
        i.add(1);
        i.add(2);
        i.add(7);
        i.add(3);

        j.add(1);
        j.add(6);
        j.add(3);
        System.out.println(CollectionsUtils.baseCollectionNotContain(i,j));
        System.out.println(CollectionsUtils.changeCollectionNotContain(i,j));

    }

    @Test
    public void equalsTest() {
        A a1 = new B();
        ((A) new B()).equals(a1);
    }

    class A{
        @Override
        public boolean equals(Object obj) {
            System.out.println("A");
            return super.equals(obj);
        }
    }

    class  B extends A{
        @Override
        public boolean equals(Object obj) {
            System.out.println("b");
            return true;
        }
    }
}

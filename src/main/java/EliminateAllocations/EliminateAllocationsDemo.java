package EliminateAllocations;

public class EliminateAllocationsDemo {
    public static void main(String[] args) {
        int n = 100000000;
        long begin = System.currentTimeMillis();
        EscapeAnalysis escapeAnalysis = new EscapeAnalysis();
        for (int i = 0; i < n; i++) {
            escapeAnalysis.escape();
        }
        System.out.println(System.currentTimeMillis() - begin);
    }



}

class EscapeAnalysis {
    public Person p;

    //1情况 -Xms5m -Xmx5m -XX:+PrintGC -XX:-EliminateAllocations
    //2情况 -Xms5m -Xmx5m -XX:+PrintGC -XX:+EliminateAllocations 或者去掉 -XX:+EliminateAllocations
    public void escape() {
        p = new Person(300, "escape ..............");
    }

    //1情况 -Xms5m -Xmx5m -XX:+PrintGC -XX:-EliminateAllocations
    //2情况 -Xms5m -Xmx5m -XX:+PrintGC -XX:+EliminateAllocations 或者去掉 -XX:+EliminateAllocations
    public String noEscape() {
        Person person = new Person(26, "noEscape .......................................");
        return person.Fields2;
    }
}

class Person {
    public int Filed;
    public String Fields2;

    public Person(int filed, String fields2) {
        Filed = filed;
        Fields2 = fields2;
    }
}
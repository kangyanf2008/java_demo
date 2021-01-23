public class ObjectInstanceThreadInit {
    public int i ;
    public ObjectInstanceThreadInit(){
        new Thread(()->{
            this.i = 8;
        }).start();
    }

    public static void main(String[] args) {
        ObjectInstanceThreadInit d = new ObjectInstanceThreadInit();
        while (d.i == 0) {
            System.out.println(d.i);
        }
        System.out.println(d.i);
        for( int j = 0; j < 20; j++ ) {
            System.out.println(new ObjectInstanceThreadInit().i);
        }

    }
}

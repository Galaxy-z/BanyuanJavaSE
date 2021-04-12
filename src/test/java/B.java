public class B extends A{
    private void method(){
        super.method2();
    }

    public static void main(String[] args) {
        B b = new B();
        b.method();
    }

}

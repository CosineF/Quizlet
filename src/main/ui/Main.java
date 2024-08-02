package ui;

//import java.io.FileNotFoundException;
// run Vocabulary Application
//public class Main {
//    public static void main(String[] args) {
//        try {
//            new VocabApp();
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to run application: file not found");
//        }
//    }
//}


public class Main {
    public static void main(String[] args) {
        new BeginFrame();
        //return221(8);
    }

    static int return221(int i) {
        if (i == 1) {
            System.out.println("value for " + i + "is 3");
            return 24;
        } else {
            int value = 24 * i + 9 * return221(i - 1);
            System.out.println("value for " + i + "is " + value);
            return value;
        }
    }
}
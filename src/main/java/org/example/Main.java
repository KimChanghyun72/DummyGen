package org.example;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        for(int i=0; i<100; i++) {
            String jumin = Dummy.getJumin(true);
            System.out.println("주민등록번호 생성기: " + jumin.substring(0, 6) + "-" + jumin.substring(6));
        }
    }
}
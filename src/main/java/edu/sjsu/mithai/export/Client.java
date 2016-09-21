package edu.sjsu.mithai.export;

/**
 * Created by sjinturkar on 9/19/16.
 */
public class Client {
    public static void main(String [] args) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Exporter e = new Exporter("KAFKA");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Exporter e = new Exporter("HTTP");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

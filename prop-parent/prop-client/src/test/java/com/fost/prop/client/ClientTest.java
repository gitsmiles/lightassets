package com.fost.prop.client;

public class ClientTest {
    public static void main(String[] args) {
        for (;;) {
            try {
                System.out.println(PropertiesUtils.getProperty("zzz/a/b", "key"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        }
    }
}

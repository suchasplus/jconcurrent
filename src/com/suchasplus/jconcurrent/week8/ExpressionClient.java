package com.suchasplus.jconcurrent.week8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Powered by suchasplus@gmail.com on 2017/4/17.
 */
public class ExpressionClient {
    static ExecutorService es= Executors.newCachedThreadPool();
    static Long sleep_time=1000*1000*1000L;

    public static class ExpressionCLT implements Runnable{
        String express = null;
        ExpressionCLT (String f) {
            express = f;
        }
        @Override
        public void run() {

            Socket client=null;
            PrintWriter writer=null;
            BufferedReader reader=null;
            try {
                client=new Socket();
                client.connect(new InetSocketAddress("localhost",18000));
                writer=new PrintWriter(client.getOutputStream(),true);

                writer.print(express);

                writer.println();
                writer.flush();
                reader=new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println("from server:"+reader.readLine());
            } catch (IOException e){
                e.printStackTrace();
            } finally {
                if(writer!=null){
                    writer.close();
                }
                if(reader!=null){
                    try {
                        reader.close();
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }

                }
                if(client!=null){
                    try {
                        client.close();
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String[] arithmetics = new String[]{
                "1 + 2 * 5",
                " 8 * ( 2 + 3)",
                " 1 * 2 / 4 * 3",
                " 100 - 5 * 2",
                " 233 + 874",
                "520 + 666 * 2",
                "300 + 6 / 20 - (9 / 3)",
                " 666 + 123",
                "888 - 320",
                "1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10"
        };

        for(int i=0;i<10;i++){
            ExpressionCLT ec=new ExpressionCLT(arithmetics[i]);
            es.execute(ec);
        }
    }
}

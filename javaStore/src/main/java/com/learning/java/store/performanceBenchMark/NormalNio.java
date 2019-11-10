package com.learning.java.store.performanceBenchMark;

/*
 @author:   chenyang
 @date  2019/11/10 12:10 AM

*/

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NormalNio {

    RandomAccessFile aFile;

    FileChannel inChannel;

    public NormalNio() throws Exception{
        this.aFile = new RandomAccessFile("/Users/chenyang/data/nio-data.txt", "rw");
        this.inChannel = aFile.getChannel();

    }

    public static void main(String[] args) throws Exception{
        NormalNio normalNio=new NormalNio();

        long start= System.currentTimeMillis();
        for(int k=0;k<100;k++) {//100*1024*1024=100m
            System.out.println(k);
            for (int i = 0; i < 1024; i++) {//1024*1024=1m
                for (int j = 0; j < 32; j++) {//1024
                    normalNio.writeFile();
                }
            }
        }
        System.out.println("执行时间:"+(System.currentTimeMillis()-start)/1000);
        normalNio.inChannel.close();
    }


    private void writeFile() throws Exception{
        inChannel.position(aFile.length());
        String newData = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" ;
        byte[] bytes=newData.getBytes();
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.put(bytes);
        buf.flip();
        while (buf.hasRemaining()) {
            inChannel.write(buf);
        }
    }
}

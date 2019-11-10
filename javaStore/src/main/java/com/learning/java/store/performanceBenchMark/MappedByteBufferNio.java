package com.learning.java.store.performanceBenchMark;

/*
 @author:   chenyang
 @date  2019/11/10 10:55 AM

 java 通过MappedByteBuffer实现mmap

*/

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferNio {

    RandomAccessFile aFile;

    FileChannel inChannel;

    MappedByteBuffer mappedByteBuffer;

    public MappedByteBufferNio() throws Exception{
        this.aFile = new RandomAccessFile("/Users/chenyang/data/nio-data.txt", "rw");

        this.inChannel = aFile.getChannel();
        //将文件的前 100*1024*1024 个字节映射到内存中
        this.mappedByteBuffer=inChannel.map(FileChannel.MapMode.READ_WRITE,0,100*1024*1024);
    }

    public static void main(String[] args) throws Exception{
        MappedByteBufferNio mappedByteBufferNio=new MappedByteBufferNio();

        long start= System.currentTimeMillis();
        for(int k=0;k<100;k++) {//100*1024*1024=100m
            System.out.println(k);
            for (int i = 0; i < 1024; i++) {//1024*1024=1m
                for (int j = 0; j < 32; j++) {//1024
                    mappedByteBufferNio.writeFile();
                }
            }
        }
        System.out.println("执行时间:"+(System.currentTimeMillis()-start)/1000);
        mappedByteBufferNio.inChannel.close();
        mappedByteBufferNio.aFile.close();
    }


    private void writeFile() throws Exception{
        inChannel.position(aFile.length());
        String newData = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" ;//32 byte
        byte[] bytes=newData.getBytes();
        mappedByteBuffer.put(bytes);

    }
}

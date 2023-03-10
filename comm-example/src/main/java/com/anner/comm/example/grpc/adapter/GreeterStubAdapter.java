package com.anner.comm.example.grpc.adapter;

import com.anner.comm.example.Greeter;
import com.anner.comm.example.grpc.greeter.GreeterGrpc;
import com.anner.comm.example.grpc.greeter.GreeterGrpc.GreeterStub;
import com.anner.comm.example.grpc.greeter.HelloReply;
import com.anner.comm.example.grpc.greeter.HelloRequest;
import com.anner.comm.grpc.adapter.StreamObserverReader;
import com.anner.comm.stream.CommStreamPipe;

import io.grpc.Channel;

/**
 * Greeter的grpc客户端适配器 , 返回一个服务对象
 */
public class GreeterStubAdapter implements Greeter {

     private final GreeterStub stub;

     public GreeterStubAdapter(Channel channel) {
          this.stub = GreeterGrpc.newStub(channel);
          // 异步方式对于非流方式调用适配起来比较麻烦
          // 如果服务中没有流方式调用，也可以用同步方式 GreeterGrpc.newBlockingStub()
     }

     @Override
     public String sayHello(String name) throws Exception {
          StreamObserverReader<HelloReply> reader = new StreamObserverReader<>();
          stub.sayHello(HelloRequest.newBuilder().setName(name).build(), reader);
          reader.waitUntilComplete();
          return reader.getSingle(Exception.class).getMessage();
     }

     @Override
     public String sayHelloToManyPeople(CommStreamPipe requestStream) throws Exception {
          // TODO Auto-generated method stub
          throw new UnsupportedOperationException("Unimplemented method 'sayHelloToManyPeople'");
     }

     @Override
     public CommStreamPipe sayHelloMultiTimes(String name) throws Exception {
          // TODO Auto-generated method stub
          throw new UnsupportedOperationException("Unimplemented method 'sayHelloMultiTimes'");
     }

     @Override
     public CommStreamPipe sayHelloByStream(CommStreamPipe requestStream) throws Exception {
          // TODO Auto-generated method stub
          throw new UnsupportedOperationException("Unimplemented method 'sayHelloByStream'");
     }

}

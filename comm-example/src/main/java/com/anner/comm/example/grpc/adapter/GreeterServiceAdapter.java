package com.anner.comm.example.grpc.adapter;

import com.anner.comm.example.Greeter;
import com.anner.comm.example.grpc.greeter.GreeterGrpc.GreeterImplBase;
import com.anner.comm.example.grpc.greeter.HelloReply;
import com.anner.comm.example.grpc.greeter.HelloRequest;

import io.grpc.stub.StreamObserver;

/**
 * Greeter的grpc服务端适配器
 */
public class GreeterServiceAdapter extends GreeterImplBase {

     private final Greeter greeter;

     public GreeterServiceAdapter(Greeter greeter) {
          this.greeter = greeter;
     }

     @Override
     public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
          try {
               String result = greeter.sayHello(request.getName());
               responseObserver.onNext(HelloReply.newBuilder().setMessage(result).build());
          } catch (Throwable e) {
               responseObserver.onError(e);
          } finally {
               responseObserver.onCompleted();
          }
     }
}

package com.anner.comm.example.grpc;

import com.anner.comm.CommRepo;
import com.anner.comm.example.Greeter;
import com.anner.comm.example.grpc.adapter.GreeterStubAdapter;
import com.anner.comm.grpc.GRPCCommClient;
import com.anner.comm.grpc.utils.GRPCAdapterRegistry;
import com.anner.comm.loadbalancer.DefaultLoadBalancer;

public class GRPCClientMain {
     public static void main(String[] args) throws Exception {
          // 创建默认的负载均衡器，添加一个grpc节点
          DefaultLoadBalancer loadBalancer = new DefaultLoadBalancer();
          loadBalancer.addClient(new GRPCCommClient("localhost", 50051));

          // 注册负载均衡器
          CommRepo.registerLoadBalancer("example", loadBalancer);

          // 注册grpc的服务
          GRPCAdapterRegistry.registerStubAdapter(Greeter.class, GreeterStubAdapter::new);

          // 调用方法
          String result = CommRepo.getCaller("example", Greeter.class).sayHello("Jackson");
          System.out.println("[调用方法] result:\n" + result);
          System.out.println();

     }
}

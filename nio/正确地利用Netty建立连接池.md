### 一、问题描述

Netty是最近非常流行的高性能异步通讯框架，相对于Java原生的NIO接口，Netty封装后的异步通讯机制要简单很多。

但是小K最近发现并不是所有开发人员在使用的过程中都了解其内部实现机制，而是照着葫芦画瓢。

网上简单搜索下，在客户端使用Netty建立连接池的文章也是比较少。今天小K给大家简单介绍下使用Netty建立连接池的方法。

首先我们来看下Netty官方给出的客户端sample实例：
```
//创建一个EventLoopGroup，可以简单认为是Netty框架下的线程池，默认最大线程数量是处理器数量的2倍
  EventLoopGroup group = new NioEventLoopGroup();
  try {
//Netty建立连接的辅助类
      Bootstrap b = new Bootstrap();
//配置属性，向pipeline添加handler
      b.group(group)
       .channel(NioSocketChannel.class)
       .option(ChannelOption.TCP_NODELAY, true)
       .handler(new ChannelInitializer<SocketChannel>() {
           @Override
           public void initChannel(SocketChannel ch) throws Exception {
               ChannelPipeline p = ch.pipeline();
               if (sslCtx != null) {
                   p.addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
               }
               //p.addLast(new LoggingHandler(LogLevel.INFO));
               p.addLast(new EchoClientHandler());
           }
       });
 
      //启动建立连接
      ChannelFuture f = b.connect(HOST, PORT).sync();
 
      //block直到连接被关闭
      f.channel().closeFuture().sync();
```
很简单？没错，确实如此。那么现在问题来了，如果你现在需要连接100个服务器，你会怎么做呢？
下面这样处理怎么样呢？我们在外层加了一个for循环
```
for(Host host : hosts){
          //创建一个EventLoopGroup，可以简单认为是Netty框架下的线程池，默认线程数量是处理器数量的2倍
          EventLoopGroup group = new NioEventLoopGroup(1);
          try {
　　　　　　　　//Netty建立连接的辅助类
              Bootstrap b = new Bootstrap();
　　　　　　　　//配置属性，向pipeline添加handler
              b.group(group)
               .channel(NioSocketChannel.class)
               .option(ChannelOption.TCP_NODELAY, true)
               .handler(new ChannelInitializer<SocketChannel>() {
                   @Override
                   public void initChannel(SocketChannel ch) throws Exception {
                       ChannelPipeline p = ch.pipeline();
                       if (sslCtx != null) {
                           p.addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
                       }
                       //p.addLast(new LoggingHandler(LogLevel.INFO));
                       p.addLast(new EchoClientHandler());
                   }
               });
   
              //启动建立连接
              ChannelFuture f = b.connect(HOST, PORT).sync();
   
              //block直到连接被关闭
              f.channel().closeFuture().sync();
}
```
问题很明显，如果每一个channel都对应一个NIOEventLoopGroup，那么我们实际上构建了一个connection:thread = 1:1的模型，

随着连接数不断地扩大，线程膨胀的问题就会突显出来。

### 一、问题解决

那么如何避免线程膨胀的问题呢？很简单，我们只要稍微修改下上面的代码就可以了。
```
    NioEventLoopGroup group = new NioEventLoopGroup();
    Bootstrap b = new Bootstrap();
   for(Host HOST : Hosts){
    try {
      b.group(group)
       .channel(NioSocketChannel.class)
       .option(ChannelOption.TCP_NODELAY, true)
       .handler(new ChannelInitializer<SocketChannel>() {
           @Override
           public void initChannel(SocketChannel ch) throws Exception {
               ChannelPipeline p = ch.pipeline();
               if (sslCtx != null) {
                   p.addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
               }
               //p.addLast(new LoggingHandler(LogLevel.INFO));
                           p.addLast(new EchoClientHandler());
                       }
         });
   
         ChannelFuture f = b.connect(HOST, PORT).sync();
     }
```

在上面的代码中，我们使用同一个bootstrap创建了多个连接，从而使连接共享了一个NioEventLoopGroup，避免了线程膨胀的问题。


## 集群模式
### 1.主从模式：

一个Master可以有多个Slaves默认配置下，master节点可以进行读和写，slave节点只能进行读操作
#### 缺点：
master节点挂了以后，redis就不能对外提供写服务了，因为剩下的slave不能成为master

### 2.sentinel（哨兵）模式
当master节点挂了以后，sentinel会在slave中选择一个做为master，并修改它们的配置文件，
其他slave的配置文件也会被修改，比如slaveof属性会指向新的master
当master节点重新启动后，它将不再是master而是做为slave接收新的master节点的同步数据，
sentinel因为也是一个进程有挂掉的可能，所以sentinel也会启动多个形成一个sentinel集群
一个sentinel或sentinel集群可以管理多个主从Redis。当使用sentinel模式的时候，客户端就不要直接连接Redis，
而是连接sentinel的ip和port，由sentinel来提供具体的可提供服务的Redis实现，这样当master节点挂掉以后，
sentinel就会感知并将新的master节点提供给使用者。
sentinel模式基本可以满足一般生产的需求，具备高可用性。
#### 缺点：
但是当数据量过大到一台服务器存放不下的情况时，主从模式或sentinel模式就不能满足需求了，
这个时候需要对存储的数据进行分片，将数据存储到多个Redis实例中
###  3.Redis-Cluster集群
redis的哨兵模式基本已经可以实现高可用，读写分离 ，但是在这种模式下每台redis服务器都存储相同的数据，
很浪费内存，所以在redis3.0上加入了cluster模式，实现的redis的分布式存储，
也就是说每台redis节点上存储不同的内容。
Redis-Cluster采用无中心结构,它的特点如下：
所有的redis节点彼此互联(PING-PONG机制),内部使用二进制协议优化传输速度和带宽。
节点的fail是通过集群中超过半数的节点检测失效时才生效。
客户端与redis节点直连,不需要中间代理层.客户端不需要连接集群所有节点,连接集群中任何一个可用节点即可。
## 数据结构
String、List、Set、Zset、hash

redis同时也内置了事务、LUA脚本、复制等功能，提供两种持久化选项
一种是每隔一段时间将数据导入到磁盘(快照模式)，
另一种是追加命令到日志中(AOF模式)。
如果只是作为高效的内存数据库使用也可以关闭持久化功能。通过哨兵(sentinel)和自动分区(Cuuster)的方式可以提高redis服务器的高可用性。

* SDS - simple synamic string - 支持自动动态扩容的字节数组
* list - 平平无奇的链表
* dict - 使用双哈希表实现的, 支持平滑扩容的字典
* zskiplist - 附加了后向指针的跳跃表
* intset - 用于存储整数数值集合的自有结构
* ziplist - 一种实现上类似于TLV, 但比TLV复杂的, 用于存储任意数据的有序序列的数据结构
* quicklist - 一种以ziplist作为结点的双链表结构, 实现的非常苟
* zipmap - 一种用于在小规模场合使用的轻量级字典结构

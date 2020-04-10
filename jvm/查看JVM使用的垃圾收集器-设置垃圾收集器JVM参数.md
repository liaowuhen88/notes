## 一、设置垃圾收集器参数

* -XX:+UseSerialGC，虚拟机运行在Client模式下的默认值，Serial+Serial Old。

* -XX:+UseParNewGC，ParNew+Serial Old，在JDK1.8被废弃，在JDK1.7还可以使用。

* -XX:+UseConcMarkSweepGC，ParNew+CMS+Serial Old。

* -XX:+UseParallelGC，虚拟机运行在Server模式下的默认值，Parallel Scavenge+Serial Old(PS Mark Sweep)。

* -XX:+UseParallelOldGC，Parallel Scavenge+Parallel Old。

* -XX:+UseG1GC，G1+G1。

## 二、测试代码：
```  
	public static void main(String[] args) {
		
		/* -XX:+UseParallelOldGC和-XX:+UseParallelGC结果一样，因为MXBean名字一样，但是实际使用的不一样 */
		List<GarbageCollectorMXBean> beans = ManagementFactory.getGarbageCollectorMXBeans();
		for (GarbageCollectorMXBean bean : beans) {
			System.out.println(bean.getName());
		}
	}
```  

## 三、测试环境：

JDK1.8.0_144，Java HotSpot(TM) 64-Bit Server VM，

默认参数：-XX:+PrintGCDetails  -XX:+PrintCommandLineFlags

打印内存信息，打印JVM参数

## 四、测试结果：

### 1、指定-XX:+UseSerialGC

虚拟机运行在Client模式下的默认值，Serial+Serial Old。

* Copy=Serial
* MarkSweepCompact=Serial Old

-XX:InitialHeapSize=65006144 -XX:MaxHeapSize=1040098304 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseSerialGC 
```  
Copy
MarkSweepCompact
Heap
 def new generation   total 19008K, used 1690K [0x00000000c2000000, 0x00000000c34a0000, 0x00000000d6aa0000)
  eden space 16896K,  10% used [0x00000000c2000000, 0x00000000c21a6838, 0x00000000c3080000)
  from space 2112K,   0% used [0x00000000c3080000, 0x00000000c3080000, 0x00000000c3290000)
  to   space 2112K,   0% used [0x00000000c3290000, 0x00000000c3290000, 0x00000000c34a0000)
 tenured generation   total 42368K, used 0K [0x00000000d6aa0000, 0x00000000d9400000, 0x0000000100000000)
   the space 42368K,   0% used [0x00000000d6aa0000, 0x00000000d6aa0000, 0x00000000d6aa0200, 0x00000000d9400000)
 Metaspace       used 2863K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 311K, capacity 386K, committed 512K, reserved 1048576K
```  
### 2、指定-XX:+UseParNewGC
ParNew+Serial Old，在JDK1.8被废弃，在JDK1.7还可以使用。

-XX:InitialHeapSize=65006144 -XX:MaxHeapSize=1040098304 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC 

* ParNew=ParNew
* MarkSweepCompact=Serial Old
```  
ParNew
MarkSweepCompact
Heap
 par new generation   total 19008K, used 1690K [0x00000000c2000000, 0x00000000c34a0000, 0x00000000d6aa0000)
  eden space 16896K,  10% used [0x00000000c2000000, 0x00000000c21a6838, 0x00000000c3080000)
  from space 2112K,   0% used [0x00000000c3080000, 0x00000000c3080000, 0x00000000c3290000)
  to   space 2112K,   0% used [0x00000000c3290000, 0x00000000c3290000, 0x00000000c34a0000)
 tenured generation   total 42368K, used 0K [0x00000000d6aa0000, 0x00000000d9400000, 0x0000000100000000)
   the space 42368K,   0% used [0x00000000d6aa0000, 0x00000000d6aa0000, 0x00000000d6aa0200, 0x00000000d9400000)
 Metaspace       used 2864K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 311K, capacity 386K, committed 512K, reserved 1048576K
Java HotSpot(TM) 64-Bit Server VM warning: Using the ParNew young collector with the Serial old collector is deprecated and will likely be removed in a future release
```  

### 3、指定-XX:+UseConcMarkSweepGC

ParNew+CMS+Serial Old。

-XX:InitialHeapSize=65006144 -XX:MaxHeapSize=1040098304 -XX:MaxNewSize=346030080 -XX:MaxTenuringThreshold=6 -XX:OldPLABSize=16 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC 

* ParNew=ParNew

* ConcurrentMarkSweep=CMS
```  
ParNew
ConcurrentMarkSweep
Heap
 par new generation   total 19008K, used 1690K [0x00000000c2000000, 0x00000000c34a0000, 0x00000000d6a00000)
  eden space 16896K,  10% used [0x00000000c2000000, 0x00000000c21a6848, 0x00000000c3080000)
  from space 2112K,   0% used [0x00000000c3080000, 0x00000000c3080000, 0x00000000c3290000)
  to   space 2112K,   0% used [0x00000000c3290000, 0x00000000c3290000, 0x00000000c34a0000)
 concurrent mark-sweep generation total 42368K, used 0K [0x00000000d6a00000, 0x00000000d9360000, 0x0000000100000000)
 Metaspace       used 2863K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 311K, capacity 386K, committed 512K, reserved 1048576K
  ```  
  
###  4、指定-XX:+UseParallelGC
-XX:InitialHeapSize=65006144 -XX:MaxHeapSize=1040098304 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseParallelGC 

虚拟机运行在Server模式下的默认值，Parallel Scavenge+Serial Old(PS Mark Sweep)。

* PS Scavenge = Parallel Scavenge
* PS MarkSweep = Serial Old
```  
PS Scavenge
PS MarkSweep
Heap
 PSYoungGen      total 18432K, used 1588K [0x00000000eb580000, 0x00000000eca00000, 0x0000000100000000)
  eden space 15872K, 10% used [0x00000000eb580000,0x00000000eb70d058,0x00000000ec500000)
  from space 2560K, 0% used [0x00000000ec780000,0x00000000ec780000,0x00000000eca00000)
  to   space 2560K, 0% used [0x00000000ec500000,0x00000000ec500000,0x00000000ec780000)
 ParOldGen       total 42496K, used 0K [0x00000000c2000000, 0x00000000c4980000, 0x00000000eb580000)
  object space 42496K, 0% used [0x00000000c2000000,0x00000000c2000000,0x00000000c4980000)
 Metaspace       used 2863K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 311K, capacity 386K, committed 512K, reserved 1048576K
```  

### 5、指定-XX:+UseParallelOldGC
Parallel Scavenge+Parallel Old。

-XX:InitialHeapSize=65006144 -XX:MaxHeapSize=1040098304 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseParallelOldGC 

* PS Scaveng e= Parallel Scavenge
* PS MarkSweep = Parallel Old

```  
PS Scavenge
PS MarkSweep
Heap
 PSYoungGen      total 18432K, used 1588K [0x00000000eb580000, 0x00000000eca00000, 0x0000000100000000)
  eden space 15872K, 10% used [0x00000000eb580000,0x00000000eb70d058,0x00000000ec500000)
  from space 2560K, 0% used [0x00000000ec780000,0x00000000ec780000,0x00000000eca00000)
  to   space 2560K, 0% used [0x00000000ec500000,0x00000000ec500000,0x00000000ec780000)
 ParOldGen       total 42496K, used 0K [0x00000000c2000000, 0x00000000c4980000, 0x00000000eb580000)
  object space 42496K, 0% used [0x00000000c2000000,0x00000000c2000000,0x00000000c4980000)
 Metaspace       used 2863K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 311K, capacity 386K, committed 512K, reserved 1048576K
  ```  
  
### 6、指定-XX:+UseG1GC

-XX:InitialHeapSize=65006144 -XX:MaxHeapSize=1040098304 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseG1GC -XX:-UseLargePagesIndividualAllocation 


```  
G1 Young Generation
G1 Old Generation
Heap
 garbage-first heap   total 63488K, used 1024K [0x00000000c2000000, 0x00000000c21001f0, 0x0000000100000000)
  region size 1024K, 2 young (2048K), 0 survivors (0K)
 Metaspace       used 2864K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 311K, capacity 386K, committed 512K, reserved 1048576K
 ```  
 
 ## 五、总结：
 
 * 1、-XX:+UseParallelGC和-XX:+UseParallelOldGC除了JVM参数不同，打印结果都一样，看解释是因为设置了名字相同的MXBean，其内部的实现是不同的。
 
 * 2、Serial的MXBean名称为Copy，新生代池名称为def new generation。
 
 * ParNew：ParNew、par new generation。
 
 * Parallel Scavenge：PS Scavenge、PSYoungGen。
 
 * Serial Old：MarkSweepCompact、tenured generation 。
 
 * Serial Old(PS Mark Sweep)：PS MarkSweep、ParOldGen。
 
 * Parallel Old：PS MarkSweep、ParOldGen。
 
 * G1（不准确）：G1 Young Generation、garbage-first heap 
 
        G1 Old Generation 、无。
 
 
 ————————————————
 版权声明：本文为CSDN博主「抱抱-」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 原文链接：https://blog.csdn.net/shi2huang/article/details/80085193
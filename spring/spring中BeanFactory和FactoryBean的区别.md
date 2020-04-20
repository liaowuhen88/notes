## 共同点：
 都是接口
## 区别：
   * BeanFactory 以Factory结尾，表示它是一个工厂类，用于管理Bean的一个工厂
      在Spring中，所有的Bean都是由BeanFactory(也就是IOC容器)来进行管理的。

   * 但对FactoryBean而言，这个Bean不是简单的Bean，而是一个能生产或者修饰对象生成的工厂Bean,
      它的实现与设计模式中的工厂模式和修饰器模式类似。
 
### 1、 BeanFactory
   * BeanFactory定义了IOC容器的最基本形式，并提供了IOC容器应遵守的的最基本的接口，也就是Spring IOC所遵守的最底层和最基本的编程规范。
   * 它的职责包括：实例化、定位、配置应用程序中的对象及建立这些对象间的依赖。
   * 在Spring代码中，BeanFactory只是个接口，并不是IOC容器的具体实现，
   * 但是Spring容器给出了很多种实现，如 DefaultListableBeanFactory、XmlBeanFactory、ApplicationContext等，都是附加了某种功能的实现。
```                  
 package org.springframework.beans.factory;
 
 import org.springframework.beans.BeansException;
 import org.springframework.core.ResolvableType;
 
 public interface BeanFactory {
     String FACTORY_BEAN_PREFIX = "&";
 
     Object getBean(String var1) throws BeansException;
 
     <T> T getBean(String var1, Class<T> var2) throws BeansException;
 
     <T> T getBean(Class<T> var1) throws BeansException;
 
     Object getBean(String var1, Object... var2) throws BeansException;
 
     <T> T getBean(Class<T> var1, Object... var2) throws BeansException;
 
     boolean containsBean(String var1);
 
     boolean isSingleton(String var1) throws NoSuchBeanDefinitionException;
 
     boolean isPrototype(String var1) throws NoSuchBeanDefinitionException;
 
     boolean isTypeMatch(String var1, ResolvableType var2) throws NoSuchBeanDefinitionException;
 
     boolean isTypeMatch(String var1, Class<?> var2) throws NoSuchBeanDefinitionException;
 
     Class<?> getType(String var1) throws NoSuchBeanDefinitionException;
 
     String[] getAliases(String var1);
 } 
 ```                
### 2、FactoryBean
   * 一般情况下，Spring通过反射机制利用<bean>的class属性指定实现类实例化Bean，在某些情况下，实例化Bean过程比较复杂，
   * 如果按照传统的方式，则需要在<bean>中提供大量的配置信息。配置方式的灵活性是受限的，这时采用编码的方式可能会得到一个简单的方案。
   * Spring为此提供了一个org.springframework.bean.factory.FactoryBean的工厂类接口，用户可以通过实现该接口定制实例化Bean的逻辑。
       FactoryBean接口对于Spring框架来说占用重要的地位，Spring自身就提供了70多个FactoryBean的实现。
   * 它们隐藏了实例化一些复杂Bean的细节，给上层应用带来了便利。从Spring3.0开始，FactoryBean开始支持泛型，即接口声明改为FactoryBean<T>的形式
 ```     
   package org.springframework.beans.factory;
   
   public interface FactoryBean<T> {
       T getObject() throws Exception;
   
       Class<?> getObjectType();
   
       boolean isSingleton();
   }
   ```   
   在该接口中还定义了以下3个方法：
   
   * T getObject()：
   
             返回由FactoryBean创建的Bean实例，如果isSingleton()返回true，则该实例会放到Spring容器中单实例缓存池中；
   * boolean isSingleton()：
   
             返回由FactoryBean创建的Bean实例的作用域是singleton还是prototype；
   * Class<T> getObjectType()：
   
             返回FactoryBean创建的Bean类型。
   * 当配置文件中<bean>的class属性配置的实现类是FactoryBean时，通过getBean()方法返回的不是FactoryBean本身，
   
            而是FactoryBean#getObject()方法所返回的对象，相当于FactoryBean#getObject()代理了getBean()方法。
   
   
   * 例：如果使用传统方式配置下面Car的<bean>时，Car的每个属性分别对应一个<property>元素标签。
```   
   package  com.baobaotao.factorybean;  
       public   class  Car  {  
           private   int maxSpeed ;  
           private  String brand ;  
           private   double price ;  
           public   int  getMaxSpeed ()   {  
               return   this . maxSpeed ;  
           }  
           public   void  setMaxSpeed ( int  maxSpeed )   {  
               this . maxSpeed  = maxSpeed;  
           }  
           public  String getBrand ()   {  
               return   this . brand ;  
           }  
           public   void  setBrand ( String brand )   {  
               this . brand  = brand;  
           }  
           public   double  getPrice ()   {  
               return   this . price ;  
           }  
           public   void  setPrice ( double  price )   {  
               this . price  = price;  
          }  
   }   
 ```   
 如果用FactoryBean的方式实现就灵活点，下例通过逗号分割符的方式一次性的为Car的所有属性指定配置值： 
```   
 package  com.baobaotao.factorybean;  
 import  org.springframework.beans.factory.FactoryBean;  
 public   class  CarFactoryBean  implements  FactoryBean<Car>  {  
     private  String carInfo ;  
     public  Car getObject ()   throws  Exception  {  
         Car car =  new  Car () ;  
         String []  infos =  carInfo .split ( "," ) ;  
         car.setBrand ( infos [ 0 ]) ;  
         car.setMaxSpeed ( Integer. valueOf ( infos [ 1 ])) ;  
         car.setPrice ( Double. valueOf ( infos [ 2 ])) ;  
         return  car;  
     }  
     public  Class<Car> getObjectType ()   {  
         return  Car. class ;  
     }  
     public   boolean  isSingleton ()   {  
         return   false ;  
     }  
     public  String getCarInfo ()   {  
         return   this . carInfo ;  
     }  
   
     // 接受逗号分割符设置属性信息  
     public   void  setCarInfo ( String carInfo )   {  
         this . carInfo  = carInfo;  
     }  
 }   
  ```   
  * 有了这个CarFactoryBean后，就可以在配置文件中使用下面这种自定义的配置方式配置CarBean了：
```    
  <bean id="car"class="com.baobaotao.factorybean.CarFactoryBean" P:carInfo="法拉利,400,2000000"/>
```   
 * 当调用getBean("car")时，Spring通过反射机制发现CarFactoryBean实现了FactoryBean的接口，这时Spring容器就调用接口方法CarFactoryBean#getObject()方法返回。
 * 如果希望获取CarFactoryBean的实例，则需要在使用getBean(beanName)方法时在beanName前显示的加上"&"前缀：如getBean("&car");
    
 ### 3 Others：
   3.1 Spring中共有两种bean，一种为普通bean，另一种则为工厂bean。
 
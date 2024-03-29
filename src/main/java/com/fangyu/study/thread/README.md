# 死磕 java线程系列之创建线程的8种方式


**问题**

（1）创建线程有哪几种方式？

（2）它们分别有什么运用场景？

## 简介
- 创建线程，是多线程编程中最基本的操作，彤哥总结了一下，大概有8种创建线程的方式，你知道吗？

### 继承Thread类并重写run()方法
```java
public class CreatingThread01 extends Thread {

    @Override
    public void run() {
        System.out.println(getName() + " is running");
    }

    public static void main(String[] args) {
        new CreatingThread01().start();
        new CreatingThread01().start();
        new CreatingThread01().start();
        new CreatingThread01().start();
    }
}
```

继承Thread类并重写run()方法，这种方式的弊端是一个类只能继承一个父类，如果这个类本身已经继承了其它类，就不能使用这种方式了。

### 实现Runnable接口
```java
public class CreatingThread02 implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is running");
    }

    public static void main(String[] args) {
        new Thread(new CreatingThread02()).start();
        new Thread(new CreatingThread02()).start();
        new Thread(new CreatingThread02()).start();
        new Thread(new CreatingThread02()).start();
    }
}
```

实现Runnable接口，这种方式的好处是一个类可以实现多个接口，不影响其继承体系。

### 匿名内部类
```java
public class CreatingThread03 {
    public static void main(String[] args) {
        // Thread匿名类，重写Thread的run()方法
        new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + " is running");
            }
        }.start();

        // Runnable匿名类，实现其run()方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " is running");
            }
        }).start();
        
        // 同上，使用lambda表达式函数式编程
        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + " is running");
        }).start();
    }
}
```

使用匿名类的方式，一是重写Thread的run()方法，二是传入Runnable的匿名类，三是使用lambda方式，现在一般使用第三种（java8+），简单快捷。

### 实现Callabe接口
```java
public class CreatingThread04 implements Callable<Long> {
    @Override
    public Long call() throws Exception {
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getId() + " is running");
        return Thread.currentThread().getId();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Long> task = new FutureTask<>(new CreatingThread04());
        new Thread(task).start();
        System.out.println("等待完成任务");
        Long result = task.get();
        System.out.println("任务结果：" + result);
    }
}
```

实现Callabe接口，可以获取线程执行的结果，FutureTask实际上实现了Runnable接口。

### 定时器（java.util.Timer）
```java
public class CreatingThread05 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        // 每隔1秒执行一次
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " is running");
            }
        }, 0 , 1000);
    }
}
```
使用定时器java.util.Timer可以快速地实现定时任务，TimerTask实际上实现了Runnable接口。

### 线程池
```java
public class CreatingThread06 {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 100; i++) {
            threadPool.execute(()-> System.out.println(Thread.currentThread().getName() + " is running"));
        }
    }
}
```
使用线程池的方式，可以复用线程，节约系统资源。

### 并行计算（Java8+）
```java
public class CreatingThread07 {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        // 串行，打印结果为12345
        list.stream().forEach(System.out::print);
        System.out.println();
        // 并行，打印结果随机，比如35214
        list.parallelStream().forEach(System.out::print);
    }
}
```
使用并行计算的方式，可以提高程序运行的效率，多线程并行执行。

### Spring异步方法
首先，springboot启动类加上@EnableAsync注解（@EnableAsync是spring支持的，这里方便举例使用springboot）。
```java
@SpringBootApplication
@EnableAsync
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

其次，方法加上@Async注解。
```java


@Service
public class CreatingThread08Service {

    @Async
    public void call() {
        System.out.println(Thread.currentThread().getName() + " is running");
    }
}
```

然后，测试用例直接跟使用一般的Service方法一模一样。
```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CreatingThread08Test {

    @Autowired
    private CreatingThread08Service creatingThread08Service;

    @Test
    public void test() {
        creatingThread08Service.call();
        creatingThread08Service.call();
        creatingThread08Service.call();
        creatingThread08Service.call();
    }
}
```
运行结果如下：
```
task-3 is running
task-2 is running
task-1 is running
task-4 is running
```
可以看到每次执行方法时使用的线程都不一样。

使用Spring异步方法的方式，可以说是相当地方便，适用于前后逻辑不相关联的适合用异步调用的一些方法，比如发送短信的功能。

总结
（1）继承Thread类并重写run()方法；

（2）实现Runnable接口；

（3）匿名内部类；

（4）实现Callabe接口；

（5）定时器（java.util.Timer）；

（6）线程池；

（7）并行计算（Java8+）；

（8）Spring异步方法；

彩蛋
上面介绍了那么多创建线程的方式，其实本质上就两种，一种是继承Thread类并重写其run()方法，一种是实现Runnable接口的run()方法，那么它们之间到底有什么联系呢？

请看下面的例子，同时继承Thread并实现Runnable接口，应该输出什么呢？
```java
public class CreatingThread09 {

    public static void main(String[] args) {
        new Thread(()-> {
            System.out.println("Runnable: " + Thread.currentThread().getName());
        }) {
            @Override
            public void run() {
                System.out.println("Thread: " + getName());
            }
        }.start();
    }
}
```
说到这里，我们有必要看一下Thread类的源码：
```java
public class Thread implements Runnable {
    // Thread维护了一个Runnable的实例
    private Runnable target;
    
    public Thread() {
        init(null, null, "Thread-" + nextThreadNum(), 0);
    }
    
    public Thread(Runnable target) {
        init(null, target, "Thread-" + nextThreadNum(), 0);
    }
    
    private void init(ThreadGroup g, Runnable target, String name,
                      long stackSize, AccessControlContext acc,
                      boolean inheritThreadLocals) {
        // ...
        // 构造方法传进来的Runnable会赋值给target
        this.target = target;
        // ...
    }
    
    @Override
    public void run() {
        // Thread默认的run()方法，如果target不为空，会执行target的run()方法
        if (target != null) {
            target.run();
        }
    }
}
```
看到这里是不是豁然开朗呢？既然上面的例子同时继承Thread并实现了Runnable接口，根据源码，实际上相当于重写了Thread的run()方法，在Thread的run()方法时实际上跟target都没有关系了。

所以，上面的例子输出结果为Thread: Thread-0，只输出重写Thread的run()方法中的内容。
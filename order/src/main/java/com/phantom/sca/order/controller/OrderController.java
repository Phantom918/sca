package com.phantom.sca.order.controller;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.phantom.sca.order.feign.ProductService;
import com.phantom.sca.order.feign.StockService;
import com.phantom.sca.order.model.User;
import com.phantom.sca.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * 测试类
 * 这里 @RefreshScope 标示能够即时的动态读取 Nacos 中的配置信息
 *
 * @Author lei.tan
 * @Date 2021/9/21 14:15
 **/
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/order")
public class OrderController {

//    final RestTemplate restTemplate;

//    final DiscoveryClient discoveryClient;

    final StockService stockService;

    final ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 流控资源名
     */
    public static final String FLOW_RESOURCE_NAME = "FlowResource";

    /**
     * 熔断降级资源名
     */
    public static final String DEGRADE_RESOURCE_NAME = "DegradeResource";

    @Value("${user.name}")
    public String userName;

    @Value("${redis.description}")
    public String redisDescription;

    public OrderController(StockService stockService, ProductService productService) {
        this.stockService = stockService;
        this.productService = productService;
    }

    @Transactional
    @RequestMapping("/add")
    public String add() {
        log.info("添加订单....");

        //使用 LoadBalanceClient 和 RestTemolate 结合的方式来访问
        /* 这里注释
        List<ServiceInstance> instances = discoveryClient.getInstances("stock-app");
        int i = new Random().nextInt(instances.size());
        ServiceInstance serviceInstance = instances.get(i);
        String url = String.format("http://%s:%s/stock/reduce", serviceInstance.getHost(), serviceInstance.getPort());
        System.out.println("request url:" + url);
        String url = "http://stock-app/stock/reduce";
        String message = restTemplate.getForObject(url, String.class);*/
        int num = orderService.add(1, 2);
        log.info("商品订单添加完毕....");

        return String.format("订单添加成功！");
    }


    @RequestMapping("/showConfig")
    public String showConfig() {
        log.info("userName = {}, redisDescription = {}", userName, redisDescription);
        return "userName = " + userName + ", redisDescription = " + redisDescription;
    }

    /**
     * opsForValue： 对应 String（字符串）
     * opsForZSet： 对应 ZSet（有序集合）
     * opsForHash： 对应 Hash（哈希）
     * opsForList： 对应 List（列表）
     * opsForSet： 对应 Set（集合）
     * opsForGeo： 对应 GEO（地理位置）
     *
     * @return
     */
    @RequestMapping("/redis")
    public String redisTest() {
        // Spring 框架提供的 CustomizableThreadFactory
        ThreadFactory threadFactory = new CustomizableThreadFactory("order-redis-pool-");
        // Google guava 工具类 提供的 ThreadFactoryBuilder ,使用链式方法创建
        ThreadFactory guavaThreadFactory = new ThreadFactoryBuilder().setNameFormat("retryClient-pool-").build();
        // Apache commons-lang3 提供的 BasicThreadFactory
        //TODO ThreadFactory basicThreadFactory = new BasicThreadFactory.Builder().namingPattern("basicThreadFactory-").build();

        ExecutorService executorService = new ThreadPoolExecutor(10, 100, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), threadFactory);
        ExecutorService executorService1 = Executors.newFixedThreadPool(1000);
        IntStream.range(0, 1000).forEach(i ->
                executorService.execute(() -> {
                            redisTemplate.opsForValue().increment("kk", 1);
                            //stringRedisTemplate.expire("kk", 10, TimeUnit.SECONDS);
                        }
                )
        );
        redisTemplate.opsForValue().set("k1", "v1", 30, TimeUnit.SECONDS);
        Object k11 = redisTemplate.opsForValue().get("k1");
        log.info("[字符缓存结果] - [{}]", k11);
        String keySet = "key_set";
        redisTemplate.opsForSet().add(keySet, new User(1L, "小明1", "123456"));
        redisTemplate.opsForSet().add(keySet, new User(2L, "九月1", "112233"));
        redisTemplate.expire(keySet, 300, TimeUnit.SECONDS);
        Set<Object> resSet1 = redisTemplate.opsForSet().members(keySet);
        if (resSet1 != null) {
            resSet1.forEach(x -> log.info(JSON.toJSONString(x)));
            log.info("resSet1={}", JSON.toJSONString(resSet1));
        }

        String keyList = "key_list";
        redisTemplate.opsForList().leftPush(keyList, new User(1L, "小明", "123456"));
        redisTemplate.opsForList().leftPush(keyList, new User(2L, "九月", "112233"));
        redisTemplate.expire(keyList, 300, TimeUnit.SECONDS);
        // 取出所有元素
        List<Object> resList = redisTemplate.opsForList().range(keyList, 0, -1);
        if (resList != null) {
            log.info("查询resList={}", JSON.toJSONString(resList));
        }

        return "OK";
    }

    @RequestMapping("/deleteKey/{key}")
    public String deleteKey(@PathVariable("key") String key) {
        Boolean res = redisTemplate.delete(key);
        return "删除结果: " + res;
    }

    @RequestMapping("/gteKey/{key}")
    public String gteKey(@PathVariable("key") String key) {
        Object res1 = redisTemplate.opsForValue().get(key);
        return "查询结果 ==> " + key + " = " + JSON.toJSONString(res1);
    }

    /**
     * 此处的 @SentinelResource 标识资源是否被限流、降级,value= "testSentinel" 表示资源名
     * 注：一般推荐将 @SentinelResource 注解加到服务实现上，而在 Web 层直接使用 Spring
     * Cloud Alibaba 自带的 Web 埋点适配。Sentinel Web 适配同样支持配置自定义流控处理逻辑
     * blockHandler: 一般将处理方式和写同一个类中
     *
     * @return
     */
    @RequestMapping("/testFlowSentinel")
    @SentinelResource(value = FLOW_RESOURCE_NAME, blockHandler = "handleFlowSentinel", entryType = EntryType.IN)
    public String testFlowSentinel(String txt) {
        log.info("hello {}", txt);
        return "你好，" + txt;
    }


    /**
     * 注意：方法必须为 public ,另外 返回值必须和源方法保持一致
     *
     * @param txt
     * @param e
     * @return
     */
    public String handleFlowSentinel(String txt, BlockException e) {
        e.printStackTrace();
        return "流控 -> 异常处理！" + txt;
    }

    @RequestMapping("/testDegradeSentinel")
    @SentinelResource(value = DEGRADE_RESOURCE_NAME, blockHandler = "handleDegradeSentinel", entryType = EntryType.IN)
    public String testDegradeSentinel(int num) {
        log.info("num = {}", num);
        int num1 = 10 / num;
        return "你好，" + num1;
    }

    /**
     * 注意：方法必须为 public ,另外 返回值必须和源方法保持一致
     *
     * @param num
     * @param e
     * @return
     */
    public String handleDegradeSentinel(int num, BlockException e) {
        e.printStackTrace();
        return "熔断 -> 异常处理！" + num;
    }


    /**
     * 流控规则
     * <p>
     * PostConstruct 是java自带注解，在构造函数之后执行，init（）方法之前执行。
     * 通常我们会是在Spring框架中使用到@PostConstruct注解 该注解的方法在整个Bean初始化中的执行顺序：
     * Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
     */
    @PostConstruct
    private static void intiFLowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        // 为哪个资源做流控
        rule.setResource(FLOW_RESOURCE_NAME);
        // 流控拦截规则，此处采用建单的 QPS（每秒请求量）
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置上面规则的拦截阈值，超过 1 QPS 则会被拦截
        rule.setCount(3);
        // 添加规则
        rules.add(rule);
        // 加载配置好的规则
        FlowRuleManager.loadRules(rules);
    }

    /**
     * 降级规则
     */
    @PostConstruct
    private static void intiDegradeRules() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        // 为哪个资源做流控
        rule.setResource(DEGRADE_RESOURCE_NAME);
        // 设置规则 >> 异常数
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        // 触发熔断异常数
        rule.setCount(2);
        // 触发熔断最小请求数
        rule.setMinRequestAmount(5);
        // 统计时长（单位毫秒）
        rule.setStatIntervalMs(10 * 1000);
        // 熔断的持续时长（单位秒）
        rule.setTimeWindow(10);
        // ----- 结合上面条件就是，10秒内，请求5次，出现2次异常，就触发熔断，熔断持续10秒，
        // 如果10秒结束后第一次请求就异常，则直接进入熔断
        // 添加规则
        rules.add(rule);
        // 加载配置好的规则
        DegradeRuleManager.loadRules(rules);
    }


}

import com.rpc.common.URL;
import com.rpc.rpc.Invoker;
import com.rpc.rpc.ProxyFactory;
import com.rpc.rpc.RpcInvocation;
import com.rpc.rpc.proxy.jdk.JdkProxyFactory;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class InvokerTest {


    public static ProxyFactory proxyFactory = new JdkProxyFactory();

    @Test
    public void testGetProxy(){
        URL url = URL.valueOf("test://test:11/test?group=dubbo&version=1.1");
        DemoService demo = new DemoServiceImpl();
        Invoker<DemoService> invoker = proxyFactory.getInvoker(demo,DemoService.class,url);
        Object obj = invoker.invoke(new RpcInvocation("hello",null,null)).getValue();
        System.out.println(obj.toString());
        Assertions.assertEquals(obj,demo.hello());
    }
}

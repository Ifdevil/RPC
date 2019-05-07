import com.rpc.common.URL;
import com.rpc.rpc.Invoker;
import com.rpc.rpc.ProxyFactory;
import com.rpc.rpc.proxy.jdk.JdkProxyFactory;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class AbstractProxyTest {

    public static ProxyFactory proxyFactory = new JdkProxyFactory();

    @Test
    public void testGetProxy(){
        URL url = URL.valueOf("test://test:11/test?group=dubbo&version=1.1");
        Invoker<DemoService> invoker = new MyInvoker<>(url);
        DemoService proxy = proxyFactory.getProxy(invoker);
        Assertions.assertNotNull(proxy);
    }
}

import com.npz.test.TestImpl2;
import com.npz.test.TestInterface2;
import com.rpc.config.ApplicationConfig;
import com.rpc.config.ProtocolConfig;
import com.rpc.config.RegistryConfig;
import com.rpc.config.ServiceConfig;
import org.junit.Test;

public class ServiceConfigTest {





    @Test
    public void testServiceConfig(){
        ServiceConfig<TestImpl2> serviceConfig = new ServiceConfig();
        serviceConfig.setApplicationConfig(new ApplicationConfig("Rpc-demo-test"));
        serviceConfig.setRegistryConfig(new RegistryConfig("multicast://127.0.0.1:1234"));
        serviceConfig.setProtocolConfig(new ProtocolConfig("rpc_fly","20880"));
        serviceConfig.setRef(new TestImpl2());
        serviceConfig.setInterfaceClass(TestInterface2.class);
        serviceConfig.export();
    }
}

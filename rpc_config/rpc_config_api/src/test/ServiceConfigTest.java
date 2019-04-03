import com.rpc.config.ApplicationConfig;
import com.rpc.config.ProtocolConfig;
import com.rpc.config.RegistryConfig;
import com.rpc.config.ServiceConfig;

public class ServiceConfigTest {





    public void testServiceConfig(){

        ServiceConfig<TestImpl> serviceConfig = new ServiceConfig();
        serviceConfig.setApplicationConfig(new ApplicationConfig("Rpc-demo-test"));
        serviceConfig.setRegistryConfig(new RegistryConfig("multicast://127.0.0.1:1234"));
        serviceConfig.setProtocolConfig(new ProtocolConfig("RPC_FLY","20880"));
        serviceConfig.setRef(new TestImpl());
    }
}

import com.rpc.common.URL;
import com.rpc.rpc.Invocation;
import com.rpc.rpc.Invoker;
import com.rpc.rpc.Result;
import com.rpc.rpc.RpcResult;

public class MyInvoker<T> implements Invoker<T> {
    URL url;
    Class<T> type;
    boolean hasException = false;
    public MyInvoker(URL url) {
        this.url = url;
        type = (Class<T>) DemoService.class;
    }

    public MyInvoker(URL url, boolean hasException) {
        this.url = url;
        type = (Class<T>) DemoService.class;
        this.hasException = hasException;
    }

    @Override
    public Class<T> getInterface() {
        return type;
    }

    @Override
    public Result invoke(Invocation invocation) {
        RpcResult result = new RpcResult();
        result.setValue("alibaba");
        return result;

    }

    public URL getUrl() {
        return url;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public void destroy() {
    }

    @Override
    public String toString() {
        return "MyInvoker.toString()";
    }
}

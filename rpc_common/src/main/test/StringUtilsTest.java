import com.rpc.common.util.StringUtils;

public class StringUtilsTest {

    public static void main(String[] args) {
        String name = "getName";
        System.out.println(name.substring(3,4).toLowerCase()+","+name.substring(4));
        int i = name.startsWith("get") ? 3 : 2;
        String a = StringUtils.camelToSplitName(name.substring(i, i + 1).toLowerCase() + name.substring(i + 1), ".");
        System.out.println(a);
    }
}

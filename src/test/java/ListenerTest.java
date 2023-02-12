import com.alibaba.fastjson.JSON;
import com.horn.observer.LotteryResult;
import com.horn.observer.LotteryServiceImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ListenerTest {
    private Logger logger = LoggerFactory.getLogger(ListenerTest.class);

    @Test
    public void test_draw(){
        LotteryServiceImpl lotteryService = new LotteryServiceImpl();
        LotteryResult res1 = lotteryService.draw("0340013");
        LotteryResult res2 = lotteryService.draw("341134");
        logger.info("Result："+JSON.toJSONString(res1)+JSON.toJSONString(res2));
    }
}

import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.PauRestApplication;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.im.service.ImService;
import com.soft.ware.rest.modular.order.model.TOrder;
import com.soft.ware.rest.modular.order.service.ITOrderService;
import io.jsonwebtoken.lang.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PauRestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImTest {

    @Autowired
    private ITOrderService orderService;

    @Autowired
    private ImService imService;

    public SessionUser buildConsumerUser(){
        SessionUser user = new SessionUser();
        user.setId("o_Eds5b8vKez2-UE3iOXpf_na-LQ");
        user.setOpenId(user.getId());
        user.setName("yancc");
        user.setOwnerId("16d0a4b0dcd411e8b2e187bf6b98e5cd");
        return user;
    }

    @Test
    public void testOrderNotify() throws Exception {
        Map<String, Object> m = orderService.findMap(Kv.obj().set("id", "1120143734019416066"));
        TOrder order = BeanMapUtils.toObject(m, TOrder.class);
        imService.sendNewOrderNotify(buildConsumerUser(), order);
        Assert.notNull(m);
    }


}

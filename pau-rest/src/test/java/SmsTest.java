import com.soft.ware.rest.PauRestApplication;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.sms.service.SmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.soft.ware.rest.modular.auth.util.WXContants.TENCENT_TEMPLATE_ID4;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PauRestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SmsTest {

    @Autowired
    private SmsService smsService;

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
        smsService.sendNotify("15083101898,15136757969", TENCENT_TEMPLATE_ID4, "1120974623603376129");
    }

}

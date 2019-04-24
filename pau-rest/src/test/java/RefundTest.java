import com.github.binarywang.wxpay.bean.request.WxPayOrderQueryRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.rest.PauRestApplication;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.HzcxWxService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PauRestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RefundTest {


    @Autowired
    private HzcxWxService hzcxWxService;


    /**
     * 按订单号强制退款
     * @throws Exception
     */
    @Test
    public void refund() throws Exception {
        String no = "";
        //no = "1118453238184235011";//史李阳
        SessionUser user = new SessionUser();
        user.setOwnerId("16d0a4b0dcd411e8b2e187bf6b98e5cd");
        WxPayService service = hzcxWxService.getWxPayService(user);
        WxPayOrderQueryRequest req = WxPayOrderQueryRequest.newBuilder().outTradeNo(no).build();
        WxPayOrderQueryResult result = service.queryOrder(req);
        System.out.println("支付状态:"+result.getTradeState());
        System.out.println("支付金额:"+result.getTotalFee());
        WxPayRefundRequest refundReq = WxPayRefundRequest.newBuilder()
                .outRefundNo(IdGenerator.getId())
                .outTradeNo(req.getOutTradeNo())
                .totalFee(result.getTotalFee())
                .refundFee(result.getTotalFee())
                .build();
        WxPayRefundResult refundResult = service.refund(refundReq);
        System.out.println("退款金额："+refundResult.getCashRefundFee());
        System.out.println(result);
    }

}

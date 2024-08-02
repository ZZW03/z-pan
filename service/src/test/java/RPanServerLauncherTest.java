import com.alibaba.otter.canal.client.CanalConnector;
import com.zzw.zpan.RPanServerLauncher;
import com.zzw.zpan.ScheduleManager;
import com.zzw.zpan.common.config.PanServerConfig;
import com.zzw.zpan.common.utils.ShareTokenUtil;
import com.zzw.zpan.constants.RPanConstants;
//import com.zzw.zpan.lock.zookeeper.ZooKeeperLockProperties;
import com.zzw.zpan.core.LockConstants;
import com.zzw.zpan.limit.MyRedisLimiter;
import com.zzw.zpan.modules.file.context.QueryFileListContext;
import com.zzw.zpan.modules.file.entity.RPanUserFile;
import com.zzw.zpan.modules.file.enums.enums.DelFlagEnum;
import com.zzw.zpan.modules.file.mapper.RPanUserFileMapper;
import com.zzw.zpan.modules.file.vo.RPanUserFileVO;
import com.zzw.zpan.modules.share.context.CreateShareUrlContext;
import com.zzw.zpan.modules.share.context.QueryShareDetailContext;
import com.zzw.zpan.modules.share.context.QueryShareListContext;
import com.zzw.zpan.modules.share.entity.RPanShare;
import com.zzw.zpan.modules.share.service.iShareService;
import com.zzw.zpan.modules.share.vo.RPanShareUrlListVO;
import com.zzw.zpan.modules.share.vo.RPanShareUrlVO;
import com.zzw.zpan.modules.test.controller.LockTester;
import com.zzw.zpan.modules.test.controller.canalTest;
import com.zzw.zpan.modules.user.context.UserRegisterContext;
import com.zzw.zpan.modules.user.mapper.RPanUserMapper;
import com.zzw.zpan.modules.user.service.IUserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@SpringBootTest(classes = RPanServerLauncher.class)
public class RPanServerLauncherTest {

    @Resource
    RPanUserMapper rPanUserMapper;

    @Resource
    IUserService iUserService;

    @Resource
    RPanUserFileMapper rPanFileMapper;

    @Resource
    PanServerConfig config;

    @Resource
    iShareService iShareService;

    @Resource
    private ScheduleManager manager;

    @Autowired
    private canalTest scheduleTask;

    @Autowired
    private LockRegistry lockRegistry;

    @Autowired
    private LockTester lockTester;

    @Qualifier("MyTaskExecutor")
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    @Qualifier("promotionConnector")
    private CanalConnector connector;



//    @Resource
//    ZooKeeperLockProperties zooKeeperLockProperties;


    @Test
    public void Test(){

        System.out.println(rPanFileMapper.selectFileList(new QueryFileListContext()
                .setUserId(1806617296794558464L)
                .setParentId(0L)
                .setDelFlag(DelFlagEnum.NO.getCode())
                .setFileTypeArray(null)
                .setParentId(0L)
        ));

    }

    @Test
    public void Test1(){

        String sharePrefix = config.getSharePrefix();

        System.out.println(sharePrefix.lastIndexOf(RPanConstants.SLASH_STR));

        if (sharePrefix.lastIndexOf(RPanConstants.SLASH_STR) == RPanConstants.MINUS_ONE_INT) {
            sharePrefix += RPanConstants.SLASH_STR;
        }

    }

    @Test
    public void create(){
        ArrayList<Long> objects = new ArrayList<>();
        objects.add(1806617299210477568L);

        RPanShareUrlVO zzw = iShareService.create(new CreateShareUrlContext().setShareName("zzw")
                .setShareType(1)
                .setShareDayType(1)
                .setUserId(1806617296794558464L)
                .setShareFileIdList(objects)
        );


    }

    @Test
    public void  getShares(){
        QueryShareListContext queryShareListContext = new QueryShareListContext();
        queryShareListContext.setUserId(1806617296794558464L);
        List<RPanShareUrlListVO> shares = iShareService.getShares(queryShareListContext);
        System.out.println(123);
        System.out.println(shares);
    }


    @Test
    public void ShareToken(){
        String s = ShareTokenUtil.generateCokeShareToken(12312312L);
        System.out.println(ShareTokenUtil.verifyShareToken(String.valueOf(s)));
    }

    @Test
    public void detail(){
        QueryShareDetailContext queryShareDetailContext = new QueryShareDetailContext();
        queryShareDetailContext.setShareId(1813127619664031744L);
        System.out.println(iShareService.detail(queryShareDetailContext));
    }

    @Test
    public  void Transfer(){

        RPanUserFile rPanUserFile = new RPanUserFile();
        rPanUserFile.setFileId(123L);
        rPanUserFile.setFileType(1);

        RPanUserFileVO viewObject = rPanUserFile.asViewObject(RPanUserFileVO.class);
        System.out.println(viewObject.getFileType());
        System.out.println(viewObject.getFileId());
    }

    @Test
    public void lockRegistryTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            threadPoolTaskExecutor.execute(() -> {
                Lock lock = lockRegistry.obtain(LockConstants.R_PAN_LOCK);
                boolean lockResult = false;
                try {
                    lockResult = lock.tryLock(60L, TimeUnit.SECONDS);
                    if (lockResult) {
                        System.out.println(Thread.currentThread().getName() + " get the lock.");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (lockResult) {
                        System.out.println(Thread.currentThread().getName() + " release the lock.");
                        lock.unlock();
                    }
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
    }


    @Test
    public void lockTesterTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            threadPoolTaskExecutor.execute(() -> {
                lockTester.testLock("imooc");
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
    }

    @Test
    @MyRedisLimiter(key = "zzw",count = 1,period = 123)
    public void limitRate(){
        System.out.println(1);
    }

    @Test
    public void CheckCanal(){

        connector.connect();

        System.out.println(connector.checkValid());

        connector.subscribe("rpan.r_pan_user");
    }

    @Test
    public void  canal() throws InterruptedException {
        String cron = "0/5 * * * * ? ";

        String key = manager.startTask(scheduleTask, cron);

        Thread.sleep(1000000);

    }


}

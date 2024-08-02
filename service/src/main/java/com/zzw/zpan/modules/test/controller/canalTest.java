package com.zzw.zpan.modules.test.controller;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.exception.CanalClientException;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zzw.zpan.ScheduleTask;
import com.zzw.zpan.exception.RPanBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class canalTest implements ScheduleTask {

    @Autowired
    @Qualifier("promotionConnector")
    private CanalConnector connector;

    @Override
    public String getName() {
        return "Canal--Test";
    }



    @Override
    public void run() {
        try {
            init();
            Message message = connector.getWithoutAck(5000);
            long batchId = message.getId();
            int size = message.getEntries().size();

            if (batchId == -1 || size == 0) {
                log.info("本次[{}]没有检测到促销数据更新。", batchId);
            } else {
                log.info("本次[{}]促销数据本次共有[{}]次更新需要处理", batchId, size);
                /*一个表在一次周期内可能会被修改多次，而对Redis缓存的处理只需要处理一次即可*/
                Set<String> factKeys = new HashSet<>();
                for (CanalEntry.Entry entry : message.getEntries()) {
                    if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN
                            || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                        continue;
                    }
                    CanalEntry.RowChange rowChange = null;
                    try {
                        rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                        CanalEntry.EventType eventType = rowChange.getEventType();
                        String tableName = entry.getHeader().getTableName();

                        if (eventType == CanalEntry.EventType.UPDATE) {
                            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                                List<CanalEntry.Column> afterColumns = rowData.getAfterColumnsList();
                                for (CanalEntry.Column afterColumn : afterColumns) {
                                    if (afterColumn.getUpdated()) {
                                        log.info("afterColumn" + afterColumn);
                                    }
                                }
                            }
                        }


                    } catch (InvalidProtocolBufferException e) {
                        throw new RPanBusinessException(" 这个转型错误");
                    }



                    String tableName = entry.getHeader().getTableName();

                    log.info("tableName" + tableName);

                    if (log.isDebugEnabled()) {
                        CanalEntry.EventType eventType = rowChange.getEventType();
                        log.debug("数据变更详情：来自binglog[{}.{}]，数据源{}.{}，变更类型{}",
                                entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                                entry.getHeader().getSchemaName(), tableName, eventType);
                    }
                }

                connector.ack(batchId); // 提交确认
                log.info("本次[{}]处理促销Canal同步数据完成", batchId);
            }
        } catch (CanalClientException e) {
            log.error("订阅失败: " + e.getMessage());
        }
    }


    private void init(){
        if(connector.checkValid()){
            connector.connect();
            connector.subscribe("rpan.r_pan_user");
            log.info("连接成功");
        } else {
            log.info("准备失败");
        }
        connector.rollback(); // 这行代码可能不需要在这里调用
    }

}

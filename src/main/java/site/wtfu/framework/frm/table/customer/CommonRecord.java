package site.wtfu.framework.frm.table.customer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.wtfu.framework.common.Common;
import site.wtfu.framework.frm.common.RecordHeader;

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2026/9/4
 *                          @since  1.0
 *                          @author 12302
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommonRecord extends Common<CommonRecord> {

    private int _limit;

    private int type;

    private long index_id;

    private CommonRecord data;

    public CommonRecord(){}
    public CommonRecord(long index_id, int type){
        this.index_id = index_id;
        this.type = type;
    }

    /*
    CREATE TABLE `customer` (
      `customer_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
      `store_id` tinyint(3) unsigned NOT NULL,
      `first_name` varchar(45) NOT NULL,
      `last_name` varchar(45) DEFAULT NULL,
      `email` char(50) NOT NULL DEFAULT 'default@example.com',
      `address_id` smallint(5) unsigned NOT NULL,
      `active` tinyint(1) NOT NULL DEFAULT '1',
      `create_date` datetime DEFAULT NULL,
      `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      PRIMARY KEY (`customer_id`,`email`),
      KEY `idx_store_address_name` (`store_id`,`address_id`,`last_name`)
    ) ENGINE=InnoDB AUTO_INCREMENT=600 DEFAULT CHARSET=utf8mb4;

    * */

    // public byte[] variableLengthField;

    public byte[] nullValueField;

    public RecordHeader header;

    @Override
    public String toString() {
        return "CommonRecord{" +
                "data=" + data +
                ", header=" + header +
                '}';
    }

    @Override
    protected CommonRecord doDecodeBytes(CommonRecord commonRecord, MappedByteBuffer ibd) {

        int maxVariableField = 9 /* column */ * 2;

        // one byte enough!
        nullValueField = new byte[1];

        int pkPos = ibd.position(), fixed = nullValueField.length + 5;

        ibd.position(pkPos - fixed - maxVariableField);
        byte[] estimateExtra = new byte[maxVariableField]; ibd.get(estimateExtra);
        ByteBuffer eeBuf = ByteBuffer.wrap(estimateExtra);

        ibd.get(nullValueField);
        header = new RecordHeader().decodeBytes(ibd);

        switch ((int) commonRecord.index_id) {
            case 42:
                if(commonRecord.type == 0){
                    data = new LeafRecord().doDecodeBytes(ibd, nullValueField, eeBuf);
                }else{
                    data = new NonLeafRecord().doDecodeBytes(ibd, nullValueField, eeBuf);
                }
                break;
            case 43:
                if(commonRecord.type == 0){
                    data = new AuxiliaryLeafRecord().doDecodeBytes(ibd, nullValueField, eeBuf);
                }else{
                    data = new AuxiliaryNonLeafRecord().doDecodeBytes(ibd, nullValueField, eeBuf);
                }
                break;
            default:
                break;
        }

        // updated really variable length data position
        _limit = pkPos - fixed - (eeBuf.capacity() - eeBuf.limit());
        return commonRecord;
    }

    protected CommonRecord doDecodeBytes(MappedByteBuffer ibd, byte[] nullValueField, ByteBuffer eeBuf){return null;}
}

package site.wtfu.framework.frm.table.record_format_demo;

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

    protected int _limit;

    private int type;

    private CommonRecord data;

    public CommonRecord(){}
    public CommonRecord(int type, int _limit){
        this.type = type;
        this._limit = _limit;
    }

    /*
    CREATE TABLE record_format_demo (
        c1 int primary key,
        c2 VARCHAR(10) NOT NULL,
        c3 CHAR(10),
        c4 VARCHAR(10)
    ) CHARSET=ascii ROW_FORMAT=COMPACT;

    INSERT INTO record_format_demo(c1, c2, c3, c4) VALUES(13, 'bbb', 'cc', 'd'),(20, 'fff', NULL, NULL);

    01 03 00 [ 00 00 10 00 26 ] 80 00 00 0D , 00 00 00 00 07 07 , A7 00 00 01 1B 01 10 , 62 62 62 , 63 63 20 20 20 20 20 20 20 20 , 64
    03    03 [ 00 00 18 00 1C ] 80 00 00 14 , 00 00 00 00 07 07 , A7 00 00 01 1B 01 1C , 66 66 66
    * */

    // public byte[] variableLengthField;

    public byte[] nullValueField;

    public RecordHeader header;

    public int c1;

    @Override
    public String toString() {
        return "CommonRecord{" +
                "c1=" + c1 +
                ", data=" + data +
                ", header=" + header +
                '}';
    }

    @Override
    protected CommonRecord doDecodeBytes(CommonRecord commonRecord, MappedByteBuffer ibd) {
        int pkPos = ibd.position();

        ibd.position(_limit);
        byte[] estimateExtra = new byte[pkPos - 6 - _limit]; ibd.get(estimateExtra);
        ByteBuffer eeBuf = ByteBuffer.wrap(estimateExtra);

        // one byte enough!
        nullValueField = new byte[1]; ibd.get(nullValueField);
        header = new RecordHeader().decodeBytes(ibd);
        c1 = ibd.getInt() & 0x7FFFFFFF;

        if(commonRecord.type == 0){
            data = new LeafRecord().doDecodeBytes(ibd, nullValueField, eeBuf);
        }else{
            data = new NonLeafRecord().doDecodeBytes(ibd, nullValueField, eeBuf);
        }

        return commonRecord;
    }

    protected CommonRecord doDecodeBytes(MappedByteBuffer ibd, byte[] nullValueField, ByteBuffer eeBuf){return null;}
}

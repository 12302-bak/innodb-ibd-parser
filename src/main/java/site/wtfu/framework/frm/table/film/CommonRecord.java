package site.wtfu.framework.frm.table.film;

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
    CREATE TABLE `film` (
      `film_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
      `title` varchar(128) NOT NULL,
      `description` text,
      `release_year` year(4) DEFAULT NULL,
      `language_id` tinyint(3) unsigned NOT NULL,
      `original_language_id` tinyint(3) unsigned DEFAULT NULL,
      `rental_duration` tinyint(3) unsigned NOT NULL DEFAULT '3',
      `rental_rate` decimal(4,2) NOT NULL DEFAULT '4.99',
      `length` smallint(5) unsigned DEFAULT NULL,
      `replacement_cost` decimal(5,2) NOT NULL DEFAULT '19.99',
      `rating` enum('G','PG','PG-13','R','NC-17') DEFAULT 'G',
      `special_features` set('Trailers','Commentaries','Deleted Scenes','Behind the Scenes') DEFAULT NULL,
      `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      PRIMARY KEY (`film_id`),
      KEY `idx_title` (`title`),
      KEY `idx_fk_language_id` (`language_id`),
      KEY `idx_fk_original_language_id` (`original_language_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8mb4;

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

        int maxVariableField = 13 /* column */ * 2;

        // one byte enough!
        nullValueField = new byte[1];

        int pkPos = ibd.position(), fixed = nullValueField.length + 5;

        ibd.position(pkPos - fixed - maxVariableField);
        byte[] estimateExtra = new byte[maxVariableField]; ibd.get(estimateExtra);
        ByteBuffer eeBuf = ByteBuffer.wrap(estimateExtra);

        ibd.get(nullValueField);
        header = new RecordHeader().decodeBytes(ibd);

        switch ((int) commonRecord.index_id) {
            case 44:
                if(commonRecord.type == 0){
                    data = new LeafRecord().doDecodeBytes(ibd, nullValueField, eeBuf);
                }else{
                    data = new NonLeafRecord().doDecodeBytes(ibd, nullValueField, eeBuf);
                }
                break;
            // other secondary index will be available soon.
            case 45:
            case 46:
            case 47:
            default:
                break;
        }

        _limit = pkPos - fixed - (eeBuf.capacity() - eeBuf.limit());
        return commonRecord;
    }

    protected CommonRecord doDecodeBytes(MappedByteBuffer ibd, byte[] nullValueField, ByteBuffer eeBuf){return null;}
}

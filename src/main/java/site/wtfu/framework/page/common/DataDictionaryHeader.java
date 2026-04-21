package site.wtfu.framework.page.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.wtfu.framework.common.Common;

import java.nio.MappedByteBuffer;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2026/4/21
 *                          @since  1.0
 *                          @author 12302
 *
 * storage/innobase/include/dict0boot.h
 */
// /*-------------------------------------------------------------*/
///* Dictionary header offsets */
//#define DICT_HDR_ROW_ID		0	/* The latest assigned row id */
//#define DICT_HDR_TABLE_ID	8	/* The latest assigned table id */
//#define DICT_HDR_INDEX_ID	16	/* The latest assigned index id */
//#define DICT_HDR_MAX_SPACE_ID	24	/* The latest assigned space id,or 0*/
//#define DICT_HDR_MIX_ID_LOW	28	/* Obsolete,always DICT_HDR_FIRST_ID*/
//#define DICT_HDR_TABLES		32	/* Root of SYS_TABLES clust index */
//#define DICT_HDR_TABLE_IDS	36	/* Root of SYS_TABLE_IDS sec index */
//#define DICT_HDR_COLUMNS	40	/* Root of SYS_COLUMNS clust index */
//#define DICT_HDR_INDEXES	44	/* Root of SYS_INDEXES clust index */
//#define DICT_HDR_FIELDS		48	/* Root of SYS_FIELDS clust index */
@Data
@EqualsAndHashCode(callSuper = true)
public class DataDictionaryHeader extends Common<DataDictionaryHeader> {

    public long DICT_HDR_ROW_ID;

    public long DICT_HDR_TABLE_ID;

    public long DICT_HDR_INDEX_ID;

    public int DICT_HDR_MAX_SPACE_ID;

    public int DICT_HDR_MIX_ID_LOW;

    public int DICT_HDR_TABLES;

    public int DICT_HDR_TABLE_IDS;

    public int DICT_HDR_COLUMNS;

    public int DICT_HDR_INDEXES;

    public int DICT_HDR_FIELDS;

    @Override
    protected DataDictionaryHeader doDecodeBytes(DataDictionaryHeader dataDictionaryHeader, MappedByteBuffer ibd) {
        DICT_HDR_ROW_ID = ibd.getLong();
        DICT_HDR_TABLE_ID = ibd.getLong();
        DICT_HDR_INDEX_ID = ibd.getLong();
        DICT_HDR_MAX_SPACE_ID = ibd.getInt();
        DICT_HDR_MIX_ID_LOW = ibd.getInt();
        DICT_HDR_TABLES = ibd.getInt();
        DICT_HDR_TABLE_IDS = ibd.getInt();
        DICT_HDR_COLUMNS = ibd.getInt();
        DICT_HDR_INDEXES = ibd.getInt();
        DICT_HDR_FIELDS = ibd.getInt();
        return dataDictionaryHeader;
    }
}

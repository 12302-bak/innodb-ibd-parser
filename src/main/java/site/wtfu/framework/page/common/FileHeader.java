package site.wtfu.framework.page.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import site.wtfu.framework.common.Common;
import site.wtfu.framework.common.ConstVal;
import site.wtfu.framework.common.enums.FIL_PAGE_TYPE_ENUM;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2026/4/20
 *                          @since  1.0
 *                          @author 12302
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FileHeader extends Common<FileHeader> {

    /**
     * 页的校验和（checksum值）
     */
    public int FIL_PAGE_SPACE_OR_CHKSUM;

    /**
     * 页号
     */
    public int FIL_PAGE_OFFSET;

    /**
     * 上一个页的页号
     */
    public int FIL_PAGE_PREV;

    /**
     * 下一个页的页号
     */
    public int FIL_PAGE_NEXT;

    /**
     * 页面被最后修改时对应的日志序列位置（英文名是：Log Sequence Number）
     */
    public long FIL_PAGE_LSN;

    /**
     * 该页的 类型
     */
    public FIL_PAGE_TYPE_ENUM FIL_PAGE_TYPE;

    /**
     * 仅在系统表空间的一个页中定义，代表文件至少被刷新到了对应的LSN值
     */
    public long FIL_PAGE_FILE_FLUSH_LSN;

    /**
     * 页属于哪个表空间
     */
    public int FIL_PAGE_ARCH_LOG_NO_OR_SPACE_ID;


    @Override
    protected FileHeader doDecodeBytes(FileHeader fileHeader, MappedByteBuffer ibd) {
        byte[] bytes = new byte[ConstVal.fil_header_length];
        ibd.get(bytes);
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN);

        FIL_PAGE_SPACE_OR_CHKSUM = buffer.getInt();
        FIL_PAGE_OFFSET = buffer.getInt();
        FIL_PAGE_PREV = buffer.getInt();
        FIL_PAGE_NEXT = buffer.getInt();
        FIL_PAGE_LSN = buffer.getLong();
        short temp = buffer.getShort();
        FIL_PAGE_TYPE = FIL_PAGE_TYPE_ENUM.valueOf(temp);
        FIL_PAGE_FILE_FLUSH_LSN = buffer.getLong();
        FIL_PAGE_ARCH_LOG_NO_OR_SPACE_ID = buffer.getInt();

        return fileHeader;
    }
}

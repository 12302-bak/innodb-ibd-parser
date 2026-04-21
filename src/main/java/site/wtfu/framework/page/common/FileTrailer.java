package site.wtfu.framework.page.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import site.wtfu.framework.common.Common;
import site.wtfu.framework.common.ConstVal;

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
public class FileTrailer extends Common<FileTrailer> {


    /**
     * 前4个字节代表页的校验和这个部分是和 File Header 中的校验和相对应的。
     * 每当一个页面在内存中修改了，在同步之前就要把它的校验和算出来，因为 File Header 在页面的前边，所以校验和会被首先同步到磁盘，
     * 当完全写完时，校验和也会被写到页的尾部，如果完全同步成功，则页的首部和尾部的校验和应该是一致的。
     * 如果写了一半儿断电了，那么在 File Header 中的校验和就代表着已经修改过的页，而在 File Trialer 中的校验和代表着原先的页，二者不同则意味着同步中间出了错。
     */
    public int FIL_PAGE_END_LSN_OLD_CHKSUM_01;

    /**
     * 后4个字节代表页面被最后修改时对应的日志序列位置（LSN）这个部分也是为了校验页的完整性的，只不过我们目前还没说 LSN 是个什么意思，所以大家可以先不用管这个属性。
     */
    public int FIL_PAGE_END_LSN_OLD_CHKSUM_02;

    @Override
    protected FileTrailer doDecodeBytes(FileTrailer fileTrailer, MappedByteBuffer ibd) {
        byte[] bytes = new byte[ConstVal.fil_trailer_length];
        ibd.get(bytes);
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN);

        FIL_PAGE_END_LSN_OLD_CHKSUM_01 = buffer.getInt();
        FIL_PAGE_END_LSN_OLD_CHKSUM_02 = buffer.getInt();
        return fileTrailer;
    }
}

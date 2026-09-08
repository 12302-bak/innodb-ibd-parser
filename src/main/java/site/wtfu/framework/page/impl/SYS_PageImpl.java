package site.wtfu.framework.page.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import site.wtfu.framework.common.Common;
import site.wtfu.framework.common.ConstVal;
import site.wtfu.framework.page.common.DataDictionaryHeader;
import site.wtfu.framework.page.common.SegmentHeader;
import site.wtfu.framework.page.impl.sys.SYS_DataDictionaryHeader;
import site.wtfu.framework.page.impl.sys.SYS_RollbackSegmentHeader;
import site.wtfu.framework.utils.AlignmentUtil;

import java.nio.MappedByteBuffer;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2026/4/21
 *                          @since  1.0
 *                          @author 12302
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"empty_space"})
public class SYS_PageImpl extends Common<SYS_PageImpl> {

    public Object body;

    // 16272 byte, rather than ~16336~
    public byte[] empty_space;

    private int page_no;

    public SYS_PageImpl(int page_no){
        this.page_no = page_no;
    }

    @Override
    protected SYS_PageImpl doDecodeBytes(SYS_PageImpl sysPage, MappedByteBuffer ibd) {

        switch (page_no){
            // Insert Buffer Header 存储Insert Buffer的头部信息
            case 3: break;

            // First Rollback Segment 第一个回滚段的页面
            case 6:
                body = new SYS_RollbackSegmentHeader().decodeBytes(ibd); break;

            // Data Dictionary Header 数据字典头部信息
            case 7:
                body = new SYS_DataDictionaryHeader().decodeBytes(ibd); break;
            default: break;
        }

        int size = AlignmentUtil.align(ibd.position()) - ConstVal.fil_trailer_length - ibd.position();
        empty_space = new byte[size]; ibd.get(empty_space);
        return sysPage;
    }
}

package site.wtfu.framework.page.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.wtfu.framework.common.Common;
import site.wtfu.framework.page.common.PageDirectory;
import site.wtfu.framework.page.common.PageHeader;

import java.nio.MappedByteBuffer;
import java.util.List;

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
public class INDEX_PageImpl extends Common<INDEX_PageImpl> {

    public PageHeader page_header;

    // include Infimum+Supremum
    public List<Object> record;

    // not sure
    public byte[] free_space;

    public PageDirectory page_dir;

    @Override
    protected INDEX_PageImpl doDecodeBytes(INDEX_PageImpl indexPage, MappedByteBuffer ibd) {
        page_header = new PageHeader().decodeBytes(ibd);

        // ...

        page_dir = new PageDirectory(page_header.PAGE_N_DIR_SLOTS).decodeBytes(ibd);
        return indexPage;
    }
}

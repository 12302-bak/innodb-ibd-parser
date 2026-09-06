package site.wtfu.framework.page.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.wtfu.framework.common.Common;
import site.wtfu.framework.frm.table.record_format_demo.CommonRecord;
import site.wtfu.framework.page.common.PageDirectory;
import site.wtfu.framework.page.common.PageHeader;
import site.wtfu.framework.page.common.PseudoRecord;

import java.nio.MappedByteBuffer;
import java.util.ArrayList;
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

    public PseudoRecord[] infi_and_super = new PseudoRecord[2];

    public List<Object> records = new ArrayList<>();

    public int free_space;

    public PageDirectory page_dir;

    public int space_id;

    public INDEX_PageImpl(int FIL_PAGE_ARCH_LOG_NO_OR_SPACE_ID){
        space_id = FIL_PAGE_ARCH_LOG_NO_OR_SPACE_ID;
    }

    @Override
    protected INDEX_PageImpl doDecodeBytes(INDEX_PageImpl indexPage, MappedByteBuffer ibd) {
        page_header = new PageHeader().decodeBytes(ibd);

        // infimum
        infi_and_super[0] = new PseudoRecord().decodeBytes(ibd, ibd.position() + 5);
        // supermum
        infi_and_super[1] = new PseudoRecord().decodeBytes(ibd, ibd.position() + 5);

        int nextRecord = infi_and_super[0].recordHeader.getNextRecord();
        ibd.position(nextRecord);

        switch (space_id) {
            case 24:
                while (nextRecord != infi_and_super[1].getSectionStart()) {
                    site.wtfu.framework.frm.table.record_format_demo.CommonRecord record =
                            new site.wtfu.framework.frm.table.record_format_demo.CommonRecord(
                                    page_header.getPAGE_LEVEL()).decodeBytes(ibd, nextRecord);
                    records.add(record);
                    nextRecord = record.header.getNextRecord();
                }
                break;
            case 25:
                while (nextRecord != infi_and_super[1].getSectionStart()) {
                    site.wtfu.framework.frm.table.customer.CommonRecord record =
                            new site.wtfu.framework.frm.table.customer.CommonRecord(
                                    page_header.getPAGE_INDEX_ID(), page_header.getPAGE_LEVEL()).decodeBytes(ibd, nextRecord);
                    records.add(record);
                    nextRecord = record.header.getNextRecord();
                }
                break;
            case 26:
                while (nextRecord != infi_and_super[1].getSectionStart()) {
                    site.wtfu.framework.frm.table.film.CommonRecord record =
                            new site.wtfu.framework.frm.table.film.CommonRecord(
                                    page_header.getPAGE_INDEX_ID(), page_header.getPAGE_LEVEL()).decodeBytes(ibd, nextRecord);
                    records.add(record);
                    nextRecord = record.header.getNextRecord();
                }
                break;
            default:
                break;
        }

        int free_pos = ibd.position();
        page_dir = new PageDirectory(page_header.PAGE_N_DIR_SLOTS).decodeBytes(ibd);
        free_space = page_dir.getSectionStart() - free_pos;

        return indexPage;
    }
}

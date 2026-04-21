package site.wtfu.framework.common.enums;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2026/4/20
 *                          @since  1.0
 *                          @author 12302
 *
 */
public enum FIL_PAGE_TYPE_ENUM {

    FIL_PAGE_TYPE_ALLOCATED((short) 0x0000,"最新分配，还没使用"),
    FIL_PAGE_UNDO_LOG((short) 0x0002,"Undo日志页"),
    FIL_PAGE_INODE((short) 0x0003,"段信息节点"),
    FIL_PAGE_IBUF_FREE_LIST((short) 0x0004,"Insert Buffer空闲列表"),
    FIL_PAGE_IBUF_BITMAP((short) 0x0005,"Insert Buffer位图"),
    FIL_PAGE_TYPE_SYS((short) 0x0006,"系统页"),
    FIL_PAGE_TYPE_TRX_SYS((short) 0x0007,"事务系统数据"),
    FIL_PAGE_TYPE_FSP_HDR((short) 0x0008,"表空间头部信息(File space header)"),
    FIL_PAGE_TYPE_XDES((short) 0x0009,"扩展描述页"),
    FIL_PAGE_TYPE_BLOB((short) 0x000A,"BLOB页"),
    FIL_PAGE_INDEX((short) 0x45BF,"索引页，也就是我们所说的 数据页");

    private final short code;

    private final String description;

    FIL_PAGE_TYPE_ENUM(short code, String description){
        this.code=code;
        this.description=description;
    }

    public static FIL_PAGE_TYPE_ENUM valueOf(short code) {
        for (FIL_PAGE_TYPE_ENUM type : FIL_PAGE_TYPE_ENUM.values()) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }
}

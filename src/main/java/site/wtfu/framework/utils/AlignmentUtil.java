package site.wtfu.framework.utils;

import site.wtfu.framework.common.ConstVal;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2026/4/21
 *                          @since  1.0
 *                          @author 12302
 *
 */
public class AlignmentUtil {

    public static int align(int position) {
        int mask = ConstVal.PAGE_SIZE - 1;
        return position + mask & ~ mask;
    }
}

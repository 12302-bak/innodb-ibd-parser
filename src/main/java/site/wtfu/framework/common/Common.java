package site.wtfu.framework.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import site.wtfu.framework.page.Page;

import java.nio.MappedByteBuffer;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2025/4/1
 *                          @since  1.0
 *                          @author 12302
 *
 */
@Setter
public abstract class Common <T extends Common<T>> {


    @Data
    @Accessors(chain = true)
    @AllArgsConstructor
    private static class Section{
        private int start;
        private int stop;
    }

    private Section _section;

    public T decodeBytes(MappedByteBuffer ibd){
        return decodeBytes(ibd, ibd.position());
    }

    public T decodeBytes(MappedByteBuffer ibd, int from){
        return decodeBytes(ibd, false, from);
    }

    public T decodeBytes(MappedByteBuffer ibd, boolean reset, int from){
        ibd.position(from);
        
        @SuppressWarnings("unchecked") T rst = (T)this;
        rst = doDecodeBytes(rst, ibd);
        this._section = new Section(from, ibd.position());

        if(reset){ ibd.position(from);}
        return rst;
    }

    protected abstract T doDecodeBytes(T t, MappedByteBuffer ibd);
}

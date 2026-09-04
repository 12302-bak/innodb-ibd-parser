package site.wtfu.framework.common;

import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

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
    protected static class Section{
        private int start;
        private int stop;

        // [...] 表示页内偏移
        @Override
        public String toString() {
            return "Section{" +
                    "start=" + start + "[" + (start % ConstVal.PAGE_SIZE) + "]" +
                    ", stop=" + stop + "[" + (stop % ConstVal.PAGE_SIZE) + "]" +
                    '}';
        }
    }

    protected Section _section = new Section();
    public int getSectionStop() { return _section.getStop(); }
    public int getSectionStart(){ return _section.getStart(); }

    public T decodeBytes(MappedByteBuffer ibd){
        return decodeBytes(ibd, ibd.position());
    }

    public T decodeBytes(MappedByteBuffer ibd, int from){
        return decodeBytes(ibd, false, from);
    }

    public T decodeBytes(MappedByteBuffer ibd, boolean reset, int from){
        _section.setStart(from);
        ibd.position(from);
        
        @SuppressWarnings("unchecked") T rst = (T)this;
        rst = doDecodeBytes(rst, ibd);
        _section.setStop(ibd.position());


        if(reset){ ibd.position(from);}
        return rst;
    }

    protected abstract T doDecodeBytes(T t, MappedByteBuffer ibd);
}

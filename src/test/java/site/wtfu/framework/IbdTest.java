package site.wtfu.framework;


import org.junit.Test;
import site.wtfu.framework.common.enums.FIL_PAGE_TYPE_ENUM;
import site.wtfu.framework.page.Page;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IbdTest {

    @Test
    public void testParseSystemIbdFile(){
        String ibdFilePath = "/tmp/mysql/ibdata1";
        MappedByteBuffer ibd;
        try (FileChannel fileChannel = new FileInputStream(ibdFilePath).getChannel()) {
            ibd = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
            ibd.order(ByteOrder.BIG_ENDIAN);

            List<Page> list = new ArrayList<>();
            while(ibd.hasRemaining()) {

                Page page = new Page().decodeBytes(ibd);
                list.add(page);
            }

            List<Page> collect = list.stream().filter(
                    it -> it.fileHeader.FIL_PAGE_TYPE
                            //== FIL_PAGE_TYPE_ENUM.FIL_PAGE_UNDO_LOG
                            == FIL_PAGE_TYPE_ENUM.FIL_PAGE_TYPE_TRX_SYS
            ).collect(Collectors.toList());

            System.out.println(collect.size());
        }catch (Exception e){ e.printStackTrace();}
    }

    @Test
    public void testParseRecordFormatDemoIbdFile() {
        String ibdFilePath = "/tmp/mysql/demos/record_format_demo.ibd";
        MappedByteBuffer ibd;
        try (FileChannel fileChannel = new FileInputStream(ibdFilePath).getChannel()) {
            ibd = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
            ibd.order(ByteOrder.BIG_ENDIAN);

            List<Page> list = new ArrayList<>();
            while(ibd.hasRemaining()) {

                Page page = new Page().decodeBytes(ibd);
                list.add(page);
            }

            System.out.println(list.size());
        }catch (IOException e){ e.printStackTrace();}
    }

    @Test
    public void testParseCustomerIbdFile() {
        String ibdFilePath = "/tmp/mysql/demos/customer.ibd";
        MappedByteBuffer ibd;
        try (FileChannel fileChannel = new FileInputStream(ibdFilePath).getChannel()) {
            ibd = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
            ibd.order(ByteOrder.BIG_ENDIAN);

            List<Page> list = new ArrayList<>();
            while(ibd.hasRemaining()) {

                Page page = new Page().decodeBytes(ibd);
                list.add(page);
            }

            System.out.println(list.size());
        }catch (Exception e){ e.printStackTrace();}
    }

    @Test
    public void testParseFilmIbdFile() {
        String ibdFilePath = "/tmp/mysql/demos/film.ibd";
        MappedByteBuffer ibd;
        try (FileChannel fileChannel = new FileInputStream(ibdFilePath).getChannel()) {
            ibd = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
            ibd.order(ByteOrder.BIG_ENDIAN);

            List<Page> list = new ArrayList<>();
            while(ibd.hasRemaining()) {

                Page page = new Page().decodeBytes(ibd);
                list.add(page);
            }

            System.out.println(list.size());
        }catch (Exception e){ e.printStackTrace();}
    }

}

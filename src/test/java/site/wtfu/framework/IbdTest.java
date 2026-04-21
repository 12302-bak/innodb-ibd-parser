package site.wtfu.framework;


import org.junit.Test;
import site.wtfu.framework.page.Page;

import java.io.FileInputStream;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class IbdTest {


    @Test
    public void testParseIbdFile() {
        String ibdFilePath = "/tmp/mysql/demos/record_format_demo.ibd";
        //ibdFilePath = "/tmp/mysql/ibdata1";
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

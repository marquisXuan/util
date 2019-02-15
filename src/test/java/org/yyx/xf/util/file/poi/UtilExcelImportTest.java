package org.yyx.xf.util.file.poi;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.xf.tool.document.word.util.UtilExcelImport;
import org.yyx.xf.entity.ImportEmployee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;


public class UtilExcelImportTest {

    /**
     * UtilExcelImportTest 日志控制器
     * Create by 叶云轩 at 2018/4/24 下午6:26
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilExcelImportTest.class);

    @Test
    public void importExcel() throws FileNotFoundException {
        URL resource = this.getClass().getClassLoader().getResource("test.xlsx");
        assert resource != null;
        String path = resource.getPath();
        File file = new File(path);
        long startTime = System.currentTimeMillis();
        String[] strings = UtilExcelImport.importExcel(file, false);
        long endTime = System.currentTimeMillis();
        LOGGER.info("\n\t------------------------------------------\n" +
                "\t| [导入耗时]: {}毫秒\n" +
                "\t------------------------------------------", endTime - startTime);
        for (String string : strings) {
            LOGGER.info("\n\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓\n" +
                    "\t├ [导入数据]: {}\n" +
                    "\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓", string);
        }
    }

    @Test
    public void importExcelIntoEntity() {
        URL resource = this.getClass().getClassLoader().getResource("test.xlsx");
        assert resource != null;
        String path = resource.getPath();
        File file = new File(path);
        try {
            Object[] objects = UtilExcelImport.importExcelIntoEntity(file, new Class[]{ImportEmployee.class});
            for (Object object : objects) {
                List<ImportEmployee> o = (List<ImportEmployee>) object;
                for (ImportEmployee importEmployee : o) {
                    LOGGER.info("\n\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓\n" +
                            "\t├ [object]: {}\n" +
                            "\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓", importEmployee);
                }
            }
        } catch (IOException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
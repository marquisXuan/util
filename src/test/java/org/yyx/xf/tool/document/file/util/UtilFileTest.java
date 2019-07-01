package org.yyx.xf.tool.document.file.util;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class UtilFileTest {

    @Test
    public void writeInFile() {
        String abc = "aaaaa";
        File file = UtilFile.writeInFile(abc, "aa.txt");

    }
}
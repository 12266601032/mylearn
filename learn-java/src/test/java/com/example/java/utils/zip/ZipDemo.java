package com.example.java.utils.zip;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ZipDemo {

    private static final int DEFAULT_BUFFER_SIZE = 8 * 1024;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 压缩为zip
     *
     * @param srcDir        压缩文件路径
     * @param out           输出流
     * @param KeepStructure 是否保留原来的目录结构,true:保留目录结构;
     *                      false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     */
    public static void zip(String srcDir, OutputStream out, boolean KeepStructure) throws IOException {

        long startMillis = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(wrapIfNecessary(out), DEFAULT_CHARSET);
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName(), KeepStructure);
            long endMillis = System.currentTimeMillis();
        } finally {
            if (zos != null) {
                zos.close();
            }
        }
    }

    private static OutputStream wrapIfNecessary(OutputStream out) {
        if (!(out instanceof BufferedOutputStream)) {
            return new BufferedOutputStream(out, DEFAULT_BUFFER_SIZE * 2);
        }
        return out;
    }

    private static InputStream wrapIfNecessary(InputStream is) {
        if (!(is instanceof BufferedInputStream)) {
            return new BufferedInputStream(is, DEFAULT_BUFFER_SIZE * 2);
        }
        return is;
    }

    /**
     * 压缩成ZIP 方法2
     *
     * @param srcFiles 需要压缩的文件列表
     * @param out      压缩文件输出流
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void zip(OutputStream out, File... srcFiles) throws IOException {
        long startMillis = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(wrapIfNecessary(out), DEFAULT_CHARSET); //wrap stream as buffered stream if necessary
            for (File srcFile : srcFiles) {
                if (srcFile.isDirectory()) { //如果指定的是目录，则递归压缩
                    compress(srcFile, zos, srcFile.getName(), true);
                } else {
                    byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
                    zos.putNextEntry(new ZipEntry(srcFile.getName()));
                    int len;
                    try (InputStream is = wrapIfNecessary(new FileInputStream(srcFile))) {
                        while ((len = is.read(buf)) != -1) {
                            zos.write(buf, 0, len);
                        }
                    }
                    zos.closeEntry();
                }
            }
            long endMillis = System.currentTimeMillis();
        } finally {
            if (zos != null) {
                zos.close();
            }
        }
    }

    /**
     * 解压缩
     *
     * @param zipFilePath        压缩文件路径
     * @param unzipDirPath       解压目标目录
     * @param includeZipFileName 是否包含zip文件名
     * @throws IOException
     */
    public static void unzip(String zipFilePath, String unzipDirPath, boolean includeZipFileName) throws IOException {
        if (zipFilePath == null || zipFilePath == "") {
            throw new IllegalArgumentException("zip field path can not be null.");
        }
        if (!zipFilePath.endsWith(".zip")) {
            throw new IllegalArgumentException("Please specify a zip field. field :" + zipFilePath + ".");
        }
        if (unzipDirPath == null || unzipDirPath == "") {
            throw new IllegalArgumentException("unzip directory can not be null.");
        }
        File zipFile = new File(zipFilePath);
        //解压目标 需要包含zip文件名
        if (includeZipFileName) {
            String zipFileName = zipFile.getName();
            zipFileName = zipFileName.substring(0, zipFileName.lastIndexOf("."));
            unzipDirPath = (unzipDirPath.endsWith("/") || unzipDirPath.endsWith(File.separator) ? unzipDirPath : unzipDirPath + File.separator) + zipFileName;
        }
        File unzipFileDir = new File(unzipDirPath);
        //创建目录
        if (!unzipFileDir.exists() || !unzipFileDir.isDirectory()) {
            unzipFileDir.mkdirs();
        }

        uncompress(zipFile, unzipFileDir, true);
    }

    private static void uncompress(File fileZip, File unzipFileDir, boolean allowOverride) throws IOException {
        if (!unzipFileDir.isDirectory()) {
            throw new IllegalArgumentException("unzip target must be a directory.");
        }
        ZipEntry zipEntry;
        String entryPath;
        File entryFile;
        ZipFile zipFile = new ZipFile(fileZip);
        Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
        while (zipEntries.hasMoreElements()) {
            zipEntry = zipEntries.nextElement();
            entryPath = unzipFileDir.getAbsolutePath() + File.separator + zipEntry.getName();
            //先创建文件路径
            int sepIndex;
            if ((sepIndex = entryPath.lastIndexOf(File.separator)) != -1) {
                File entryDir = new File(entryPath.substring(0, sepIndex));
                if (!entryDir.exists() || !entryDir.isDirectory()) { //如果有同名文件 则创建同名目录
                    entryDir.mkdirs();
                }
            }
            //然后创建文件
            entryFile = new File(entryPath);
            //允许重复解压
            if (allowOverride && entryFile.exists() && entryFile.isFile()) {
                //删除已存在的目标文件
                entryFile.delete();
            }
            //如果压缩时保存 文件目录结构，则无需处理解压内容，创建完文件即可。
            if (entryFile.isDirectory()) {
                if (!entryFile.exists()) {
                    entryFile.mkdirs();
                }
                continue;
            }
            //写入文件
            try (OutputStream os = wrapIfNecessary(new FileOutputStream(entryFile));
                 InputStream is = wrapIfNecessary(zipFile.getInputStream(zipEntry))) {
                byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
                int len;
                while ((len = is.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                os.flush();
            }
        }
    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile       源文件
     * @param zos              zip输出流
     * @param name             压缩后的名称
     * @param keepStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean keepStructure) throws IOException {
        byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            try (InputStream is = wrapIfNecessary(new FileInputStream(sourceFile))) {
                while ((len = is.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
            }
            // Complete the entry
            zos.closeEntry();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (keepStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + File.separator));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }

            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (keepStructure) {
                        // 注意：field.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + File.separator + file.getName(), keepStructure);
                    } else {
                        compress(file, zos, file.getName(), keepStructure);
                    }
                }
            }
        }
    }


    private File root;
    private File root_a;
    private File root_a_aa_txt;
    private String root_a_aa_txt_content;

    private File root_b;
    private File root_c;
    private File root_c_c1;
    private File root_c_c2;

    private String basePath;

    @Before
    public void setUp() throws IOException {

        basePath = this.getClass().getClassLoader().getResource("").getPath();
        root = new File(basePath + "root");
        root.mkdirs();
        root_a = new File(root, "a");
        root_a.mkdirs();
        root_a_aa_txt = new File(root_a, "aa.txt");
        root_a_aa_txt.createNewFile();
        root_a_aa_txt_content = "~！@#￥%……&*（）——+·1234567890-=qwertyuiop[]\\{}|asdfghjkl;':师德师风是大法师肥是打工娃儿";
        try (FileOutputStream fos = new FileOutputStream(root_a_aa_txt)) {
            fos.write(root_a_aa_txt_content.getBytes());
            fos.flush();
        }
        root_b = new File(root, "b");
        root_b.mkdirs();
        root_c = new File(root, "c");
        root_c.mkdirs();
        root_c_c1 = new File(root_c, "c.1");
        root_c_c1.mkdirs();
        root_c_c2 = new File(root_c, "c.2");
        root_c_c2.mkdirs();

    }

    @After
    public void after() {
        root_a_aa_txt.delete();
        root_c_c2.delete();
        root_c_c1.delete();
        root_c.delete();
        root_b.delete();
        root_a.delete();
        root.delete();

    }

    @Test
    public void testZip() throws IOException {

        File zipFile = new File(basePath + "zipDemo.zip");
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipDemo.zip(root.getAbsolutePath(), fos, true);
        fos.flush();
        fos.close();
        assertTrue(zipFile.isFile());
        ZipDemo.unzip(zipFile.getAbsolutePath(), basePath, true);
        File unzipDir = new File(basePath + "zipDemo");
        assertTrue(unzipDir.isDirectory());
        Map<String, File[]> filesCollector = new HashMap<>();
        Map<String, File> fileCollector = new HashMap<>();
        allFilesMap(unzipDir, filesCollector, fileCollector);
        assertEquals(1, filesCollector.get(unzipDir.getName()).length);
        assertEquals(3, filesCollector.get(root.getName()).length);
        assertEquals(2, filesCollector.get(root_c.getName()).length);
        assertTrue(fileCollector.get(root_c_c1.getName()).isDirectory());
        assertTrue(fileCollector.get(root_c_c2.getName()).isDirectory());
        assertEquals(0, filesCollector.get(root_b.getName()).length);
        assertTrue(fileCollector.get(root_b.getName()).isDirectory());
        assertEquals(1, filesCollector.get(root_a.getName()).length);
        assertTrue(fileCollector.get(root_a_aa_txt.getName()).isFile());
        FileInputStream fis = new FileInputStream(fileCollector.get(root_a_aa_txt.getName()));
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        assertEquals(root_a_aa_txt_content, new String(bytes));
    }

    private void allFilesMap(File file, Map<String, File[]> filesCollector, Map<String, File> fileCollector) {

        File[] files = file.listFiles();
        filesCollector.put(file.getName(), files);
        if (files.length == 0) {
            fileCollector.put(file.getName(), file);
            return;
        }
        for (File f : files) {
            if (f.isFile()) {
                fileCollector.put(f.getName(), f);
                filesCollector.put(f.getName(), new File[]{f});
            } else {
                allFilesMap(f, filesCollector, fileCollector);
            }
        }
    }
}

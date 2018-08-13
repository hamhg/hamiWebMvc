package com.hami.biz.system.utils;

import org.springframework.web.multipart.MultipartRequest;

import java.io.*;
import java.util.Enumeration;

/**
 * <pre>
 * <li>Program Name : FileUtil
 * <li>Description  :
 * <li>History      : 2017. 12. 25.
 * </pre>
 *
 * @author HHG
 */
public abstract class FileUtil {
    /**
     * 파일을 삭제한다.
     */
    public static void deleteAllFile(MultipartRequest multi)
    {
        if (multi == null) return;
        Enumeration enum1 = (Enumeration) multi.getFileNames();
        String fileName;
        File file;
        while (enum1.hasMoreElements())
        {
            fileName = (String) enum1.nextElement();
            file = (File) multi.getFile(fileName);
            if (file != null) file.delete();
        }
    }

    /**
     * MultipartRequest형의 업로드할 파일을 반환한다.

    public static MultipartRequest makeMultipartRequest(HttpServletRequest request, File saveDirectoryFile, int maxPostSize) throws IOException
    {
        if (!saveDirectoryFile.exists())
        {
            saveDirectoryFile.mkdirs();// 디렉토리가 존재하지 않을경우 새로 생성.
        }
        maxPostSize = maxPostSize == 0 ? 10 * 1024 * 1024 : maxPostSize;// 10MB
        String saveDirectory = saveDirectoryFile.getAbsolutePath();
        MultipartRequest multi = new MultipartRequest(request, saveDirectory, maxPostSize, request.getCharacterEncoding(), new DefaultFileRenamePolicy());
        return multi;
    }
     */

    /**
     * 파일 내용을 문자열로 반환한다.
     */
    public static String getFileContents(File file) throws IOException
    {
        String line;
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(file));
            boolean appendLine = false;
            while ((line = br.readLine()) != null)
            {
                if (appendLine) sb.append(System.getProperty("line.separator"));
                sb.append(line);
                appendLine = true;
            }
            return sb.toString();
        }
        finally
        {
            if (br != null) try
            {
                br.close();
            }
            catch (Exception e)
            {
            }
        }
    }

    /**
     * 파일에 기록한다.
     */
    public static boolean writeTextFile(File file, String text, boolean append, boolean force) throws IOException
    {
        if (!file.exists() || force)
        {
            BufferedWriter writer = null;
            try
            {
                writer = new BufferedWriter(new FileWriter(file, append));
                writer.write(text);
                return true;
            }
            finally
            {
                if (writer != null) try
                {
                    writer.close();
                }
                catch (Exception e)
                {
                }
            }
        }
        else return false;
    }

    /**
     * 파일을 복사한다.
     */
    public static void copyFile(File src, File dest) throws IOException
    {
        //if ( ! src.isFile() )
        int readBytes = 0;
        byte[] buffer = new byte[1024];
        FileInputStream fin = null;
        FileOutputStream fout = null;
        if (!dest.getParentFile().exists()) dest.getParentFile().mkdirs();
        try
        {
            fin = new FileInputStream(src);
            fout = new FileOutputStream(dest);
            while ((readBytes = fin.read(buffer)) != -1)
                fout.write(buffer, 0, readBytes);
        }
        finally
        {
            if (fin != null) try
            {
                fin.close();
            }
            catch (Exception e)
            {
            }
            if (fout != null) try
            {
                fout.close();
            }
            catch (Exception e)
            {
            }
        }
    }

    /**
     * BufferedReader를 문자열로 반환한다.
     */
    public static String readBuffer(BufferedReader reader) throws IOException
    {
        char[] cbuf = new char[1024];
        int rcnt;
        StringBuffer sb = new StringBuffer();
        while (true)
        {
            rcnt = reader.read(cbuf);
            System.out.println("---------------------------->:" + rcnt);
            if (rcnt < 0) break;
            sb.append(new String(cbuf, 0, rcnt));
        }
        return sb.toString();
    }

    /**
     * V3파일을 복사한다.
     */
    public static void main(String[] args) throws IOException
    {
        copyFile(new File("c:/V3_7.0.zip"), new File("c:/xx/V3_7.0.zip"));
    }
}

package com.hami.sys.jdbc.sql;

import com.hami.sys.util.StringUtils;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 * <li>Program Name : QueryLoader
 * <li>Description  :
 * <li>History      : 2017. 12. 25.
 * </pre>
 *
 * @author HHG
 */
@SuppressWarnings({"unchecked"})
public class QueryLoader {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 파일정보를 담아둘 임시저장소
     */
    protected HashMap memStatement = new HashMap();
    /**
     * QueryLoader
     */
    protected static QueryLoader instance = new QueryLoader();
    /**
     * instance를 반환한다.
     */
    public static QueryLoader getInstance()
    {
        return instance;
    }

    /**
     * getElementWithClassName를 실행한다.
     */
    public String getElement(Object obj, String sqlName, Object replace)
    {
        String className = (obj instanceof String) ? obj.toString() : (obj instanceof Class) ? ((Class) obj).getName() : obj.getClass().getName();
        String path = (obj instanceof String) ? "" : (obj instanceof Class) ? ((Class) obj).getResource("").getPath() : obj.getClass().getResource("").getPath();
        path = path.substring(0,path.indexOf("classes")+8);
        return getElementWithClassName(path, className, sqlName, replace);
    }

    /**
     * getElementWithPath를 실행한다.
     */
    public String getElementWithClassName(String basepath, String className, String sqlName, Object replace)
    {
        String filePath = basepath + className.replace('.', '/');
        filePath = filePath.substring(0,filePath.length()-3)+".xml";
        log.debug(" * DAO Class Name: '{}', Method Name: '{}'", className, sqlName);
        log.debug(" * Statements Path: '{}'", filePath);
        return getElementWithPath(filePath, sqlName, replace);
    }

    /**
     * "@BLOCK@" 치환하여 Sql RETURN
     */
    public String getElementWithPath(String filePath, String sqlName, Object replace)
    {
        try
        {
            Document doc = getFromMemory(filePath);
            if (doc == null)
            {
                doc = saveDocument(filePath);
            }
            Element element = doc.getRootElement();
            List list = element.getChildren();
            Iterator iterator = list.iterator();
            while (iterator.hasNext())
            {
                Element child = (Element) iterator.next();
                List ChildList = child.getAttributes();
                Iterator iter = ChildList.iterator();
                while (iter.hasNext())
                {
                    Attribute attribute = (Attribute) iter.next();
                    if (attribute.getValue().equals(sqlName)) { return replaceBlock(child.getText(), replace); }
                }
            }
            log.debug("Sql Statement Not Found...");
            return null;
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * SQL QUERY에서 "@BLOCK@"을 치환한다.
     */
    private String replaceBlock(String text, Object replace)
    {
        text = (text.contains(";"))?text.replace(";", ""):text;
        if (replace == null)
        {
            return text.replaceAll("@BLOCK@", "");
        }
        else if (replace instanceof String)
        {
            return text.replaceAll("@BLOCK@", replace.toString());
        }
        else if (replace instanceof HashMap)
        {
            String query = text;
            HashMap map = (HashMap) replace;
            Iterator iter = map.keySet().iterator();
            String key;
            while (iter.hasNext())
            {
                key = (String) iter.next();
                query = query.replaceAll(key, StringUtils.nvl((String) map.get(key), ""));
            }
            return query;
        }
        else
        {
            return text.replaceAll("@BLOCK@", "");
        }
    }

    /**
     * 메모리에 파일을 저장한다.
     */
    private Document saveDocument(String filePath) throws JDOMException, IOException
    {
        SAXBuilder builder = new SAXBuilder();
        File file = new File(filePath);
        Document doc = builder.build(file);
        memStatement.put(filePath, new Object[] { doc, String.valueOf(file.lastModified()) });
        return doc;
    }

    /**
     * 메모리에서 파일을 읽어온다.
     */
    private Document getFromMemory(String filePath)
    {
        Object[] objarr = (Object[]) memStatement.get(filePath);
        if (objarr != null)
        {
            if (objarr[1].toString().equals(String.valueOf(new File(filePath).lastModified()))) { return (Document) objarr[0]; }
        }
        return null;
    }
}

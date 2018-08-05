package com.hami.sys.jdbc.record;

import com.hami.biz.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 * <li>Program Name : RecordUtils
 * <li>Description  :
 * <li>History      : 2018. 2. 17.
 * </pre>
 *
 * @author HHG
 */
public class RecordUtils {
    public final static Logger log = LoggerFactory.getLogger("com.hami.fw.jdbc.record.RecordUtils");
    public static final RecordSet EMPTY_RECORD_SET = new MetadataRecordSet(new String[0]);

    public static RecordMetadata generateRecordMetadata(Class recordClass) {
        List columnNamesList = new ArrayList();
        PropertyDescriptor pds[] = BeanUtils.getPropertyDescriptors(recordClass);
        PropertyDescriptor arr$[] = pds;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            PropertyDescriptor pd = arr$[i$];
            if (pd.getWriteMethod() != null)
                columnNamesList.add(pd.getName());
        }

        return new RecordMetadata((String[]) columnNamesList.toArray(new String[0]));
    }

    public static Object convertToBean(Record srcRecord, Class targetClass) {
        return convertToBean(srcRecord, targetClass, false);
    }

    public static Object convertToBean(Record srcRecord, Class targetClass, boolean forceConvertNumeric) {
        Object targetObj = null;
        try {
            targetObj = targetClass.newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
            new BizException(e1);
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            new BizException(e2);
        }
        PropertyDescriptor prop[] = BeanUtils.getPropertyDescriptors(targetClass);
        int len$ = prop.length;
        for (int i = 0; i < len$; i++) {
            PropertyDescriptor property = prop[i];
            String propertyNm = property.getName();
            if (!srcRecord.containsKey(propertyNm))
                continue;
            Class propType = property.getPropertyType();
            Method setter = property.getWriteMethod();
            try {
                if (setter == null)
                    continue;
                Method targetSetter = BeanUtils.findDeclaredMethodWithMinimalParameters(targetClass, setter.getName());
                if (targetSetter == null)
                    continue;
                Object objVal = srcRecord.get(propertyNm);
                Class targetSetterParamType = targetSetter.getParameterTypes()[0];
                if (ClassUtils.isAssignableValue(targetSetterParamType, objVal)) {
                    ReflectionUtils.invokeMethod(targetSetter, targetObj, new Object[]{objVal});
                    continue;
                }
                if (ClassUtils.isPrimitiveOrWrapper(objVal.getClass())) {
                    Object castObjVal = objVal.toString();
                    ReflectionUtils.invokeMethod(targetSetter, targetObj, new Object[]{castObjVal});
                    continue;
                }
                if (objVal instanceof String) {
                    Class targetWrapperSetterParamType = ClassUtils.resolvePrimitiveIfNecessary(targetSetterParamType);
                    java.lang.reflect.Constructor strCtor = targetWrapperSetterParamType.getConstructor(new Class[]{String.class});
                    Object castObjVal = BeanUtils.instantiateClass(strCtor, new Object[]{objVal});
                    ReflectionUtils.invokeMethod(targetSetter, targetObj, new Object[]{castObjVal});
                    continue;
                }
                Class objValClass = objVal.getClass();
                if (forceConvertNumeric && (objVal instanceof BigDecimal)) {
                    BigDecimal srcVal = (BigDecimal) objVal;
                    Object castObjVal = null;
                    if (ClassUtils.isAssignable(Short.TYPE, targetSetterParamType))
                        castObjVal = srcVal.shortValue();
                    else if (ClassUtils.isAssignable(Integer.TYPE, targetSetterParamType))
                        castObjVal = srcVal.intValue();
                    else if (ClassUtils.isAssignable(Float.TYPE, targetSetterParamType))
                        castObjVal = srcVal.floatValue();
                    else if (ClassUtils.isAssignable(Double.TYPE, targetSetterParamType))
                        castObjVal = srcVal.doubleValue();
                    else if (ClassUtils.isAssignable(Long.TYPE, targetSetterParamType))
                        castObjVal = srcVal.longValue();
                    ReflectionUtils.invokeMethod(targetSetter, targetObj, castObjVal);
                } else {
                    log.warn((new StringBuilder()).append("failed to convert : FieldType[")
                            .append(targetSetterParamType).append("], Value[").append(objVal).append("]").toString());
                }
                continue;
            } catch (Exception e) {
                if (log.isDebugEnabled())
                    log.error((new StringBuilder()).append("bind error with [").append(propertyNm).append("]").toString(),e);
            }
        }

        return targetObj;
    }

    public static List convertToBeanList(RecordSet recordSet, Class targetClass) {
        return convertToBeanList(recordSet, targetClass, false);
    }

    public static List convertToBeanList(RecordSet recordSet, Class targetClass, boolean forceConvertNumeric) {
        List convertedList = new ArrayList();
        Record record;

        for (Iterator iterator = recordSet.iterator(); iterator.hasNext(); convertedList.add(convertToBean(record, targetClass, forceConvertNumeric)))
            record = (Record) iterator.next();

        return convertedList;
    }
}

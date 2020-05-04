package com.mrlao.mvb.util;

import com.mrlao.mvb.annotation.ResourceID;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("all")
public class ResourceIDHelper {
    private static final HashMap<String, Integer> idmap = new HashMap<>();

    public static void save(String key, int id) {
        idmap.put(key, id);
    }

    public static Integer get(String key) {
        return idmap.get(key);
    }

    public static void clear() {
        idmap.clear();
    }

    public static boolean hadInit(Class cls) {
        return idmap.containsKey(cls.toString());
    }

    public static void parseResourceID(Object object) {
        if (hadInit(object.getClass())) {
            return;
        }
        ArrayList<ResourceID> resourceIDList = new ArrayList<>();
        ArrayList<Field> fieldList = new ArrayList<>();
        AnnotationUtil.parseFiledAnnotation(ResourceID.class, object.getClass(), resourceIDList, fieldList);
        if (fieldList.isEmpty()) {
            return;
        }
        for (int i = 0; i < resourceIDList.size(); i++) {
            Integer id = ClassUtil.getFieldValue(fieldList.get(i), object);
            idmap.put(resourceIDList.get(i).id(), id);
        }
        idmap.put(object.getClass().toString(), -1);
    }

    public static int[] ids(String[] idsText) {
        int[] ids = new int[idsText.length];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = get(idsText[i]);
        }
        return ids;
    }
}

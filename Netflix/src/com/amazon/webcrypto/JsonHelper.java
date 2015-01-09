// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.amazon.webcrypto;

import java.util.*;
import org.json.*;

public class JsonHelper {

    public JsonHelper() {
    }

    private static Object fromJson(Object obj) throws JSONException {
        if (obj == JSONObject.NULL) {
            obj = null;
        } else {
            if (obj instanceof JSONObject)
                return toMap((JSONObject) obj);
            if (obj instanceof JSONArray)
                return toList((JSONArray) obj);
        }
        return obj;
    }

    public static Map getMap(JSONObject jsonobject, String s)
            throws JSONException {
        return toMap(jsonobject.getJSONObject(s));
    }

    public static boolean isEmptyObject(JSONObject jsonobject) {
        return jsonobject.names() == null;
    }

    public static Object toJSON(Object obj) throws JSONException {
        Object obj1;
        if (obj instanceof Map) {
            obj1 = new JSONObject();
            Map map = (Map) obj;
            Object obj2;
            for (Iterator iterator = map.keySet().iterator(); iterator
                    .hasNext(); ((JSONObject) (obj1)).put(obj2.toString(),
                    toJSON(map.get(obj2))))
                obj2 = iterator.next();

        } else if (obj instanceof Iterable) {
            obj1 = new JSONArray();
            for (Iterator iterator1 = ((Iterable) obj).iterator(); iterator1
                    .hasNext(); ((JSONArray) (obj1)).put(iterator1.next()))
                ;
        } else {
            obj1 = obj;
        }
        return obj1;
    }

    public static List toList(JSONArray jsonarray) throws JSONException {
        ArrayList arraylist = new ArrayList();
        for (int i = 0; i < jsonarray.length(); i++)
            arraylist.add(fromJson(jsonarray.get(i)));

        return arraylist;
    }

    public static Map toMap(JSONObject jsonobject) throws JSONException {
        HashMap hashmap = new HashMap();
        String s;
        for (Iterator iterator = jsonobject.keys(); iterator.hasNext(); hashmap
                .put(s, fromJson(jsonobject.get(s))))
            s = (String) iterator.next();

        return hashmap;
    }
}

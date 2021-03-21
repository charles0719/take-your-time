package com.charles.leeksmock;

import com.google.gson.Gson;
import java.lang.reflect.Type;

/**
 * @author charles
 * @date 2021/3/21 14:29
 */
public class BeanUtils<T> {
    /*
     * Json转换泛型
     */
    public static <T> T Convert(String jsonString, Type cls) {
        T t = null;
        try {
            if (jsonString != null && !jsonString.equals("")) {
                Gson gson = new Gson();
                t = gson.fromJson(jsonString, cls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}

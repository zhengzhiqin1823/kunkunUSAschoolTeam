package com.serverlet;

import com.mapper.fragmentMapper;
import com.test.pojo.fragment;

public class StaticMethods {

    public static String getTextByFirstFm(String firstFm, fragmentMapper mapper) {
        StringBuilder text = new StringBuilder();
        fragment f1 = mapper.selectByKey(firstFm).get(0);
        text.append(f1.getData());
        while (!f1.getNext().equals("")) {
            f1 = mapper.selectByKey(f1.getNext()).get(0);
            text.append(f1.getData());
        }
        return text.toString();
    }

    public static void deleteFmByFirstFm(String firstFm, fragmentMapper mapper)
    {
        fragment f1 = mapper.selectByKey(firstFm).get(0);
        while (!f1.getNext().equals(""))
        {
            String id = f1.getNext();
            mapper.deleteByKey(f1.getFmid());
            f1 = mapper.selectByKey(id).get(0);
        }
        mapper.deleteByKey(f1.getFmid());
    }

}

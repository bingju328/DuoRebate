package com.network;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        test();
    }
    public void test() {
        String s = "cn.wangxiao.zhuntiku://zdwx?type=0&typeAction=2&param={\"ParamIsRight\":true,\"" +
                "Type\":0,\"TypeAction\":2,\"Data\":{\"ID\":\"\",\"SectionID\":\"\",\"ChapterID\":\"\"" +
                ",\"TagID\":\"\",\"ProductId\":\"\",\"url\":\"http://wap2.wangxiao.cn/Course/Products" +
                "Detail?ProductsId=25d5df00-e9eb-4d89-9226-97698d985d24&ClassId=&PackageTypeId=&Subje" +
                "ctType=&username=&key=&SysClassId=\",\"Title\":\"\",\"SubjectId\":\"\",\"SysClassName\":\"\"}}";

//        String sss = "param=\\{.{0,100000}}";
        String sss = "param=\\{.+}";
        Pattern pattern = Pattern.compile(sss);
        Matcher matcher = pattern.matcher( s );
        matcher.find();
        String group = matcher.group();
        System.out.println("result = " + group);

    }
}
package com.aigestudio.wheelpicker.util;



import com.aigestudio.wheelpicker.model.City;
import com.aigestudio.wheelpicker.model.Districts;
import com.aigestudio.wheelpicker.model.Province;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Administrator on 2017/2/16.
 * 省的xml数据解析工具类
 */

public class RegionService {
    /**
     * 解析省的xml数据
     * @param instream
     * @return
     * @throws Exception
     */
    public static List<Province> getProvinces (InputStream instream) throws Exception{
        List<Province> persons = new ArrayList<Province>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//创建DOM解析工厂
        DocumentBuilder dombuild = factory.newDocumentBuilder();//创建DON解析器
        Document dom = dombuild.parse(instream);//开始解析XML文档并且得到整个文档的对象模型
        Element root= dom.getDocumentElement();//得到根节点<persons>
        NodeList personList = root.getElementsByTagName("Province");//得到根节点下所有标签为<person>的子节点
        for(int i = 0;i<personList.getLength();i++){//遍历person节点
            Province person = new Province();//首先创建一个Person
            Element personElement = (Element) personList.item(i);//得到本次Person元素节点
            person.setid(personElement.getAttribute("ID"));//得到Person节点中的ID
            person.setprovinceName(personElement.getAttribute("ProvinceName"));//得到Person节点中的ProvinceName
            NodeList personChilds = personElement.getChildNodes();//得到Person节点下的所有子节点
//            for(int j=0;j<personChilds.getLength();j++){//遍历person节点下的所有子节点
//                if(personChilds.item(j).getNodeType()== Node.ELEMENT_NODE){//如果是元素节点的话
//                    Element childElement  = (Element) personChilds.item(j); //得到该元素节点
//                    if("name".equals(childElement.getNodeName())){//如果该元素节点是name节点
//                        person.setName(childElement.getFirstChild().getNodeValue());//得到name节点下的第一个文本子节点的值
//                    }else if("age".equals(childElement.getNodeName())){//如果该元素节点是age节点、
//                        person.setAge(new Short(childElement.getFirstChild().getNodeValue()));//得到age节点下的第一个文本字节点的值
//                    }
//                }
//            }
            persons.add(person);//遍历完person下的所有子节点后将person元素加入到集合中去
        }
        return persons;
    }
    /**
     * 解析市的xml数据
     * @param instream
     * @return
     * @throws Exception
     */
    public static List<City> getCities (InputStream instream) throws Exception{
        List<City> persons = new ArrayList<City>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//创建DOM解析工厂
        DocumentBuilder dombuild = factory.newDocumentBuilder();//创建DON解析器
        Document dom = dombuild.parse(instream);//开始解析XML文档并且得到整个文档的对象模型
        Element root= dom.getDocumentElement();//得到根节点<persons>
        NodeList personList = root.getElementsByTagName("City");//得到根节点下所有标签为<City>的子节点
        for(int i = 0;i<personList.getLength();i++){//遍历person节点
            City city = new City();//首先创建一个Person
            Element personElement = (Element) personList.item(i);//得到本次Person元素节点
            city.setID(personElement.getAttribute("ID"));//得到Person节点中的ID
            city.setCityName(personElement.getAttribute("CityName"));//得到Person节点中的ID
            city.setPID(personElement.getAttribute("PID"));//得到Person节点中的ID
            persons.add(city);//遍历完person下的所有子节点后将person元素加入到集合中去
        }
        return persons;
    }
    /**
     * 解析地区的xml数据
     * @param instream
     * @return
     * @throws Exception
     */
    public static List<Districts> getdistricts (InputStream instream) throws Exception{
        List<Districts> persons = new ArrayList<Districts>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//创建DOM解析工厂
        DocumentBuilder dombuild = factory.newDocumentBuilder();//创建DON解析器
        Document dom = dombuild.parse(instream);//开始解析XML文档并且得到整个文档的对象模型
        Element root= dom.getDocumentElement();//得到根节点<persons>
        NodeList personList = root.getElementsByTagName("District");//得到根节点下所有标签为<City>的子节点
        for(int i = 0;i<personList.getLength();i++){//遍历person节点
            Districts districts = new Districts();//首先创建一个Person
            Element personElement = (Element) personList.item(i);//得到本次Person元素节点
            districts.setID(personElement.getAttribute("ID"));//得到Person节点中的ID
            districts.setDistrictName(personElement.getAttribute("DistrictName"));//得到Person节点中的ID
            districts.setCID(personElement.getAttribute("CID"));//得到Person节点中的ID
            persons.add(districts);//遍历完person下的所有子节点后将person元素加入到集合中去
        }
        return persons;
    }
}

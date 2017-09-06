package com.bussniess.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dictionary {

	private static Dictionary instance;

	// el ${application.javabean.fieldname}
	// ognl %{#application.map1.key } %{#application.dict.itemMap.male}
	// struts2 ui标签 <s:select list="map">

	// map< groupkey , map< itemkey , itemvalue>>
	private Map<String, Map<String, String>> dictMap;// 包含所有的字典数据,并且这些字典数据都是分组的

	// map<groupkey , groupvalue>
	private Map<String, String> groupMap;// 值存储和group相关的数据,只包含group元素的key和value属性的值

	// map< itemkey , itemvalue>
	private Map<String, String> itemMap;// 存储所有的item的key和value

	private Dictionary() {
	}

	public static synchronized Dictionary getInstance() {
		if (instance == null) {
			instance = new Dictionary();
			instance.init();
		}
		return instance;
	}

	private void init() {

		Map<String, Map<String, String>> dictMap2 = new LinkedHashMap<String, Map<String, String>>();
		Map<String, String> groupMap2 = new LinkedHashMap<String, String>();
		Map<String, String> itemMap2 = new LinkedHashMap<String, String>();

		String filepath = Dictionary.class.getResource("/dictionary.xml").getFile();
		SAXReader reader = new SAXReader();

		try {
			Document document = reader.read(filepath);

			Element rootElement = document.getRootElement();

			List<Element> groupElementList = rootElement.elements();

			for (Element groupElement : groupElementList) {
				String groupKey = groupElement.attributeValue("key");
				String groupValue = groupElement.attributeValue("value");

				// 进行数据有效性检查
				if (groupKey == null || groupKey.trim().length() == 0) {
					throw new RuntimeException("数据字典不能有无效数据");
				}
				if (groupValue == null || groupValue.trim().length() == 0) {
					throw new RuntimeException("数据字典不能有无效数据");
				}

				groupMap2.put(groupKey, groupValue);

				// 取出一个group下的所有item元素
				List<Element> itemElementList = groupElement.elements();
				Map<String, String> tempMap = new LinkedHashMap<String, String>();
				for (Element itemElement : itemElementList) {
					String itemKey = itemElement.attributeValue("key");
					String itemValue = itemElement.attributeValue("value");

					// 进行数据有效性检查
					if (itemKey == null || itemKey.trim().length() == 0) {
						throw new RuntimeException("数据字典不能有无效数据");
					}
					if (itemValue == null || itemValue.trim().length() == 0) {
						throw new RuntimeException("数据字典不能有无效数据");
					}
					tempMap.put(itemKey, itemValue);

					itemMap2.put(itemKey, itemValue);
				}

				dictMap2.put(groupKey, tempMap);
			}

		} catch (DocumentException e) {
			throw new RuntimeException("数据字典初始化失败:" + e);
		}

		dictMap = dictMap2;
		itemMap = itemMap2;
		groupMap = groupMap2;

	}

	// 重新加载
	public static synchronized void reload() {
		if (instance == null) {
			getInstance();
		} else {
			instance.init();
		}
	}

	public Map<String, Map<String, String>> getDictMap() {
		return dictMap;
	}

	public void setDictMap(Map<String, Map<String, String>> dictMap) {
		this.dictMap = dictMap;
	}

	public Map<String, String> getGroupMap() {
		return groupMap;
	}

	public void setGroupMap(Map<String, String> groupMap) {
		this.groupMap = groupMap;
	}

	public Map<String, String> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<String, String> itemMap) {
		this.itemMap = itemMap;
	}

}

/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.ame.cache;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Cache {

	/**
	 * 获取缓存的名字
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 根据key批量获取缓存中的记录
	 * @param keys
	 * @return
	 */
	List<Optional<Object>> list(Set<Object> keys);

	/**
	 * 根据key获取缓存中的记录
	 * 
	 * @param key
	 * @return
	 */
	Optional<Object> get(Object key);

	/**
	 * 返回对应类型的缓存记录
	 * 
	 * @param key
	 * @param type
	 * @param <T>
	 * @return
	 */
	<T> Optional<T> get(Object key, Class<T> type);

	/**
	 * 添加一个缓存记录
	 * 
	 * @param key
	 * @param value      原则上不支持list或者map类型，但是如果要缓存集合对象，缓存内部实现不会分析集合内部的引用关系，需要外部程序自己控制。
	 * @param aliasNames 缓存记录key的别名
	 */
	void put(Object key, Object value, String... aliasNames);

	/**
	 * 先移除记录，再更新缓存
	 * 
	 * @param key
	 * @param value     原则上不支持list或者map类型，但是如果要缓存集合对象，缓存内部实现不会分析集合内部的引用关系，需要外部程序自己控制。
	 * @param aliasNames
	 */
	void evictAndPut(Object key, Object value, String... aliasNames);

	/**
	 * 移除缓存记录
	 * 
	 * @param key
	 */
	void evict(Object key);

	/**
	 * 清空缓存
	 */
	void clear();

    void setCacheManager(CacheManager cacheManager);

}

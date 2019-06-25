/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  League.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津市勘察院数据的存储、共享、处理等   
 * @author: 天勘岩土工程信息化项目组    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津市勘察院传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loris.common.bean.Entity;
import com.loris.common.filter.Filter;
import com.loris.common.util.ArraysUtil;
import com.loris.common.util.ReflectUtil;

/**
 * @ClassName: League
 * @Description: 基本的SQL操作类，主要用于批量进行数据插入和更新
 * @author: 天勘岩土工程信息化项目组
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tigis.com.cn Inc. All rights reserved.
 *             注意：本内容仅限于天津市勘察院内部传阅，禁止外泄以及用于其他的商业目
 */
@Component
public class SqlHelper
{
	private static Logger logger = Logger.getLogger(SqlHelper.class);

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	/** 批量数据 */
	protected int batchSize = 1000;

	/**
	 * 批量更新实体数据
	 * 
	 * @param entities
	 *            实体列表
	 * @return 是否更新成功的标志
	 * @throws SQLException
	 */
	public <T extends Entity> boolean updateBatch(List<T> entities, Class<T> clazz) throws SQLException
	{
		TableName tableName = clazz.getAnnotation(TableName.class);
		if (tableName == null)
		{
			throw new SQLException("The " + clazz.getName() + " has no table name defined.");
		}

		List<Field> fields = new ArrayList<>();
		getEntitySQLFields(clazz, fields);
		if (fields.size() <= 0)
		{
			throw new SQLException("The " + clazz.getName() + " has no field defined.");
		}

		Field idField = getAndRemoveIdField(fields);
		if (idField == null)
		{
			throw new SQLException("The " + clazz.getName() + " has no id field defined.");
		}

		int size = fields.size();
		if (size <= 0)
		{
			throw new SQLException("The " + clazz.getName() + " has no field defined.");
		}

		String sql = createUpdateSQL(tableName, fields, idField);
		// logger.info(sql);

		Connection connection = null;
		PreparedStatement ps = null;
		boolean autoCommit = false;

		try
		{
			connection = jdbcTemplate.getDataSource().getConnection();
			autoCommit = connection.getAutoCommit();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(sql);

			int i = 0;
			for (Entity entity : entities)
			{
				try
				{
					Object idValue = getFieldValue(idField, entity);
					//关键值为空时，数据不能进行更新
					if(idValue == null)
					{
						continue;
					}
					if (setFieldsValue(entity, fields, ps))
					{
						// 要设置Key Field值
						//setFieldValue(size + 1, idField, entity, ps);
						ps.setObject(size + 1, idValue);
						ps.addBatch();
						i++;	
					}				
				}
				catch (IllegalAccessException e)
				{
				}
				if (i % batchSize == 0)
				{
					ps.executeBatch();
					connection.commit();
				}
			}

			if (i % batchSize != 0)
			{
				ps.executeBatch();
				connection.commit();
			}
			return true;
		}
		catch (SQLException e)
		{
			// e.printStackTrace();
			logger.warn(e.toString());
			try
			{
				if (connection != null)
				{
					connection.rollback();
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			throw e;
		}
		finally
		{
			if (ps != null)
			{
				ps.close();
			}
			if (connection != null)
			{
				if (autoCommit)
					connection.setAutoCommit(autoCommit);
				connection.close();
			}
		}
	}

	/**
	 * 批量插入数据
	 * 
	 * @param entities
	 * @return 是否成功的标志
	 */
	public <T extends Entity> boolean insertBatch(List<T> entities, Class<T> clazz) throws SQLException
	{
		TableName tableName = clazz.getAnnotation(TableName.class);
		if (tableName == null)
		{
			throw new SQLException("The " + clazz.getName() + " has no table name defined.");
		}

		List<Field> fields = new ArrayList<>();
		getEntitySQLFields(clazz, fields);
		if (fields.size() <= 0)
		{
			throw new SQLException("The " + clazz.getName() + " has no field defined.");
		}

		String sql = createInsertSQL(tableName, fields);
		//logger.info(sql);

		Connection connection = null;
		PreparedStatement ps = null;
		boolean autoCommit = false;

		try
		{
			connection = jdbcTemplate.getDataSource().getConnection();
			autoCommit = connection.getAutoCommit();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(sql);

			int i = 0;
			for (Entity entity : entities)
			{
				try
				{
					if (setFieldsValue(entity, fields, ps))
					{
						ps.addBatch();
						i++;
					}
				}
				catch (IllegalAccessException e)
				{
				}
				if (i % batchSize == 0)
				{
					ps.executeBatch();
					connection.commit();
				}
			}

			if (i % batchSize != 0)
			{
				ps.executeBatch();
				connection.commit();
			}
			return true;
		}
		catch (SQLException e)
		{
			// e.printStackTrace();
			logger.warn(e.toString());
			try
			{
				if (connection != null)
				{
					connection.rollback();
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			throw e;
		}
		finally
		{
			if (ps != null)
			{
				ps.close();
			}
			if (connection != null)
			{
				if (autoCommit)
					connection.setAutoCommit(autoCommit);
				connection.close();
			}
		}
	}

	/**
	 * 设置数据更新时的各字段的值
	 * @param entity 实体
	 * @param fields 字段列表
	 * @param ps 数据状态
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	protected boolean setFieldsValue(Entity entity, List<Field> fields, PreparedStatement ps)
			throws IllegalAccessException, SQLException
	{
		int index = 1;

		for (Field field : fields)
		{
			setFieldValue(index, field, entity, ps);
			index++;
		}
		return true;
	}

	/**
	 * 设置Field的值数据
	 * 
	 * @param index
	 *            序号
	 * @param field
	 *            Field域
	 * @param entity
	 *            实体数据
	 * @param ps
	 *            存储状态
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	protected void setFieldValue(int index, Field field, Entity entity, PreparedStatement ps)
			throws IllegalAccessException, SQLException
	{
		String typeName = field.getType().getName();
		switch (typeName)
		{
		case "java.lang.Boolean":
		case "boolean":
			Boolean b = getFieldValue(field, entity);
			if(b == null) ps.setNull(index, Types.BOOLEAN);
			else ps.setBoolean(index, b);
			break;
		case "bytes":
		case "[B":
			byte[] bytes = (byte[]) field.get(entity);
			ps.setBytes(index, bytes);
			break;
		case "java.lang.Byte":
		case "byte":
			Byte b2 = getFieldValue(field, entity);
			if(b2 == null) ps.setNull(index, Types.BINARY);
			else ps.setByte(index, b2);
			break;
		case "java.lang.Short":
		case "short":
			Short readShort = getFieldValue(field, entity);
			if(readShort == null) ps.setNull(index,  Types.SMALLINT);
			else ps.setShort(index, readShort);
			break;
		case "java.lang.Integer":
		case "int":
			Integer readInt = getFieldValue(field, entity);
			if(readInt == null) ps.setNull(index, Types.INTEGER);
			else ps.setInt(index, readInt);
			break;
		case "java.lang.Long":
		case "long":
			Long readLong = getFieldValue(field, entity);
			if(readLong == null) ps.setNull(index, Types.BIGINT);
			else ps.setLong(index, readLong);
			break;
		case "java.lang.Float":
		case "float":
			Float readFloat = getFieldValue(field, entity);
			if(readFloat == null) ps.setNull(index, Types.FLOAT);
			else ps.setFloat(index, readFloat);
			break;
		case "java.lang.Double":
		case "double":
			Double readDouble = getFieldValue(field, entity);
			if(readDouble == null) ps.setNull(index, Types.DOUBLE);
			else ps.setDouble(index, readDouble);
			break;
		case "java.lang.String":
			String object = getFieldValue(field, entity);
			if (object == null) ps.setNull(index, Types.VARCHAR);
			else ps.setString(index, object);
			break;
		case "java.util.Date":
			Date date = getFieldValue(field, entity);
			if (date == null) ps.setNull(index, Types.TIMESTAMP);
			else ps.setTimestamp(index, new Timestamp(date.getTime()));
			break;
		case "java.lang.Character":
		case "char":
			break;
		default:
			Class<?> clazz = field.getType();
			if(clazz.isEnum())
			{
				Enum<?> enumObj = getFieldValue(field, entity);
				if(enumObj == null) ps.setString(index, null);
				else ps.setString(index, enumObj.name());;
				break;
			}			
			throw new RuntimeException("Field: " + field.getName() + " [" + typeName + "] 不支持,出现BUG,请联系管理员。");
		}
	}

	/**
	 * 创建与拼接更新SQL语句
	 * 
	 * @param tableName
	 * @param fields
	 * @param idField
	 * @return
	 */
	public static String createUpdateSQL(TableName tableName, List<Field> fields, Field idField)
	{
		String sql = "update " + tableName.value() + " set ";

		int fieldIndex = 0;
		for (Field field : fields)
		{
			fieldIndex++;
			sql += field.getName() + "=?";

			if (fieldIndex != fields.size())
			{
				sql += ", ";
			}
		}
		sql += " where " + idField.getName() + "=?";
		return sql;
	}

	/**
	 * 创建拼接插入数据的SQL语句
	 * 
	 * @param tableName
	 * @param fields
	 * @return
	 */
	public static String createInsertSQL(TableName tableName, List<Field> fields)
	{
		String sql = "insert into " + tableName.value() + " (";
		String values = " values(";

		int fieldIndex = 0;
		for (Field field : fields)
		{
			fieldIndex++;
			sql += field.getName();
			values += "?";

			if (fieldIndex != fields.size())
			{
				sql += ", ";
				values += ", ";
			}
		}
		sql += ") " + values + ")";
		return sql;
	}

	/**
	 * 获得数据字段的内容
	 * 
	 * @param field
	 *            Field
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Field field, Entity entity)
	{
		try
		{
			return (T) field.get(entity);
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * 设置属性的值
	 * 
	 * @param field
	 *            属性值域
	 * @param entity
	 *            实体类型
	 * @param value
	 */
	public static <T> void setFieldValue(Field field, Entity entity, Object value)
	{
		try
		{
			field.set(entity, value);
		}
		catch (Exception e)
		{
		}
	}

	/**
	 * 获得实体所有的字段
	 * 
	 * @param clazz
	 * @param fields
	 */
	public static void getEntitySQLFields(Class<? extends Entity> clazz, List<Field> fields)
	{
		List<Field> allFields = ReflectUtil.getAllFields(clazz, false);
		for (Field field : allFields)
		{
			if (Modifier.isStatic(field.getModifiers()))
			{
				continue;
			}
			TableField tableField = field.getAnnotation(TableField.class);
			if (tableField == null || tableField.exist())
			{
				field.setAccessible(true);
				fields.add(field);
			}
		}
	}

	/**
	 * 获得ID值的域名
	 * 
	 * @param clazz
	 * @return
	 */
	public static Field getEntityIdField(Class<? extends Entity> clazz)
	{
		List<Field> fields = new ArrayList<>();
		getEntitySQLFields(clazz, fields);
		return getIdField(fields, false);
	}

	/**
	 * 获得并删除唯一ID值的域
	 * 
	 * @param fields
	 *            列表
	 * @return 域
	 */
	public static Field getAndRemoveIdField(List<Field> fields)
	{
		return getIdField(fields, true);
	}

	/**
	 * 获得ID Field域
	 * 
	 * @param fields
	 *            域的列表
	 * @param removed
	 *            是否从列表中删除
	 * @return
	 */
	public static Field getIdField(List<Field> fields, boolean removed)
	{
		int size = fields.size();
		for (int i = size - 1; i >= 0; i--)
		{
			Field field = fields.get(i);
			TableId tableId = field.getAnnotation(TableId.class);
			if (tableId != null)
			{
				if (removed)
					fields.remove(i);
				return field;
			}
		}
		return null;
	}

	/**
	 * 插入数据列表
	 * 
	 * @param values
	 *            列表值
	 * @param clazz
	 *            数据类型
	 * @param mapper
	 *            DAO数据接口
	 * @param filter
	 *            过滤器，比较两个数据是否一样
	 * @param query
	 *            数据查询过滤器
	 * @param helper
	 *            数据服务接口：这里是快速数据更新和服务接口
	 * @param overwrite
	 *            是事更新数据
	 * @param checkExist
	 * @return 是否成功的标志
	 */
	public static <T extends Entity> boolean insertList(List<T> values, Class<T> clazz, BaseMapper<T> mapper,
			Filter<T> filter, QueryWrapper<T> query, SqlHelper helper, boolean checkExist, boolean overwrite)
	{
		if (values == null || values.size() == 0)
		{
			logger.warn("Warn: No " + clazz.getName() + " need to be updated.");
			return false;
		}

		Field idField = getEntityIdField(clazz);
		if (idField == null)
		{
			logger.warn("Warn: No " + clazz.getName() + " id field has been set, can't overwrite.");
		}

		List<T> newList = values;		
		if(checkExist)
		{
			//处理数据重叠的问题
			List<T> existList = mapper.selectList(query);
			List<T> updateList = new ArrayList<>();
			newList = new ArrayList<>();
			
			for (T t : values)
			{
				filter.setValue(t);
				T existObj = ArraysUtil.getSameObject(existList, filter);
				existList.remove(existObj);
	
				if (existObj == null)
				{
					newList.add(t);
				}
				else
				{
					if (overwrite && idField != null)
					{
						String obj = getFieldValue(idField, existObj);
						BeanUtils.copyProperties(t, existObj);
						setFieldValue(idField, existObj, obj);
						updateList.add(existObj);
					}
				}
			}
	
			if (updateList.size() > 0)
			{
				try
				{
					helper.updateBatch(updateList, clazz);
				}
				catch (Exception e)
				{
					logger.warn("Error occured when update " + clazz.getName() + "[" + updateList.size() + "]: "
							+ e.toString());
				}
			}
			if (newList.size() == 0)
			{
				logger.info("Warn: No " + clazz.getName() + " need to be updated.");
				return true;
			}
		}		
		try
		{
			return helper.insertBatch(newList, clazz);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 插入数据列表
	 * @param values 数据列表
	 * @param clazz 数据类型
	 * @param mapper 数据接口
	 * @param filter 数据过滤器
	 * @param key 关键字
	 * @param helper 数据接口
	 * @param checkExist 是否检查存在的数据
	 * @param overwrite 是否用新数据覆盖
	 * @return
	 */
	public static <T extends Entity> boolean insertList(List<T> values, Class<T> clazz, BaseMapper<T> mapper,
			Filter<T> filter, String key, SqlHelper helper, boolean checkExist, boolean overwrite)
	{
		if (values == null || values.size() == 0)
		{
			logger.warn("Warn: No " + clazz.getName() + " need to be updated.");
			return false;
		}
		List<String> tids = ArraysUtil.getObjectFieldValue(values, clazz, key);
		QueryWrapper<T> queryWrapper = new QueryWrapper<T>().in(key, tids);
		return insertList(values, clazz, mapper, filter, queryWrapper, helper, checkExist, overwrite);
	}
	
	/**
	 * 插入数据列表
	 * @param values 数据列表
	 * @param clazz 数据类型
	 * @param mapper 数据接口
	 * @param filter 数据过滤器
	 * @param key 关键字
	 * @param helper 数据接口
	 * @param overwrite 是否用新数据覆盖
	 * @return
	 */
	public static <T extends Entity> boolean insertList(List<T> values, Class<T> clazz, BaseMapper<T> mapper,
			Filter<T> filter, String key, SqlHelper helper, boolean overwrite)
	{
		return insertList(values, clazz, mapper, filter, key, helper, true, overwrite);
	}
	
	/**
	 * 更新数据列表
	 * @param values 数据列表
	 * @param clazz 数据类型
	 * @param helper 数据接口
	 * @return
	 */
	public static <T extends Entity> boolean updateList(List<T> values, Class<T> clazz, SqlHelper helper)
	{
		try
		{
			return helper.updateBatch(values, clazz);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public int getBatchSize()
	{
		return batchSize;
	}

	public void setBatchSize(int batchSize)
	{
		this.batchSize = batchSize;
	}
}

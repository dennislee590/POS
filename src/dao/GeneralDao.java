package dao;

import java.util.List;
import java.util.Map;

public interface GeneralDao {

		//create
		void add(Object obj);
		//read
		<T> List<T> queryByParameters(String tableName, Map<String, String> parameters, Class<T> clazz);
		//update
		void update(Object obj);
		//delete
		void delete(Object id, Class<?> clazz);

}

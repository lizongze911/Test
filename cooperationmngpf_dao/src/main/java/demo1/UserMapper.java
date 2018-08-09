package demo1;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {
	List getAll();
	
	List getPage();
	
	User getOne(Integer id);
	
	void insert(User user);
	
	void update(User user);

	void delete(User user);
}

package com.food.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.food.model.Food;


@Service @Transactional//transactional是关于数据库的内容，不用了解    Dao作为一个bean注入到action里
public class FoodDao {
	@Resource SessionFactory factory;//如果不写这个  就得写get set方法    ，  spring配置文件里还得写一个bean???????????
	
	
	 /*添加Food信息*/
    public void addFood(Food food) throws Exception {
    	Session s = factory.getCurrentSession();//获取数据源  id=sessionfactory---database做相关数据库的操作
    	s.save(food);//传过来的是对象，也就是说可以把整个对象传过来
    }
    
    
    /*删除Food信息*/
    public void deleteFood (int foodid) throws Exception {//通过id删除的，这个id（主键）不用我们定义，也不需要我们修改
        Session s = factory.getCurrentSession(); 
        Object food = s.load(Food.class, foodid);//food对应的类是Object,根据food类型查的它，得到的也是food类型  通过foodid查找
        //s.load的返回类型是Object，所以前面写Object  当然也可以强制转换：Food food =(Food)s.load(Food.class, foodid);
        //其实这里应该抛出异常，如果找不到就会报错，所以要加一个抛出异常的判断
        s.delete(food);//删除的是一条记录
    }
    
    
    /*更新Food信息*/
    public void updateFood(Food food) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(food);//不可以动主键啊
    }
    
    
    /*根据主键获取对象*/
    public Food getFoodById(int foodid) {
        Session s = factory.getCurrentSession();//这步查找的方法步骤和删除的方法一样
        Food food = (Food)s.get(Food.class, foodid);//这步查找的方法步骤和删除的方法一样
        return food;
    }
    
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Food> getAllFoods() {
        Session s = factory.getCurrentSession();
        String hql = "From Food";//等同于sql当中的select*from food
        Query q = s.createQuery(hql);//执行查询语句
        List foodList = q.list();//把查询的结果放到list，list当中的每一个元素都是food对象
        return (ArrayList<Food>) foodList;
    }
    
    /*用关键词查找*/
    public ArrayList<Food> QueryFoodInfo(String foodName) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Food food where 1=1";//如果不写where 1 =1，如果有空就会报错
    	if(!foodName.equals("")) hql = hql + " and food.foodname like '%" + foodName + "%'";
    	// !foodName.equals("")指的是foodName不为空        这个小写的food是上一句话的food  也就是Food；foodname是表当中的字段，又是food里面foodname的属性
    	/*select*from food as f   where f.foodname like "%foodName%"  %是通配符，就是传的参数foodName前面后面都可以有别的字
    	也就是说只要包含了foodName的字样，就可以查出来
    	*/
    	Query q = s.createQuery(hql);
    	List foodList = q.list();
    	return (ArrayList<Food>) foodList;
    }
}

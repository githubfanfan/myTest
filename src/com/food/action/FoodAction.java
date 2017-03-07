package com.food.action;
/*import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import javax.annotation.Resource;
*/


import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.food.dao.FoodDao;
import com.food.model.Customer;
import com.food.model.Food;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller @Scope("prototype")//controller如果后面继续写(value f)的话，struts配置文件里应该写：class=f；prototype
public class FoodAction extends ActionSupport{//extends ActionSupport：sturts2给我们提供的支持，比如拦截器啥的
	//FoodAction  注入到 <action name="food_*" class="foodAction" method="{1}">中的class名字要变成小写
	@Resource FoodDao foodDao;
	 
	 private Food food;//这是从页面传值的变量，在action里需要getset方法
     private List<Food> foodList;//?????这个list是从页面传来的？？？？
     private String keyWords;
     
     
     
	 public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public List<Food> getFoodList() {
		return foodList;
	}

	public void setFoodList(List<Food> foodList) {
		this.foodList = foodList;
	}

	public Food getFood() {
	   return food;
	 }

     public void setFood(Food food) {
		this.food = food;
	 }



 	private Customer customer;
 	
 	public Customer getCustomer() {
 		return customer;
 	}

 	public void setCustomer(Customer customer) {
 		this.customer = customer;
 	}

 	
	 /*添加food信息*/
		public String addFood() throws Exception{//返回值都是String,action的方法除了调用方法都是返回String类型的
	       foodDao.addFood(food);
	      return "message"; 
	    }
        
		/*!!!!!!!!显示所有food*/
        public String showFood()throws Exception{
        	foodList = foodDao.getAllFoods();
        	return "show_view";
        }
        
        /*显示某一food详尽信息*/
        public String showDetail()throws Exception{
        	food = foodDao.getFoodById(food.getFoodid());//通过id查询得到的food是一个完整的对象
        	return "detail_view";
        }
        
        /*显示food的修改项*/
        public String showEdit()throws Exception{
        	food = foodDao.getFoodById(food.getFoodid());
        	return "edit_view";
        }
        
        /*编辑food*/
        public String editFood()throws Exception{
        	foodDao.updateFood(food);
        	return "edit_message";
        }
        
        /*删除food*/
        public String deleteFood()throws Exception{
        	foodDao.deleteFood(food.getFoodid());
        	return "delete_message";
        }
        
        /*查询food*/
        public String queryFoods()throws Exception{
        	foodList = foodDao.QueryFoodInfo(keyWords);
        	return "show_view";
        }

}

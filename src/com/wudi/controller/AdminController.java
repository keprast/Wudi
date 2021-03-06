package com.wudi.controller;

import java.math.BigDecimal;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wudi.model.NavsModel;
import com.wudi.model.StudentModel;
import com.wudi.model.admin.BuildingModel;
import com.wudi.model.admin.DormitoryModel;
/**
 * 
* @ClassName: AdminController
* @Description: TODO 后台管理页面跳转管理类
* @author xiao
* @date 2018年10月29日下午4:08:09
*
 */
public class AdminController extends Controller{

	/**
	 * 
	* @Title: index
	* @Description:后台管理默认到达页面
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void index() {
		render("index.html");
	}
	/**
	 * 
	* @Title: getnavs
	* @Description: 获取主页面左侧菜单数据
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void getnavs() {
		Object object=NavsModel.getListForLeft();
		renderJson(object);
	}
	/**
	 * 显示菜单列表
	 */
	public void navsinfo() {
		render("sys/navsinfo.html");
	}
	/**
	 * 
	* @Title: getnavs
	* @Description: 获取侧菜单数据列表
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void getNavsList() {
		//获取页面查询的关键字
		String key=getPara("key");
		Page<NavsModel> list=NavsModel.getList(1,100,key);
		setAttr("infos", list);
		renderJson();
	}
	/**
	 * 打开菜单添加页面
	 */
	public void openNavsAdd() {
		render("sys/navsAdd.html");
	}
	/**
	 * 添加保存菜单信息
	 */
	public void saveNavs() {
		String title=getPara("title");
		String href=getPara("href");
		String icon=getPara("icon");
		String fid=getPara("fid");
		boolean result=NavsModel.saveModel(title, href, icon, fid);
		setAttr("result", result);
		renderJson();
		
	}
	/**
	 * 打开菜单修改页面
	 */
	public void openNavsEdit() {
		//接收页面数据
		String id=getPara("id");
		setAttr("id", id);
		renderFreeMarker("sys/navsEdit.html");
	}
	/**
	 * 更新保存菜单信息
	 */
	public void updatenavs() {
		String id=getPara("id");
		String title=getPara("title");
		String href=getPara("href");
		String icon=getPara("icon");
		String fid=getPara("fid");
		boolean result=NavsModel.updateModel(id,title, href, icon, fid);
		setAttr("result", result);
		renderJson();
		
	}
	/**
	* @Title: students
	* @Description: 打开学生信息列表页面
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void students() {
		render("stu/studentinfo.html");
	}
	
	/**
	* @Title: queryStudents
	* @Description: 获取学生信息列表信息（查询），在这里，我们是用异步加载方式，
	* 就是说，页面先打开了，然后在用js向后台获取数据，这个就是。
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void queryStudents() {
		//获取页面查询的关键字
		String key=getPara("key");
		//开始查询
		Page<StudentModel> students=StudentModel.getList(1, 10,key);
		//将查到的学生信息列表放到infos，给页面
		setAttr("infos", students);
		//返回格式是json
		renderJson();
	}
	/**
	* @Title: openStudentAdd
	* @Description:打开添加信息页面
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void openStudentAdd() {
		render("stu/studentAdd.html");
	}
	
	/**
	* @Title: getstudent
	* @Description:获取需要修改的学生信息
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void getstudent() {
		//接收页面数据
		String no=getPara("no");
		//根据条件查询数据库的数据
		StudentModel student=StudentModel.getByNo(no);
		//放到编辑页面上去
		setAttr("m", student);
		//返回格式是json
		renderJson();
	}
	/**
	* @Title: openStudentEdit
	* @Description:打开修改信息页面
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void openStudentEdit() {
		//接收页面数据
		String no=getPara("no");
		setAttr("no", no);
		renderFreeMarker("stu/studentEdit.html");
	}
	/**
	* @Title: saveStudent
	* @Description:数据保存，在添加信息页面上，点击保存的那个按键做的事情
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void saveStudent() {
		String no=getPara("no");
		String name=getPara("name");
		String cls=getPara("cls");
		int sex=getParaToInt("sex");
		//保存数据
		boolean result=StudentModel.save(no,name,cls,sex);
		
		setAttr("result", result);
		renderJson();
	}
	/**
	 * 
	* @Title: updateStudent
	* @Description:更新信息，就是修改信息页面，点击保存的那个按钮做的事情
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void updateStudent() {
		String no=getPara("no");
		String name=getPara("name");
		String cls=getPara("cls");
		int sex=getParaToInt("sex");
		//更新数据
		boolean result=StudentModel.update(no,name,sex,cls);
		
		setAttr("result", result);
		renderJson();
	}
	/**
	 * 
	* @Title: delStudent
	* @Description:删除信息，这个我们是根据唯一主键id来删除的。
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void delStudent() {
		String no=getPara("id");
		//删除
		boolean result=StudentModel.delStudentByNO(no);
		//返回结果
		setAttr("result", result);
		renderJson();
	}
	
	
	
	/**
	* @Title: dormitory
	* @Description: 打开学生宿舍信息列表页面
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void dormitory() {
		render("dor/dormitoryinfo.html");
	}
	/**
	* @Title: queryDormitory
	* @Description: 获取学生宿舍信息列表信息（查询），在这里，我们是用异步加载方式，
	* 就是说，页面先打开了，然后在用js向后台获取数据，这个就是。
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void queryDormitory() {
		//获取页面查询的关键字
		String key=getPara("key");
		//开始查询
		Page<DormitoryModel> Dormitory=DormitoryModel.getList(1, 10,key);
		//将查到的学生信息列表放到infos，给页面
		setAttr("infos", Dormitory);
		//返回格式是json
		renderJson();
	}
	/**
	* @Title: openDormitoryAdd
	* @Description:打开添加信息页面
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void openDormitoryAdd() {
		render("dor/dormitoryAdd.html");
	}
	
	/**
	* @Title: getdormitory
	* @Description:获取需要修改的学生宿舍信息
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void getdormitory() {
		//接收页面数据
		String id=getPara("id");
		//根据条件查询数据库的数据
		DormitoryModel Dormitory=DormitoryModel.getById(id);
		//放到编辑页面上去
		setAttr("m", Dormitory);
		//返回格式是json
		renderJson();
	}
	/**
	* @Title: openDormitoryEdit
	* @Description:打开修改信息页面
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void openDormitoryEdit() {
		//接收页面数据
		String id=getPara("id");
		setAttr("id", id);
		renderFreeMarker("dor/dormitoryEdit.html");
	}
	/**
	* @Title: saveDormitory
	* @Description:数据保存，在添加信息页面上，点击保存的那个按键做的事情
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void saveDormitory() {
		String id=getPara("id");
		String name=getPara("name");
		String building_id=getPara("building_id");
		int capacity=getParaToInt("capacity");
		int type=getParaToInt("type");
		int status=getParaToInt("status");
		String latitud=getPara("latitude");
		BigDecimal latitude=new BigDecimal(latitud); 
		latitude=latitude.setScale(2, BigDecimal.ROUND_HALF_UP);
		String longitud=getPara("longitude");
		BigDecimal longitude=new BigDecimal(longitud); 
		longitude=longitude.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		//保存数据
		boolean result=DormitoryModel.save(id,name,building_id,capacity,type,status,latitude,longitude);
		
		setAttr("result", result);
		renderJson();
	}
	/**
	 * 
	* @Title: updateDormitory
	* @Description:更新信息，就是修改信息页面，点击保存的那个按钮做的事情
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void updateDormitory() {
		String id=getPara("id");
		String name=getPara("name");
		String building_id=getPara("building_id");
		int capacity=getParaToInt("capacity");
		int type=getParaToInt("type");
		int status=getParaToInt("status");
		String latitud=getPara("latitude");
		BigDecimal latitude=new BigDecimal(latitud); 
		latitude=latitude.setScale(2, BigDecimal.ROUND_HALF_UP);
		String longitud=getPara("longitude");
		BigDecimal longitude=new BigDecimal(longitud); 
		longitude=longitude.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		boolean result=DormitoryModel.update(id,name,building_id,capacity,type,status,latitude,longitude);
		
		setAttr("result", result);
		renderJson();
	}
	/**
	 * 
	* @Title: delDormitory
	* @Description:删除信息，这个我们是根据唯一主键id来删除的。
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void delDormitory() {
		String id=getPara("id");
		//删除
		boolean result=DormitoryModel.delDormitoryByID(id);
		//返回结果
		setAttr("result", result);
		renderJson();
	}
	
	
	
	/**
	* @Title: building
	* @Description: 打开学校楼房信息列表页面
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void building() {
		render("bui/buildinginfo.html");
	}
	/**
	* @Title: queryBuilding
	* @Description: 获取学校楼房信息列表信息（查询），在这里，我们是用异步加载方式，
	* 就是说，页面先打开了，然后在用js向后台获取数据，这个就是。
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void queryBuilding() {
		//获取页面查询的关键字
		String key=getPara("key");
		//开始查询
		Page<BuildingModel> Building=BuildingModel.getList(1, 10,key);
		//将查到的学生信息列表放到infos，给页面
		setAttr("infos", Building);
		//返回格式是json
		renderJson();
	}
	/**
	* @Title: openBuilding
	* @Description:打开添加信息页面
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void openBuildingAdd() {
		render("bui/buildingAdd.html");
	}
	
	/**
	* @Title: getbuilding
	* @Description:获取需要修改的学校楼房信息
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void getbuilding() {
		//接收页面数据
		String id=getPara("id");
		//根据条件查询数据库的数据
		BuildingModel Building=BuildingModel.getById(id);
		//放到编辑页面上去
		setAttr("m", Building);
		//返回格式是json
		renderJson();
	}
	/**
	* @Title: opeBuildingEdit
	* @Description:打开修改信息页面
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void openBuildingEdit() {
		//接收页面数据
		String id=getPara("id");
		setAttr("id", id);
		renderFreeMarker("bui/buildingEdit.html");
	}
	/**
	* @Title: saveBuilding
	* @Description:数据保存，在添加信息页面上，点击保存的那个按键做的事情
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void saveBuilding() {
		String id=getPara("id");
		String name=getPara("name");
		String school_id=getPara("school_id");
		String addr=getPara("addr");
		String remark=getPara("remark");
		
		//保存数据
		boolean result=BuildingModel.save(id,name,school_id,addr,remark);
		
		setAttr("result", result);
		renderJson();
	}
	/**
	 * 
	* @Title: updateBuildingt
	* @Description:更新信息，就是修改信息页面，点击保存的那个按钮做的事情
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void updateBuilding() {
		String id=getPara("id");
		String name=getPara("name");
		String school_id=getPara("school_id");
		String addr=getPara("addr");
		String remark=getPara("remark");
		
		boolean result=BuildingModel.update(id,name,school_id,addr,remark);
		
		setAttr("result", result);
		renderJson();
	}
	/**
	 * 
	* @Title: delBuilding
	* @Description:删除信息，这个我们是根据唯一主键id来删除的。
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public void delBuilding() {
		String id=getPara("id");
		//删除
		boolean result=BuildingModel.delBuildingByID(id);
		//返回结果
		setAttr("result", result);
		renderJson();
	}
}

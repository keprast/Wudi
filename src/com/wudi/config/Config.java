package com.wudi.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.wudi.controller.AdminController;
import com.wudi.controller.WeixinController;
import com.wudi.interceptor.AdminInterceptor;
import com.wudi.interceptor.WeixinIntercepter;
import com.wudi.model.NavsModel;
import com.wudi.model.StudentModel;
import com.wudi.model.admin.BuildingModel;
import com.wudi.model.admin.DormitoryModel;
/**
 * 	系统配置类
 *   @author XIAO
 *   2018年10月26日10:57:14
 */
public class Config extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		// 加载配置文件，注意：配置文件必须放在src目录下,要不然找不到
		loadPropertyFile("config.properties");
		// 配置一些系统变量
		me.setDevMode(getPropertyToBoolean("DevMode", true));//设置为开发模式，方便查看日志
		
	}

	@Override
	public void configRoute(Routes me) {
		// 设置路由，客户端访问就是在这里设置的路径地址
		me.add("/admin", AdminController.class,"WEB-INF/admin");//后台数据管理访问路径：localhost:8080/admin
		me.add("/wudi", WeixinController.class);//微信小程序访问路径：localhost:8080/wudi
		
	}

	@Override
	public void configEngine(Engine me) {
		
	}

	@Override
	public void configPlugin(Plugins me) {
		// 插入其他插件，比如，连接数据库的mysql插件和连接多数据库插件
		DruidPlugin dsMysql = new DruidPlugin(getProperty("jdbcUrl"), getProperty("user"),
				getProperty("password").trim());
		{
			dsMysql.setTestOnBorrow(true);
			dsMysql.setTestOnReturn(true);
			dsMysql.setMaxWait(20000);
		}
		//mysql插件
		ActiveRecordPlugin arpMysql = new ActiveRecordPlugin("mysql", dsMysql);
		//从配置文件里查找出来，是否显示sql语句
		boolean showSql = getPropertyToBoolean("showSql", true);
		//系统设置是否显示sql
		arpMysql.setShowSql(showSql);
		{
			//将数据库表，绑定到这来来，注意，表名和类要相对应
			arpMysql.addMapping("navs", NavsModel.class);//主页面左侧菜单显示的表
			arpMysql.addMapping("student", StudentModel.class);//学生表
			arpMysql.addMapping("dormitory", DormitoryModel.class);//宿舍表
			arpMysql.addMapping("building", BuildingModel.class);//学校楼房信息表
		}
		//添加插件
		me.add(dsMysql);
		me.add(arpMysql);
	
		
	}

	@Override
	public void configInterceptor(Interceptors me) {
		//添加拦截器
		me.add(new AdminInterceptor());//添加后台数据管理访问拦截器
		me.add(new WeixinIntercepter());//添加微信小程序访问拦截器
	}

	@Override
	public void configHandler(Handlers me) {
		
	}

}

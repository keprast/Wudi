var $;
layui.config({
	base : "js/"
}).use(['form','layer','jquery'],function(){
	var form = layui.form(),
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage;
		$ = layui.jquery;
		var id=$("input[name='id']").val();
		//加载页面数据
		$.get("getdormitory?id="+id, function(data){
			var d=data.m;
	        	//执行加载数据的方法
	        	$("input[name='name']").val(d.name);
	        	$("input[name='building_id']").val(d.building_id);
	        	$("input[name='capacity']").val(d.capacity);
	        	$("input[name='type']").val(d.type);
	        	$("input[name='status']").val(d.status);
	        	$("input[name='latitude']").val(d.latitude);
	        	$("input[name='longitude']").val(d.longitude);
		})

 	form.on("submit(addUser)",function(data){console.log(data.field);
 		var index;
 		 $.ajax({//异步请求返回给后台
	    	  url:'updateDormitory',
	    	  type:'POST',
	    	  data:data.field,
	    	  dataType:'json',
	    	  beforeSend: function(re){
	    		  index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
	          },
	    	  success:function(d){
	    			//弹出loading
			    	top.layer.close(index);
			  		top.layer.msg("操作成功！");
			   		layer.closeAll("iframe");
			  	 		//刷新父页面
			  	 	parent.location.reload();
		    		
	    	  },
	    	  error:function(XMLHttpRequest, textStatus, errorThrown){
	    		  top.layer.msg('操作失败！！！服务器有问题！！！！<br>请检测服务器是否启动？', {
	    		        time: 20000, //20s后自动关闭
	    		        btn: ['知道了']
	    		      });
	           }
	      });
 		return false;
 	})
	
})

/**
 *   Date:  2018-12-13
 * Author:  wang tingting   
 *   desc:  message，confirm公用方法及其他
 * 
 *
 */

$.extend({
    message: function (data,sureFn) {
//		$.message({
	//		'title':'',=====>弹框的标题
	//		'text':'',=====>弹框要显示的文字
	//		'sureText':''=====>确定按钮要显示的文字
	//		'cancelText':'=====>取消按钮要显示的文字，不传则不显示取消按钮
	//	},function(){======>点击确定后要执行的函数 不传默认关闭弹框
	//	});
	    var html = '<div class="m_box">'
            + '<div class="m_box_content">'
            + '<div class="m_box_top">'
            + '<img src="icons/info.png">'
	            +'<span class="">'+data.title+'</span>'
	    		+'</div>'
	    		+'<div class="m_box_body">'
				+'<div class="info_text">'+data.text+'</div>';
				if(data.cancelText){
					html+='<div class="btn_group">'
		    			+'<div id ="btn_cancel" class="m_box_btn cancel_btn cursorP">'+data.cancelText+'</div>'
		    			+'<div id ="btn_ok" class="m_box_btn sure_btn cursorP">'+data.sureText+'</div>'
		    			+'</div>';
				}else{
					html+='<div class="m_box_btn sure_btn cursorP">'+data.sureText+'</div>';
				}
				html+='</div>'
		    	+'</div>'
		    	+'</div>';
	    $("body").append(html);
	    if(sureFn){
	    	$("body").on("click",".sure_btn",function(){
	        	$(".m_box").remove();
	        	sureFn();
	    	});
	    }else{
	    	$("body").on("click",".sure_btn",function(){
	        	$(".m_box").remove();
	    	});
	    }
		$("body").on("click",".cancel_btn",function(){
	    	$(".m_box").remove();
		});
    },
    notice:function(text,s){
//		text=====>弹框要显示的文字
//		s=====>几毫秒后消失    不传默认5000
    	var html = '<div class="notice_box">'+text+'</div>';
	    $("body").append(html);
	    $(".notice_box").css("margin-left","-"+$(".notice_box").outerWidth()/2+"px")
	    if(s){
	    	setTimeout(function(){
	    		$(".notice_box").remove();
	    	},s);
	    }else{
	    	setTimeout(function(){
	    		$(".notice_box").remove();
	    	},5000);
	    }
    }
});

/*
 * Date:2018-11-27
 * Author:Wang Tingting
 * */


;(function($,undefined){
	var loginUrl = "/platform/login.html";
	var api = {
		logoff: "/rest/security/logout",
		getCurrentUserName: "/rest/security/getCurrentUserName",
		changePassword: "/rest/security/changePassword",
		currenti18n: "/rest/i18n/current",
		getI18nInfo: "/rest/i18n/getI18nInfo",
		homeLogo: "/rest/security/home/logo",
		loginUrl: "/rest/security/login/getLoginUrl"
	};
	var dom = {
		J_loginOut:".J_loginOut",
		J_langBox:".J_langBox",
		J_langBoxT:".J_langBoxT",
		J_username:".J_username",
		J_changePsw:".J_changePsw",
		J_close_cPsw:".J_close_cPsw",
		J_cPsw_box:".J_cPsw_box",
		J_opsw:".J_opsw",
		J_npsw:".J_npsw",
		J_snpsw:".J_snpsw",
		J_cPsw_error:'.J_cPsw_error',
		J_sureChange:".J_sureChange",
		J_T_ChangePassword:".J_T_ChangePassword",
		J_T_Password:".J_T_Password",
		J_T_NewPassword:".J_T_NewPassword",
		J_T_RNewPassword:".J_T_RNewPassword",
		J_T_CPassword:".J_T_CPassword",
		J_T_NPassword:".J_T_NPassword",
		J_T_text5:".J_T_text5",
		J_T_text6:".J_T_text6",
		J_T_text7:".J_T_text7",
		J_T_text8:".J_T_text8",
		J_T_text13:'.J_T_text13'
	};
	var login=(function(){
		return{
			init:function(){
            	getI18n();
            	this.operaClick();
                this.getLang();
                this.getUsername();
                this.changePsw();
        		this.getFontSize();
                this.getI18nInfo();
				this.getHomeLogo();
				this.getLoginUrl();
            },
            operaClick:function(){
            	var _this=this;
            	$(window).resize(function () {          //当浏览器大小变化时
            		_this.getFontSize();
            	});
				$(dom.J_langBox).on("click", ".box-li", function () {
					$(this).parent().parent().find(dom.J_langBoxT).text($(this).text()).attr("data-lang",$(this).attr("data-lang")).attr("data-region",$(this).attr("data-region"));
				})
            	$("body").on("blur",dom.J_opsw,function(){
            		_this.checkOldPsw($(this).val(),$(this));
            	});
            	$("body").on("blur",dom.J_npsw,function(){
            		_this.checkNewPsw($(this).val(),$(this));
            	});
            	$("body").on("blur",dom.J_snpsw,function(){
            		_this.checksNewPsw($(this).val(),$(this));
            	});
                $("body").on("click",dom.J_sureChange,function(){
                	_this.sureChange();
                });
                $("body").on("keyup",dom.J_opsw,function(e){
					if(event.keyCode ==13){
						_this.sureChange();
					}
                });
                $("body").on("keyup",dom.J_npsw,function(e){
					if(event.keyCode ==13){
						_this.sureChange();
					}
                });
                $("body").on("keyup",dom.J_snpsw,function(e){
					if(event.keyCode ==13){
						_this.sureChange();
					}
                });
				$(dom.J_loginOut).on("click", function () {
					$.message({
						'title': $(dom.J_T_text5).text(),
						'text': $(dom.J_T_text7).text(),
						'sureText': $(dom.J_T_text6).text(),
						'cancelText': $(dom.J_T_text8).text()
					}, function () {
						_this.loginOut();
					});
				});
				$("#setting_btn").attr("href", rootUrl + "/Setting");
			},
            getFontSize:function(){
        		if(document.documentElement.clientWidth>1920){
        			document.documentElement.style.fontSize = document.documentElement.clientWidth / 38.4 + 'px';
            	}else{
            		document.documentElement.style.fontSize = 1920 / 38.4 + 'px';
            	}
            	$("body").css("visibility","visible");
			},
			getHomeLogo: function () {
				request(api.homeLogo, null, 'GET', function (s) {
					if (s.code == 1 && s.data.imageData) {
						$(".J_logo_src").attr("src", "data:" + s.data.imageType + ";base64," + s.data.imageData);
					} else {
						$(".J_logo_src").attr("src", "images/logo_w.png");
					}
				});
			},
			getLoginUrl: function () {
				request(api.loginUrl, null, 'GET', function (s) {
					if (s.code == 1 && s.data.loginUrl) {
						loginUrl = s.data.loginUrl;
					}
				});
			},
			getI18nInfo: function () {
				request(api.getI18nInfo, null, 'GET', function (s) {
					var html = "";
					if (s.code == 1) {
						$.each(s.data, function (i, v) {
							html += '<div id="chk_' + v.region.toLowerCase() + v.language.toLowerCase() + '" class="box-li" data-lang="' + v.language + '" data-region="' + v.region + '">' + v.name + '</div>';
						})
					} else {
						html = '<div id="chk_cnzh" class="box-li" data-lang="zh" data-region="CN">中文</div>'
							+ '<div id="chk_usen" class="box-li" data-lang="en" data-region="US">English</div>';
					}
                	$(dom.J_langBox).html(html);
                },function(){
                	var html='<div id="chk_cnzh" class="box-li" data-lang="zh" data-region="CN">中文</div>'
						+'<div id="chk_usen" class="box-li" data-lang="en" data-region="US">English</div>';
                	$(dom.J_langBox).html(html);
                });
            },
    		loginOut:function() {
				// location.href=rootUrl+"/logout";
				requestT(api.logoff, null, 'POST', function (s) {
					s = JSON.parse(s);
					if (s.code == 1) {
						location.href = rootUrl + loginUrl;
					} else {
						console.log(s.message);
					}
				});
			},
    		getUsername:function(){
            	requestT(api.getCurrentUserName,null,'GET',function(s){
            		s=JSON.parse(s);
            		if(s.code==1){
                		$(dom.J_username).html(s.data.name);
                		if(s.data.photoBase64Code){
                    		$(".J_head_img").find("img").attr("src","data:"+s.data.photoType+";base64,"+s.data.photoBase64Code).removeClass("d");
                		}else {
                            $(".J_head_img").find("img").attr("src", rootUrl + "/platform/icons/user.png").addClass("d");
                        }
            		}else{
            			console.log(s.message);
            		}
                });
    		},
    		getLang:function(){
				request(api.currenti18n,null,'get',function(s){
					if(s.code==1){
						if(s.data.language=="zh"){
							if(s.data.region=="CN"){
								$(dom.J_langBoxT).text("中文简体").attr({"data-lang":"zh","data-region":"CN"});
							}else{
								$(dom.J_langBoxT).text("中文繁體").attr({"data-lang":"zh","data-region":"TW"});
							}
						}else{
							$(dom.J_langBoxT).text("English").attr({"data-lang":"en","data-region":"US"});
						}
					}
                });
    		},
    		changePsw:function(){
	             $(dom.J_changePsw).on("click",function(){
	    			var html= '<div class="changePsw J_cPsw_box">'
                        + '<div class="changePsw_content">'
                        + '<div class="changePsw_top">'
                        + '<img  class="fl changepsw_icon" src="images/changepsw.png">'
                        + '<span>' + $(dom.J_T_ChangePassword).text() + '</span>'
                        + '<img class="fr close J_close_cPsw cursorP" src="icons/close_b.png">'
	                		+'</div>'
	                		+'<div class="changePsw_body">'
	            			+'<div class="form_group">'
	        				+'<label>'+$(dom.J_T_Password).text()+'</label>'
	        				+'<input class="J_opsw" type="password">'
	        				+'<div class="cPsw_error J_cPsw_error"></div>'
	            			+'</div>'
	            			+'<div class="form_group">'
	        				+'<label>'+$(dom.J_T_NewPassword).text()+'</label>'
	        				+'<input class="J_npsw" type="password">'
	        				+'<div class="cPsw_error J_cPsw_error"></div>'
	            			+'</div>'
	            			+'<div class="form_group">'
	        				+'<label>'+$(dom.J_T_RNewPassword).text()+'</label>'
	        				+'<input class="J_snpsw" type="password">'
	        				+'<div class="cPsw_error J_cPsw_error"></div>'
	            			+'</div>'
	            			+'<div id="btn_ok" class="change_box_btn J_sureChange cursorP">'+$(dom.J_T_text6).text()+'</div>'
	                		+'</div>'
		                	+'</div>'
		                	+'</div>'
	            	 $("body").append(html);
	             });
	             $("body").on("click",dom.J_close_cPsw,function(){
	            	 $(dom.J_cPsw_box).remove();
	             });
    		},
            checkOldPsw:function(val,t){
            	val=val.replace(/^\s+|\s+$/g,'');
            	$(dom.J_cPsw_error).removeClass("s");
                if(val){
                    t.next().text("").removeClass("s");
                    return true;
                }else{
                    t.next().text($(dom.J_T_Password).text()).addClass("s");
                    return false;
                }
            },
            checkNewPsw:function(val,t){
            	var reg=/(?!\d+$)[\dA-Za-z!@#$%^&*]{6,30}$/;
                val=val.replace(/^\s+|\s+$/g,'');
                $(dom.J_cPsw_error).removeClass("s");
                if(val){
                	if(!reg.test(val)){
                		t.next().text($(dom.J_T_text13).text()).addClass("s");
                        return false;
                	}else{
                		t.next().text("").removeClass("s");
                        return true;
                	}
                }else{
                    t.next().text($(dom.J_T_NewPassword).text()).addClass("s");
                    return false;
                }
            },
            checksNewPsw:function(val,t){
            	var nPsw=$(dom.J_npsw).val().replace(/^\s+|\s+$/g,'');
                val=val.replace(/^\s+|\s+$/g,'');
                $(dom.J_cPsw_error).removeClass("s");
                if(!val){
                    t.next().text($(dom.J_T_CPassword).text()).addClass("s");
                    return false;
                }else if(val!=nPsw){
                    t.next().text($(dom.J_T_NPassword).text()).addClass("s");
                    return false;
                }else{
                    return true;
                }
            },
            sureChange:function(){
            	var data={},
            		oPsw=$(dom.J_opsw).val(),
            		nPsw=$(dom.J_npsw).val(),
            		snPsw=$(dom.J_snpsw).val(),
            		_this=this;
            	if(!this.checkOldPsw(oPsw,$(dom.J_opsw))){
            		return false;
            	}
            	if(!this.checkNewPsw(nPsw,$(dom.J_npsw))){
            		return false;
            	}
            	if(!this.checksNewPsw(snPsw,$(dom.J_snpsw))){
            		return false;
            	}
            	data.oldPassword=oPsw;
            	data.newPassword=nPsw;
            	data.confirmPassword=snPsw;
                requestT(api.changePassword,data,'POST',function(s){
            		s=JSON.parse(s);
                	if(s.code==1){
                		_this.loginOut();
                	}else{
                    	$.message({
                    		'title':$(dom.J_T_text5).text(),
                    		'text':s.message,
                    		'sureText':$(dom.J_T_text6).text()
                    	});
                	}
                })
            }
		};
	})();
	$(function(){
    	login.init();
    });
})(jQuery);
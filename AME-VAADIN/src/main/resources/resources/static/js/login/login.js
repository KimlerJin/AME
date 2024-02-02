/*
 * Date:2018-11-27
 * Author:Wang Tingting
 * */


;(function ($, undefined) {
    var api = {
        login: "/rest/security/logon",
        logoff: "/rest/security/logout",
        changei18n: "/rest/i18n",
        currenti18n: "/rest/i18n/current",
        getCurrentUserName: "/rest/security/getCurrentUserName",
        getI18nInfo: "/rest/i18n/getI18nInfo"
    };
    var dom = {
        name: ".J_name",
        password: ".J_password",
        error: ".J_error",
        login: ".J_login",
        J_LDAP: ".J_LDAP",
        J_lang: ".J_lang",
        J_langT: ".J_langT",
        J_userLogin: '.J_userLogin',
        J_T_text1: ".J_T_text1",
        J_T_text2: ".J_T_text2",
        J_T_text3: ".J_T_text3",
        J_T_text4: ".J_T_text4",
        J_T_text5: ".J_T_text5",
        J_T_text6: ".J_T_text6",
        J_T_text7: ".J_T_text7",
        J_lang_check_box: '.J_lang_check_box',
        J_login_btn_disabled: '.J_login_btn_disabled'
    };
    var login = (function () {
        return {
            init: function () {
                initLogo("PC");
                initBackground("PC");
                loginI18n();
                this.changeLang();
                this.getFontSize();
                this.operaClick();
                this.checknameF();
                this.checkpswF();
                this.login();
//                this.getMouseP();
//                this.getLang();
                this.imgArrID;
                this.count = 0;
                // this.getI18nInfo();
                this.switchLocale();
            },
            operaClick: function () {
                var _this = this;
                $(window).resize(function () {          //当浏览器大小变化时
                    _this.getFontSize();
                });
                setTimeout(function () {
                    $(dom.name).focus()
                }, 0);
                $(document).on("click", function () {
                    $(this).find(".check_box").removeClass("show");
                });
                $(dom.J_lang).on("click", function (e) {
                    $(this).find(".check_box").toggleClass("show");
                    e.stopPropagation ? e.stopPropagation() : e.cancelBubble = true;
                });
//    			$(dom.J_lang).on("click",".lang_img",function(){
//    				var language=$(this).attr("data-lang"),
//						region=$(this).attr("data-region"),
//						text=$(this).find("img").attr("title")
//		                _this.changeLang(language,region,text);
//    			});
                if (!!window.ActiveXObject || "ActiveXObject" in window) {
                    $(".rotate_box").addClass("ie");
                } else {
                    $(".rotate_box").removeClass("ie");
                }
                if (localStorage.LDAP) {
                    if (JSON.parse(localStorage.LDAP)) {
                        $(dom.J_LDAP).addClass("active");
                    } else {
                        $(dom.J_LDAP).removeClass("active");
                    }
                }
                $(dom.J_LDAP).on("click", function () {
                    $(this).toggleClass("active");
                    localStorage.LDAP = $(this).hasClass("active");
                })
            },
            getFontSize: function () {
                if (document.documentElement.clientWidth > 1920) {
                    document.documentElement.style.fontSize = document.documentElement.clientWidth / 38.4 + 'px';
                } else {
                    document.documentElement.style.fontSize = 1920 / 38.4 + 'px';
                }
                $("body").css("visibility", "visible");
            },
            changeLang: function () {
                var data = {}, I18n;
                $(dom.J_lang_check_box).on("click", ".J_lang_img", function () {
                    data.language = $(this).attr("data-lang");
                    data.region = $(this).attr("data-region");
                    var text = $(this).find("img").attr("title");
                    $(dom.J_langT).text(text);
                    $(dom.error).removeClass("s");
                    requestJson("/rest/i18n/all", data, "GET", function (s) {
                        if (s.code == 1) {
                            I18n = s.data;
                            var t_i18n = "",
                                attr_t = "";
                            $.each($(".lang_text"), function (i, v) {
                                t_i18n = $(this).attr("data-i18n");
                                $(this).text(s.data[t_i18n]);
                            })
                            $.each($(".lang_attr"), function (m, n) {
                                t_i18n = $(this).attr("data-i18n");
                                attr_t = $(this).attr("data-t");
                                $(this).attr(attr_t, s.data[t_i18n]);
                            });
                        }
                    });
                })
            },
            getI18nInfo: function () {
                request(api.getI18nInfo, null, 'GET', function (s) {
                    var html = "";
                    if (s.code == 1) {
                        $.each(s.data, function (i, v) {
                            html += '<div id="chk_' + v.region.toLowerCase() + v.language.toLowerCase() + '" class="lang_img J_lang_img" data-lang="' + v.language + '" data-region="' + v.region + '">'
                                + '<img title="' + v.name + '" src="' + rootUrl + '/' + v.imagePath + '">'
                                + '</div>';
                        })
                    } else {
                        html = '<div id="chk_cnzh" class="lang_img J_lang_img" data-lang="zh" data-region="CN">'
                            + '<img title="中文" src="images/login/CN.png">'
                            + '</div>'
                            + '<div id="chk_usen" class="lang_img J_lang_img" data-lang="en" data-region="US">'
                            + '<img title="English" src="images/login/US.png">'
                            + '</div>';
                    }
                    $(dom.J_lang_check_box).html(html);
                }, function () {
                    var html = '<div id="chk_cnzh" class="lang_img J_lang_img" data-lang="zh" data-region="CN">'
                        + '<img title="中文" src="images/login/CN.png">'
                        + '</div>'
                        + '<div id="chk_usen" class="lang_img J_lang_img" data-lang="en" data-region="US">'
                        + '<img title="English" src="images/login/US.png">'
                        + '</div>';
                    $(dom.J_lang_check_box).html(html);
                });
            },
            checknameF: function () {
                var _this = this;
                $(dom.name).on("blur", function () {
                    _this.checkname($(this).val(), $(this));
                });
            },
            checkpswF: function () {
                var _this = this;
                $(dom.password).on("blur", function () {
                    _this.checkpsw($(this).val(), $(this));
                });
            },
            checkname: function (val, t) {
                val = val.replace(/^\s+|\s+$/g, '');
                $(dom.error).removeClass("s");
                if (!val) {
                    t.parent().next().text($(dom.J_T_text1).text()).addClass("s");
                    return false;
                } else {
                    return true;
                }
            },
            checkpsw: function (val, t) {
                val = val.replace(/^\s+|\s+$/g, '');
                $(dom.error).removeClass("s");
                if (!val) {
                    t.parent().next().text($(dom.J_T_text2).text()).addClass("s");
                    return false;
                } else {
                    return true;
                }
            },
            checkUser: function (fn) {
                var _this = this;
                requestT(api.getCurrentUserName, null, 'GET', function (s) {
                    s = JSON.parse(s);
                    if (s.code == 1) {
                        var d = s.data;
                        // $.message({
                        // 	'title':$(dom.J_T_text5).text(),
                        // 	'text':$(dom.J_T_text3).text(),
                        // 	'sureText':"切换当前账号进入系统",
                        // 	'cancelText': "使用之前账号进入系统"
                        // },function (){
                        // 	_this.logOut();
                        // },function(){
                        // 	location.href = rootUrl;
                        // });
                        _this.logOut();
                        $(dom.J_userLogin).val(0);
                    } else {
                        $(dom.J_userLogin).val(1);
                    }
                    if (fn) {
                        fn();
                    }
                });
            },
            login: function () {
                var _this = this;
                $(dom.login).on("click", function () {
                    _this.checkUser(function () {
                        if ($(dom.J_userLogin).val() == 1) {
                            _this.loginFn();
                        }
                    });
                });
                $(dom.name).keyup(function (event) {
                    if (event.keyCode == 13) {
                        _this.checkUser(function () {
                            if ($(dom.J_userLogin).val() == 1) {
                                _this.loginFn();
                            }
                        });
                    }
                });
                $(dom.password).keyup(function (event) {
                    if (event.keyCode == 13) {
                        _this.checkUser(function () {
                            if ($(dom.J_userLogin).val() == 1) {
                                _this.loginFn();
                            }
                        });
                    }
                });
            },
            logOut: function () {
                var _this = this;
                requestT(api.logoff, null, 'POST', function (s) {
                    s = JSON.parse(s);
                    if (s.code == 1) {
                        console.log("logout");
                        _this.loginFn();
                    } else {
                        console.log(s.message);
                    }
                });
            },
            loginFn: function () {
                $(dom.J_login_btn_disabled).show();
                $(dom.name).blur();
                $(dom.password).blur();
                var data = {},
                    name = $(dom.name).val(),
                    password = $(dom.password).val(),
                    ldap;
                if ($(dom.J_LDAP).hasClass("active")) {
                    ldap = true;
                } else {
                    ldap = false;
                }
                if (!this.checkname(name, $(dom.name))) {
                    return false;
                }
                if (!this.checkpsw(password, $(dom.password))) {
                    return false;
                }
                data.name = name;
                data.password = password;
                data.ldap = ldap;
                requestT(api.login, data, 'POST', function (s) {
                    s = JSON.parse(s);
                    var path = getUrlname("path")
                    if (s.code == 1) {
                        if (path) {
                            location.href = localhostPaht() + path;
                        } else {
                            location.href = rootUrl;
                        }
                    } else if (s.code == 405 || s.code == 406 || s.code == 407) {
                        $.message({
                            'title': $(dom.J_T_text5).text(),
                            'text': s.message,
                            'sureText': $(dom.J_T_text6).text()
                        }, function () {
                            if (path) {
                                location.href = localhostPaht() + path;
                            } else {
                                location.href = rootUrl;
                            }
                        });
                    } else if (s.code === 0) {
                        $.message({
                            'title':  $(dom.J_T_text5).text(),
                            'text': $(dom.J_T_text7).text(),
                            'sureText': $(dom.J_T_text6).text()
                        });
                    } else {
                        $.message({
                            'title': $(dom.J_T_text5).text(),
                            'text': s.message,
                            'sureText': $(dom.J_T_text6).text()
                        });
                    }
                    $(dom.J_login_btn_disabled).hide();
                });
            },
            switchLocale: function () {
                $(".meper-locale").change(function () {
                    if (this.checked) {
                        setCookie(LOCALE_KEY, "EN");
                    } else {
                        setCookie(LOCALE_KEY, "ZH");
                    }
                    loginI18n();
                });
            }
//    		getLang:function(){
//    			var language,
//					region,text,
//					_this=this;
//				request(api.currenti18n,null,'get',function(s){
//					if(s.code==1){
//						if(s.data.language=="zh"){
//							if(s.data.region=="CN"){
//								language="zh";
//			    				region="CN";
//			    				text="中文简体";
//							}else{
//								language="zh";
//			    				region="TW";
//			    				text="中文繁體";
//							}
//						}else{
//							language="en";
//		    				region="US";
//		    				text="English";
//						}
//						_this.changeLang(language,region,text);
//					}
//                });
//    		},
//    		changeLang:function(language,region,text){
//				var data={},
//				_this=this;
//				data.language=language;
//				data.region=region;
//				requestT(api.changei18n,data,'POST',function(s){
//            		s=JSON.parse(s);
//            		if(s.code==1){
//    					$(dom.J_langT).text(text);
//    					$(dom.error).removeClass("s");
//                    	getI18n(function(){
//                    		_this.checkUser();
////            				_this.imgArrP();
//                    	});
//            		}else{
//            			console.log(s.message);
//            		}
//                });
//    		},
//    		getMouseP:function(){
//    			var _this=this;
//    			$(".img_arr").mousemove(function(e){ 
//    				 var x = e.pageX,
//			         	y = e.pageY;
//      				$(".info").hide();
//    				 $(".mouse_info").html($(this).attr("data-info")).css({
//    					 "left":x-134,
//    					 "top":y-$(".info").innerHeight()
//    				 }).show();
//    				 clearInterval(_this.imgArrID);
//    			}).mouseout(function(){
//    				$(".mouse_info").hide();
//					_this.count=1;
//    				_this.imgArrP();
//    			});
//    		},
//    		stop3D:function(dom,x,y){
//    			var _this=this;
//				 $(".ie_img_box").addClass("stop");
//				 $(".face_box").addClass("stop");
//				 $(".img_arr span").addClass("stop");
//				 $(".vertex").addClass("stop");
// 				 $("."+dom.attr("data-dom")).html(dom.attr("data-info")).css({
//  					 "left":x+10-134,
//  					 "top":y-$("."+dom.attr("data-dom")).innerHeight()
//  				 }).show();
//				setTimeout(function(){
//					$(".ie_img_box").removeClass("stop");
//					$(".face_box").removeClass("stop");
//					$(".img_arr span").removeClass("stop");
//					$(".vertex").removeClass("stop");
//					_this.count=1;
//    				if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){
//        				_this.imgArrP();
//    			   }
//				}, 2000);
//	   		},
//	   		imgArrP:function(){
//	   			var _this=this,
//	   				isIE=$(".rotate_box").attr("class").indexOf("ie");
//	               this.imgArrID=setInterval(function(){
//	            	   if(isIE>-1){
//	            			$.each($(".ie_img_box .img_arr"),function(){
//		  		   				 var px = $(this).offset().left,
//				   					py=$(this).offset().top,
//						         	x = $('.rotate_box').offset().left+160,
//						         	y = $('.rotate_box').offset().top+100,
//						         	sx = $(this).find("span").offset().left,
//				   					sy=$(this).find("span").offset().top;
//		    		   				if(px<x+0.5&&px>x&&py<y){
//		    		   					if(_this.count==0){
//		    		   						_this.stop3D($(this),sx,sy);
//		    		   					}
//		    		   				}else{
//		           		   				_this.count=0;
//		       		   					$("."+$(this).attr("data-dom")).hide();
//		       		   				}
//		  		   			});
//	                	}else{
//	            			$.each($(".face_box .img_arr"),function(){
//	    		   				var px = $(this).offset().left,
//	    		   					py=$(this).offset().top,
//	    				         	x = $('.rotate_box').offset().left+111,
//	    				         	y = $('.rotate_box').offset().top+100,
//	    				         	sx = $(this).find("span").offset().left,
//	    		   					sy=$(this).find("span").offset().top;
//	    		   				if(px<x+0.5&&px>x&&py<y){
//	    		   					if(_this.count==0){
//	        		   					_this.stop3D($(this),sx,sy);
//	    		   		    			if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){
//	    		   		    				clearInterval(_this.imgArrID);
//	    	   		    			    }
//	    		   					}
//	    		   				}else{
//	           		   				_this.count=0;
//	       		   					$("."+$(this).attr("data-dom")).hide();
//	       		   				}
//	    		   			});
//	                	}
//	               }, 0);
//	   		}
        };
    })();
    $(function () {
        login.init();
    });
})(jQuery);


var zhI18n;
var enI18n;
const LOCALE_KEY = "MEPER-LOCALE";

var loginI18n = function () {
    var locale = getCookie(LOCALE_KEY);
    if (locale == null) {
        locale = "ZH";
    }

    var i18Url = "/rest/i18n/all";
    if (locale === "EN") {
        i18Url += "?language=en&region=US";
        $('.meper-locale').prop("checked", true);
        if (enI18n != null) {
            var t_i18n = "",
                attr_t = "";
            $.each($(".lang_text"), function (i, v) {
                t_i18n = $(this).attr("data-i18n");
                $(this).text(enI18n[t_i18n]);
            })
            $.each($(".lang_attr"), function (m, n) {
                t_i18n = $(this).attr("data-i18n");
                attr_t = $(this).attr("data-t");
                $(this).attr(attr_t, enI18n[t_i18n]);
            });
            $("#input_username").attr("placeholder", enI18n["Home.Page.Account"]);
            $("#input_password").attr("placeholder", enI18n["Home.Page.Password"]);
            return;
        }
    } else {
        $('.meper-locale').prop("checked", false);
        if (zhI18n != null) {
            var t_i18n = "",
                attr_t = "";
            $.each($(".lang_text"), function (i, v) {
                t_i18n = $(this).attr("data-i18n");
                $(this).text(zhI18n[t_i18n]);
            })
            $.each($(".lang_attr"), function (m, n) {
                t_i18n = $(this).attr("data-i18n");
                attr_t = $(this).attr("data-t");
                $(this).attr(attr_t, zhI18n[t_i18n]);
            });
            $("#input_username").attr("placeholder", zhI18n["Home.Page.Account"]);
            $("#input_password").attr("placeholder", zhI18n["Home.Page.Password"]);
            return;
        }
    }

    request(i18Url, null, "GET", function (s) {
        if (s.code == 1) {
            if (locale === "EN") {
                enI18n = s.data;
            } else {
                zhI18n = s.data;
            }
            var t_i18n = "",
                attr_t = "";
            $.each($(".lang_text"), function (i, v) {
                t_i18n = $(this).attr("data-i18n");
                $(this).text(s.data[t_i18n]);
            })
            $.each($(".lang_attr"), function (m, n) {
                t_i18n = $(this).attr("data-i18n");
                attr_t = $(this).attr("data-t");
                $(this).attr(attr_t, s.data[t_i18n]);
            });
            $("#input_username").attr("placeholder", s.data["Home.Page.Account"]);
            $("#input_password").attr("placeholder", s.data["Home.Page.Password"]);
        }
    });
};
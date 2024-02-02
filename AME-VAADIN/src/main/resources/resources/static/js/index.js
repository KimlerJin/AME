/*
 * Date:2018-12-06
 * Author:Wang Tingting
 * */


;(function ($, undefined) {
	var api = {
		getBuildVersion: "/rest/system/getBuildVersion",
		changei18n: "/rest/i18n",
		getAllModule: "/rest/security/getAllModule",
		checkLicence: "/rest/security/checkLicence",
		checkPasswordExpiration: "/rest/security/checkPasswordExpiration",
		getConfigMoible: '/rest/security/getConfigMoible',
		getRoleByUser:'/rest/security/getRoleByUser',
		getToken: "/rest/security/getMeperToken",
		getUserIcon:"/rest/logo/getUserIcon"
	};
	var dom = {
		J_content: ".J_content",
		J_prev: ".J_prev",
		J_next: ".J_next",
		J_top_right: ".J_top_right",
		J_langBox: ".J_langBox",
		J_langBoxT: ".J_langBoxT",
		J_about: ".J_about",
		J_Popup_box: ".J_Popup_box",
		J_close: ".J_close",
		J_BuildVersion: ".J_BuildVersion",
		J_BuildNumber: ".J_BuildNumber",
		J_T_text9: ".J_T_text9",
		J_T_text10: ".J_T_text10",
		J_T_text11: ".J_T_text11",
		J_T_text12: ".J_T_text12",
		J_T_text14: ".J_T_text14",
		J_T_text15: ".J_T_text15",
		J_mes_top: ".J_mes_top",
		J_checkLicence: ".J_checkLicence",
		J_topMes_close: ".J_topMes_close"
	};
	var index = (function () {
		return {
			init: function () {
				var _this = this;
				this.clickHeadMenu();
				this.changeLang();
				this.checkPasswordExpirationFn();
				this.checkLicence();
				this.getRoleByUser();
				this.I18n = getI18n();
				this.getModule();
				this.first=true;
				this.getMeperToken();
				this.getUserIcon();
			},
			clickHeadMenu: function () {
				$(dom.J_top_right + " .font_menu").on("click", function (e) {
					$(this).toggleClass("active").siblings().removeClass("active");
					$(document).one('click', function () {
						$(dom.J_top_right + " .font_menu").removeClass("active");
					})
					e.stopPropagation();
				});
//				setTimeout(function () {
//					$(dom.J_mes_top).hide();
//				}, 10000);
				$(dom.J_mes_top).on("click",dom.J_topMes_close, function () {
					$(dom.J_mes_top).remove();
				})
			},
			checkLicence: function () {
				var _this=this;
				request(api.checkLicence, null, 'post', function (s) {
					if(s.code==1){
						if(s.data){
							var html='<div class="swiper-slide content J_checkLicence_content">'
                                + '<img class="fl" src="images/index/info.png">提示：<span class="J_checkLicence">' + s.data + '</span>'
					            +'<div id="closeTopMes" class="fr close J_topMes_close"></div>'
								+'</div>'
							$(dom.J_mes_top).show().find(".swiper-wrapper").append(html);
							_this.topMesSwiper();
						}else{
							$(".J_checkLicence_content").remove();
						}
					}else{
						$(".J_checkLicence_content").remove();
					}
				});
			},
			checkPasswordExpirationFn: function () {
				var _this=this;
				request(api.checkPasswordExpiration, null, 'post', function (s) {
					if(s.code==1){
						if(s.data){
							var html= '<div class="swiper-slide content J_checkPassword_content">'
                                + '<img class="fl" src="images/index/info.png">提示：<span class="J_checkPassword">' + s.data + '</span>'
					            +'<div id="closeTopMes" class="fr close J_topMes_close"></div>'
								+'</div>'
							$(dom.J_mes_top).show().find(".swiper-wrapper").append(html);
							_this.topMesSwiper();
						}else{
							$(".J_checkPassword_content").remove();
						}
					}else{
						$(".J_checkPassword_content").remove();
					}
				});
			},
			getRoleByUser:function(){
				var _this=this;
				request(api.getRoleByUser, null, 'get', function (s) {
					if(s.code==1){
						if(!s.data){
							var html= '<div class="swiper-slide content J_RoleByUser_content">'
                                + '<img class="fl" src="images/index/info.png">提示：<span class="J_User">' + s.message + '</span>'
					            +'<div id="closeTopMes" class="fr close J_topMes_close"></div>'
								+'</div>'
							$(dom.J_mes_top).show().find(".swiper-wrapper").append(html);
							_this.topMesSwiper();
						}else{
							$(".J_RoleByUser_content").remove();
						}
					}else{
						$(".J_RoleByUser_content").remove();
					}
				});
			},
			getMeperToken:function(){
				request(api.getToken, null, 'get', function (s) {
				});
			},
			getUserIcon:function () {
				request(api.getUserIcon, null, 'get', function (result) {
					if (result.code == 1) {
						$("#userIcon").attr("src", result.data);
					}

				});
			},
			topMesSwiper:function(){
				if($(dom.J_mes_top).find(".swiper-slide").length>1){
					$(dom.J_mes_top).swiper({
						mode:'vertical',
						autoplay: 5000,
						loop: true
					});
				}
			},
			changeLang: function () {
				var _this = this;
				$(dom.J_langBox).on("click", ".box-li", function () {
					var language = $(this).attr("data-lang"),
						region = $(this).attr("data-region"),
						data = {};
					data.language = language;
					data.region = region;
					requestT(api.changei18n, data, 'POST', function (s) {
						s = JSON.parse(s);
						if (s.code == 1) {
							_this.I18n=getI18n();
							_this.getModule();
							_this.checkLicence();
							_this.checkPasswordExpirationFn();
							_this.getRoleByUser();
						}
					});
				})
			},
			getBuildVersionF: function () {
				requestT(api.getBuildVersion, null, 'GET', function (s) {
					s = JSON.parse(s);
					if (s.code == 1) {
						$(dom.J_BuildVersion).html(s.data.buildVersion);
						$(dom.J_BuildNumber).html(s.data.buildNumber);
					}
				});
			},
			getModule: function () {
				var _this = this;
				requestT(api.getAllModule, null, 'GET', function (s) {
					s = JSON.parse(s);
					if (s.code == 1) {
						var wWidth = $(window).width(),
							html = '',
							date = new Date,
							year = date.getFullYear(),
							lang=$(dom.J_langBoxT).attr("data-lang"),
							region=$(dom.J_langBoxT).attr("data-region");
						$.each(s.data, function (i, v) {
							if (wWidth > 1400 && (i + 1) % 10 == 1) {
								html += '<div class="swiper-slide slide_box" style="width:'+$(".slide_box").width()+'px;height:'+$(".slide_box").height()+'px">';
							} else if (wWidth < 1400 && (i + 1) % 8 == 1) {
								html += '<div class="swiper-slide slide_box" style="width:'+$(".slide_box").width()+'px;height:'+$(".slide_box").height()+'px">';
							}
							html += '<div class="box">'
								+ '<a id="' + v.caption + '" href="' + rootUrl + v.link + '">';
							if (v.backgroundPath.length<=1) {
								if (v.backgroundPath == "") {
									html += '<img class="bg_img" src="' + rootUrl + '/platform/images/index/default_bg.gif">';
								} else {
									html += '<img class="bg_img" src="' + rootUrl + '/' + v.backgroundPath + '">';
								}
							}else {
								html += '<div class="swiper-container">'
									+ '<div class="swiper-wrapper">';
								$.each(v.backgroundPath, function (m, n) {
									html += '<div class="swiper-slide"><img src="' + rootUrl + '/' + n + '"></div>'
										+ '<div class="swiper-slide"><img src="' + rootUrl + '/' + n + '"></div>'
								})
								html += '</div></div>'
							}
							html += '<div class="box_content">'
								+ '<div class="box_title">' + v.name + '</div>';
							// + '<div class="small_text">' + v.caption + '</div>'
							if (v.iconPath == "") {
								html += '<div class="img_box"><img src="' + rootUrl + '/platform/images/index/default_icon.png"></div>';
							} else {
								html += '<div class="img_box"><img src="' + rootUrl + '/' + v.iconPath + '"></div>';
							}
							html += '<div class="box_info" title="' + (v.description ? v.description : v.name) + '">'
								//								+ '<marquee direction="left" behavior="scroll" scrollamount="3" align="middle">'
								+ '<span>' + (v.description ? v.description : v.name) + '</span>'
								//								+ '</marquee>'
								+ '<img class="fr arrow" src="images/index/arrow.png">'
								+ '<img class="light" src="images/index/light.png">'
								+ '</div>'
								+ '</div>'
								+ '</a>'
								+ '</div>';
							if (wWidth > 1400 && (i + 1) % 10 == 0) {
								html += '</div>';
							} else if (wWidth < 1400 && (i + 1) % 8 == 0) {
								html += '</div>';
							}
						});
						var helpHtml = '<div class="box">'
                            + '<a id="Help" target="_blank" href="' + rootUrl + '/HelpPage">'
                            + '<div class="swiper-container">'
                            + '<div class="swiper-wrapper">'
                            + '<div class="swiper-slide"><img src="images/index/1.png"></div>'
                            + '<div class="swiper-slide"><img src="images/index/9.png"></div>'
                            + '</div>'
                            + '</div>'
                            + '<div class="box_content">'
                            + '<div class="box_title">帮助</div>'
                            + '<div class="small_text">Help</div>'
                            + '<div class="img_box"><img src="images/index/icon10.png"></div>'
                            + '<div class="box_info"  title="' + $(dom.J_T_text9).text() + '">'
                            + '<span>' + $(dom.J_T_text9).text() + '</span><img class="fr arrow" src="images/index/arrow.png">'
                            + '<img class="light" src="images/index/light.png">'
							+ '</div>'
							+ '</div>'
							+ '</a>'
							+ '</div>',
							aboutHtml = '<div class="box about J_about">'
                                + '<div class="box_content">'
                                + '<div class="img_box">'
                                + '<img src="images/index/icon11.png">'
								+ '<div class="box_title">关于<br>'
								+ '<span class="small_text">About</span>'
								+ '</div>'
								+ '</div>'
								+ '<div class="logo">'
								+ '<img src="images/logo_b.png">'
								+ '</div>'
								+ '<div class="small_info">' + $(dom.J_T_text10).text() + '</div>'
								//                        +'<div class="small_info">Build Version <span class="">3.1</span></div>'
								+ '<div class="small_info">Version <span class="J_BuildVersion"></span>-<span class="J_BuildNumber"></span></div>'
								+ '<div class="small_info">'
								+ '<span>' + $(dom.J_T_text11).text() + year + " " + $(dom.J_T_text15).text() + ' </span>'
								+ '<br/>'
								+ '<a id="about" target="_blank" href="http://jaspermes.cn/">' + $(dom.J_T_text12).text() + '</a>'
								+ '&nbsp;&nbsp;<a id="about" class="ml10" target="_blank" href="' + urls() + '/Dependency">' + $(dom.J_T_text14).text() + '</a>'
								+ '</div>'
								+ '</div>'
								+ '</div>';
						if (wWidth > 1400 && (s.data.length + 2) % 10 == 1) {
							html += helpHtml + '</div><div class="swiper-slide slide_box" style="width:'+$(".slide_box").width()+'px;height:'+$(".slide_box").height()+'px">' + aboutHtml + '</div>';
						} else if (wWidth > 1400 && (s.data.length + 2) % 10 == 2) {
							html += '<div class="swiper-slide slide_box" style="width:'+$(".slide_box").width()+'px;height:'+$(".slide_box").height()+'px">' + helpHtml + aboutHtml + '</div>';
						} else if (wWidth < 1400 && (s.data.length + 2) % 8 == 1) {
							html += helpHtml + '</div><div class="swiper-slide slide_box" style="width:'+$(".slide_box").width()+'px;height:'+$(".slide_box").height()+'px">' + aboutHtml + '</div>';
						} else if (wWidth < 1400 && (s.data.length + 2) % 8 == 2) {
							html += '<div class="swiper-slide slide_box" style="width:'+$(".slide_box").width()+'px;height:'+$(".slide_box").height()+'px">' + helpHtml + aboutHtml + '</div>';
						} else {
							html += helpHtml + aboutHtml + '</div>';
						}
						$(dom.J_content).find(".swiper-wrapper").html(html);
						if(_this.first==true){
							_this.swiperC();
							_this.first=false;
						}
						_this.getBuildVersionF();
						_this.getConfigMoible();
					}
				});
			},
			getConfigMoible: function () {
				request(api.getConfigMoible, null, 'GET', function (s) {
					if (s.code == 1) {
						var mobileHtml = '<div class="box about">'
                            + '<div class="box_content">'
                            + '<div class="img_box">'
                            + '<img src="images/index/icon11.png">'
							+ '<div class="box_title">手机APP配置<br>'
							+ '<span class="small_text">Mobile Config</span>'
							+ '</div>'
							+ '</div>'
							+ '<div class="code">'
							+ '<img src="data:png;base64,' + s.data + '">'
							+ '</div>'
							+ '</div>'
							+ '</div>',
							wWidth = $(window).width();
						var slide_box_length = $(dom.J_content).find(".slide_box").length,
							box_length = $(dom.J_content).find(".slide_box").eq(slide_box_length - 1).find(".box").length;
						if (wWidth > 1400 && box_length <= 9) {
							$(dom.J_content).find(".slide_box").eq(slide_box_length - 1).append(mobileHtml);
						} else if (wWidth > 1400 && box_length > 9) {
							$(dom.J_content).append('<div class="swiper-slide slide_box" style="width:'+$(".slide_box").width()+'px;height:'+$(".slide_box").height()+'px">' + mobileHtml + '</div>');
						} else if (wWidth < 1400 && box_length <= 7) {
							$(dom.J_content).find(".slide_box").eq(slide_box_length - 1).append(mobileHtml);
						} else if (wWidth < 1400 && box_length > 7) {
							$(dom.J_content).append('<div class="swiper-slide slide_box" style="width:'+$(".slide_box").width()+'px;height:'+$(".slide_box").height()+'px">' + mobileHtml + '</div>');
						}
					}
				});
			},
			swiperC: function () {
				var content = $(dom.J_content).swiper({
						onSwiperCreated: function (swiper) {
							if ($(".slide_box").length > 1) {
								$(dom.J_next).addClass("cursorP").css("opacity", "1");
								$(dom.J_prev).removeClass("cursorP").css("opacity", "0");
							} else {
								$(dom.J_next).removeClass("cursorP").css("opacity", "0");
								$(dom.J_prev).removeClass("cursorP").css("opacity", "0");
							}
						},
						onSlideChangeEnd: function (swiper) {
							if (swiper.activeIndex == 0) {
								$(dom.J_prev).removeClass("cursorP").css("opacity", "0");
								$(dom.J_next).addClass("cursorP").css("opacity", "1");
							} else if (swiper.activeIndex == $(".slide_box").length - 1) {
								$(dom.J_next).removeClass("cursorP").css("opacity", "0");
								$(dom.J_prev).addClass("cursorP").css("opacity", "1");
							} else {
								$(dom.J_next).addClass("cursorP").css("opacity", "1");
								$(dom.J_prev).addClass("cursorP").css("opacity", "1");
							}
						}
					}),
					s = [6000, 15000, 12000, 13000, 8000, 16000, 10000, 14000];
				$(dom.J_prev).on("click", function () {
					content.swipePrev();
				})
				$(dom.J_next).on("click", function () {
					content.swipeNext();
				})
				$.each($(".swiper-container"), function (i, v) {
					$(this).swiper({
						autoplay: s[i % 8],
						loop: true
					});
				});
			}
		};
	})();
	$(function () {
		index.init();
	});
})(jQuery);
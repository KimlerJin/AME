/*
 * Date:2018-11-27
 * Author:Wang Tingting
 * public function
 * */

 
//api接口统一路径
var urls = function(){
	var curWwwPath = window.document.location.href,
		pathName=window.document.location.pathname,
		pos = curWwwPath.indexOf(pathName),
		localhostPaht = curWwwPath.substring(0, pos),
		projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
};
var localhostPaht = function(){
	var curWwwPath = window.document.location.href,
	pathName=window.document.location.pathname,
	pos = curWwwPath.indexOf(pathName),
	localhostPaht = curWwwPath.substring(0, pos);
	return localhostPaht;
};
 var rootUrl=urls();
//var rootUrl="http://119.119.118.9:8080";
//传参为：接口，数据(可以null)，‘get,post,delete,put’
var request=function(url, data,types, successfn, errorfn) {
    data = (data==null || data=="" || typeof(data)=="undefined")? "" : JSON.stringify(data);
    url=rootUrl+url;
    $.ajax({
        type: types,
        data: data,
        url: url,
        cache:false,
        dataType: "json",
        async: false,
        contentType : 'application/json;charset=UTF-8',
        success: function(d){
            successfn(d);
        },
        error: function(e){
        	if(errorfn){
                errorfn(e);
        	}
        	console.log(e);
        }
    });
};
var requestJson=function(url, data,types, successfn, errorfn) {
    data = (data==null || data=="" || typeof(data)=="undefined")? "" : data;
    url=rootUrl+url;
    $.ajax({
        type: types,
        data: data,
        url: url,
        cache:false,
        dataType: "json",
        async: false,
        contentType : 'application/json;charset=UTF-8',
        success: function(d){
            successfn(d);
        },
        error: function(e){
        	if(errorfn){
                errorfn(e);
        	}
        	console.log(e);
        }
    });
};

var requestT=function(url, data,types, successfn, errorfn) {
    data = (data==null || data=="" || typeof(data)=="undefined")? "" : JSON.stringify(data);
    url=rootUrl+url;
    $.ajax({
        type: types,
        data: data,
        url: url,
        cache:false,
        dataType: "text",
        contentType : 'application/json;charset=UTF-8',
        success: function(d){
            successfn(d);
        },
        error: function(e){
        	if(errorfn){
                errorfn(e);
        	}
        	console.log(e);
        }
    });
};

var getI18n=function(callback){
	var I18n;
	request("/rest/i18n/all",null,"GET",function(s){
		if(s.code==1){
			I18n=s.data;
			var t_i18n="",
				attr_t="";
			$.each($(".lang_text"),function(i,v){
				t_i18n=$(this).attr("data-i18n");
				$(this).text(s.data[t_i18n]);
			})
			$.each($(".lang_attr"),function(m,n){
				t_i18n=$(this).attr("data-i18n");
				attr_t=$(this).attr("data-t");
				$(this).attr(attr_t,s.data[t_i18n]);
			});
			if(callback){
				callback();
			}
            $("#input_username").attr("placeholder", s.data["Home.Page.Account"]);
            $("#input_password").attr("placeholder", s.data["Home.Page.Password"]);
		}
	});
	return I18n;
};

var  getUrlname=function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = (window.location.search+window.location.hash).substr(1).match(reg);
    if (r != null){
    	return unescape(r[2]);
    }else{
    	return null; 
    };  
};

var initLogo=function (type) {
    request("/rest/logo/getLoginLogo?type=" + type, null, "GET", function (result){
        if (result.code == 1) {
            var img = document.getElementById("login_logo");
            if (result.data == null || result.data == '' || result == undefined) {
            } else {
                img.setAttribute("src", result.data);
            }
        }
    }, function (result) {
        console.log(result);
    })
}

var initBackground=function (type) {
    request("/rest/logo/getLoginBackground?type=" + type, null, "GET", function (result){
        if (result.code == 1) {
            var element = document.querySelector('body');
            console.log(element);
            if (result.data == null || result.data == '' || result == undefined) {
            } else {
                element.style.background = "url('" + result.data + "') no-repeat center";
                element.style.backgroundSize = "cover";
            }
        }
    }, function (result) {
        console.log(result);
    })
}

// 构建要设置的 Cookie 字符串
function setCookie(name, value, days) {
    var expires = '';

    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = '; expires=' + date.toUTCString();
    }else{
        expires = '; expires=null';
    }
    document.cookie = name + '=' + encodeURIComponent(value) + expires +'; path=/';
}

function getCookie(cookieName) {
    var name = cookieName + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var cookieArray = decodedCookie.split(';');

    for (var i = 0; i < cookieArray.length; i++) {
        var cookie = cookieArray[i];
        while (cookie.charAt(0) === ' ') {
            cookie = cookie.substring(1);
        }
        if (cookie.indexOf(name) === 0) {
            return cookie.substring(name.length, cookie.length);
        }
    }
    return null; // 如果找不到指定名称的Cookie，返回null
}
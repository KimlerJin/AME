jQuery(function() {
	setTimeout(function() {
		//$(".side-bar .v-panel-content").hide();
		$("body").on("click",".side-bar .v-panel-caption",
				function() {
					var thisPanel = !$(this).parent().parent();
					if (!$(this).parent().parent().find(".v-panel-content").is(
							":hidden")) {
						$(this).parent().parent().find(".v-panel-content")
								.slideUp();
						$(this).children().find(".expand").css(
								"transform-origin", "50% 50% 0");
						$(this).children().find(".expand").css("transform",
								"rotate(0deg)");
						$(this).parent().parent().removeClass("v-panel-active");
					} else {
						$(".side-bar .v-panel-content").slideUp();
						$(this).parent().parent().find(".v-panel-content")
								.slideDown();
						$(".expand").css("transform", "rotate(0deg)");
						$(".expand").css("transform-origin", "50% 50% 0");
						$(this).children().find(".expand").css("transform",
								"rotate(90deg)");
						$(".v-panel-active").removeClass(
								"v-panel-active");
						$(this).parent().parent().addClass("v-panel-active");

					}
				});

		$(".side-menu").click(function() {
			$(".side-menu-active").removeClass("side-menu-active");
			$(this).addClass("side-menu-active");
		});
	}, 0);
});

function initSideBar(){
	$(".side-bar .v-panel-content").hide();
}
function menuClick(id) {
	var thisMenu = $("#" + id);
	if (id && thisMenu.length > 0) {
		var thisPContent = $("#" + id).parent().parent().parent();
		var thisPCaption = thisPContent.parent();
		if (!thisMenu.hasClass("side-menu-active")) {
			// 控制该按钮的颜色
			$(".side-menu").removeClass("side-menu-active");
			thisMenu.addClass("side-menu-active");

			// 控制该按钮对应的panel展开/关闭
			if (thisPContent.is(":hidden")) {
				$(".side-bar .v-panel-content").slideUp();
				thisPContent.slideDown();
			}

			// 控制该按钮对应的caption颜色
			$(".v-panel-active").removeClass("v-panel-active");
			thisPCaption.addClass("v-panel-active");

			// 控制该按钮对应的三角形展开/关闭颜色
			$(".expand").css("transform", "rotate(0deg)");
			$(".expand").css("transform-origin", "50% 50% 0");
			thisPCaption.children().find(".expand").css("transform",
					"rotate(90deg)");
		} else {
			if (thisPContent.is(":hidden")) {
				$(".side-bar .v-panel-content").slideUp();
				thisPContent.slideDown();
			}
		}
	}else{
		$(".side-bar .v-panel-content").slideUp();
		$(".v-panel-active").removeClass("v-panel-active");
		$(".side-menu").removeClass("side-menu-active");
	}

}

var isOpenOne=true;
function initTopMenuPopup() {
	$.each($(".header-popup-content"), function() {
		var linkWidth=0;
		$.each($(this).find(".v-link"),function(){
			if($(this).outerWidth()>linkWidth){
				linkWidth=$(this).outerWidth();
			}
		})
		if(isOpenOne){
			$(this).width(
					linkWidth* Math.ceil($(this).find(".v-link").length / 11));
		}
	});
	isOpenOne=false;
}
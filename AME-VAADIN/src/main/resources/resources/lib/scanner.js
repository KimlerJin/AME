function scanner(p){
	var msg = {
		cmd: 'qrcode',
		mode: 'get'
	},
	data = JSON.stringify(msg);
	window.postMessage(data);
	document.addEventListener('message', function(e) {
		var msg = JSON.parse(e.data);
		if (msg.cmd == "qrcode") {
			window.scanFunction([p,msg.data]);
		}
	});
}
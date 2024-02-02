var printAreaCount = 0;
function printerHtml(printContent){
    Promise.all(printContent.imgs.map(loadImage))
        .then(images => {
            printHtml(printContent.htmlContent);
        })
        .catch(error => console.error('Image loading failed:', error));
}

function removePrintArea(id) {
    $("iframe#" + id).remove();
}

function loadImage(src) {
    return new Promise((resolve, reject) => {
        var img = new Image();
        img.src = src;
        img.onload = () => resolve(img);
        img.onerror = reject;
    });
}



function printHtml(printContent){
    var idPrefix = "printArea_";
    removePrintArea(idPrefix + printAreaCount);
    printAreaCount++;
    var iframeId = idPrefix + printAreaCount;
    var iframeStyle = 'position:absolute;width:0px;height:0px;left:-500px;top:-500px;';
    iframe = document.createElement('IFRAME');
    $(iframe).attr({
        style: iframeStyle,
        id: iframeId
    });
    document.body.appendChild(iframe);
    var doc = iframe.contentWindow.document;
    doc.write(printContent);
    doc.close();
    var frameWindow = iframe.contentWindow;
    frameWindow.close();
    frameWindow.focus();
    frameWindow.print();
}
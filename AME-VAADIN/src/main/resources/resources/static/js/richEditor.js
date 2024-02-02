/*
 * Date:2020-09-21
 * Author:Wang Tingting
 * */
var E = window.wangEditor
var editor = new E('#editor_toolbar','#editor_container');
editor.customConfig.onchange = function (html) {
    document.getElementById("editor").dispatchEvent(
        new CustomEvent('editorChange', {
            "detail": {  //可携带额外的数据
                val: html
            },
        })
    );
}
editor.create();
function setVal(html){
    editor.txt.html(html);
}
function setReadonly(val){
    editor.$textElem.attr('contenteditable', !val);
}

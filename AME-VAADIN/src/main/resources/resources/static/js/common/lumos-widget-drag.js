//鼠标悬停事件
function mouseover(element) {
    if (element != null) {
        element.addEventListener("mouseover", function (event) {
            element.style.boxShadow = "0 0 15px rgba(77, 199, 244, 1)";
            element.style.cursor = 'move';
            event.stopPropagation();
        }, false);
        element.addEventListener("mouseout", function (event) {
            element.style.boxShadow = "";
            element.style.cursor = 'default';
            event.stopPropagation();
        }, false);
    }
}

function dragEnter(element) {
    if (element != null) {
        element.addEventListener('dragenter', function (event) {
            // 防止拖拉效果被重置，允许被拖拉的节点放入目标节点
            element.style.boxShadow = "0 0 15px rgba(105, 105, 105, 1)";
            element.style.transform = 'scale(1.02)';
            element.style.backgroundColor = '#f0f0f0';
            event.stopPropagation();
        }, false);
    }
}

function dragLeave(element) {
    if (element != null) {
        element.addEventListener('dragleave', function (event) {
            // 防止拖拉效果被重置，允许被拖拉的节点放入目标节点
            element.style.boxShadow = "";
            element.style.transform = 'scale(1.0)';
            element.style.backgroundColor = '';
            event.stopPropagation();
        }, false);
    }
}

function dragEnd(element) {
    if (element != null) {
        element.addEventListener('dragend', function (event) {
            let elements = document.getElementsByClassName("droppable");
            Array.prototype.forEach.call(elements, function (item) {
                console.log(item);
                item.style.backgroundColor = '';
                item.style.transform = 'scale(1.0)';
                item.style.boxShadow = "";
            });
            event.stopPropagation();
        }, false);
    }
}

function disableRootContextMenu(element) {
    //禁止root右击菜单
    element.addEventListener('contextmenu', function (e) {
        e.preventDefault();
    }, false);
}

let clientX = null;
let clientY = null;

//实时变更坐标位置
function dragOver(element) {
    element.addEventListener('dragover', function(event) {
        clientX = event.clientX - 350;
        clientY = event.clientY - 75;
        event.preventDefault();
        // 阻止元素被放置到其他区域的默认行为
    }, false);
}

//获取拖拽时的坐标
function getPositionXY() {
    const jsonObject = {
        x: clientX,
        y: clientY
    };
    return jsonObject;
}


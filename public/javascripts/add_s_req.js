/**
 * @author Bohan Zheng
 */
function addSimpleReq() {
    var simpleReq = document.getElementById("s_req");
    //alert(simpleReq);
    var simpleReqTitle = getSelectedText(simpleReq);
    var simpleReqList = document.getElementById("s_list");
    var li = document.createElement("li");
    li.innerHTML = "<span>" + simpleReqTitle + "</span><select name="+simpleReq.value+"><option value=''> </option><option value='and'>AND</option><option value='or'>OR</option><option value='not'>NOT</option></select>" + "<button onclick='deleteSimpleReq(this)'>Remove</button>";
    simpleReqList.appendChild(li);
}

function deleteSimpleReq(node) {
    var simpleReqList = document.getElementById("s_list");
    var d = node.parentNode;
    simpleReqList.removeChild(d);
}

function getSelectedText(obj) {
    for (var i = 0; i < obj.length; i++) {
        if (obj[i].selected == true) {
            return obj[i].innerHTML;
        }
    }
}

function generateExpression(simpleReqObjs) {
    var expression = "";
    for ( i = 0; i < simpleReqObjs.length; i++) {
        expression+=simpleReqObjs[i].getElementsByTagName("select")[0].name;
        expression+=simpleReqObjs[i].getElementsByTagName("select")[0].value;
    }
    return expression;
}

function doSubmit() {
    var hidden=document.createElement("input");
    hidden.type="hidden";
    var simpleReqObjs = new Array;
    simpleReqObjs = document.getElementById("s_list").getElementsByTagName("li");
    hidden.value=generateExpression(simpleReqObjs);
    hidden.name="sr_ids";
    document.getElementById("s_submit").appendChild(hidden);
}

/**
 * @author Bohan Zheng
 */
function addSimpleReq() {
    var simpleReq = document.getElementById("s_req");
    //alert(simpleReq);
    var simpleReqTitle = getSelectedText(simpleReq);
    var simpleReqList = document.getElementById("s_list");
    var li = document.createElement("li");
    li.innerHTML = "<a onclick='deleteSimpleReq(this)'></a>";
    if (simpleReqList.children.length > 1) {
        li.innerHTML += "<select name=" + simpleReq.value + "><option value='and'>AND</option><option value='or'>OR</option><option value='not'>NOT</option></select>";
    } else
        li.innerHTML += "<span style='width:29px;border:white;'>&nbsp;</span>";
    li.innerHTML += "<span onclick='changePriority(this)'>" + simpleReqTitle + "</span>";
    simpleReqList.appendChild(li);
}

function deleteSimpleReq(node) {
    var simpleReqList = document.getElementById("s_list");
    var d = node.parentNode;
    simpleReqList.removeChild(d);
    var liList = new Array;
    liList = simpleReqList.getElementsByTagName("li");
    if (liList.length > 0) {
        var fli = liList[0];
        var fselect = fli.children[1];
        var ftitle = fli.children[2];
        var fspan = generateSpace();
        fli.removeChild(fselect);
        fli.removeChild(ftitle);
        fli.appendChild(fspan);
        fli.appendChild(ftitle);
    }
}

function generateSpace() {
    var fspan = document.createElement("span");
    fspan.style.width = "29px";
    fspan.style.border = "white";
    fspan.innerHTML = "&nbsp;";
    return fspan;
}

function changePriority(node) {
    var p = node.parentNode;
    if (node.style.backgroundColor == "rgb(221, 221, 221)")
        node.style.backgroundColor = "#fff";
    else
        node.style.backgroundColor = "#ddd";
    p.removeChild(node);
    p.appendChild(node);
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
        expression += simpleReqObjs[i].getElementsByTagName("select")[0].name;
        expression += simpleReqObjs[i].getElementsByTagName("select")[0].value;
    }
    return expression;
}

function doSubmit() {
    var hidden = document.createElement("input");
    hidden.type = "hidden";
    var simpleReqObjs = new Array;
    simpleReqObjs = document.getElementById("s_list").getElementsByTagName("li");
    hidden.value = generateExpression(simpleReqObjs);
    hidden.name = "sr_ids";
    document.getElementById("s_submit").appendChild(hidden);
}

function init(simpleReqs) {
    var simpleReqs = [{
        "id" : "1",
        "name" : "simple requirement 1",
        "relation" : "",
        "priority" : "1"
    }, {
        "id" : "2",
        "name" : "simple requirement 2",
        "relation" : "and",
        "priority" : "1"
    }, {
        "id" : "3",
        "name" : "simple requirement 3",
        "relation" : "and",
        "priority" : "1"
    }];

    //var simpleReqs = eval("(" + simpleReqs + ")");

    var simpleReqList = document.getElementById("s_list");
    for ( i = 0; i < simpleReqs.length; i++) {
        var li = document.createElement("li");

        var button = document.createElement("a");
        button.onclick = function() {
            deleteSimpleReq(this);
        };
        li.appendChild(button);

        if (simpleReqs[i].relation == "") {
            var space = generateSpace();
            li.appendChild(space);
        } else {

            var select = document.createElement("select");
            select.name = simpleReqs[i].id;

            var and = document.createElement("option");
            if (simpleReqs[i].relation == "and")
                and.selected = "selected";
            and.innerHTML = "AND";
            var or = document.createElement("option");
            if (simpleReqs[i].relation == "or")
                or.selected = "selected";
            or.innerHTML = "OR";
            var not = document.createElement("option");
            if (simpleReqs[i].relation == "not")
                not.selected = "selected";
            not.innerHTML = "NOT";

            select.appendChild(and);
            select.appendChild(or);
            select.appendChild(not);
            li.appendChild(select);
        }
        var span = document.createElement("span");
        span.innerHTML = simpleReqs[i].name;
        span.onclick=function(){
            changePriority(this);
        };
        li.appendChild(span);

        simpleReqList.appendChild(li);
    }
}

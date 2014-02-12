/**
 * @author Bohan Zheng
 */
function addSimpleReq() {
    var simpleReq = document.getElementById("s_req");
    //alert(simpleReq);
    var simpleReqTitle = getSelectedText(simpleReq);
    var simpleReqList = document.getElementById("s_list");
    var li = document.createElement("li");
    li.innerHTML = "<span>" + simpleReqTitle + "</span><select name=" + simpleReq.value + "><option value=''> </option><option value='and'>AND</option><option value='or'>OR</option><option value='not'>NOT</option></select>" + "<button onclick='deleteSimpleReq(this)'>Remove</button>";
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
    // var simpleReqs = [{
        // "id" : "1",
        // "name" : "simple requirement 1",
        // "relation" : "and",
        // "priority" : "1"
    // }, {
        // "id" : "2",
        // "name" : "simple requirement 2",
        // "relation" : "and",
        // "priority" : "1"
    // }, {
        // "id" : "3",
        // "name" : "simple requirement 3",
        // "relation" : "",
        // "priority" : "1"
    // }];
    
    //var simpleReqs = eval("(" + simpleReqs + ")");
    
    var simpleReqList = document.getElementById("s_list");
    for ( i = 0; i < simpleReqs.length; i++) {
        var li = document.createElement("li");

        var span = document.createElement("span");
        span.innerHTML = simpleReqs[i].name;
        li.appendChild(span);

        var select = document.createElement("select");
        select.name = simpleReqs[i].id;

        var nothing = document.createElement("option");
        if (simpleReqs[i].relation == "")
            nothing.selected = "selected";
        nothing.innerHTML = "";
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
        
        select.appendChild(nothing);
        select.appendChild(and);
        select.appendChild(or);
        select.appendChild(not);
        li.appendChild(select);
        
        var button=document.createElement("button");
        button.onclick="deleteSimpleReq(this)";
        button.innerText="Remove";
        li.appendChild(button);
        
        simpleReqList.appendChild(li);
    }
}

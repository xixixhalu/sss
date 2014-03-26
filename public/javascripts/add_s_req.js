/**
 * @author Bohan Zheng
 */
function addSimpleReq() {
    var simpleReq = document.getElementById("s_req");
    if (checkExistReq(simpleReq.value)) {
        alert("This simple reqirement has already been added!!");
        return;
    }
    //alert(simpleReq);
    var simpleReqTitle = getSelectedText(simpleReq);
    var simpleReqList = document.getElementById("s_list");
    var li = document.createElement("li");
    li.innerHTML = "<a onclick='deleteSimpleReq(this)'></a>";
    if (simpleReqList.children.length > 1) {
        li.innerHTML += "<select name='simpleReqRel'><option value='and'>AND</option><option value='or'>OR</option><option value='not'>NOT</option></select>";
    } else
        li.innerHTML += "<span name='simpleReqRel' style='width:29px;border:white;' value=''>&nbsp;</span>";
    li.innerHTML += "<input type='hidden' name='simpleReqId' value=" + simpleReq.value + ">";
    li.innerHTML += "<span>" + simpleReqTitle + "</span>";
    li.innerHTML += "<input type='text' name='simpleReqGroup' value='1'>";
    simpleReqList.appendChild(li);
}

function checkExistReq(id) {
    var ids = document.getElementsByName("simpleReqId");
    for ( i = 0; i < ids.length; i++) {
        if (id == ids[i].value) {
            return true;
        }
    }
    return false;
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
        var ftitle = fli.children[3];
        var fgroup = fli.children[4];
        var fspan = generateSpace();
        fli.removeChild(fselect);
        fli.removeChild(ftitle);
        fli.removeChild(fgroup);
        fli.appendChild(fspan);
        fli.appendChild(ftitle);
        fli.appendChild(fgroup);
    }
}

function generateSpace() {
    var fspan = document.createElement("span");
    fspan.style.width = "29px";
    fspan.style.border = "white";
    fspan.innerHTML = "&nbsp;";
    fspan.setAttribute("name", "simpleReqRel");
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

//define a course object
function courseObj(cId, cName, cRelation, cGroup) {
    this.id = cId;
    this.name = cName;
    this.relation = cRelation;
    this.group = cGroup;
}

function generateJsonObj(simpleReqObjs) {
    var temp = new Array;
    for ( i = 0; i < simpleReqObjs.length; i++) {
        //create a course object
        var id = document.getElementsByName("simpleReqId")[i].value;
        var nameobj = simpleReqObjs[i];
        var name = nameobj.children[3].innerHTML;
        var relations = document.getElementsByName("simpleReqRel");
        var relation = relations[i].value;
        var group = document.getElementsByName("simpleReqGroup")[i].value;
        var req = new courseObj(id, name, relation, group);
        temp.push(req);
    }
    return temp;
}

function doSubmit() {
    if (checkRequirementForm()) {
        var hidden = document.createElement("input");
        hidden.type = "hidden";
        var simpleReqObjs = new Array;
        simpleReqObjs = document.getElementById("s_list").getElementsByTagName("li");
        var jsonObj = generateJsonObj(simpleReqObjs);
        hidden.value = JSON.stringify(jsonObj);
        hidden.name = "sr_ids";
        document.getElementById("s_submit").appendChild(hidden);
        return true;
    } else
        return false;
}

function init(simpleReqs) {

    //var simpleReqs = eval("(" + simpleReqs + ")");

    var simpleReqList = document.getElementById("s_list");
    for ( i = 0; i < simpleReqs.length; i++) {
        var li = document.createElement("li");

        var hidden = document.createElement("input");
        hidden.value = simpleReqs[i].id;
        hidden.type = "hidden";
        hidden.name = "simpleReqId";

        var button = document.createElement("a");
        button.onclick = function() {
            deleteSimpleReq(this);
        };
        li.appendChild(button);

        if (simpleReqs[i].relation == "" || !simpleReqs[i].relation) {
            var space = generateSpace();
            li.appendChild(space);
        } else {
            var select = document.createElement("select");
            select.name = "simpleReqRel";

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

        li.appendChild(hidden);

        var span = document.createElement("span");
        span.innerHTML = simpleReqs[i].name;
        span.onclick = function() {
            changePriority(this);
        };
        li.appendChild(span);

        var text = document.createElement("input");
        text.type = "text";
        text.value = simpleReqs[i].group;
        text.name = "simpleReqGroup";
        li.appendChild(text);
        //添加组
        simpleReqList.appendChild(li);
    }
}

/**
 * @author Bohan Zheng
 */

/**
 * add an simple requirement into the requirement list
 */
function addSimpleReq() {
    var simpleReq = document.getElementById("s_req");
    if (checkExistReq(simpleReq.value)) {
        alert("This simple requirement has already been added!!");
        return;
    }
    //alert(simpleReq);
    var simpleReqTitle = getSelectedText(simpleReq);
    var simpleReqList = document.getElementById("s_list");
    var li = document.createElement("li");
    li.innerHTML = "<a onclick='deleteSimpleReq(this)'></a>";
    li.innerHTML += "<input type='hidden' name='simpleReqId' value=" + simpleReq.value + ">";
    if (simpleReqList.children.length > 1) {
        li.innerHTML += "<select name='simpleReqRel'><option value='and'>AND</option><option value='or'>OR</option><option value='not'>NOT</option></select>";
    } else
        li.innerHTML += "<span name='simpleReqRel' style='width:29px;border:white;' value=''>&nbsp;</span>";
    li.innerHTML += "<span>" + simpleReqTitle + "</span>";
    li.innerHTML += "<input type='text' name='simpleReqGroup' value='1'>";
    simpleReqList.appendChild(li);
}
/**
 * check the simple requirement is already exist in the list
 * @param id
 *  id of simple requirement
 */
function checkExistReq(id) {
    var ids = document.getElementsByName("simpleReqId");
    for ( i = 0; i < ids.length; i++) {
        if (id == ids[i].value) {
            return true;
        }
    }
    return false;
}
/**
 * delete the simple requirement from the list
 * @param node
 *  the li node contains the simple requirement
 */
function deleteSimpleReq(node) {
    var simpleReqList = document.getElementById("s_list");
    var d = node.parentNode;
    simpleReqList.removeChild(d);
    var liList = new Array;
    liList = simpleReqList.getElementsByTagName("li");
    if (liList.length > 0) {
        var fli = liList[0];
        var fselect = fli.children[2];
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
/**
 * generate the wihte space before the first simple requirement
 */
function generateSpace() {
    var fspan = document.createElement("span");
    fspan.style.width = "29px";
    fspan.style.border = "white";
    fspan.innerHTML = "&nbsp;";
    fspan.setAttribute("name", "simpleReqRel");
    return fspan;
}
/**
 * changing the node priority by changing it background color (not used)
 * @param  node
 */
function changePriority(node) {
    var p = node.parentNode;
    if (node.style.backgroundColor == "rgb(221, 221, 221)")
        node.style.backgroundColor = "#fff";
    else
        node.style.backgroundColor = "#ddd";
    p.removeChild(node);
    p.appendChild(node);
}
/**
 * get the text user select in the select box.
 * @param obj
 * the input object whose type is select
 */
function getSelectedText(obj) {
    for (var i = 0; i < obj.length; i++) {
        if (obj[i].selected == true) {
            return obj[i].innerHTML;
        }
    }
}

/**
 * define a course Object
 * @param  cId
 * course id
 * @param  cName
 * course name
 * @param  cRelation
 * the relation with the course before it
 * @param  cGroup
 * priority group. for example (cs101 and cs102) or (cs103 and cs104)
 * in this case, we could define cs101, cs102 cGroup to be 1 and cs103, cs104 cGroup to be 2, 
 * the courses with the same group value will group together, 
 * (you need to store the same group together in an array)
 */
function courseObj(cId, cName, cRelation, cGroup) {
    this.id = cId;
    this.name = cName;
    this.relation = cRelation;
    this.group = cGroup;
}
/**
 * generate a json object from simple requirement list
 * @param {Object} simpleReqObjs
 * array of li nodes containing simple requirement
 */
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
/**
 * submit the data to the server
 */
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

/**
 * initiate the page
 * @param simpleReqs
 */
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
        li.appendChild(hidden);
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
            and.value="and";
            var or = document.createElement("option");
            if (simpleReqs[i].relation == "or")
                or.selected = "selected";
            or.innerHTML = "OR";
            or.value="or";
            var not = document.createElement("option");
            if (simpleReqs[i].relation == "not")
                not.selected = "selected";
            not.innerHTML = "NOT";
            not.value="not";
            select.appendChild(and);
            select.appendChild(or);
            select.appendChild(not);
            li.appendChild(select);
        }

        var span = document.createElement("span");
        span.innerHTML = simpleReqs[i].name;
        // span.onclick = function() {
        // changePriority(this);
        // };
        li.appendChild(span);

        var text = document.createElement("input");
        text.type = "text";
        text.value = simpleReqs[i].group;
        text.name = "simpleReqGroup";
        li.appendChild(text);
        //add group
        simpleReqList.appendChild(li);
    }
}

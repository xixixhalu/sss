/**
 * @author Bohan Zheng
 */

var courseObjs;
/**
 * get all courses data from the server, initiate the data to json object
 * */
window.onload = function() {
    var jsonData = document.getElementById("jsonData").innerText;
    courseObjs = eval("(" + jsonData + ")");
};
/**
 * initiate the style sheet for some dynamic element
 * */
$(document).ready(function() {
    $("ul.req_course_list li:odd").css("background-color", "#F8F8F8");

    var helpPanelWidth = (document.body.clientWidth - 1000) / 2;
    $("#helpPanelLeft").css("width", helpPanelWidth);

    $(window).resize(function() {
        helpPanelWidth = (document.body.clientWidth - 1000) / 2;
        $('#helpPanelLeft').css("width", helpPanelWidth);
        $('helpPanelRight').css('width', helpPanelWidth);
    });
});
/**
 * drop down effect for the requirement
 * @param id
 *         - id of the requirement clicked
 * */
function dropDown(id) {
    var node = document.getElementById(id);
    var nodes = node.children;
    var titleStyle = nodes[0].style.display;
    var listStyle = nodes[1].style.display;
    if (titleStyle == "none") {
        nodes[0].style.display = "block";
        nodes[1].style.display = "none";
    } else {
        nodes[1].style.display = "block";
        nodes[0].style.display = "none";
    }
}

/**
 * expand the clicked course group tab at the right side panel
 * 
 */
function expand(id) {
    var node = document.getElementById(id);
    var nodes = node.children;
    var titleStyle = nodes[0].style.display;
    var listStyle = nodes[1].style.display;

    nodes[1].style.display = "block";
    nodes[0].style.display = "none";
}

/**
 * shrink the clicked course group tab at the right side panel
 */
function shrink(id) {
    var node = document.getElementById(id);
    var nodes = node.children;
    var titleStyle = nodes[0].style.display;
    var listStyle = nodes[1].style.display;

    nodes[0].style.display = "block";
    nodes[1].style.display = "none";
}

/**
 * expand all the course group tabs at the right side panel
 */
function expandAll() {
    var req_list = $('#reqs_list')[0].children;
    for (var i = 0; i < req_list.length; i++) {
        var req = req_list[i];
        if (req.tagName == 'LI') {
            expand(req.id);
        }
    }
}

/**
 * shrink all the course group tabs at the right side panel
 */
function shrinkAll() {
    var req_list = $('#reqs_list')[0].children;
    for (var i = 0; i < req_list.length; i++) {
        var req = req_list[i];
        if (req.tagName == 'LI') {
            shrink(req.id);
        }
    }
}

//adding course to want-to-take list

function addLikeCourse(id, curNode) {
    var oldColor = curNode.parentElement.style.color;
    curNode.parentElement.style.color = 'red';
    if (!checkConstraints(id)) {

        curNode.parentElement.style.color = oldColor;

        return null;
    }
    curNode.parentElement.style.color = oldColor;
    var wantTake = document.getElementById("wantTake");
    wantTake.appendChild(generateLi(id, curNode));
    var name = curNode.parentElement.className;
    var sameCourse = document.getElementsByClassName(name);
    for ( i = 0; i < sameCourse.length; i++) {
        sameCourse[i].style.display = "none";
    }
}

/**
 * adding course to already-taken list
 * @param id
 * - id of the course clicked
 * @param curNode
 * - the current course element clicked
 * */
function addTakenCourse(id, curNode) {
    if (checkCourseExist(id)) {
        alert("This course is already in the course bin!");
        return null;
    }
    var wantTake = document.getElementById("alreadyTaken");
    wantTake.appendChild(generateLi(id, curNode));
    var name = curNode.parentElement.className;
    var sameCourse = document.getElementsByClassName(name);
    for ( i = 0; i < sameCourse.length; i++) {
        sameCourse[i].style.display = "none";
    }
}

/**
 * adding course to already-taken list
 * @param id
 * - id of the course clicked
 * @param curNode
 * - the current course element clicked
 * @return courseLi
 * - the <li> element containing the course
 * */
function generateLi(id, curNode) {
    var courseLi = document.createElement("li");
    courseLi.id = id;
    courseLi.innerHTML = courseObjs[id].prefix + courseObjs[id].num + " - " + courseObjs[id].title;
    var simpleReqId = curNode.parentElement.parentElement.id;
    var complexReqId = curNode.parentElement.parentElement.parentElement.parentElement.parentElement.id;
    simpleReqId = simpleReqId.substring(3);
    complexReqId = complexReqId.substring(4);
    courseLi.innerHTML += "<input type='hidden' value='" + simpleReqId + "' name='simpleReqId'>";
    courseLi.innerHTML += "<input type='hidden' value='" + complexReqId + "' name='complexReqId'>";
    courseLi.innerHTML += "<a onclick='removeCourse(" + id + ")'><i class='fa fa-times-circle'></i></a>";
    return courseLi;
}

/**
 * check pre&corequisite constraints and if the course is already in the course bin.
 * @param id
 * - the id of the course
 */
function checkConstraints(id) {
    if (checkCourseExist(id)) {
        alert("This course is already in the course bin!");
        return false;
    }
    if (!checkPrereq(id)) {
        var str = courseObjs[id].prefix + courseObjs[id].num + " has the prerequisite constraints!\n";
        var prereqs = courseObjs[id].prereq;
        for ( i = 0; i < prereqs.length; i++) {
            var relation = prereqs[i].relation;
            if (prereqs[i].relation == 'and')
                relation = ',';
            str += " " + relation + " " + prereqs[i].prefix + prereqs[i].num;
        }
        alert(str);
        return false;
    }
    if (!checkCoreq(id)) {
        var str = courseObjs[id].prefix + courseObjs[id].num + " has the corequisite constraints!\n";
        var coreqs = courseObjs[id].coreq;
        for ( i = 0; i < coreqs.length; i++) {
            var relation = coreqs[i].relation;
            if (coreqs[i].relation == 'and')
                relation = ',';
            str += " " + coreqs[i].relation + " " + coreqs[i].prefix + coreqs[i].num;
        }
        alert(str);
        return false;
    }
    return true;
}

/**
 * get all courses in the course bin want-to-take list
 * @return Array courses
 * - containing the id of all the courses in the want to take list
 * */
function getLikeCourses() {
    var courseNodes = document.getElementById("wantTake").children;
    var courses = new Array;
    for ( i = 0; i < courseNodes.length; i++) {
        //courses[courseNodes[i].id]=courseNodes[i].innerText;
        courses.push(courseNodes[i].id);
    }
    return courses;
}

/**
 * get all courses in the course bin already-taken list
 * @return Array courses
 * - containing the id of all the courses in the already-taken list
 * */
function getTakenCourses() {
    var courseNodes = document.getElementById("alreadyTaken").children;
    var courses = new Array;
    for ( i = 0; i < courseNodes.length; i++) {
        //courses[courseNodes[i].id]=courseNodes[i].innerText;
        courses.push(courseNodes[i].id);
    }
    return courses;
}

/**
 * check if the course is already in the course bin
 * @param id
 * - id of the course
 * */
function checkCourseExist(id) {
    var likeCourses = getLikeCourses();
    var takenCourses = getTakenCourses();
    var courses = likeCourses.concat(takenCourses);
    for ( i = 0; i < courses.length; i++) {
        if (id == courses[i]) {
            return true;
        }
    }
    return false;
}

/**
 * remove course from course bin
 * @param curId
 * - id of the course
 * */
function removeCourse(curId) {
    //get the element of the course need to be remove
    var courseLi = document.getElementById(curId);

    var oldColor = courseLi.style.color;
    //get all courses in the course bin
    var allCourses = getLikeCourses().concat(getTakenCourses());
    //loop all courses in the course bin
    for ( i = 0; i < allCourses.length; i++) {
        var id = allCourses[i];
        var prereq = new Array;
        var coreq = new Array;
        //check if the course has prerequisite
        if (courseObjs[id].prereq) {
            //get it prerequisite，check if its prerequisite containing the course need to be remove
            prereq = courseObjs[id].prereq;
            for ( j = 0; j < prereq.length; j++) {
                if (prereq[j].id == curId) {
                    //if yes，alert user this course cannot be remove
                    courseLi.style.color = 'red';
                    var curCourse = courseObjs[id].prefix + courseObjs[id].num;
                    alert(courseObjs[curId].prefix + courseObjs[curId].num + " is the prerequisite of " + curCourse);

                    courseLi.style.color = oldColor;

                    return false;
                }
            }
        }
        //check corequisite
        if (courseObjs[id].coreq) {
            coreq = courseObjs[id].coreq;
            for ( j = 0; j < coreq.length; j++) {
                if (coreq[j].id == curId) {
                    courseLi.style.color = 'red';
                    var curCourse = courseObjs[id].prefix + courseObjs[id].num;
                    alert(courseObjs[curId].prefix + courseObjs[curId].num + " is the corequisite of " + curCourse);
                    courseLi.style.color = oldColor;
                    return false;
                }
            }
        }
    }
    courseLi.style.color = oldColor;
    courseLi.parentElement.removeChild(courseLi);
    var sameCourses = document.getElementsByClassName("c" + curId);
    for ( i = 0; i < sameCourses.length; i++) {
        sameCourses[i].style.display = "block";
    }
}

/**
 * check prerequisites
 * @param id
 * id of the course
 * */
function checkPrereq(id) {
    var p = false;

    var prereq = new Array;

    if (courseObjs[id].prereq)
        prereq = courseObjs[id].prereq;

    if (prereq.length == 0)
        return true;

    var likeCourses = getLikeCourses();
    var takenCourses = getTakenCourses();
    var courses = likeCourses.concat(takenCourses);

    var prereqCourses = new Array;

    //check if the prerequisites exist in course bin ignoring the relation
    for ( i = 0; i < courses.length; i++) {
        for ( reqNum = 0; reqNum < prereq.length; reqNum++) {
            if (courses[i] == prereq[reqNum].id)
                prereqCourses.push(courses[i]);
        }
    }
    //if exist check if they satisfy the relation
    if (prereqCourses.length > 0) {

        //record the status (satisfied or not) of every group in the prerequisites.
        var ifsatisfy = new Array;
        //get the total number of the group in the prerequisites.
        var gs = new Array;
        for ( i = 0; i < prereq.length; i++) {
            if (i == 0) {
                gs.push(prereq[i].group);
            } else {
                if (prereq[i].group != prereq[i - 1].group) {
                    gs.push(prereq[i].group);
                }
            }
        }
        //get the relationship between each group，save for later use
        var groupRelation = new Array;
        while (gs.length > 0) {
            //deal with the first group in the prerequisite
            var g = gs.shift();
            //get the courses in the group
            var group = new Array;
            for ( i = 0; i < prereq.length; i++) {
                if (prereq[i].group == g) {
                    group.push(prereq[i]);
                }
            }
            var total = group.length;
            //check the relationship between the courses within the group
            try {
                //if there is only one course in the group, then use 'or' as default relation
                var relation = group[1].relation;
            } catch(e) {
                var relation = "or";
            }
            groupRelation.push(group[0].relation);
            if (relation == "or") {
                for ( i = 0; i < prereqCourses.length; i++) {
                    for ( j = 0; j < group.length; j++) {
                        if (prereqCourses[i] == group[j].id) {
                            total--;
                        }
                    }
                }
                if (total < group.length) {
                    ifsatisfy.push(true);
                } else
                    ifsatisfy.push(false);
            } else if (relation == "and") {
                for ( i = 0; i < prereqCourses.length; i++) {
                    for ( j = 0; j < group.length; j++) {
                        if (prereqCourses[i] == group[j].id) {
                            total--;
                        }
                    }
                }
                if (total == 0) {
                    ifsatisfy.push(true);
                } else
                    ifsatisfy.push(false);
            } else {
                //reserve for 'not' relationship
            }
            //finished with the first group, going to deal with the next group
        }

        //check the relationship between the groups
        if (groupRelation.length == 1) {
            //if there is only one group
            return ifsatisfy[0];
        } else {
            //more than one group
            if (groupRelation[1] == "or") {
                var ret = false;
                for ( i = 0; i < ifsatisfy.length; i++) {
                    ret = ifsatisfy[i] || ret;
                }
            } else if (groupRelation[1] == "and") {
                var ret = true;
                for ( i = 0; i < ifsatisfy.length; i++) {
                    ret = ifsatisfy[i] && ret;
                }
            } else {
                //reserve for 'not' relationship
            }
        }
        return ret;
    } else {
        return false;
    }
}

/**
 * check corequisites
 * @param id
 * id of the course
 * */
function checkCoreq(id) {
    var c = false;
    var coreq = new Array;
    if (courseObjs[id].coreq)
        coreq = courseObjs[id].coreq;

    if (coreq.length == 0)
        return true;

    var likeCourses = getLikeCourses();
    var takenCourses = getTakenCourses();
    var courses = likeCourses.concat(takenCourses);

    //check corequisites with the same pattern
    var coreqCourses = new Array;
    //if course bin contains the corequisite of this course then add this corequisite into coreqCourses Array
    for ( i = 0; i < courses.length; i++) {
        for ( reqNum = 0; reqNum < coreq.length; reqNum++) {
            if (courses[i] == coreq[reqNum].id)
                coreqCourses.push(courses[i]);
        }
    }
    //if it has corequisite，deal with it，if not return false；
    if (coreqCourses.length > 0) {
        //c = true;
        //record the status (satisfied or not) of every group in the prerequisites.
        var ifsatisfy = new Array;
        //get the total number of the group in the prerequisites.
        var gs = new Array;
        for ( i = 0; i < coreq.length; i++) {
            if (i == 0) {
                gs.push(coreq[i].group);
            } else {
                if (coreq[i].group != coreq[i - 1].group) {
                    gs.push(coreq[i].group);
                }
            }
        }
        //get the relationship between each group，save for later use
        var groupRelation = new Array;
        while (gs.length > 0) {
            //deal with the first group in the prerequisite
            var g = gs.shift();
            //get the courses in the first group
            var group = new Array;
            for ( i = 0; i < coreq.length; i++) {
                if (coreq[i].group == g) {
                    group.push(coreq[i]);
                }
            }
            var total = group.length;
            //check the relationship between the courses within the group
            try {
                //if there is only one course in the group, then use 'or' as default relation
                var relation = group[1].relation;
            } catch(e) {
                var relation = "or";
            }
            groupRelation.push(group[0].relation);
            if (relation == "or") {
                for ( i = 0; i < coreqCourses.length; i++) {
                    for ( j = 0; j < group.length; j++) {
                        if (coreqCourses[i] == group[j].id) {
                            total--;
                        }
                    }
                }
                if (total < group.length) {
                    ifsatisfy.push(true);
                } else
                    ifsatisfy.push(false);
            } else if (relation == "and") {
                for ( i = 0; i < coreqCourses.length; i++) {
                    for ( j = 0; j < group.length; j++) {
                        if (coreqCourses[i] == group[j].id) {
                            total--;
                        }
                    }
                }
                if (total == 0) {
                    ifsatisfy.push(true);
                } else
                    ifsatisfy.push(false);
            } else {
                //reserve for 'not' relationship
            }
            //finished with the first group, going to deal with the next group
        }
        //deal with the relationship between the group
        if (groupRelation.length == 1) {
            //if only has on group
            return ifsatisfy[0];
        } else {
            //if not
            if (groupRelation[1] == "or") {
                var ret = false;
                for ( i = 0; i < ifsatisfy.length; i++) {
                    ret = ifsatisfy[i] || ret;
                }
            } else if (groupRelation[1] == "and") {
                var ret = true;
                for ( i = 0; i < ifsatisfy.length; i++) {
                    ret = ifsatisfy[i] && ret;
                }
            } else {
                //reserve for 'not' relationship
            }
        }
        return ret;
    } else {
        return false;
    }
}

/**
 * course object for want to take courses
 * @param id
 * - id of the course
 * @param cid
 * - id of the complex requirement containing the course
 * @param sid
 * - id of the simple requirement containing the course
 * */
function wantTakeCourse(id, sid, cid) {
    this.id = id;
    this.sid = sid;
    this.cid = cid;
}
/**
 * The data structure wrapped with course id and course maximum depth
 * the maximum depth will be filled after clicking "AUTO_FILL_COURSE"
 */
function ASO(id, maxDepth) {
    this.id = id;
    this.maxDepth = maxDepth;
}

/**
 * the function invoked when clicking "NEXT" button
 * the form get submitted by this function
 */
function submitCourse(form) {
    /* create hidden field of selected courses */
    var acForm = document.getElementById("acForm");
    var wantTake = document.getElementById("wantTake").getElementsByTagName("li");
    var alreadyTaken = document.getElementById("alreadyTaken").getElementsByTagName("li");
    /***************************************************/
    var dataArray = new Array;
    for ( i = 0; i < wantTake.length; i++) {
        var id = wantTake[i].id;
        var sid = wantTake[i].getElementsByTagName("input")[0].value;
        var cid = wantTake[i].getElementsByTagName("input")[1].value;
        dataArray.push(new wantTakeCourse(id, sid, cid));
    }
    var json = JSON.stringify(dataArray);

    var inp = document.createElement("input");
    inp.setAttribute("type", "hidden");
    inp.setAttribute("name", "wantTakeCourses");
    inp.setAttribute("value", json);
    acForm.appendChild(inp);
    /***************************************************/
    var dataArray = new Array;
    for ( i = 0; i < alreadyTaken.length; i++) {
        var id = alreadyTaken[i].id;
        var sid = alreadyTaken[i].getElementsByTagName("input")[0].value;
        var cid = alreadyTaken[i].getElementsByTagName("input")[1].value;
        dataArray.push(new wantTakeCourse(id, sid, cid));
    }
    var json = JSON.stringify(dataArray);

    var inp = document.createElement("input");
    inp.setAttribute("type", "hidden");
    inp.setAttribute("name", "alreadyTakenCourses");
    inp.setAttribute("value", json);
    acForm.appendChild(inp);
    /***************************************************/

    var dataArray = new Array;
    for ( i = 0; i < wantTake.length; i++) {
        var id = wantTake[i].id;
        // var maxDepth = 1;
        // if (wantTake[i].getElementsByTagName("input")[2])
        	maxDepth = wantTake[i].getElementsByTagName("input")[2].value;
        dataArray.push(new ASO(id, maxDepth));
    }

    var inpASO = document.createElement("input");
    inpASO.setAttribute("type", "hidden");
    inpASO.setAttribute("name", "aso");
    inpASO.setAttribute("value", JSON.stringify(dataArray));
    acForm.appendChild(inpASO);
    /***************************************************/
    form.submit();
}

/**
 * auto fill course bin
 * */
function autoCourse() {
    var c = confirm("Choose your desired courses first, after click the auto there is no going back.\nHave you finished your choice? ");
    if (c == false)
        return;
    var wantTake = document.getElementById("wantTake").getElementsByTagName("li");
    var wantDataArray = new Array;
    for ( i = 0; i < wantTake.length; i++) {
        var id = wantTake[i].id;
        var sid = wantTake[i].getElementsByTagName("input")[0].value;
        var cid = wantTake[i].getElementsByTagName("input")[1].value;
        wantDataArray.push(new wantTakeCourse(id, sid, cid));
    }

    var alreadyTaken = document.getElementById("alreadyTaken").getElementsByTagName("li");
    var alreadyDataArray = new Array;
    for ( i = 0; i < alreadyTaken.length; i++) {
        var id = alreadyTaken[i].id;
        var sid = alreadyTaken[i].getElementsByTagName("input")[0].value;
        var cid = alreadyTaken[i].getElementsByTagName("input")[1].value;
        alreadyDataArray.push(new wantTakeCourse(id, sid, cid));
    }

    var json = eval('({"wantTakeCourses":' + JSON.stringify(wantDataArray) + ', "alreadyTakenCourses":' + JSON.stringify(alreadyDataArray) + '})');

    /* null manipulation */
    if (true) {
        var url = "/student/autoFillCourse";
        $.post(url, {
            wantTakeCourses : JSON.stringify(wantDataArray),
            alreadyTakenCourses : JSON.stringify(alreadyDataArray)
        }, function(data) {
            var ul_want = document.getElementById('wantTake');
            //!!!!!!!!!!!!! var ul_already = document.getElementById('alreadyTaken');

            var courseArr = eval('(' + data + ')');
            var wantArr = courseArr.want;
            var alreadyArr = courseArr.already;

            for (var i = 0; i < wantArr.length; i++) {
                var id = wantArr[i].id;
                var f = false;
                var likeCourses = getLikeCourses();
                var takenCourses = getTakenCourses();
                var courses = likeCourses.concat(takenCourses);
                for ( j = 0; j < courses.length; j++) {
                    if (courses[j] == id)
                        f = true;
                }
                if (f) {
                    var inp = document.createElement('input');
                    inp.setAttribute('type', 'hidden');
                    inp.setAttribute('value', wantArr[i].maxDepth);
                    inp.setAttribute('name', 'maxDepth');
                    document.getElementById(id).appendChild(inp);
                    continue;
                }
                var li = document.createElement('li');
                li.id = id;
                li.innerHTML = courseObjs[id].prefix + courseObjs[id].num + ' - ' + courseObjs[id].title + '<a onclick="removeCourse(' + id + ')"><i class="fa fa-times-circle"></i></a>' + '<input type="hidden" value="-1" name="simpleReqId">' + '<input type="hidden" value="-1" name="complexReqId">' + '<input type="hidden" value="' + wantArr[i].maxDepth + '" name="maxDepth">';
                li.style.color = "#9f9f9f";
                ul_want.appendChild(li);

                var name = 'c' + li.id;
                var sameCourse = document.getElementsByClassName(name);
                for (var j = 0; j < sameCourse.length; j++) {
                    sameCourse[j].style.display = "none";
                }
            }

            // post-condition
            document.getElementById('auto_next_course_button').innerHTML = "NEXT STEP";

            $('#undo_fill_button')[0].className = 'button left_auto pure-button button-secondary';
			$('#take_mandatory_button')[0].className = 'pure-button pure-button-disabled';

            expandAll();

            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            // for (var i = 0; i < alreadyArr.length; i++) {
            // var li = document.createElement('li');
            // li.innerHTML = wantArr[i].prefix + wantArr[i].num + ' - ' + wantArr[i].title;
            // ul_already.appendChild(li);
            // }

        });
    }
}

//switch button status in the bottom of course bin
function auto_next_course_action(form) {
    var element_text = $('#auto_next_course_button').html();
    if (element_text == 'AUTO FILL COURSES') {
        autoCourse();
        return false;
    } else if (element_text == 'NEXT STEP') {
        submitCourse(form);
        return true;
    }
}

//take all courses of a specified simple requirement

function take_all_action(srElement) {
    var courses = srElement.getElementsByTagName('li');
    for (var count = 0; count < courses.length; count++) {
        var id = courses[count].className.substring(1);
        var curNode = courses[count].getElementsByClassName('likebutton')[0];
        var oldColor = curNode.parentElement.style.color;
        if (checkCourseExist(id))
            continue;
        if (!checkPrereq(id)) {
            var str = courseObjs[id].prefix + courseObjs[id].num + " has the prerequisite constraints!\n";
            var prereqs = courseObjs[id].prereq;
            for ( i = 0; i < prereqs.length; i++) {
                var relation = prereqs[i].relation;
                if (prereqs[i].relation == 'and')
                    relation = ',';
                str += " " + relation + " " + prereqs[i].prefix + prereqs[i].num;
            }
            curNode.parentElement.style.color = 'red';
            alert(str);
            curNode.parentElement.style.color = oldColor;
            return false;
        }
        if (!checkCoreq(id)) {
            var str = courseObjs[id].prefix + courseObjs[id].num + " has the corequisite constraints!\n";
            var coreqs = courseObjs[id].coreq;
            for ( i = 0; i < coreqs.length; i++) {
                var relation = coreqs[i].relation;
                if (coreqs[i].relation == 'and')
                    relation = ',';
                str += " " + coreqs[i].relation + " " + coreqs[i].prefix + coreqs[i].num;
            }
            curNode.parentElement.style.color = 'red';
            alert(str);
            curNode.parentElement.style.color = oldColor;
            return false;
        }
        curNode.parentElement.style.color = oldColor;
        addLikeCourse(id, curNode);
    }

}
/**
 * This function undoes the AUTO_FILL_COURSE step
 * the system selected courses will be cleared from the course bin
 * additional, expanded tabs will be shrinked
 */
function undo_fill() {
	
	if ($('#undo_fill_button')[0].className == 'button left_auto pure-button button-secondary') {
		if (confirm("Are you sure to undo the auto fill step?") == true) {
		    // first visually clear course bin
		    var ul_want = document.getElementById('wantTake');
		    for (var i = 0; i < ul_want.children.length; i++) {
		        var li_want = ul_want.children[i];
		        if (li_want.style.color == "rgb(159, 159, 159)") {
		            ul_want.removeChild(li_want);
		            i--;
		        }
		    }
		    var element_text = $('#auto_next_course_button');
		    element_text.html('AUTO FILL COURSES');
		
		    $('#undo_fill_button')[0].className = 'button left_auto pure-button pure-button-disabled';
		
		    var req_course_list = $('.req_course_list');
		    for (var j = 0; j < req_course_list.length; j++) {
		        var courses = req_course_list[j].children;
		        for (var i = 0; i < courses.length; i++) {
		            var course = courses[i];
		            if (course.tagName == 'LI') {
		                var picked = 0;
		                var wantTake = document.getElementById("wantTake").children;
		                for (var k = 0; k < wantTake.length; k++) {
		                    if (course.className == 'c' + wantTake[k].id) {
		                        picked = 1;
		                        break;
		                    }
		                }
		                if (course.style.display == 'none' && picked == 0) {
		                    course.style.display = 'inline';
		                }
		            }
		        }
		    }
		
		    shrinkAll();
		
		    // ajax
		    var url = "/student/undo_fill";
		    var degreeid = $('#degreeid').val();
		    $.post(url, {
		        degreeId : degreeid
		    }, function(data) {});
		}
   }
    
}

/**
 * the function invoked when "TAKE_ALL_MANDATORY" button being clicked
 * @param {Object} parentElem
 */
function take_all_mandatory_action(parentElem) {
	if ($('#take_mandatory_button')[0].className == 'button-warning pure-button') {
		if (confirm("Are you sure to take all the mandatory courses?") == true) {
			$('#take_mandatory_button')[0].className = 'pure-button pure-button-disabled';
			
		    var nodes = parentElem.children;
		    for (var i = 0; i < nodes.length; i++) {
		        if (nodes[i].tagName != 'LI')
		            continue;
		        var req_courses = nodes[i].getElementsByClassName("req_course_list");
		        if (req_courses.length != 1)
		            continue;
		        for (var j = 0; j < req_courses.length; j++) {
		            var desc = req_courses[j].getElementsByClassName('req_desc')[0].innerHTML;
		            var patt = /[0-9]+/g;
		            var req_number = patt.exec(desc);
		            var courses = req_courses[j].getElementsByTagName('li');
		            if (req_number == courses.length) {
		                expand(nodes[i].id);
		                for (var z = 0; z < courses.length; z++) {
		                    var display = courses[z].getAttribute('style');
		                    if (display == null || display.replace(/\s/g, "").indexOf('display:none') < 0) {
		                        var wantTake = document.getElementById("wantTake");
		                        var id = courses[z].className.substring(1);
		                        var curNode = courses[z].getElementsByClassName('likebutton')[0];
		                        wantTake.appendChild(generateLi(id, curNode));
		                        var name = courses[z].className;
		                        var sameCourse = document.getElementsByClassName(name);
		                        for (var y = 0; y < sameCourse.length; y++) {
		                            sameCourse[y].style.display = "none";
		                        }
		                    }
		                }
		            }
		        }
		    }
		}
	}
}

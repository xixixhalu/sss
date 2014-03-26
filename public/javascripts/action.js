/**
 * @author Bohan Zheng
 */
var courseObjs;

window.onload = function() {
    var jsonData = document.getElementById("jsonData").innerText;
    courseObjs = eval("(" + jsonData + ")");
};

//dropdown effect
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

//adding course to want-to-take list
function addLikeCourse(id, curNode) {
    if (!checkConstraints(id)) {
        return null;
    }
    var wantTake = document.getElementById("wantTake");
    wantTake.appendChild(generateLi(id, curNode));
}

//adding course to already-taken list
function addTakenCourse(id, curNode) {
    if (checkCourseExist(id)) {
        alert("This course is already in the course bin!");
        return null;
    }
    var wantTake = document.getElementById("alreadyTaken");
    wantTake.appendChild(generateLi(id, curNode));
}



function generateLi(id, curNode) {
    var courseLi = document.createElement("li");
    courseLi.id = id;
    courseLi.innerHTML = courseObjs[id].prefix + " " + courseObjs[id].num + " - " + courseObjs[id].title;
    var simpleReqId = curNode.parentElement.parentElement.id;
    var complexReqId = curNode.parentElement.parentElement.parentElement.parentElement.parentElement.id;
    simpleReqId = simpleReqId.substring(3);
    complexReqId = complexReqId.substring(4);
    courseLi.innerHTML += "<input type='hidden' value='" + simpleReqId + "' name='simpleReqId'>";
    courseLi.innerHTML += "<input type='hidden' value='" + complexReqId + "' name='complexReqId'>";
    courseLi.innerHTML += "<a onclick='removeCourse(" + id + ")'>&otimes;</a>";
    return courseLi;
}

//check pre&corequisite constraints and if the the course is already in the course bin.
function checkConstraints(id) {
    if (checkCourseExist(id)) {
        alert("This course is already in the course bin!");
        return false;
    }
    if (!checkPrereq(id)) {
        var str = "check the prerequisite constraints!\n";
        var prereqs = courseObjs[id].prereq;
        for ( i = 0; i < prereqs.length; i++)
            str += " " + prereqs[i].relation + " " + prereqs[i].prefix + prereqs[i].num;
        alert(str);
        return false;
    }
    if (!checkCoreq(id)) {
        var str = "check the corequisite constraints!\n";
        var coreqs = courseObjs[id].coreq;
        for ( i = 0; i < coreqs.length; i++)
            str += " " + coreqs[i].relation + " " + coreqs[i].prefix + coreqs[i].num;
        alert(str);
        return false;
    }
    return true;
}

//get all courses in the course bin want-to-take list
function getLikeCourses() {
    var courseNodes = document.getElementById("wantTake").children;
    var courses = new Array;
    for ( i = 0; i < courseNodes.length; i++) {
        //courses[courseNodes[i].id]=courseNodes[i].innerText;
        courses.push(courseNodes[i].id);
    }
    return courses;
}

//get all courses in the course bin already-taken list
function getTakenCourses() {
    var courseNodes = document.getElementById("alreadyTaken").children;
    var courses = new Array;
    for ( i = 0; i < courseNodes.length; i++) {
        //courses[courseNodes[i].id]=courseNodes[i].innerText;
        courses.push(courseNodes[i].id);
    }
    return courses;
}

//check if the course is already in the course bin
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

//remove course from course bin
function removeCourse(curId) {
    //获取需要删除的节点
    var courseLi = document.getElementById(curId);
    //获取course bin中的所有课
    var allCourses = getLikeCourses().concat(getTakenCourses());
    //循环course bin中的所有课，
    for ( i = 0; i < allCourses.length; i++) {
        var id = allCourses[i];
        var prereq = new Array;
        var coreq = new Array;
        //查看这门课是否有先行课
        if (courseObjs[id].prereq) {
            //如果有取出这门课的所有先行课，判断它的先行课中是否有需要删除的课
            prereq = courseObjs[id].prereq;
            for ( j = 0; j < prereq.length; j++) {
                if (prereq[j].id == curId) {
                    //如果有，提示用户不能删除
                    var curCourse = courseObjs[id].prefix + courseObjs[id].num;
                    alert("This course is the prerequisite of "+curCourse);
                    return false;
                }
            }
        }
        //查看并行课
        if (courseObjs[id].coreq) {
            coreq = courseObjs[id].coreq;
            for ( j = 0; j < coreq.length; j++) {
                if (coreq[j].id == curId) {
                    //如果有，提示用户不能删除
                    var curCourse = courseObjs[id].prefix + courseObjs[id].num;
                    alert("This course is the corequisite of "+curCourse);
                    return false;
                }
            }
        }
    }
    courseLi.parentElement.removeChild(courseLi);
}

//check prerequisites
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

        //用来记录每组的条件是否满足
        var ifsatisfy = new Array;
        //找出一共有多少组
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
        //先取出组之间的关系，便于以后处理
        var groupRelation = new Array;
        while (gs.length > 0) {
            //先处理prerequisite里的第一组
            var g = gs.shift();
            //取出第一组的课
            var group = new Array;
            for ( i = 0; i < prereq.length; i++) {
                if (prereq[i].group == g) {
                    group.push(prereq[i]);
                }
            }
            var total = group.length;
            //判断第一组课之间的关系
            try {
                //如果这一组课里只有一门课,则无法取到这组课之间的关系，那么默认设置课之间的关系为or
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
                //留着处理not关系
            }
            //第一组处理完了，准备处理下一组
        }

        //处理组之间的关系
        if (groupRelation.length == 1) {
            //如果只有一组
            return ifsatisfy[0];
        } else {
            //不止一组
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
                //留着处理not关系
            }
        }
        return ret;
    } else {
        return false;
    }
}

//check corequisites
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
    //如果course bin里有这门课的corequisite则把这个corequisite添加到coreqCourses Array里
    for ( i = 0; i < courses.length; i++) {
        for ( reqNum = 0; reqNum < coreq.length; reqNum++) {
            if (courses[i] == coreq[reqNum].id)
                coreqCourses.push(courses[i]);
        }
    }
    //如果存在corequisite，处理，不存在则返回false；
    if (coreqCourses.length > 0) {
        //c = true;
        //用来记录每组的条件是否满足
        var ifsatisfy = new Array;
        //找出一共有多少组
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
        //先取出组之间的关系，便于以后处理
        var groupRelation = new Array;
        while (gs.length > 0) {
            //先处理第一组
            var g = gs.shift();
            //取出第一组的课
            var group = new Array;
            for ( i = 0; i < coreq.length; i++) {
                if (coreq[i].group == g) {
                    group.push(coreq[i]);
                }
            }
            var total = group.length;
            //判断第一组课之间的关系
            try {
                //如果这一组课里只有一门课,则无法取到这组课之间的关系，那么默认设置课之间的关系为or
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
                //留着处理not关系
            }
            //第一组处理完了，准备处理下一组
        }
        //处理组之间的关系
        if (groupRelation.length == 1) {
            //如果只有一组
            return ifsatisfy[0];
        } else {
            //不止一组
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
                //留着处理not关系
            }
        }
        return ret;
    } else {
        return false;
    }
}

function wantTakeCourse(id, sid, cid) {
    this.id = id;
    this.sid = sid;
    this.cid = cid;
}

function submitCourse(form) {
    /* create hidden field of selected courses */
    var acForm = document.getElementById("acForm");
    var wantTake = document.getElementById("wantTake").getElementsByTagName("li");
    var alreadyTaken = document.getElementById("alreadyTaken").getElementsByTagName("li");

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

    form.submit();
}

function autoCourse() {
	
	var wantTake = document.getElementById("wantTake").getElementsByTagName("li");
    var wantDataArray = new Array;
    for ( i = 0; i < wantTake.length; i++) {
        var id = wantTake[i].id;
        var sid = wantTake[i].getElementsByTagName("input")[0].value;
        var cid = wantTake[i].getElementsByTagName("input")[1].value;
        wantDataArray.push(new wantTakeCourse(id, sid, cid));
    }
    
    
    var alreadyTaken = document.getElementById("alreadyTaken").getElementsByTagName("li");
    var alreadyDataArray=new Array;
    for ( i = 0; i < alreadyTaken.length; i++) {
        var id = alreadyTaken[i].id;
        var sid = alreadyTaken[i].getElementsByTagName("input")[0].value;
        var cid = alreadyTaken[i].getElementsByTagName("input")[1].value;
        alreadyDataArray.push(new wantTakeCourse(id, sid, cid));
    }
    
    var json = eval('({"wantTakeCourses":' + JSON.stringify(wantDataArray) 
    	+ ', "alreadyTakenCourses":' + JSON.stringify(alreadyDataArray) +'})');

	/* null manipulation */
    if (true) {
        var url = "/student/autoFillCourse";
        $.post(url, 
        	{
        		wantTakeCourses: JSON.stringify(wantDataArray),
        		alreadyTakenCourses: JSON.stringify(alreadyDataArray)
        	}, function(data) {
            // var coursesObj = eval("(" + data + ")");
             //var courses = coursesObj.courses;
            // for ( i = 0; i < courses.length; i++) {
                // var li = document.createElement("li");
                // li.innerHTML = courses[i].prefix + courses[i].num + " - " + courses[i].title;
                // ul.appendChild(li);
            // }
        });
    }
}

function autoSemester() {
	
	var wantTake = document.getElementById("wantTake").getElementsByTagName("li");
    var wantDataArray = new Array;
    for ( i = 0; i < wantTake.length; i++) {
        var id = wantTake[i].id;
        var sid = wantTake[i].getElementsByTagName("input")[0].value;
        var cid = wantTake[i].getElementsByTagName("input")[1].value;
        wantDataArray.push(new wantTakeCourse(id, sid, cid));
    }
    
    
    var alreadyTaken = document.getElementById("alreadyTaken").getElementsByTagName("li");
    var alreadyDataArray=new Array;
    for ( i = 0; i < alreadyTaken.length; i++) {
        var id = alreadyTaken[i].id;
        var sid = alreadyTaken[i].getElementsByTagName("input")[0].value;
        var cid = alreadyTaken[i].getElementsByTagName("input")[1].value;
        alreadyDataArray.push(new wantTakeCourse(id, sid, cid));
    }
    
    var json = eval('({"wantTakeCourses":' + JSON.stringify(wantDataArray) 
    	+ ', "alreadyTakenCourses":' + JSON.stringify(alreadyDataArray) +'})');

	/* null manipulation */
    if (true) {
        var url = "/student/autoFillSemester";
        $.post(url, 
        	{
        		wantTakeCourses: JSON.stringify(wantDataArray),
        		alreadyTakenCourses: JSON.stringify(alreadyDataArray)
        	}, function(data) {
            // var coursesObj = eval("(" + data + ")");
            // var courses = coursesObj.courses;
            // for ( i = 0; i < courses.length; i++) {
                // var li = document.createElement("li");
                // li.innerHTML = courses[i].prefix + courses[i].num + " - " + courses[i].title;
                // ul.appendChild(li);
            // }
        });
    }
}


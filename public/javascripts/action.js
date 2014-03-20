/**
 * @author Bohan Zheng
 */
var courseObjs = {
    1 : {
        prefix : "CS",
        number : "510",
        title : "Software Management and Economics",
    },
    2 : {
        prefix : "CS",
        number : "572",
        title : "Web Search Engine and Information Retrival"
    },
    3 : {
        prefix : "CS",
        number : "577a",
        title : "Software Engineering A",
        coreq : [{
            id : "1",
            relation : "",
            group : "0"
        }]
    },
    4 : {
        prefix : "CS",
        number : "577b",
        title : "Software Engineering B",
        prereq : [{
            id : "3",
            relation : "",
            group : "0"
        }, {
            id : "2",
            relation : "or",
            group : "0"
        }]
    }
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
function addLikeCourse(id) {
    if (checkCourseExist(id)) {
        alert("This course is already in the course bin!");
        return null;
    }
    if (!checkPrereq(id)) {
        alert("check the prerequisite constraints!");
        return null;
    }
    if (!checkCoreq(id)) {
        alert("check the corequisite constraints!");
        return null;
    }
    var wantTake = document.getElementById("wantTake");
    var courseLi = document.createElement("li");
    courseLi.id = id;
    courseLi.innerHTML = courseObjs[id].prefix + " " + courseObjs[id].number + " - " + courseObjs[id].title + "<a onclick='removeCourse(" + id + ")'>&otimes;</a>";
    wantTake.appendChild(courseLi);
}

//adding course to already-taken list
function addTakenCourse(id) {
    if (checkCourseExist(id)) {
        alert("This course is already in the course bin!");
        return null;
    }
    if (!checkPrereq(id)) {
        alert("check the prerequisite constraints!");
        return null;
    }
    if (!checkCoreq(id)) {
        alert("check the corequisite constraints!");
        return null;
    }
    var wantTake = document.getElementById("alreadyTaken");
    var courseLi = document.createElement("li");
    courseLi.id = id;
    courseLi.innerHTML = courseObjs[id].prefix + " " + courseObjs[id].number + " - " + courseObjs[id].title + "<a onclick='removeCourse(" + id + ")'>&otimes;</a>";
    wantTake.appendChild(courseLi);
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
function removeCourse(id) {
    var courseLi = document.getElementById(id);
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
            var relation = group[1].relation;
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
    for ( i = 0; i < courses.length; i++) {
        for ( reqNum = 0; reqNum < coreq.length; reqNum++) {
            if (courses[i] == coreq[reqNum].id)
                coreqCourses.push(courses[i]);
        }
    }
    if (coreqCourses.length > 0) {
        c = true;
    } else {
        return false;
    }
    return c;
}

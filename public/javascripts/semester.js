/**
 * @author Bohan Zheng
 */

/**
 * initiate the page and all the json data
 * */
window.onload = function() {
    var jsonData = document.getElementById("jsonData").innerText;
    courseObjs = eval("(" + jsonData + ")");
    var want = eval("(" + document.getElementById("want").innerText + ")");
    var already = eval("(" + document.getElementById("already").innerText + ")");
    /****************************/
    ASO = eval('(' + document.getElementById("ASO").innerText + ')');
    minSemester = 0;
    for (var i = 0; i < ASO.length; ++i) {
        maxDepth = ASO[i].maxDepth;
        if (maxDepth > minSemester)
            minSemester = maxDepth;
    }
    /****************************/
    var wantTakeUL = document.getElementById("wantTake");
    for ( i = 0; i < want.length; i++) {
        var li = document.createElement("li");
        var id = want[i].id;
        li.id = id;
        var prefix = courseObjs[id].prefix;
        var num = courseObjs[id].num;
        var title = courseObjs[id].title;
        li.innerHTML = prefix + num + " - " + title + "<a><i class='fa fa-arrow-circle-right'></i></a>";
        wantTakeUL.appendChild(li);
    }
    var alreadyTakenUL = document.getElementById("alreadyTaken");
    for ( i = 0; i < already.length; i++) {
        var li = document.createElement("li");
        var id = already[i].id;
        li.id = id;
        var prefix = courseObjs[id].prefix;
        var num = courseObjs[id].num;
        var title = courseObjs[id].title;
        li.innerHTML = prefix + num + " - " + title + "<a><i class='fa fa-arrow-circle-right'></i></a>";
        alreadyTakenUL.appendChild(li);
    }
    $(document).ready(clickable);
};
/**
 * some pre-process when add course into a certain semester
 * */
function clickable() {
    $(".left_list a").click(function() {
        if (this.parentElement.style.textDecoration != "line-through") {
            var i = this.parentElement.innerHTML.indexOf("<a>");
            var course = this.parentElement.innerHTML.substring(0, i);
            // var j = course.lastIndexOf("     ");
            // if (j > 0) {
            // var course = course.substring(j + 5);
            // }
            var id = this.parentElement.id;
            if (addCourseToSemester(course, id)) {
                this.parentElement.style.textDecoration = "line-through";
                this.parentElement.innerHTML = course;
            }
        } else {
            alert("You've already assigned this course to the semester!");
        }
    });
}
/**
 * initial add semester
 * @param f
 * - record the time the user clicked the add button
 * */
var f = 0;
function addSemesters() {
	if ($('#add_semesters_button')[0].className == 'button-warning pure-button') {
	    f = 0;
	    document.getElementById("pop_back").style.display = "block";
	    document.getElementById("pop_window").style.display = "block";
	    document.getElementById("pop_content_a").style.display = "block";
   }
}
/**
 * show add semester pop up window
 * @param f
 * - record the time the user clicked the add button, 
 * the first time and the second time has different function
 * */
function add() {
    if (f == 0) {
        var semesterNum = document.getElementById("semesterNum").value;
        document.getElementById("pop_content_a").style.display = "none";
        generateSemesterList(semesterNum);
        document.getElementById("pop_content_b").style.display = "block";
        f = 1;
    } else {
        document.getElementById("pop_back").style.display = "none";
        document.getElementById("pop_window").style.display = "none";
        document.getElementById("pop_content_b").style.display = "none";
        appendSemester();
    }
}
/**
 * generate the semester list in the pop up window
 * @param num
 * - number of semesters
 * */
function generateSemesterList(num) {
    var table = document.getElementById("semesterTable");
    table.innerHTML = "<tr><td>Semester</td><td>Year</td></tr>";
    for ( i = 0; i < num; i++) {
        var tr = document.createElement("tr");
        tr.innerHTML = "<td><select><option>Spring</option><option>Summer</option><option>Fall</option><option>Winter</option></select></td><td><select><option>2014</option><option>2015</option><option>2016</option><option>2017</option><option>2018</option></select></td><td><input type='hidden' /></td><td><input type='hidden' /></td>";
        table.appendChild(tr);
    }
}
/**
 * cancel add semester
 * */
function cancel() {
    document.getElementById("pop_back").style.display = "none";
    document.getElementById("pop_window").style.display = "none";
    document.getElementById("pop_content_a").style.display = "none";
    document.getElementById("pop_content_b").style.display = "none";
    f = 0;
}
/**
 * add semester to the semester list
 * */
function appendSemester() {
    var semesterTrs = document.getElementById("semesterTable").getElementsByTagName("tr");
    var req_list = document.getElementById("req_list");
    for ( i = 1; i < semesterTrs.length; i++) {
        var semester = semesterTrs[i].getElementsByTagName("td")[0].getElementsByTagName("select")[0].value;
        var year = semesterTrs[i].getElementsByTagName("td")[1].getElementsByTagName("select")[0].value;
        var max = semesterTrs[i].getElementsByTagName("td")[2].getElementsByTagName("input")[0].value;
        var min = semesterTrs[i].getElementsByTagName("td")[3].getElementsByTagName("input")[0].value;

        var li = document.createElement("li");

        var right_list = document.createElement("div");
        right_list.className = "right_list";

        var sem_title = document.createElement("div");
        sem_title.className = "sem_title";
        sem_title.innerHTML = semester + " " + year;
        sem_title.onclick = semesterDropDown;

        var div = document.createElement("div");
        div.style.display = "none";

        var req_course_list = document.createElement("ul");
        req_course_list.className = "req_course_list";

        var credits = document.createElement("div");
        credits.className = "credits";

        credits.innerHTML = "<span>Minimun Credits:</span><input type='number' name='min' value='" + min + "'/><span>Maximun Credits:</span><input type='number' name='max' value='" + max + "'/>";
        //<a class='auto button'>AUTO</a>";
        
        var sem_hint = document.createElement("span");
        sem_hint.innerHTML = "Desired courses go here...";
        div.appendChild(sem_hint);

        div.appendChild(req_course_list);
        //div.appendChild(credits);

        right_list.appendChild(sem_title);
        right_list.appendChild(div);
        li.appendChild(right_list);
        req_list.appendChild(li);
    }
}
/**
 * drop down effect for semester
 * @param evt
 * - the element triggered the click event 
 * */
function semesterDropDown(evt) {
	shrinkAll();
    var thisDiv = (evt) ? evt.target : window.event.srcElement;
    if (thisDiv.parentElement.getElementsByTagName("div")[1].style.display == "none") {
        var lis = document.getElementById("req_list").getElementsByTagName("li");
        for ( i = 1; i < lis.length; i++) {
            var div1 = lis[i].getElementsByTagName("div")[0];
            if (!div1) {
                continue;
            }
            var div2 = div1.getElementsByTagName("div")[1];
            if (div2.style.display == "block") {
                div1.getElementsByTagName("div")[1].style.display = "none";
                div1.getElementsByTagName("div")[0].style.backgroundColor = '#FFF';
                break;
            }
        }
        expand(thisDiv);
    } else {
        shrink(thisDiv);
    }
}

/**
 * expand the clicked semester tab at the right side panel
 */
function expand(thisDiv) {
    thisDiv.parentElement.getElementsByTagName("div")[1].style.display = "block";
    thisDiv.style.backgroundColor = '#DDD';
}
/**
 * expand all the semester tabs at the right side panel
 */
function expandAll() {
    var sem_list = $('.req_list')[0].children;
    for (var i = 0; i < sem_list.length; i++) {
        var sem = sem_list[i];
        if (sem.tagName == 'LI' && sem.id != 'firstli') {
            expand(sem.getElementsByTagName('div')[1]);
        }
    }
}

/**
 * shrink the clicked semester tab at the right side panel
 */
function shrink(thisDiv) {
    thisDiv.parentElement.getElementsByTagName("div")[1].style.display = "none";
    thisDiv.style.backgroundColor = 'white';
}
/**
 * shrink all the semester tabs at the right side panel
 */
function shrinkAll() {
    var sem_list = $('.req_list')[0].children;
    for (var i = 0; i < sem_list.length; i++) {
        var sem = sem_list[i];
        if (sem.tagName == 'LI' && sem.id != 'firstli') {
            shrink(sem.getElementsByTagName('div')[1]);
        }
    }
}
/**
 * add course into semester
 * @param course
 * - course title
 * @param id
 * - course id
 * */
function addCourseToSemester(course, id) {
    var lis = document.getElementById("req_list").children;
    var num = 0;
    for ( i = 1; i < lis.length; i++) {
        if (lis[i].getElementsByTagName("div")[0].getElementsByTagName("div")[1].style.display == "block") {
            var ul = lis[i].getElementsByTagName("div")[0].getElementsByTagName("div")[1].getElementsByTagName("ul")[0];
            num = i;
            break;
        }
    }
    if (!ul) {
        alert("Please select a semester first!");
        return false;
    }
    if (!checkSemesterConstraints(id, num)) {
        return false;
    }
    var li = document.createElement("li");
    li.id = id;
    li.innerHTML = course + "<a onclick='removeCourseFromSemester(this," + id + ")'><i class='fa fa-times-circle'></i></a>";
    ul.appendChild(li);
    return true;
}
/**
 * remove course from semester
 * @param a
 * - the <a> element triggered the click event
 * @param id
 * - course id
 * */
function removeCourseFromSemester(a, id) {
	var r = confirm("Are you sure to remove this course from this semester?");
	if (r == true)
 	{
  		var li = a.parentElement;
    	li.parentElement.removeChild(li);
    	//var course = getPrefixNumber(li);
    	document.getElementById(id).style.textDecoration = "none";
    	document.getElementById(id).innerHTML = document.getElementById(id).innerHTML + "<a><i class='fa fa-arrow-circle-right'></i></a>";
        clickable();
    	return true;
  	}
	else
  	{
  		return false;
	}   
}
/**
 * get the prefix and number of the course
 * @param li
 * <li> element that contain the course
 * @return course
 * course prefix and number in one string
 * */
function getPrefixNumber(li) {
    var course = li.innerHTML;
    var i = course.indexOf(" - ");
    course = course.substring(0, i);
    var j = course.lastIndexOf("    ");
    course = course.substring(j);
    return course;
}
/**
 *auto assign courses into semester
 * */
function autoSemester() {
    var semesterNum = document.getElementById("req_list").children.length - 1;
    if (semesterNum < minSemester) {
        alert("you must add at least " + minSemester + " semesters first!");
        return;
    }
    var wantTake = document.getElementById("wantTake").getElementsByTagName("li");
    var wantDataArray = new Array;
    for ( i = 0; i < wantTake.length; i++) {
        var id = wantTake[i].id;
        // var sid = wantTake[i].getElementsByTagName("input")[0].value;
        // var cid = wantTake[i].getElementsByTagName("input")[1].value;
        wantDataArray.push(new wantTakeCourse(id, -1, -1));
    }

    var alreadyTaken = document.getElementById("alreadyTaken").getElementsByTagName("li");
    var alreadyDataArray = new Array;
    for ( i = 0; i < alreadyTaken.length; i++) {
        var id = alreadyTaken[i].id;
        // var sid = alreadyTaken[i].getElementsByTagName("input")[0].value;
        // var cid = alreadyTaken[i].getElementsByTagName("input")[1].value;
        alreadyDataArray.push(new wantTakeCourse(id, -1, -1));
    }

    var json = eval('({"wantTakeCourses":' + JSON.stringify(wantDataArray) + ', "alreadyTakenCourses":' + JSON.stringify(alreadyDataArray) + '})');

    /* null manipulation */
    if (true) {
        var url = "/student/autoFillSemester";
        $.post(url, {
            wantTakeCourses : JSON.stringify(wantDataArray),
            alreadyTakenCourses : JSON.stringify(alreadyDataArray),
            /* [semester:{num:1,title:spring 2014,;minCredit:1,maxCredit:10,courses:[1,2,3]},...]*/
            semesterData : JSON.stringify(getSemesterData()),
            semesterNum : semesterNum
        }, function(data) {
            var semesterObj = eval("(" + data + ")");
            var semesterUls = document.getElementById("req_list").getElementsByClassName("req_course_list");
            for ( i = 0; i < semesterObj.length; i++) {
                var courses = semesterObj[i].courses;
                var semester = semesterUls[i];
                semester.innerHTML = "";
                for ( j = 0; j < courses.length; j++) {
                    var li = document.createElement("li");
                    var id = courses[j];
                    li.id = id;
                    li.innerHTML = courseObjs[id].prefix + courseObjs[id].num + " - " + courseObjs[id].title + "<a onclick='removeCourseFromSemester(this," + id + ")'><i class='fa fa-times-circle'></i></a>";
                    semester.appendChild(li);

                    //this.parentElement.style.textDecoration = "line-through";
                }
            }
            
	        $("#wantTake li").css("text-decoration", "line-through");
	        
            for (var i = 0; i < $('#wantTake li').size(); i++) {
	            var j = $('#wantTake li')[i].innerHTML.indexOf("<a>");
	            var course = $('#wantTake li')[i].innerHTML.substring(0, j);
	            $("#wantTake li")[i].innerHTML = course;
            }
            
            $('#auto_next_semester_button').html('GET FINAL STUDY PLAN');
            $('#undo_assign_button')[0].className = 'button left_auto pure-button button-secondary';
            $('#add_semesters_button')[0].className = 'pure-button pure-button-disabled';
            expandAll();
        });
    }
}
/**
 * the semester object
 * @param num
 * the number of semester e.g. 1 means it is the first semester
 * @param title
 * e.g. spring 2014
 * @param minCredit
 * minimum credit 
 * @param maxCredit
 * maximum credit
 * @param courses
 * courses in the semester
 * */
function Semester(num, title, minCredit, maxCredit, courses) {
    this.num = num;
    this.title = title;
    this.minCredit = minCredit;
    this.maxCredit = maxCredit;
    this.courses = courses;
}
/**
 * get all semester data from the semester list
 * @return semesters;
 * - Array contains all semester object
 * */
function getSemesterData() {
    var semesterList = document.getElementById("req_list");
    var semesterLis = semesterList.children;
    var semesters = new Array();
    for ( i = 1; i < semesterLis.length; i++) {
        var num = i;
        var title = semesterLis[i].getElementsByClassName("sem_title")[0].innerText;
        var minCredit = 1;
        var maxCredit = 1;
        // var minCredit = semesterLis[i].getElementsByTagName("input")[0].value;
        // var maxCredit = semesterLis[i].getElementsByTagName("input")[1].value;
        var courses = new Array();
        var courseLis = semesterLis[i].getElementsByClassName("req_course_list")[0].children;
        for ( j = 0; j < courseLis.length; j++) {
            courses.push(courseLis[j].id);
        }
        var semester = new Semester(num, title, minCredit, maxCredit, courses);
        semesters.push(semester);
    }
    return semesters;
}
/**
 * check all the constraints when adding course into semester
 * @param id
 * - course id
 * @param num
 * - semester number
 * */
function checkSemesterConstraints(id, num) {
    var maxDepth = 0;
    for (var i = 0; i < ASO.length; ++i) {
        if (ASO[i].id == id){
            maxDepth = ASO[i].maxDepth;
            break;
        }
    }
    if (num < maxDepth) {
        alert("the eariliest possiblity for this course is No." + maxDepth + " semester");
        return false;
    }
    var reqList = document.getElementById("req_list").getElementsByClassName("req_course_list");
    var prereqs = new Array();
    for ( i = 0; i < num; i++) {
        var prereqlis = reqList[i].children;
        for ( j = 0; j < prereqlis.length; j++) {
            prereqs.push(prereqlis[j].id);
        }
    }
    var coreqlis = reqList[num - 1].children;
    var coreqs = new Array();
    for ( i = 0; i < coreqlis.length; i++) {
        coreqs.push(coreqlis[i].id);
    }
    if (!checkSemesterReq(id, prereqs, "pre")) {
        var str = "check the prerequisite constraints!\n";
        var prereqs = courseObjs[id].prereq;
        for ( i = 0; i < prereqs.length; i++)
            str += " " + prereqs[i].relation + " " + prereqs[i].prefix + prereqs[i].num;
        alert(str);
        return false;
    }
    if (!checkSemesterReq(id, coreqs, "co")) {
        var str = "check the corequisite constraints!\n";
        var coreqs = courseObjs[id].coreq;
        for ( i = 0; i < coreqs.length; i++)
            str += " " + coreqs[i].relation + " " + coreqs[i].prefix + coreqs[i].num;
        alert(str);
        return false;
    }
    return true;
}
/**
 * check prerequisite and corequisite constraint when adding course into semester
 * @param id
 * - course id
 * @param courses, array contain some course id
 * - check the constraints within this given set of course, 
 * @param req
 * -check prerequisite or corequisite, 
 * only two value 'req' -> prerequisite 'co' -> corequisite
 * */
function checkSemesterReq(id, courses, req) {
    var p = false;

    var prereq = new Array;
    if (req == "pre") {
        if (courseObjs[id].prereq)
            prereq = courseObjs[id].prereq;
    } else if (req == "co") {
        if (courseObjs[id].coreq)
            prereq = courseObjs[id].coreq;
    }
    if (prereq.length == 0)
        return true;

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
        var ifsatisfy = new Array;
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
        var groupRelation = new Array;
        while (gs.length > 0) {
            var g = gs.shift();
            var group = new Array;
            for ( i = 0; i < prereq.length; i++) {
                if (prereq[i].group == g) {
                    group.push(prereq[i]);
                }
            }
            var total = group.length;
            try {
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
            }
        }

        if (groupRelation.length == 1) {
            return ifsatisfy[0];
        } else {
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
            }
        }
        return ret;
    } else {
        return false;
    }
}

/**
 * the function invoked after clicked "AUTO_NEXT_SEMESTER" button
 */
function auto_next_semester_action()
{
	var element_text = $('#auto_next_semester_button').html();
	if(element_text == 'AUTO ASSIGN SEMESTERS')
	{
		autoSemester();
	}
	else if(element_text == 'GET FINAL STUDY PLAN')
	{
		window.location.href = 'studyplan';
	}
}

/**
 * undo the step of "AUTO_NEXT_SEMESTER"
 * NOT_IMPLEMENTED
 */
function undo_assign() {
    alert("Still under construction...");
}

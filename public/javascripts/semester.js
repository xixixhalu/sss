/**
 * @author Bohan Zheng
 */

window.onload = function() {
    var jsonData = document.getElementById("jsonData").innerText;
    var courseObjs = eval("(" + jsonData + ")");
    var want = eval("(" + document.getElementById("want").innerText + ")");
    var already = eval("(" + document.getElementById("already").innerText + ")");
    var wantTakeUL = document.getElementById("wantTake");
    for ( i = 0; i < want.length; i++) {
        var li = document.createElement("li");
        var id = want[i].id;
        li.id = id;
        var prefix = courseObjs[id].prefix;
        var num = courseObjs[id].num;
        var title = courseObjs[id].title;
        li.innerHTML = prefix + num + " - " + title + "<a>&oplus;</a>";
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
        li.innerHTML = prefix + num + " - " + title + "<a>&oplus;</a>";
        alreadyTakenUL.appendChild(li);
    }
    $(document).ready(function() {
        $(".left_list a").click(function() {
            var i = this.parentElement.innerHTML.indexOf("<a>");
            var course = this.parentElement.innerHTML.substring(0, i);
            // var j = course.lastIndexOf("     ");
            // if (j > 0) {
            // var course = course.substring(j + 5);
            // }
            var id = this.parentElement.id;
            if (addCourseToSemester(course, id))
                this.parentElement.style.textDecoration = "line-through";
        });
    });
};
var f = 0;
function addSemesters() {
    f = 0;
    document.getElementById("pop_back").style.display = "block";
    document.getElementById("pop_window").style.display = "block";
    document.getElementById("pop_content_a").style.display = "block";
}

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

function generateSemesterList(num) {
    var table = document.getElementById("semesterTable");
    table.innerHTML = "<tr><td>Semester</td><td>Year</td><td>Max Credits</td><td> Min Credits</td></tr>";
    for ( i = 0; i < num; i++) {
        var tr = document.createElement("tr");
        tr.innerHTML = "<td><select><option>Spring</option><option>Summer</option><option>Fall</option><option>Winter</option></select></td><td><select><option>2014</option><option>2015</option><option>2016</option><option>2017</option><option>2018</option></select></td><td><input type='number' /></td><td><input type='number' /></td>";
        table.appendChild(tr);
    }
}

function cancel() {
    document.getElementById("pop_back").style.display = "none";
    document.getElementById("pop_window").style.display = "none";
    document.getElementById("pop_content_a").style.display = "none";
    document.getElementById("pop_content_b").style.display = "none";
    f = 0;
}

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
        sem_title.onclick = semesterDorpDown;

        var div = document.createElement("div");
        div.style.display = "none";

        var req_course_list = document.createElement("ul");
        req_course_list.className = "req_course_list";

        var credits = document.createElement("div");
        credits.className = "credits";

        credits.innerHTML = "<span>Minimun Credits:</span><input type='number' name='min' value='" + min + "'/><span>Maximun Credits:</span><input type='number' name='max' value='" + max + "'/>";
        //<a class='auto button'>AUTO</a>";

        div.appendChild(req_course_list);
        div.appendChild(credits);

        right_list.appendChild(sem_title);
        right_list.appendChild(div);
        li.appendChild(right_list);
        req_list.appendChild(li);
    }
}

function semesterDorpDown(evt) {
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
                break;
            }
        }
        thisDiv.parentElement.getElementsByTagName("div")[1].style.display = "block";
    } else {
        thisDiv.parentElement.getElementsByTagName("div")[1].style.display = "none";
    }
}

function addCourseToSemester(course, id) {
    var lis = document.getElementById("req_list").children;
    for ( i = 1; i < lis.length; i++) {
        if (lis[i].getElementsByTagName("div")[0].getElementsByTagName("div")[1].style.display == "block") {
            var ul = lis[i].getElementsByTagName("div")[0].getElementsByTagName("div")[1].getElementsByTagName("ul")[0];
            break;
        }
    }
    if (!ul) {
        alert("Please select a semester first!");
        return false;
    }
    var li = document.createElement("li");
    li.id=id;
    li.innerHTML = course + "<a onclick='removeCourseFromSemester(this," + id + ")'>&otimes;</a>";
    ul.appendChild(li);
    return true;
}

function removeCourseFromSemester(a, id) {
    var li = a.parentElement;
    li.parentElement.removeChild(li);
    //var course = getPrefixNumber(li);
    document.getElementById(id).style.textDecoration = "none";
}

function getPrefixNumber(li) {
    var course = li.innerHTML;
    var i = course.indexOf(" - ");
    course = course.substring(0, i);
    var j = course.lastIndexOf("    ");
    course = course.substring(j);
    return course;
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
        var url = "/student/autoFillSemester";
        $.post(url, {
            wantTakeCourses : JSON.stringify(wantDataArray),
            alreadyTakenCourses : JSON.stringify(alreadyDataArray),
            /* [semester:{num:1,title:spring 2014,;minCredit:1,maxCredit:10,courses:[1,2,3]},...]*/
            semesterData: JSON.stringify(getSemesterData())
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

function Semester(num, title, minCredit, maxCredit, courses) {
    this.num = num;
    this.title = title;
    this.minCredit = minCredit;
    this.maxCredit = maxCredit;
    this.courses = courses;
}

function getSemesterData() {
    var semesterList = document.getElementById("req_list");
    var semesterLis = semesterList.children;
    var semesters=new Array();
    for ( i = 1; i < semesterLis.length; i++) {
        var num=i;
        var title = semesterLis[i].getElementsByClassName("sem_title")[0].innerText;
        var minCredit=semesterLis[i].getElementsByTagName("input")[0].value;
        var maxCredit=semesterLis[i].getElementsByTagName("input")[1].value;
        var courses=new Array();
        var courseLis=semesterLis[i].getElementsByClassName("req_course_list")[0].children;
        for(j=0;j<courseLis.length;j++){
            courses.push(courseLis[j].id);
        }
        var semester=new Semester(num,title,minCredit,maxCredit,courses);
        semesters.push(semester);
    }
    return semesters;
}

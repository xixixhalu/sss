/**
 * @author Bohan Zheng
 */

window.onload = initAll;

function initAll() {
    document.getElementById("prereq").onkeyup = function() {
        searchSuggest("prereq", "popups_pre");
    };
    document.getElementById("coreq").onkeyup = function() {
        searchSuggest("coreq", "popups_co");
    };

    if (window.XMLHttpRequest) {
        xhr = new XMLHttpRequest();
    } else {
        if (window.ActiveXObject) {
            try {
                xhr = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e) {
            }
        }
    }

    if (xhr) {
        xhr.onreadystatechange = getCoursesArray;
        xhr.open("GET", "/admin/retrieveWholeCourses", true);
        xhr.send(null);
    } else {
        alert("Sorry, but I couldn't create an XMLHttpRequest");
    }
}

function getCoursesArray() {
    var coursesArray = new Array();
    if (xhr.readyState == 4) {
        if (xhr.status == 200) {
            if (xhr.responseText) {
                var coursesObj = eval("(" + xhr.responseText + ")");
                coursesArray = coursesObj.courses;
            }
        } else {
            alert("There was a problem with the request " + xhr.status);
        }
    }
    return coursesArray;
}

function searchSuggest(value, div) {

    var coursesArray = getCoursesArray();
    var curCoursesArray = new Array();

    var str = document.getElementById(value).value;

    if (str != "") {
        document.getElementById(div).innerHTML = "";

        for (var i = 0, j = 0; i < coursesArray.length; i++) {
            var thisCourse = coursesArray[i].name + " - " + coursesArray[i].title;
            if (thisCourse.toLowerCase().indexOf(str.toLowerCase()) == 0) {
                curCoursesArray[j] = thisCourse;
                j++;
            }
        }

        for (var i = 0; i < curCoursesArray.length; i++) {
            var tempDiv = document.createElement("li");
            tempDiv.innerHTML = curCoursesArray[i];
            if (div == "popups_pre")
                tempDiv.onclick = makeChoice;
            else
                tempDiv.onclick = makeChoice2;

            document.getElementById(div).appendChild(tempDiv);
        }

        var foundCt = document.getElementById(div).childNodes.length;
    } else {
        document.getElementById(div).innerHTML = "";
    }
}

function makeChoice(evt) {
    var thisDiv = (evt) ? evt.target : window.event.srcElement;
    document.getElementById("prereq").value = thisDiv.innerHTML;
    document.getElementById("popups_pre").innerHTML = "";
}

function makeChoice2(evt) {
    var thisDiv = (evt) ? evt.target : window.event.srcElement;
    document.getElementById("coreq").value = thisDiv.innerHTML;
    document.getElementById("popups_co").innerHTML = "";
}

function addReq(name, list) {
    var courseName = document.getElementById(name).value;
    var i = courseName.indexOf(" -");
    courseName = courseName.substring(0, i);
    var courseList = document.getElementById(list);
    var li = document.createElement("li");
    // alert(courseList.children.length);
    if (courseList.children.length > 0) {
        li.innerHTML = "<select><option>AND</option><option>OR</option></select><div><span>" + courseName + "</span><input type='text'/></div>";
    } else {
        li.innerHTML = "<div><span>" + courseName + "</span><input type='text'/></div>";
    }
    li.ondblclick = function() {
        remove(li, list);
    };
    courseList.appendChild(li);
    document.getElementById(name).value="";
}

function remove(li, list) {
    var courseList = document.getElementById(list);
    var f = courseList.children;
    if (li != f[0]) {
        courseList.removeChild(li);
    } else {
        if (f.length != 1) {
            var li2 = li.parentElement.children[1];
            li2.removeChild(li2.getElementsByTagName("select")[0]);
        }
        courseList.removeChild(li);
    }
}

function Course(id, relation, group) {
    this.id = id;
    this.relation = relation;
    this.group = group;
}

function getReqCourses(req) {
    var courses = new Array;
    var reqlist=document.getElementById(req);
    
    return courses;
}

function doSubmit(form) {
    var prereqs = getReqCourses("reqlist");
    var coreqs = getReqCourses("coqlist");
    var prereqString = JSON.stringify(prereqs);
    var coreqString = JSON.stringify(coreqs);
    document.getElementsByName("prerequisite_ids").value = prereqString;
    document.getElementsByName("corequisite_ids").value = coreqString;
    form.submit();
}

/**
 * @author Bohan Zheng
 */

window.onload = initAll;

function initAll() {
    document.getElementById("prereq").onkeyup = function() {
        searchSuggest("prereq", "popups_pre", event.keyCode);
    };
    document.getElementById("coreq").onkeyup = function() {
        searchSuggest("coreq", "popups_co", event.keyCode);
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
    initEdit();
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

function searchSuggest(value, div, keyCode) {

    var coursesArray = getCoursesArray();
    var curCoursesArray = new Array();
    var curIdArray = new Array();

    var str = document.getElementById(value).value;

    if (str != "") {
        document.getElementById(div).innerHTML = "";

        for (var i = 0, j = 0; i < coursesArray.length; i++) {
            var thisCourse = coursesArray[i].prefix + coursesArray[i].num + " - " + coursesArray[i].title;

            if (thisCourse.toLowerCase().indexOf(str.toLowerCase()) == 0) {
                curCoursesArray[j] = thisCourse;
                curIdArray[j] = coursesArray[i].id;
                j++;
            }
        }

        for (var i = 0; i < curCoursesArray.length; i++) {
            var tempDiv = document.createElement("li");

            var title = document.createElement("div");
            var id = document.createElement("input");

            title.innerHTML = curCoursesArray[i];
            id.value = curIdArray[i];
            id.type = "hidden";

            if (div == "popups_pre")
                tempDiv.onclick = makeChoice;
            else
                tempDiv.onclick = makeChoice2;

            tempDiv.appendChild(title);
            tempDiv.appendChild(id);
            document.getElementById(div).appendChild(tempDiv);
        }

        var foundCt = document.getElementById(div).childNodes.length;

        if (foundCt == 0) {
            document.getElementById(value).className = "b_text_error";
        } else {
            document.getElementById(value).className = "b_text";
        }
        if (foundCt == 1 && keyCode != 8) {
            document.getElementById(value).value = document.getElementById(div).firstChild.getElementsByTagName("div")[0].innerHTML;
            if (div == "popups_pre")
                document.getElementById("prerequisite_id").value = document.getElementById(div).firstChild.getElementsByTagName("input")[0].value;
            else
                document.getElementById("corequisite_id").value = document.getElementById(div).firstChild.getElementsByTagName("input")[0].value;
            document.getElementById(div).innerHTML = "";
        }

    } else {
        document.getElementById(div).innerHTML = "";
    }
}

function makeChoice(evt) {
    var thisDiv = (evt) ? evt.target : window.event.srcElement;
    document.getElementById("prereq").value = thisDiv.innerHTML;
    document.getElementById("prerequisite_id").value = thisDiv.parentElement.getElementsByTagName("input")[0].value;
    document.getElementById("popups_pre").innerHTML = "";
}

function makeChoice2(evt) {
    var thisDiv = (evt) ? evt.target : window.event.srcElement;
    document.getElementById("coreq").value = thisDiv.innerHTML;
    document.getElementById("corequisite_id").value = thisDiv.parentElement.getElementsByTagName("input")[0].value;
    document.getElementById("popups_co").innerHTML = "";
}

function addReq(name, list) {
    var courseName = document.getElementById(name).value;

    if (courseName == "") {
        alert("Please enter the course name!");
        return;
    }
    var i = courseName.indexOf(" -");
    courseName = courseName.substring(0, i);
    var courseList = document.getElementById(list);
    if (name == "prereq")
        var course_id = document.getElementById("prerequisite_id").value;
    else
        var course_id = document.getElementById("corequisite_id").value;

    //check exist
    if (checkExist(course_id)) {
        alert("This course has already been added!!");
        return;
    }
    if (document.getElementsByTagName("body")[0].id == course_id) {
        alert("Courses cannot be the prerequisites or corequisites of itself!!");
        return;
    }

    var li = document.createElement("li");
    // alert(courseList.children.length);
    if (courseList.children.length > 0) {
        li.innerHTML = "<select name='relation'><option>AND</option><option>OR</option></select><div><span>" + courseName + "</span><input type='number' name='group' value='1'/></div>";
    } else {
        li.innerHTML = "<div><span>" + courseName + "</span><input type='number' name='group' value='1'/></div>";
    }
    li.innerHTML += "<input type='hidden' name='course_id' value='" + course_id + "'>";
    li.ondblclick = function() {
        remove(li, list);
    };
    courseList.appendChild(li);
    document.getElementById(name).value = "";
}

function checkExist(id) {
    var ids = document.getElementsByName("course_id");
    for ( i = 0; i < ids.length; i++) {
        if (ids[i].value == id) {
            return true;
        }
    }
    return false;
}

function remove(li, list, evt) {
    var thisDiv = (evt) ? evt.target : window.event.srcElement;
    if (thisDiv.nodeName == "INPUT") {
        return;
    }
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

function Course(id, prefix, num, relation, group) {
    this.id = id;
    this.prefix = prefix;
    this.num = num;
    this.relation = relation;
    this.group = group;
}

function getReqCourses(req) {
    var courses = new Array();
    var reqlist = document.getElementById(req);
    var lis = new Array();
    if (req == "reqlist")
        req_id = "prerequisite_id";
    else
        req_id = "corequisite_id";

    lis = reqlist.getElementsByTagName("li");
    for ( i = 0; i < lis.length; i++) {
        var id = lis[i].lastChild.value;
        var group = lis[i].getElementsByTagName("div")[0].getElementsByTagName("input")[0].value;
        if (i == 0)
            var relation = "";
        else
            var relation = lis[i].getElementsByTagName("select")[0].value.toLowerCase();
        var prefix = lis[i].getElementsByTagName("div")[0].innerText.substring(0, 2);
        var num = lis[i].getElementsByTagName("div")[0].innerText.substring(2);
        var course = new Course(id, prefix, num, relation, group);
        courses.push(course);
    }
    return courses;
}

function doSubmit(form) {
    if (checkCourseForm()) {
        var prereqs = getReqCourses("reqlist");
        var coreqs = getReqCourses("coqlist");
        var prereqString = JSON.stringify(prereqs);
        var coreqString = JSON.stringify(coreqs);
        document.getElementById("prerequisite_id").value = prereqString;
        document.getElementById("corequisite_id").value = coreqString;
        return true;
    } else
        return false;
    //form.submit();
}

function initEdit() {
    var prereqStr = document.getElementById("prerequisite_id").value;
    var coreqStr = document.getElementById("corequisite_id").value;
    var prereqs = eval("(" + prereqStr + ")");
    var coreqs = eval("(" + coreqStr + ")");
    var ul1 = document.getElementById("reqlist");
    if (prereqs != null) {
        for ( i = 0; i < prereqs.length; i++) {
            var li = document.createElement("li");
            var courseName = prereqs[i].prefix + prereqs[i].num;
            var course_id = prereqs[i].id;
            var relation = prereqs[i].relation;
            var group = prereqs[i].group;
            if (ul1.children.length > 0) {
                if (relation == "or") {
                    li.innerHTML += "<select name='relation'><option>AND</option><option selected='selected'>OR</option></select>";
                } else
                    li.innerHTML += "<select name='relation'><option>AND</option><option>OR</option></select>";
                li.innerHTML += "<div><span>" + courseName + "</span><input type='number' name='group' value='"+group+"'/></div>";
            } else {
                li.innerHTML = "<div><span>" + courseName + "</span><input type='number' name='group' value='"+group+"'/></div>";
            }
            li.innerHTML += "<input type='hidden' name='course_id' value='" + course_id + "'>";
            li.ondblclick = function(evt) {
                var li = (evt) ? evt.target : window.event.srcElement;
                remove(li.parentElement.parentElement, "reqlist");
            };
            ul1.appendChild(li);
        }
    }

    var ul2 = document.getElementById("coqlist");
    if (coreqs != null) {
        for ( i = 0; i < coreqs.length; i++) {
            var li = document.createElement("li");
            var courseName = coreqs[i].prefix + coreqs[i].num;
            var course_id = coreqs[i].id;
            var relation = coreqs[i].relation;
            var group = coreqs[i].group;
            if (ul2.children.length > 0) {
                if (relation == "or") {
                    li.innerHTML += "<select name='relation'><option>AND</option><option selected='selected'>OR</option></select>";
                } else
                    li.innerHTML += "<select name='relation'><option>AND</option><option>OR</option></select>";
                li.innerHTML += "<div><span>" + courseName + "</span><input type='number' name='group' value='"+group+"'/></div>";
            } else {
                li.innerHTML = "<div><span>" + courseName + "</span><input type='number' name='group' value='"+group+"'/></div>";
            }
            li.innerHTML += "<input type='hidden' name='course_id' value='" + course_id + "'>";
            li.ondblclick = function(evt) {
                var li = (evt) ? evt.target : window.event.srcElement;
                remove(li.parentElement.parentElement, "coqlist");
            };
            ul2.appendChild(li);
        }
    }

    document.getElementById("prerequisite_id").value = "";
    document.getElementById("corequisite_id").value = "";
}

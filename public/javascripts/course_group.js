/**
 * @author Bohan Zheng
 */

/**
 * add selected courses into the course group
 */
function add() {
    var courses = document.getElementsByName("courses");
    var checked_course = new Array;
    for ( i = 0; i < courses.length; i++) {
        if (courses[i].checked == true) {
            checked_course.push(courses[i].value);
            courses[i].checked = false;
        }
    }
    var selected = document.getElementById("selected");

    var s_courses = document.getElementsByName("s_courses");
    var selected_course = new Array;
    if (s_courses) {
        for ( i = 0; i < s_courses.length; i++) {
            selected_course.push(s_courses[i].value);
        }
    }
    var result = combineCourse(checked_course, selected_course);

    var resultName = new Array;
    for ( i = 0; i < result.length; i++) {
        resultName.push(document.getElementById(result[i]).innerHTML);

    }
    //alert(selected_course[0]);
    while (selected.firstChild) {
        selected.removeChild(selected.firstChild);
    }

    for ( i = 0; i < resultName.length; i++) {
        var li = document.createElement("li");
        var checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.name = "s_courses";

        checkbox.value = result[i];
        var span = document.createElement("span");
        span.innerHTML = resultName[i];
        li.appendChild(checkbox);
        li.appendChild(span);
        selected.appendChild(li);
    }
}

/**
 * combine the newly selected courses with the courses already in the course group
 * @param courses
 * courses already in the course group
 * @param s_courses
 * newly selected courses
 */
function combineCourse(courses, s_courses) {
    if (courses.length == 0) {
        return false;
    } else if (s_courses.length == 0) {
        return courses;
    } else {
        var result = new Array;
        result = s_courses;
        for ( i = 0; i < courses.length; i++) {
            var f = 0;
            for ( j = 0; j < s_courses.length; j++) {
                if (courses[i] == s_courses[j])
                    f = 1;
            }
            if (f == 0)
                result.push(courses[i]);
        }
        return result;
    }
}

/**
 *remove the courses from course group
 */
function remove() {
    var courses = document.getElementsByName("s_courses");
    var s_courses = new Array;
    for ( i = 0; i < courses.length; i++) {
        if (courses[i].checked == true) {
            s_courses.push(courses[i]);
        }
    }
    var selected = document.getElementById("selected");
    var s = s_courses[0];
    for ( i = 0; i < s_courses.length; i++) {
        selected.removeChild(s_courses[i].parentNode);
    }
}

/**
 * submit course group data
 * @param e
 * form object
 */
function onCgSub(e) {
    var s_courses = document.getElementsByName("s_courses");
    var selected_course = new Array;
    if (s_courses) {
        for ( i = 0; i < s_courses.length; i++) {
            selected_course.push(s_courses[i].value);
        }
    }

    var hidden = document.createElement("input");
    hidden.type = "hidden";
    hidden.value = selected_course;
    hidden.name = "course_ids";
    document.getElementById("selected").appendChild(hidden);
    e.submit();
}
/**
 * we used the same javascript file in add/edit degree program pages
 * submit degree program data
 * @param e
 * form object
 */
function onDegreeSub(e) {
    var s_courses = document.getElementsByName("s_courses");
    var selected_course = new Array;
    if (s_courses) {
        for ( i = 0; i < s_courses.length; i++) {
            selected_course.push(s_courses[i].value);
        }
    }

    var hidden = document.createElement("input");
    hidden.type = "hidden";
    hidden.value = selected_course;
    hidden.name = "req_ids";
    document.getElementById("selected").appendChild(hidden);

    e.submit();
}

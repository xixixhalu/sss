/**
 * @author Bohan Zheng
 */
function generateURL(url) {
    var id = document.getElementById("changeCg").value;

    return url;
}

/**
 * show courses of the selected course group
 */
function change() {
    // alert(1);
    var id = document.getElementById("changeCg").value;
    var ul = document.getElementById("courseingroup");
    while (ul.firstChild) {
        ul.removeChild(ul.firstChild);
    }

    if (id != "") {
        var url = "/admin/retrieveCgCourses/" + id;
        $.get(url, function(data) {
            var coursesObj = eval("(" + data + ")");
            var courses = coursesObj.courses;
            for ( i = 0; i < courses.length; i++) {
                var li = document.createElement("li");
                li.innerHTML = courses[i].prefix + courses[i].num + " - " + courses[i].title;
                ul.appendChild(li);
            }
        });
    }
}
/**
 * initiate the page, show courses of the selected course group
 * @param id
 * id of course group
 */
function init(id) {
    $("#changeCg option[value='"+id+"']").attr("selected","selected");
    var ul = document.getElementById("courseingroup");
    var url = "/admin/retrieveCgCourses/" + id;
    $.get(url, function(data) {
        var coursesObj = eval("(" + data + ")");
        var courses = coursesObj.courses;
        for ( i = 0; i < courses.length; i++) {
            var li = document.createElement("li");
            li.innerHTML = courses[i].prefix + courses[i].num + " - " + courses[i].title;
            ul.appendChild(li);
        }
    });
}

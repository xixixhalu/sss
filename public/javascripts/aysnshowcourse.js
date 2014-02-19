/**
 * @author Bohan Zheng
 */
function generateURL() {
    var id = document.getElementById("changeCg").value;
    var url = "file://localhost/Users/bohan/Documents/workspace/Student_Scheduling_System/app/views/testdata.html";
    return url;
}

function change(){
    alert(1);
    $.get(generateURL(), function(data,status){
        var courses=eval("("+data+")");
        var ul=document.getElementById("courseingroup");
        for(i=0;i<courses.length;i++){
        var li = document.createElement("li");
        li.innerHTML=courses[i].prifex+" - "+courses[i].name;
        ul.appendChild(li);
        }
    });
    
}


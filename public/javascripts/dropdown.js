/**
 * @author Bohan Zheng
 */
function dropdown() {
    var disp = document.getElementById("dropdown").style.display;
    //alert(disp);
    if (disp == "none")
        document.getElementById("dropdown").style.display = "block";
    else
        document.getElementById("dropdown").style.display = "none";
}

function onDelConf(e){
	var c=confirm("Are you sure? Cascading deletion might be involved.");
	if(c==true)	{
		e.submit();
	}
}
$(document).ready(function(){
    $(".list_row:odd").css({"background-color":"#F8F8F8"});
    
});
function submit(node){
    
    node.submit();
}

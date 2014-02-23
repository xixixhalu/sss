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
	var c=confirm("Are you sure?");
	if(c==true)	{
		e.submit();
	}
}

function submit(node){
    
    node.submit();
}

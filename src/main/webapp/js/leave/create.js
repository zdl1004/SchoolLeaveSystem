var image_count = 0;
var max_image_count = 5;
function addImage(){
	if(image_count >= max_image_count){
		alert("最多只允许上传" + max_image_count + "张图片！");
		return;
	}
	image_count++;
	var line = document.createElement("div");
	var p = document.createElement("span");
	p.innerText = "图片" + image_count + "：";
	var input = document.createElement("input");
	input.type = "file";
	input.name = "image";
	line.append(p);
	line.append(input);
	document.getElementById("images").append(line);
}

function clearImages(){
	document.getElementById("images").innerHTML = "";
	image_count = 0;
}


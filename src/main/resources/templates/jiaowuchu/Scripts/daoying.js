function daoying() {
	tat = document.getElementById("userloign").innerHTML
	aaa = ""
	ja = ""
	for(i = 19, a = 20; i < 58; i++, a--) {
		ja += "<div style='overflow:hidden;width:328px;height:1px;position:absolute;top:" + (i+35) + "px;left:30px;filter:alpha(opacity=" + a + ");opacity:" + (a / 100) + "'><p class='daoying' style='margin:-" + (18 - (i - 18)) + "px 0px 0px 0px'>" + tat + "</p></div>"
	}
	userloign.innerHTML += ja
}
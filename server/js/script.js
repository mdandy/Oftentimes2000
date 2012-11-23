// JavaScript Document

function init_main_page()
{
	check_authentication();
	$("#active_user").html(sessionStorage.username);
}

function check_authentication()
{
	if (!sessionStorage.username)
		window.location.href = "login.html";
	
	if (sessionStorage.username == "")
		window.location.href = "login.html";
}

function logout()
{
	sessionStorage.username = "";
	window.location.href = "login.html";
}

function format_announcement_form(index) 
{
	if (index == 1) 
	{
		$("#advertisement").show();
		$("#public_service_announcement").hide();
		$("#event").hide();
	} 
	else if (index == 2) 
	{
		$("#advertisement").hide();
		$("#public_service_announcement").show();
		$("#event").hide();
	} 
	else if (index == 3) 
	{
		$("#advertisement").hide();
		$("#public_service_announcement").hide();
		$("#event").show();
	} 
	else 
	{
		$("#advertisement").hide();
		$("#public_service_announcement").hide();
		$("#event").hide();
	}
}
// JavaScript Document

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

function init_main_page()
{
	check_authentication();
	$("#active_user").html(sessionStorage.username);
}

function init_account_page()
{
	AJAXManager.get_profile(init_profile);
}

function init_profile(profile)
{
	var form = document.forms["profile_form"];
	form.name.value = profile.name;
	form.street_address.value = profile.street_address;
	form.city.value = profile.city;
	form.state.value = profile.state;
	form.zipcode.value = profile.zipcode;
	form.website.value = profile.website;
	form.email.value = profile.email;
	form.about.value = profile.about;
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